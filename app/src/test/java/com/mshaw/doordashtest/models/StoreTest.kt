package com.mshaw.doordashtest.models

import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase
import okio.Buffer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime
import java.time.Month
import java.time.OffsetDateTime
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class StoreTest : TestCase() {
    private var store: Store? = null
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    @Before
    public override fun setUp() {
        super.setUp()
        store = try {
            val stream = javaClass.getResourceAsStream("/store.json") ?: return
            val reader = JsonReader.of(Buffer().readFrom(stream))
            moshi.adapter(Store::class.java).fromJson(reader)
        } catch (e: Exception) {
            null
        }
    }

    @Test
    fun shouldGetDistanceFromConsumerIfOpen() {
        val currentDate = Date.from(OffsetDateTime.parse("2021-02-03T02:45:00Z").toInstant())
        assert(store?.getDistanceFromConsumerFormatted(currentDate) == "3.0 mi")
    }

    @Test
    fun shouldShowClosedIfAfterClosedDate() {
        val currentDate = Date.from(OffsetDateTime.parse("2021-02-03T07:45:00Z").toInstant())
        assert(store?.getDistanceFromConsumerFormatted(currentDate) == "Closed")
    }

    @Test
    fun shouldShowClosedIfBeforeOpenDate() {
        val currentDate = Date.from(OffsetDateTime.parse("2021-02-02T18:45:16Z").toInstant())
        assert(store?.getDistanceFromConsumerFormatted(currentDate) == "Closed")
    }
}