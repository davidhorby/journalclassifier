package com.dhorby.semantics.data

import java.io.File

object ImportCSV {

    fun readFile(fileName: String): List<String> = File(fileName).useLines { it.toList() }
}
