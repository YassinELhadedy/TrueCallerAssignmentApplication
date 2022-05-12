package com.example.truecallerassignmentapplication.infrastructure

import com.example.truecallerassignmentapplication.domain.model.Blog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

private const val DATA_ERROR = "API GET-WAY INVALID IN BlogDataSource"

@RunWith(ParameterizedRobolectricTestRunner::class)
@Config(sdk = [27])
// parameters declared in the params() are injected through the constructor to the test class
class GetRepoUnitTest(private val param: SetupTestParameter<*>) {

    @Test
    fun testGetWithSuccessFilterFromRepository() {
        // parameter use in a test
        runBlocking {
            val testParameter = param.setup()

            testParameter.getNormalIDs().map {
                val triple = testParameter.getAllWithNormalIDs(it)
                Triple(triple.first, triple.second, triple.third)
            }.forEach {
                assertEquals(it.first, it.third)
            }
        }
    }

    @Test
    fun testGetWithFaultyFilterFromRepository() {
        runBlocking {
            val testParameter = param.setup()

            try {
                testParameter.getFaultyIDs().map {
                    val triple = testParameter.getAllWithFaultyIDs(it)
                    Triple(triple.first, triple.second, triple.third)
                }
            } catch (e: Throwable) {
                assertEquals(
                    e.message,
                   DATA_ERROR
                )
                Assert.assertTrue(e is InfrastructureException)
            }
        }
    }

    companion object {
        @JvmStatic
        // name argument is optional, it will show up on the test results
        @ParameterizedRobolectricTestRunner.Parameters(name = "Input: {0}")
        // parameters are provided as arrays, allowing more than one parameter
         fun params() = listOf<Any>(    object : SetupTestParameter<Blog> {
            override fun setup(): TestParameter<Blog> {
                val trueCallerWebService = Mockito.mock(TrueCallerWebService::class.java)
                val blogs = listOf(
                    Blog("TrueCallerBlog"),
                    Blog("FaceBookBlog")
                )
                val filterNormalMap = hashMapOf(Pair(BlogDataSource.GET_TRUE_CALLER_BLOG,null) to blogs[0], Pair(BlogDataSource.GET_FACE_BOOK_BLOG,null) to blogs[1])
                val filterFaultMap = hashMapOf<BlogGetApiComponent, Throwable>(Pair(BlogDataSource.GET_TWITTER_BLOG,null) to RuntimeException(DATA_ERROR))


                return object : TestParameter<Blog> {
                    override suspend fun getNormalIDs(): Set<BlogGetApiComponent> = filterNormalMap.keys

                    override suspend fun getAllWithNormalIDs(id: BlogGetApiComponent): Triple<Blog, BlogGetApiComponent, Blog> {
                        Mockito.doReturn(
                            filterNormalMap[id]?.content
                        )
                            .`when`(trueCallerWebService)
                            .getTrueCallerBlogResponse()//ArgumentMatchers.anyInt()
                        val blogsRepo = BlogsRepo(trueCallerWebService)

                        return Triple(
                            blogsRepo.get(id).first(),//id
                            id,
                            filterNormalMap[id]!!
                        )
                    }

                    override suspend fun getFaultyIDs(): Set<BlogGetApiComponent> = filterFaultMap.keys

                    override suspend fun getAllWithFaultyIDs(id: BlogGetApiComponent): Triple<Blog, BlogGetApiComponent, Throwable> {
                        Mockito.doReturn(
                            null
                        )
                            .`when`(trueCallerWebService)
                            .getTrueCallerBlogResponse()
                        val blogsRepo = BlogsRepo(trueCallerWebService)

                        return Triple(
                            blogsRepo.get(id).first(),
                            id,
                            filterFaultMap[id]!!
                        )
                    }

                }
            }

            override fun toString(): String =
                BlogsRepo::class.java.simpleName
        } )
    }


    interface TestParameter<out T> {
        suspend fun getNormalIDs(): Set<BlogGetApiComponent>
        suspend fun getAllWithNormalIDs(id: BlogGetApiComponent): Triple<T, BlogGetApiComponent, T>
        suspend fun getFaultyIDs(): Set<BlogGetApiComponent>
        suspend fun getAllWithFaultyIDs(id: BlogGetApiComponent): Triple<T, BlogGetApiComponent, Throwable>
    }

    interface SetupTestParameter<out T> {
        fun setup(): TestParameter<T>
    }
}