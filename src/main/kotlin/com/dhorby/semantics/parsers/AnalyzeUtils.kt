package com.dhorby.semantics.parsers

import com.dhorby.semantics.StopWords
import org.apache.commons.lang.StringUtils.isNumeric
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.CharArraySet
import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import java.io.StringReader

val bmcStopSet: CharArraySet = CharArraySet(StopWords.bmcStopWords, false)

fun tokenizePhrase(phrase: String?): List<String> {
    val tokens: MutableList<String> = ArrayList()
    val englishAnalyzer = EnglishAnalyzer(bmcStopSet)
    try {
        englishAnalyzer.tokenStream("someField", StringReader(phrase))
            .use { stream ->
                val cattr: CharTermAttribute = stream.addAttribute<CharTermAttribute>(CharTermAttribute::class.java)
                stream.reset()
                while (stream.incrementToken()) {
                    if (cattr.length > 0) {
                        val word: String =
                            stream.getAttribute<CharTermAttribute>(CharTermAttribute::class.java).toString()
                        if (!isNumeric(word)) tokens.add(
                            stream.getAttribute<CharTermAttribute>(
                                CharTermAttribute::class.java
                            ).toString()
                        )
                    }
                }
                stream.end()
            }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return tokens
}
