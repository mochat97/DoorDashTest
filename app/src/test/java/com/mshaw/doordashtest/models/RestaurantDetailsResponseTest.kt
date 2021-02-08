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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class RestaurantDetailsResponseTest : TestCase() {
    private var restaurantDetailsResponse: RestaurantDetailsResponse? = null
    private var restaurantDetailsResponseSpy: RestaurantDetailsResponse? = null
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    @Before
    public override fun setUp() {
        super.setUp()
        restaurantDetailsResponse = try {
            val stream = javaClass.getResourceAsStream("/restaurant_details_response.json") ?: return
            val reader = JsonReader.of(Buffer().readFrom(stream))
            moshi.adapter(RestaurantDetailsResponse::class.java).fromJson(reader)
        } catch (e: Exception) {
            null
        }

        restaurantDetailsResponseSpy = Mockito.spy(restaurantDetailsResponse)
    }

    @Test
    fun shouldFormatAddress() {
        val correctFormatting = """
            711 Stanford Shopping Center
            Palo Alto, CA 94304
        """.trimIndent()
        assert(restaurantDetailsResponse?.addressFormatted == correctFormatting)
    }

    @Test
    fun shouldFormatDeliveryFee() {
        assert(restaurantDetailsResponse?.deliveryFeeFormatted == "Delivery fee: $1.99")
    }

    @Test
    fun shouldFormatDeliveryTime() {
        assert(restaurantDetailsResponse?.deliveryTimeFormatted == "Delivery time: 44 - 54 mins")
    }

    @Test
    fun shouldFormatPhoneNumber() {
        doReturn("(650) 461-4450").`when`(restaurantDetailsResponseSpy)?.formatNumber(anyString(), anyString())
        assert(restaurantDetailsResponseSpy?.phoneNumberFormatted == "(650) 461-4450")
    }
}