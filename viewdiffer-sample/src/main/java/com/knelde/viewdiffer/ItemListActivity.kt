package com.knelde.viewdiffer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.knelde.viewdiffer.ItemListActivity.SimpleItemRecyclerViewAdapter.Companion.EXTRA_ITEM_ID
import com.knelde.viewdiffer.differs.MyCheckBoxDiffer
import com.knelde.viewdiffer.data.DummyContent
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        setupRecyclerView(item_list)

        container.setTextViewDiffer {
            pulse(it)
            highlight(it)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DETAILS_REQUEST_CODE -> {
                val id = data?.getIntExtra(EXTRA_ITEM_ID, -1)
                id?.let {
                    val pos = it - 1
//                     It's beyond me why this toggles the most recent View value and therefore causes {@TextWatcher#onBeforeTextChanged() to have previous item's value
//                    item_list.adapter?.notifyItemChanged(pos)
                    //https://github.com/acervenky/miuiaod/blob/master/smali/android/support/v7/widget/RecyclerView%24AdapterDataObservable.smali#L121
                    //https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter#notifyItemChanged(int,%20java.lang.Object)
                    item_list.adapter?.notifyItemChanged(pos, "")
                    title_text_view.text = "Item #$it was selected"
                }

            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, false)

        recyclerView.adapter = adapter
        recyclerView.clipChildren = false
        recyclerView.clipToPadding = false
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val values: List<DummyContent.DummyItem>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        companion object {
            const val EXTRA_ITEM_ID = "extra_item_id"
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.bind(item)
        }

        override fun getItemId(position: Int): Long {
            return values[position].id.toLong()
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            init {
                val textWatcher = TextViewDiffer {
                    pulse(view)
                    highlight(view)
                }
                textWatcher.set(view.details)

                val checkBoxDiffer =
                    MyCheckBoxDiffer {
                        pulse(it)
                        highlight(it)
                    }

                checkBoxDiffer.set(view.checkbox)
            }

            fun bind(item: DummyContent.DummyItem) {
                view.id_text.text = item.id.toString()
                view.details.text = item.details
                view.checkbox.isChecked = item.checked

                view.setOnClickListener {
                    val intent = android.content.Intent(
                        it.context,
                        ItemDetailActivity::class.java
                    )
                    intent.putExtra(EXTRA_ITEM_ID, item.id)

                    ActivityCompat.startActivityForResult(
                        parentActivity,
                        intent,
                        DETAILS_REQUEST_CODE,
                        null
                    )
                }
            }
        }
    }

    companion object {
        const val DETAILS_REQUEST_CODE = 1007
    }
}
