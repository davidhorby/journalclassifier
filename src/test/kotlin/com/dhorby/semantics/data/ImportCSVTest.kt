package com.dhorby.semantics.data

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

internal class ImportCSVTest {

    @Test
    fun readFileAsLinesUsingUseLines() {

        val fileName: String? = javaClass.classLoader.getResource("testdata.tsv").file

        val lines = fileName?.let {
            ImportCSV.readFile(it)
        } ?: emptyList<String>()
        assertThat(lines.size, equalTo(20))

    }
}
