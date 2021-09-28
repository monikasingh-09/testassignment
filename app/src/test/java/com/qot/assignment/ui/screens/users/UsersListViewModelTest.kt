package com.qot.assignment.ui.screens.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.qot.assignment.data.Repository
import com.qot.assignment.data.models.entity.User
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: UsersListViewModel

    @MockK
    lateinit var repository: Repository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = UsersListViewModel(repository).apply {
            defaultDispatcher = testDispatcher
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


    @Test
    fun `when fetch users from cache then success`() {
        val usersList = listOf(
            User(id = "test1"),
            User(id = "test2")
        )
        coEvery { repository.getUsersFromCache() } returns MutableLiveData(usersList)

        val usersListLiveData = viewModel.observeUsersList()
        testDispatcher.advanceUntilIdle()
        usersListLiveData.value!!.size shouldBe 2
        usersListLiveData.value!![0].id shouldBe "test1"
    }

    @Test
    fun `when fetch users list from remote then success`() {
        coEvery { repository.getUsersList() } returns ""

        viewModel.errorMsg.value shouldBe null

        viewModel.getUsersList()
        testDispatcher.advanceUntilIdle()

        viewModel.errorMsg.value shouldBe ""
    }

    @Test
    fun `when fetch users list from remote then error`() {
        coEvery { repository.getUsersList() } returns "No internet"

        viewModel.errorMsg.value shouldBe null

        viewModel.getUsersList()
        testDispatcher.advanceUntilIdle()

        viewModel.errorMsg.value shouldBe "No internet"
    }
}