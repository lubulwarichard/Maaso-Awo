package net.unique.awo.base

import android.content.Context

/**
 * Base view any view must implement.
 */
public interface BaseView {
    /**
     * Returns the context in which the application is running.
     * @return the context in which the application is running
     */
    fun getContext() : Context

}