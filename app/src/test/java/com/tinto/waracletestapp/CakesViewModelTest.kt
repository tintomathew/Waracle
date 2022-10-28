package com.tinto.waracletestapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tinto.waracletestapp.cakes.CakesViewModel
import com.tinto.waracletestapp.model.CakeDataModel
import com.tinto.waracletestapp.repository.CakesRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CakesViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiUsersObserver: Observer<List<CakeDataModel>>

    @MockK
    var cakesRepository: CakesRepository = mockk()

    // class under test
    private lateinit var viewModel: CakesViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CakesViewModel(cakesRepository)
    }

    @Test
    fun showProgressTest() {
        Assert.assertTrue(
            "progress indicator test failed",
            viewModel.isLoading.value == true
        )
    }

    @Test
    fun testNetworkErrorTest() {
        viewModel.networkError.postValue("")
        Assert.assertNotNull(
            "network error test failed",
            viewModel.networkError.value
        )
    }
    // TODO add test case for the live data
}