package com.knelde.viewdiffer

import android.view.ViewGroup
import android.widget.TextView

///**
// * Sets {@link ViewDiffer} to the {@link TextView}
// */
//fun TextView.setViewDiffer(differ: TextViewDiffer) {
//    this.addTextChangedListener(differ)
//}

/**
 * Recursively sets {@link TextViewDiffer} to all {@link TextView}s in the layout
 */
fun ViewGroup.setTextViewDiffer(action: (view: TextView) -> Unit) {
    recursiveTextViewDiffer(this, action)
}

private fun recursiveTextViewDiffer(parent: ViewGroup, onDiffAction: (view: TextView) -> Unit) {
    for (i in 0 until parent.childCount) {
        val childView = parent.getChildAt(i)
        if (childView is ViewGroup) {
            recursiveTextViewDiffer(childView, onDiffAction)
        } else {
            if (childView is TextView) {
                // this actions should go into some worker thread queue. rather than firing off animators at the same time we can reuse single animator for multiple view. might be more efficient.
                val textWatcher =
                    TextViewDiffer(onDiffAction)
                textWatcher.set(childView)
            }
        }
    }
}
