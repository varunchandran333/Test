package com.example.logintest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.logintest.R
import com.example.logintest.ui.login.LoginActivity
import com.example.logintest.ui.login.LoginViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<LoginActivity>(
        LoginActivity::class.java
    )
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel = mock(LoginViewModel::class.java)
    private val correctUserEntry = "sachin@gmail.com"
    private val falseUserEntry = "sachin@"
    private val correctPassword = "password"
    private val falsePassword = "pass"

    @Test
    @Throws(Exception::class)
    fun clickLoginButton_successCase() {
        onView(withId(R.id.username))
            .perform(replaceText(""))
        onView(withId(R.id.password))
            .perform(replaceText(""))
        onView(withId(R.id.login))
            .perform(click())
        onView(withId(R.id.username))
            .perform(typeText(correctUserEntry), closeSoftKeyboard())
        onView(withId(R.id.password))
            .perform(typeText(correctPassword), closeSoftKeyboard())
        onView(withId(R.id.buttonRememberMe))
            .perform(click())
        onView(withId(R.id.login))
            .perform(click())
    }
    @Test
    @Throws(Exception::class)
    fun clickLoginButton_failurecase() {
        onView(withId(R.id.username))
            .perform(replaceText(""))
        onView(withId(R.id.password))
            .perform(replaceText(""))
        onView(withId(R.id.login))
            .perform(click())
        onView(withId(R.id.username))
            .perform(typeText(falseUserEntry), closeSoftKeyboard())
        onView(withId(R.id.password))
            .perform(typeText(falsePassword), closeSoftKeyboard())
        onView(withId(R.id.buttonRememberMe))
            .perform(click())
        onView(withId(R.id.login))
            .perform(click())
    }

    @Test
    @Throws(Exception::class)
    fun validateCorrectUsername() {
        assertTrue(viewModel.isUserNameValid(correctUserEntry))
    }

    @Test
    @Throws(Exception::class)
    fun validateErrorUsername() {
        assertFalse(viewModel.isUserNameValid(falseUserEntry))
    }

    @Test
    @Throws(Exception::class)
    fun validateCorrectPassword() {
        assertTrue(viewModel.isPasswordValid(correctPassword))
    }

    @Test
    @Throws(Exception::class)
    fun validateErrorPassword() {
        assertFalse(viewModel.isPasswordValid(falsePassword))
    }

    //Stuck with java.lang.IllegalStateException: No instrumentation registered! Must run under a registering instrumentation.
//    @Test
//    fun launchActivityTest() {
//        onView(withId(R.id.login))
//            .perform(click())
//        intended(hasComponent(LoginSuccess::class.java.name))
//    }
}