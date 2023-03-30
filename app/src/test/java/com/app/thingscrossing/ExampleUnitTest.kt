package com.app.thingscrossing

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.util.Currency

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {
        print(Currency.getAvailableCurrencies()
            .sortedBy { it.displayName }
            .map { "${it.displayName} ${it.defaultFractionDigits}" })

    }
}