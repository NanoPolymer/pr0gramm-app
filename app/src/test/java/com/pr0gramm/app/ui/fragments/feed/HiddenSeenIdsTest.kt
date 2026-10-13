package com.pr0gramm.app.ui.fragments.feed

import kotlin.test.Test
import kotlin.test.assertEquals

class HiddenSeenIdsTest {
    @Test
    fun newItemsAreHiddenIfSeen() {
        val hidden = hiddenSeenIds(listOf(1, 2, 3), knownIds = setOf(), previousHiddenIds = setOf(), seenIds = setOf(2), keepId = null)
        assertEquals(setOf(2L), hidden)
    }

    @Test
    fun knownItemsKeepTheirState() {
        // item 1 was seen during the session, item 2 was hidden before
        val hidden = hiddenSeenIds(listOf(1, 2, 3), knownIds = setOf(1, 2), previousHiddenIds = setOf(2), seenIds = setOf(1, 2, 3), keepId = null)
        assertEquals(setOf(2L, 3L), hidden)
    }

    @Test
    fun targetItemIsNeverHidden() {
        val hidden = hiddenSeenIds(listOf(1, 2), knownIds = setOf(), previousHiddenIds = setOf(), seenIds = setOf(1, 2), keepId = 1)
        assertEquals(setOf(2L), hidden)
    }
}
