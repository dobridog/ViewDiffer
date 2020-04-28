package com.knelde.viewdiffer

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.knelde.viewdiffer.data.DummyContent
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * An activity representing a single Item detail screen.
 */
class ItemDetailActivity : AppCompatActivity() {

    var item: DummyContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        fab.setOnClickListener { view ->
            item?.let {
                it.checked = !it.checked
                setChecked(it.checked)
            }
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val id = intent.getIntExtra(ItemListActivity.SimpleItemRecyclerViewAdapter.EXTRA_ITEM_ID, -1)
            item = DummyContent.ITEMS[id-1]
            item?.let {
                title_edit_text.setText(it.details)
                setChecked(it.checked)
            }

        }
    }

    private fun setChecked(saved: Boolean) {
        if (saved) {
            fab.icon = resources.getDrawable(android.R.drawable.checkbox_on_background, theme)
            fab.text = getString(R.string.fab_checked_label)
        } else {
            fab.icon = resources.getDrawable(android.R.drawable.checkbox_off_background, theme)
            fab.text = getString(R.string.fab_unchecked_label)
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem) =
            when (menuItem.itemId) {
                android.R.id.home -> {
                    DummyContent.ITEMS[item?.id!!-1].details = title_edit_text.text.toString()

                    val intent = Intent()
                    intent.putExtra(ItemListActivity.SimpleItemRecyclerViewAdapter.EXTRA_ITEM_ID,
                        item?.id as Int
                    )
                    setResult(RESULT_OK, intent)
                    finish()
                    true
                }
                else -> super.onOptionsItemSelected(menuItem)
            }
}
