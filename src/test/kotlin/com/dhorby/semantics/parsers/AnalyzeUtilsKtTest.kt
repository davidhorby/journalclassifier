package com.dhorby.semantics.parsers

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AnalyzeUtilsKtTest {

    val expectedTokens = listOf("rain", "spain", "stai", "mainli")

    @Test
    fun tokenizePhrase() {

        val phrase = "The rain in Spain stays mainly on the plain"
        val tokens: List<String> = tokenizePhrase(phrase)
        assertThat(tokens, equalTo(expectedTokens))
    }
}
