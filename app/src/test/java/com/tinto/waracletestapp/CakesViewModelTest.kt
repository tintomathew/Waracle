package com.tinto.waracletestapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tinto.waracletestapp.cakes.CakesViewModel
import com.tinto.waracletestapp.model.CakeDataModel
import com.tinto.waracletestapp.repository.CakesRepository
import com.tinto.waracletestapp.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CakesViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    var cakesRepository: CakesRepository = mockk()

    // class under test
    private lateinit var viewModel: CakesViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
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
    fun filteredCakeResponseTest() {
        val firstItem = CakeDataModel(
            title = "Banana cake",
            desc = "Donkey kongs favourite",
            image = "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg"
        )
        val response = listOf(
            CakeDataModel(
                title ="victoria sponge",
                desc = "sponge with jam",
                image = "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg"
        ),
            firstItem
        )
        coEvery { cakesRepository.getSearchedImage() } returns Resource.Success(response)
        coroutinesTestRule.testDispatcher.run {
            viewModel.getCakesData()
            Assert.assertEquals(
                "filteredCakeResponse test failed" + viewModel.response.value?.size,
                viewModel.response.value?.get(0),
                firstItem
            )
        }
    }

    @Test
    fun cakeErrorResponseTest() {
        coEvery { cakesRepository.getSearchedImage() } returns Resource.Error("")
        coroutinesTestRule.testDispatcher.run {
            viewModel.getCakesData()
            Assert.assertEquals(
                "cakeErrorResponse test failed" + viewModel.networkError.value,
                "Error in fetching data",
                viewModel.networkError.value
            )
        }
    }
}