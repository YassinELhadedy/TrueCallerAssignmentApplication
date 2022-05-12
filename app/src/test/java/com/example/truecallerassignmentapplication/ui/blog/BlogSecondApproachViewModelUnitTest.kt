package com.example.truecallerassignmentapplication.ui.blog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.exa.nanashopper.domain.AndExpr
import com.exa.nanashopper.domain.Condition
import com.exa.nanashopper.domain.Operator
import com.exa.nanashopper.domain.Pagination
import com.example.truecallerassignmentapplication.domain.service.Blogs
import com.example.truecallerassignmentapplication.infrastructure.BlogDataSource
import com.example.truecallerassignmentapplication.ui.util.state.Resource
import com.example.truecallerassignmentapplication.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BlogSecondApproachViewModelUnitTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var blogService: Blogs

    @Mock
    private lateinit var apiBlogsObserver: Observer<Resource<Any>?>

    private val expr1 = AndExpr(
        Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG),
        Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG)
    )

    private val inputData =Pagination(AndExpr(expr1, Condition("END_POINT", Operator.Equal, BlogDataSource.GET_TRUE_CALLER_BLOG)))

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {

            //Arrange
            Mockito.doReturn(
                flowOf("TRUE/**sp**/CALLER/**sp**/BLOG/**sp**/RESPONSE")
            )
                .`when`(blogService)
                .getBlogs(inputData)

            val viewModel = BlogSecondApproachViewModel(blogService)

            //Action
            viewModel.fetchBlogsParallelSecondApproach(inputData)

            //Result
            viewModel.blogData.observeForever(apiBlogsObserver)
            Mockito.verify(blogService).getBlogs(inputData)
            Mockito.verify(apiBlogsObserver).onChanged(
                Resource.Success(listOf("TRUE", "CALLER", "BLOG", "RESPONSE"))
            )
            viewModel.blogData.removeObserver(apiBlogsObserver)
        }
    }
}