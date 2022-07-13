package com.tarikmedjber.spacexclient

import com.tarikmedjber.spacexclient.engine.models.CompanyInfo
import com.tarikmedjber.spacexclient.engine.models.Launches
import com.tarikmedjber.spacexclient.engine.network.*
import com.tarikmedjber.spacexclient.engine.repositories.SpaceXRepository
import com.tarikmedjber.spacexclient.engine.repositories.SpaceXRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SpaceXRepositoryTests {

    private lateinit var spaceXRepository: SpaceXRepository;
    private val requestExecutor: RequestExecutor = spyk(RequestExecutorImpl())
    private val companyInfoService: CompanyInfoService = mockk()
    private val launchesService: LaunchesService = mockk()

    private val companyInfo: CompanyInfo = mockk()
    private val launches: List<Launches> = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        spaceXRepository =
            SpaceXRepositoryImpl(companyInfoService, launchesService, requestExecutor)
    }

    @Test
    fun `Get company info should emit on success`() = runTest {
        // Given get company info returns a successful response
        coEvery { companyInfoService.getCompanyInfo() } returns Response.success(
            companyInfo
        )
        // When get company info is called
        val requestStatesActual: List<RequestState<CompanyInfo>> =
            spaceXRepository.getCompanyInfo().toList()

        // Then I should see the request was in a Loading state
        assert(requestStatesActual.first() is RequestState.Loading)
        // And finally was in a Success state
        assert(requestStatesActual.last() is RequestState.Success)
    }

    @Test
    fun `Get company info should emit on failure`() = runTest {
        // Given get company info returns an error response
        coEvery { companyInfoService.getCompanyInfo() } returns Response.error(
            401, mockk(relaxed = true)
        )
        // When get company info is called
        val requestStatesActual: List<RequestState<CompanyInfo>> =
            spaceXRepository.getCompanyInfo().toList()

        // Then I should see the request was in a Loading state
        assert(requestStatesActual.first() is RequestState.Loading)
        // And finally was in a Failure state
        assert(requestStatesActual.last() is RequestState.Failure)
    }

    @Test
    fun `Get all launches should emit on success`() = runTest {
        // Given get launches returns a successful response
        coEvery { launchesService.getLaunches() } returns Response.success(
            launches
        )
        // When get launches is called
        val requestStatesActual: List<RequestState<List<Launches>>> =
            spaceXRepository.getLaunches().toList()

        // Then I should see the request was in a Loading state
        assert(requestStatesActual.first() is RequestState.Loading)
        // And finally was in a Success state
        assert(requestStatesActual.last() is RequestState.Success)
    }

    @Test
    fun `Get all launches should emit on failure`() = runTest {
        // Given get launches returns an error response
        coEvery { launchesService.getLaunches() } returns Response.error(
            401, mockk(relaxed = true)
        )
        // When get launches is called
        val requestStatesActual: List<RequestState<List<Launches>>> =
            spaceXRepository.getLaunches().toList()

        // Then I should see the request was in a Loading state
        assert(requestStatesActual.first() is RequestState.Loading)
        // And finally was in a Failure state
        assert(requestStatesActual.last() is RequestState.Failure)
    }

}