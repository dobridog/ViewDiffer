package com.knelde.viewdiffer.differs

import android.widget.CompoundButton
import com.knelde.viewdiffer.Differ

class MyCheckBoxDiffer(override var onDiffAction: ((view: CompoundButton) -> Unit)?) : Differ<CompoundButton>, CompoundButton.OnCheckedChangeListener {

    override fun set(view: CompoundButton) {
        view.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(view: CompoundButton?, isChecked: Boolean) {
        view?.let { checkBox ->
            if (isChecked) {
                onDiffAction?.invoke(checkBox)
            }
        }
    }
}