package com.mshaw.doordashtest.ui.restaurantlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mshaw.doordashtest.models.RestaurantListResponse
import com.mshaw.doordashtest.models.Store
import com.mshaw.doordashtest.network.restaurantlist.RestaurantListManager
import com.mshaw.doordashtest.util.AwaitResult
import com.mshaw.doordashtest.util.state.State
import com.mshaw.doordashtest.utils.TestCoroutineRule
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
import retrofit2.HttpException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class RestaurantListViewModelTest : TestCase() {
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var observer: Observer<in State<List<Store>>>

    @Mock
    private lateinit var response: Response

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var restaurantListManager: RestaurantListManager

    @Mock
    private lateinit var viewModel: RestaurantListViewModel
    private var successfulResponse: RestaurantListResponse? = null
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    @Before
    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(testDispatcher)
        viewModel = RestaurantListViewModel(restaurantListManager)
        viewModel.restaurantListLiveData.observeForever(observer)

        successfulResponse = try {
            val reader = JsonReader.of(Buffer().readFrom(javaClass.getResourceAsStream("/restaurant_list_response.json")))
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
        `when`(restaurantListManager.getRestaurantList(37.422740, -122.139956, 0, 50)).thenReturn(AwaitResult.Ok(successfulResponse!!, response))
        viewModel.fetchRestaurantList(37.422740, -122.139956, 0, 50)
        verify(observer).onChanged(State.Success(successfulResponse!!.stores))
        viewModel.restaurantListLiveData.removeObserver(observer)
    }

    @Test
    fun shouldEmitErrorState() = runBlocking {
        val exception = Exception()
        `when`(restaurantListManager.getRestaurantList(37.422740, -122.139956, 0, 50)).thenReturn(AwaitResult.Error(exception))
        viewModel.fetchRestaurantList(37.422740, -122.139956, 0, 50)
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