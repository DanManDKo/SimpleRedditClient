package com.reddit.data.common.storage

import com.reddit.data.common.cache.CachePolicy
import com.reddit.data.common.cache.CachedEntry
import com.reddit.data.common.cache.ObservableLruCache
import com.reddit.data.common.cache.Page
import com.reddit.domain.model.PageBundle
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.ConcurrentHashMap

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-18
 * Time: 20:54
 */
class MemoryPagedListStorage<Query, Entity>
private constructor(
    max: Int,
    private val limit: Int,
    private val cachePolicy: CachePolicy,
    private val fetcher: ((Params<Query, Entity>) -> Single<FetchResult<Entity>>)
) {

    private val cache: ObservableLruCache<Query, CachedEntry<Page<Entity>>> =
        ObservableLruCache(max)

    private val updateSubject = PublishSubject.create<Query>()
    private val fetchMap = ConcurrentHashMap<Query, Observable<Any>>()

    operator fun get(query: Query): Observable<PageBundle<Entity>> {
        val objectObservable = cache[query]
            .filter { cachePolicy.test(it) }
            .map<Page<Entity>> { it.entry }
            .map<PageBundle<Entity>> { PageBundle(it.getDataList(), it.hasNext, it.maxCount) }
            .toObservable()
        return objectObservable.repeatWhen { updateSubject.filter { q -> query == q } }
            .mergeWith(fetchIfExpired(query))
    }

    fun update(
        filter: (Entity) -> Boolean,
        onUpdateCallback: (Entity) -> Entity
    )
            : Completable {
        return cache.get()
            .concatMapCompletable { cacheEntity ->
                Completable.fromAction {
                    val page = cacheEntity.value.entry
                    var changed = false
                    for (index in page.getDataList().indices) {
                        val entity = page.getDataList()[index]
                        if (filter.invoke(entity)) {
                            val newEntity = onUpdateCallback.invoke(entity)
                            page.replace(index, newEntity)
                            changed = true
                        }
                    }
                    if (changed) {
                        cache.put(cacheEntity.key, CachePolicy.createEntry(page))
                        updateSubject.onNext(cacheEntity.key)
                    }
                }
            }
    }

    fun remove(query: Query, filter: (Entity) -> Boolean)
            : Completable {
        return cache[query]
            .flatMapCompletable { cacheEntity ->
                removeEntity(cacheEntity, query, filter)
            }
    }

    fun remove(filter: (Entity) -> Boolean): Completable {
        return cache.get()
            .concatMapCompletable { cacheEntity ->
                removeEntity(cacheEntity.value, cacheEntity.key, filter)
            }
    }

    private fun removeEntity(
        cacheEntity: CachedEntry<Page<Entity>>, query: Query,
        filter: (Entity) -> Boolean
    ): Completable {
        return Completable.fromAction {
            val page = cacheEntity.entry
            var changed = false
            for (index in page.getDataList().size - 1 downTo 0) {
                val entity = page.getDataList()[index]
                if (filter.invoke(entity)) {
                    page.remove(index)
                    changed = true
                }
            }
            if (changed) {
                cache.put(query, CachePolicy.createEntry(page))
                updateSubject.onNext(query)
            }
        }
    }

    private fun fetchIfExpired(query: Query): Observable<PageBundle<Entity>> {
        return cache[query]
            .map { cachedEntry -> !cachePolicy.test(cachedEntry) }
            .defaultIfEmpty(true)
            .filter { expired -> expired }
            .observeOn(Schedulers.io())
            .flatMapCompletable {
                Completable.create { emitter ->
                    val exception = fetchNext(query, true).blockingGet()
                    if (exception != null && !emitter.isDisposed) {
                        emitter.onError(exception)
                    }
                    emitter.onComplete()
                }
            }.toObservable()
    }

    private fun getParams(refresh: Boolean, page: Page<Entity>, query: Query)
            : Params<Query, Entity> {
        return if (refresh)
            Params(query, 0, limit, null, page.getPage(refresh))
        else
            Params(query, page.size(), limit, page.lastObject, page.getPage(refresh))
    }

    fun fetchNext(query: Query, refresh: Boolean = false): Completable {
        var observable: Observable<Any>? = fetchMap[query]
        if (observable == null) {
            observable = cache[query]
                .defaultIfEmpty(CachePolicy.createEntry(Page()))
                .map<Page<Entity>> { it.entry }
                .flatMapObservable { page ->
                    fetch(getParams(refresh, page, query))
                        .doOnSuccess { result ->
                            if (refresh) page.clean()
                            page.addResult(result.data)
                            page.hasNext = result.hasNext
                            page.maxCount = result.maxCount
                            cache.put(query, CachePolicy.createEntry(page))
                            updateSubject.onNext(query)
                        }
                        .toObservable()
                }
            observable = observable
                .doOnTerminate { fetchMap.remove(query) }
                .doOnDispose { fetchMap.remove(query) }
                .publish()
                .refCount()
        }
        val finalObservable = observable
        observable = observable
            .doOnSubscribe { fetchMap[query] = finalObservable }
        return observable!!.ignoreElements()
    }

    fun refresh(query: Query): Completable {
        return fetchNext(query, true)
    }

    private fun fetch(params: Params<Query, Entity>): Single<FetchResult<Entity>> {
        return fetcher.invoke(params)
    }

    class Builder<Query, Entity>(
        private val fetcher: ((Params<Query, Entity>) -> Single<FetchResult<Entity>>)
    ) {

        private var max = 50
        private var limit = 10
        private var cachePolicy: CachePolicy? = null

        fun capacity(max: Int): Builder<Query, Entity> {
            this.max = max
            return this
        }

        fun limit(limit: Int): Builder<Query, Entity> {
            this.limit = limit
            return this
        }

        fun cachePolicy(cachePolicy: CachePolicy): Builder<Query, Entity> {
            this.cachePolicy = cachePolicy
            return this
        }

        fun build(): MemoryPagedListStorage<Query, Entity> {
            if (cachePolicy == null) {
                cachePolicy = CachePolicy.infinite()
            }
            return MemoryPagedListStorage(
                max,
                limit,
                cachePolicy!!,
                fetcher
            )
        }
    }

    data class Params<Query, Entity> internal constructor(
        var query: Query,
        var size: Int,
        var limit: Int,
        var entity: Entity?,
        var page: Int
    )

    data class FetchResult<E>(
        val data: List<E>,
        val hasNext: Boolean,
        val maxCount: Int = -1
    )
}