package com.reddit.presentation.feature

import com.reddit.presentation.common.BaseViewModel
import com.reddit.presentation.common.error.DefaultErrorHandler
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val errorHandler: DefaultErrorHandler
) : BaseViewModel() {

//    var contentState = MutableLiveData<ContentState>()
//    var refreshing = MutableLiveData<Boolean>()
//    var content = MutableLiveData<List<Post>>()
//
//    var filterClickedEvent = SingleLiveEvent<Unit>()
//    var postClickedEvent = SingleLiveEvent<Post>()
//    var shareClickedEvent = SingleLiveEvent<Post>()
//
//    var hasNext = MutableLiveData<Boolean>()
//    var loading = MutableLiveData<Boolean>()
//
//    private lateinit var filter: Period
//
//    init {
//        observeFilter()
//    }
//
//    private fun observeFilter() {
//        RxDisposable.manage(
//                this, "observeFilter",
//                redditInteractor.observeFilter()
//                        .distinctUntilChanged()
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({
//                            filter = it
//                            observeTopList()
//                        }, { error -> errorHandler.handleError(error) })
//        )
//    }
//
//    private fun observeTopList() {
//        RxDisposable.manage(this, "topList",
//                redditInteractor.getTopList(filter)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe { contentState.setValue(ContentState.LOADING) }
//                        .doOnError { contentState.setValue(ContentState.ERROR) }
//                        .doOnNext {
//                            if (it.data.isEmpty()) contentState.value = ContentState.EMPTY
//                            else contentState.value = ContentState.CONTENT
//                        }
//                        .subscribe({
//                            content.value = it.data
//                            hasNext.value = it.hasNext
//                        }, ({
//                            errorHandler.handleError(it) { message ->
//                                errorMessage.setValue(message) }
//                        }))
//        )
//    }
//
//    fun fetchNext() {
//        RxDisposable.manage(this, "fetchNext",
//                redditInteractor.fetchNextList(filter)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe { loading.value = true }
//                        .doOnTerminate { loading.value = false }
//                        .subscribe({}, ({
//                            errorHandler.handleError(it) { message ->
//                                errorMessage.setValue(message)
//                            }
//                        }))
//        )
//    }
//
//    fun onRefresh() {
//        RxDisposable.manage(this, "refresh",
//                redditInteractor.refresh(filter)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe { refreshing.value = true }
//                        .subscribe({
//                            refreshing.value = false
//                        }, ({
//                            errorHandler.handleError(it) { message ->
//                                errorMessage.setValue(message)
//                            }
//                        })))
//    }
//
//    fun onRetryClicked() {
//        observeTopList()
//    }
//
//    fun onFilterClickedEvent() {
//        filterClickedEvent.call()
//    }
//
//    fun onPostClicked(post: Post) {
//        postClickedEvent.value = post
//    }
//
//    fun onShareClicked(post: Post) {
//        shareClickedEvent.value = post
//    }

}