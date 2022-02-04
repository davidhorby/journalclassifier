package com.dhorby.semantics.data

import com.dhorby.semantics.model.Article
import java.io.File

object ImportCSV {

    fun readFile(fileName: String): List<String> = File(fileName).useLines { it.toList() }

}


