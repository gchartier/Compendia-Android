package com.gabrieldchartier.compendia.ui

// Types of content to wrap in an event
data class Loading(val isLoading: Boolean)
data class Data<T>(val data: Event<T>?, val response: Event<Response>?)
data class StateError(val response: Response)
data class Response(val message: String?, val responseType: ResponseType)

// Types of responses to send back to the activity
sealed class ResponseType {

    class Toast: ResponseType()

    class Dialog: ResponseType()

    class None: ResponseType()
}

// Data/Response wrapper that represents an event which is exposed via LiveData
open class Event<out T>(private val content: T) { // TODO understand this generic more

    var hasBeenRetrieved = false
        private set // Allow external read but not write

    // Returns the event content only if it has not already been retrieved
    fun getContentIfNotHandled(): T? {
        return if (hasBeenRetrieved) null else {
            hasBeenRetrieved = true
            content
        }
    }

    // Returns the content even if it has already been handled
    fun peekContent(): T = content

    companion object {

        // If the data passed into the event is not null, return the event, else return null
        fun <T> dataEvent(data: T?): Event<T>? {
            data?.let {
                return Event(it)
            }
            return null
        }

        // If the response passed into the event is not null, return the event, else return null
        fun responseEvent(response: Response?): Event<Response>?{
            response?.let{
                return Event(it)
            }
            return null
        }
    }

    override fun toString(): String {
        return "Event(content=$content, hasBeenRetrieved=$hasBeenRetrieved)"
    }
}