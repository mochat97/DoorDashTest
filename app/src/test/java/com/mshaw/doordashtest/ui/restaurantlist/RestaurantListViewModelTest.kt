package com.mshaw.doordashtest.ui.restaurantlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mshaw.doordashtest.models.RestaurantListResponse
import com.mshaw.doordashtest.network.restaurant.RestaurantManager
import com.mshaw.doordashtest.util.state.AwaitResult
import com.mshaw.doordashtest.util.state.State
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.Response
import okio.Buffer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class RestaurantListViewModelTest : TestCase() {
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var observer: Observer<in State<RestaurantListResponse>>

    @Mock
    private lateinit var response: Response

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var restaurantManager: RestaurantManager

    @Mock
    private lateinit var viewModel: RestaurantListViewModel
    private var successfulResponse: RestaurantListResponse? = null
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    private val lat = 37.422740
    private val lng = -122.139956
    private val offset = 0
    private val limit = 50

    @Before
    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(testDispatcher)
        viewModel = RestaurantListViewModel(restaurantManager)
        viewModel.restaurantListLiveData.observeForever(observer)

        successfulResponse = try {
            val stream = javaClass.getResourceAsStream("/restaurant_list_response.json") ?: return
            val reader = JsonReader.of(Buffer().readFrom(stream))
            moshi.adapter(RestaurantListResponse::class.java).fromJson(reader)
        } catch (e: Exception) {
            null
        }
    }

    @Test
    fun shouldParseJsonAsResponse() {
        val successfulResponse = successfulResponse
        if (successfulResponse == null) {
            fail("successResponse is null")
            return
        }

        assert(successfulResponse.numResults == 527)
        assert(successfulResponse.stores.isNotEmpty())
        assert(successfulResponse.stores.size == 50)
    }

    @Test
    fun shouldEmitSuccessState() = runBlocking {
        val successfulResponse = successfulResponse
        if (successfulResponse == null) {
            fail("successResponse is null")
            return@runBlocking
        }

        `when`(restaurantManager.getRestaurantList(lat, lng, offset, limit)).thenReturn(AwaitResult.Ok(successfulResponse, response))

        viewModel.fetchRestaurantList(lat, lng, offset, limit)
        verify(observer).onChanged(State.Success(successfulResponse))
        viewModel.restaurantListLiveData.removeObserver(observer)
    }

    @Test
    fun shouldEmitErrorState() = runBlocking {
        val exception = Exception()
        `when`(restaurantManager.getRestaurantList(lat, lng, offset, limit)).thenReturn(AwaitResult.Error(exception))

        viewModel.fetchRestaurantList(lat, lng, offset, limit)
        verify(observer).onChanged(State.Error(exception))
        viewModel.restaurantListLiveData.removeObserver(observer)
    }

    @After
    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}