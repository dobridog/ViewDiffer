package com.knelde.viewdiffer.data

import java.util.*

/**
 * Helper class for providing sample content
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    private const val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
    }

    private fun createDummyItem(position: Int): DummyItem {
        return DummyItem(position, "Item $position", false)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: Int, var details: String, var checked: Boolean) {
        override fun toString(): String = details
    }
}
