package com.reddit.presentation.common.databinding

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 14:32
 */
class OneTimeActionWithParameter<T> constructor(private val event: (T) -> Unit) {

    private var t: T? = null

    fun invoke(t: T) {
        if (t != null && this.t != null && t is Array<*>) {
            if (!(t as Array<*>).contentEquals(this.t as Array<*>)) {
                this.t = t
                event.invoke(t)
            }
        } else {
            if (this.t != t) {
                this.t = t
                event.invoke(t)
            }
        }
    }

    fun reset() {
        t = null
    }
}