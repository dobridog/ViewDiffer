package com.knelde.viewdiffer

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import java.lang.ref.WeakReference

/**
 * Instance based differ that specializes in {@link TextView} values. There should be one {@link TextViewDiffer} per {@link TextView} instance
 *
 * @param onDiffAction action to take when different value is set. Do not retain this {@link View} reference
 */
open class TextViewDiffer(override var onDiffAction: ((view: TextView) -> Unit)?)
    : TextWatcher, Differ<TextView> {

    private var viewRef: WeakReference<TextView> = WeakReference<TextView>(null)
    private var beforeText: String? = null

    /**
     * A difference assumes existing non-empty text was changed or removed
     * Override to change behavior
     */
    protected fun isDiff(before: String?, after: String?): Boolean {
        return !before.isNullOrEmpty() && before != after
    }

    override fun afterTextChanged(after: Editable?) {
        if (isDiff(beforeText, after?.toString())) {
            viewRef.get()?.let { view ->
                onDiffAction?.invoke(view)
            }
        }
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // no action
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        beforeText = p0.toString()
    }

    override fun set(view: TextView) {
        view.addTextChangedListener(this)
        viewRef = WeakReference(view)
    }
}