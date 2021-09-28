package com.qot.assignment.ui.screens.profile

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.qot.assignment.data.Repository
import com.qot.assignment.data.models.entity.User
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class UserProfileViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: UserProfileViewModel

    @MockK
    lateinit var repository: Repository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = UserProfileViewModel(repository).apply {
            defaultDispatcher = testDispatcher
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


    @Test
    fun `when user param is valid then success`() {
        val intent = mockk<Intent>()
        every { intent.getParcelableExtra<User>("user") } returns User(
            "testId",
            "testName"
        )

        val value = viewModel.initArgs(intent)

        value shouldBe true
        viewModel.userId shouldBe "testId"
    }

    @Test
    fun `when user param is invalid then error`() {
        val intent = mockk<Intent>()
        every { intent.getParcelableExtra<User>("user") } returns null

        val value = viewModel.initArgs(intent)

        value shouldBe false
        viewModel.userId shouldBe ""
    }

    @Test
    fun `when get user detail from cache then success`() {
        val user = User(
            "testId",
            "Miss",
            "Monika",
            "Singh",
            "testPic",
            "Female",
            "test@email.com",
            "2323",
            "343434"
        )

        coEvery { repository.getCachedUserInfo("testId") } returns MutableLiveData(user)
        viewModel.userId = "testId"

        val usersListLiveData = viewModel.userInfoObserver()
        testDispatcher.advanceUntilIdle()

        usersListLiveData.value shouldBe user
    }

    @Test
    fun `when get user detail from api then success`() {
        viewModel.userId = "testId"
        coEvery { repository.getUserInfo("testId") } just runs

        viewModel.fetchUserInfo()
        testDispatcher.advanceUntilIdle()

        coVerify { repository.getUserInfo("testId") }
    }

}