package com.tarikmedjber.spacexclient

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tarikmedjber.spacexclient.engine.models.CompanyInfo
import com.tarikmedjber.spacexclient.engine.models.Launches
import com.tarikmedjber.spacexclient.engine.network.RequestState
import com.tarikmedjber.spacexclient.engine.repositories.SpaceXRepository
import com.tarikmedjber.spacexclient.ui.FilteredOptions
import com.tarikmedjber.spacexclient.ui.HomeViewModel
import com.tarikmedjber.spacexclient.ui.HomeViewModelImpl
import com.tarikmedjber.spacexclient.ui.OrderType
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var homeViewModel: HomeViewModel

    private val spaceXRepository: SpaceXRepository = mockk()

    private val companyInfo: CompanyInfo = mockk()
    private val launches: List<Launches> = mockk(relaxed = true)
    private val filteredOptions = FilteredOptions.LaunchSuccess(OrderType.Descending)
    private val failureMessage = "Failure Messages"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        coEvery { spaceXRepository.getCompanyInfo() } returns flow {
            emit(RequestState.Failure(failureMessage))
        }
        homeViewModel = HomeViewModelImpl(spaceXRepository)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get company info should return loading state when request is first called`() = runTest {
        // Given the server returns a loading response with company info
        coEvery { spaceXRepository.getCompanyInfo() } returns flow {
            emit(RequestState.Loading())
        }
        // And the view model calls the company info method
        homeViewModel.getCompanyInfo()

        // Then the current company info state should be of Loading
        val state = homeViewModel.companyInfoState.value
        assertEquals(HomeViewModel.State.Loading, state)
    }


    @Test
    fun `Get company info should return success state when request is successful`() = runTest {
        // Given the server returns a successful response with company info
        coEvery { spaceXRepository.getCompanyInfo() } returns flow {
            emit(RequestState.Success(companyInfo))
        }
        // And the view model calls the company info method
        homeViewModel.getCompanyInfo()

        // Then the current company info state should be of Success
        val state = homeViewModel.companyInfoState.value
        assertEquals(HomeViewModel.State.Success, state)
    }

    @Test
    fun `Get company info should return error state when request is Failure`() = runTest {
        // Given the server returns a Failure response with company info
        coEvery { spaceXRepository.getCompanyInfo() } returns flow {
            emit(RequestState.Failure(failureMessage))
        }
        // And the view model calls the company info method
        homeViewModel.getCompanyInfo()

        // Then the current company info state should be of Error
        val state = homeViewModel.companyInfoState.value
        assertEquals(HomeViewModel.State.Error, state)
    }


    @Test
    fun `Get launches should return loading state when request is loading`() = runTest {
        // Given the server returns a loading response with launches
        coEvery { spaceXRepository.getLaunches() } returns flow {
            emit(RequestState.Loading())
        }
        // And the view model calls the get launches method
        homeViewModel.getLaunches(filteredOptions)

        // Then the current launches state should be of Loading
        val state = homeViewModel.launchesState.value
        assertEquals(HomeViewModel.State.Loading, state)
    }


    @Test
    fun `Get launches should return success state when request is successful`() = runTest {
        // Given the list is not empty
        every { launches.isEmpty() } returns false

        // And the server returns a successful response with launches
        coEvery { spaceXRepository.getLaunches() } returns flow {
            emit(RequestState.Success(launches))
        }
        // And the view model calls the get launches method
        homeViewModel.getLaunches(filteredOptions)

        // Then the current launches state should be of Success
        val state = homeViewModel.launchesState.value
        assertEquals(HomeViewModel.State.Success, state)
    }

    @Test
    fun `Get launches should return error state when request is Success but empty list`() =
        runTest {
            // Given the list is empty
            every { launches.isEmpty() } returns true

            // And the server returns a successful response with launches
            coEvery { spaceXRepository.getLaunches() } returns flow {
                emit(RequestState.Success(launches))
            }
            // And the view model calls the get launches method
            homeViewModel.getLaunches(filteredOptions)

            // Then the current launches state should be of Success
            val state = homeViewModel.launchesState.value
            assertEquals(HomeViewModel.State.Error, state)
        }

    @Test
    fun `Get launches should return error state when request is Failure`() = runTest {
        // Given the server returns a failure response with launches
        coEvery { spaceXRepository.getLaunches() } returns flow {
            emit(RequestState.Failure(failureMessage))
        }
        // And the view model calls the get launches method
        homeViewModel.getLaunches(filteredOptions)

        // Then the current launches state should be of Error
        val state = homeViewModel.launchesState.value
        assertEquals(HomeViewModel.State.Error, state)
    }
}