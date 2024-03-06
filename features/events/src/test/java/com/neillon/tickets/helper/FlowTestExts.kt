package com.neillon.tickets.helper

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.junit.Assert

data class TestCollector<T>(val job: Job, val results: MutableList<T>) {
    fun dispose() = job.cancel()
    fun clearResults() = results.clear()

    fun assertIsActive() = apply { assertTrue(job.isActive) }
    fun assertIsComplete() = apply { assertTrue(job.isCompleted) }
    fun assertValueCount(expectedCount: Int) = apply {
        assertEquals(
            expectedCount,
            results.size
        )
    }
    fun assertValueAt(index: Int, predicate: (T) -> Boolean) = apply {
        assertTrue(predicate(results[index]))
    }
    fun assertValue(predicate: (T) -> Boolean) = assertValueCount(1).assertValueAt(0, predicate)
    fun assertValuesOnly(vararg values: T) = assertIsActive().apply {
        values.forEachIndexed { index, t -> Assert.assertEquals(results[index], t) }
    }
    fun assertLastValue(predicate: (T) -> Boolean) = apply { assertTrue(predicate(results[results.size - 1])) }
}

fun <T> Flow<T>.test(coroutineScope: CoroutineScope): TestCollector<T> {
    val results = mutableListOf<T>()
    return TestCollector(coroutineScope.launch(Dispatchers.Unconfined) { collect(results::add) }, results)
}
