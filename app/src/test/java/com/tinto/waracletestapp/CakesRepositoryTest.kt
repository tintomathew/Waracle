package com.tinto.waracletestapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tinto.waracletestapp.model.CakeDataModel
import com.tinto.waracletestapp.repository.CakesRepository
import com.tinto.waracletestapp.service.CakesApiService
import com.tinto.waracletestapp.util.Resource
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class CakesRepositoryTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK
    var cakesApiService: CakesApiService = mockk()

    // class under test
    lateinit var cakesRepository: CakesRepository

    @Before
    fun setup() {
        cakesRepository = CakesRepository(cakesApiService)
    }

    @Test
    fun testCakeSuccessTest() {
        val bananaModel = CakeDataModel(
            title = "Banana cake",
            desc = "Donkey kongs favourite",
            image = "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg"
        )
        coEvery { cakesApiService.getSearchedImage() } returns Response.success(listOf(bananaModel))
        runBlocking {
            val response = cakesRepository.getSearchedImage()
            Assert.assertEquals("testCakeSuccess test failed", bananaModel, response.data?.get(0))
        }
    }

    @Test
    fun testCakeErrorTest() {
        coEvery { cakesApiService.getSearchedImage() } returns Response.error(
            400,
            "{\"key\":[\"error\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        runBlocking {
            val response = cakesRepository.getSearchedImage()
            Assert.assertTrue("testCakeSuccess test failed", response is Resource.Error)
        }
    }
}