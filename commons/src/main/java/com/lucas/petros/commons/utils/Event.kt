package com.lucas.petros.commons.utils

/**
 * Event class to be used by ViewModel states that must observed only when there is a value change
 *
 * @property content returned by the Event
 */
class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Get content if not handled
     *
     * @return
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

}