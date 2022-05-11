package com.example.truecallerassignmentapplication

import okhttp3.internal.filterList
import org.junit.Test

import org.junit.Assert.*
import java.util.regex.Pattern
import java.util.stream.Collectors

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val st = "Iii*45*67*iii"


        val list1 = st.chunked(2)
        val list = st.chunked(2).map {
           it[0]
       }

        val list3 = st.toLowerCase().split("*").groupBy { it }

        val ss= list3.map { "${it.key}=${it.value.size}" }.joinToString("/--/")
        assertEquals(list3, ss)
    }
}