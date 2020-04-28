package com.knelde.viewdiffer

import android.view.View

interface Differ<T : View> {

    /**
     * Action that will be triggered when the view gets a different value
     */
    var onDiffAction: ((view: T) -> Unit)?

    /**
     * Sets the view that will check diff on
     *
     * @param view the view
     */
    fun set(view: T)

}