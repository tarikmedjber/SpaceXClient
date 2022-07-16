package com.tarikmedjber.spacexclient

import android.content.Context
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tarikmedjber.spacexclient.ui.HomeFragment
import com.tarikmedjber.spacexclient.ui.HomeViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    private lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_SpaceXClient)
        scenario.moveToState(Lifecycle.State.STARTED)
    }


    @Test
    fun testTitlesAreVisible() {
        onView(withId(R.id.company_title)).check(matches((isDisplayed())))
        onView(withId(R.id.launches_title)).check(matches((isDisplayed())))
    }

    @Test
    fun testCompanyInfoProgressBarIsDisplayed() {
        scenario.onFragment { fragment ->
            fragment.companyInfoObserver.onChanged(HomeViewModel.State.Loading)
        }
        onView(withId(R.id.company_info_progress_bar)).check(matches((isDisplayed())))
    }

    @Test
    fun testLaunchesProgressBarIsDisplayed() {
        scenario.onFragment { fragment ->
            fragment.launchesObserver.onChanged(HomeViewModel.State.Loading)
        }
        onView(withId(R.id.launches_progress_bar)).check(matches((isDisplayed())))
    }

    @Test
    fun testDialogIsShownWhenCompanyInfoIsError() {
        scenario.onFragment { fragment ->
            fragment.companyInfoObserver.onChanged(HomeViewModel.State.Error)
        }
        onView(withText(R.string.company_info_error_text)).check(matches(isDisplayed()))
        onView(withText(R.string.try_again)).check(matches(isDisplayed()))
        onView(withText(R.string.cancel))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testDialogIsShownWhenLaunchesIsError() {
        scenario.onFragment { fragment ->
            fragment.launchesObserver.onChanged(HomeViewModel.State.Error)
        }
        onView(withText(R.string.launches_error_text)).check(matches(isDisplayed()))
        onView(withText(R.string.try_again)).check(matches(isDisplayed()))
        onView(withText(R.string.cancel))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCompanyInfoIsDisplayedWhenSuccessError() {
        scenario.onFragment { fragment ->
            fragment.companyInfoObserver.onChanged(HomeViewModel.State.Success)
        }
        onView(withId(R.id.company_info_text)).check(matches(isDisplayed()))
    }

    @Test
    fun testLaunchesListIsDisplayedWhenSuccessError() {
        scenario.onFragment { fragment ->
            fragment.launchesObserver.onChanged(HomeViewModel.State.Success)
        }
        onView(withId(R.id.launches_list)).check(matches(isDisplayed()))
    }
}