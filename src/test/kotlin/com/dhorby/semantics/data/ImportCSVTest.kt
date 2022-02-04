package com.dhorby.semantics.data

import com.dhorby.semantics.model.Article
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

internal class ImportCSVTest {

    val firstArticle = Article ("10260", "1668008065101249", "Background: A retrospective review of patients treated with Occipital Nerve Stimulation (ONS) at two large tertiary referral centres has been audited in order to optimise future treatment pathways.Methods: Patient's medical records were retrospectively reviewed, and each patient was contacted by a trained headache expert to confirm clinical diagnosis and system efficacy. Results were compared to reported outcomes in current literature on ONS for primary headaches.Results: Twenty-five patients underwent a trial of ONS between January 2007 and December 2012, and 23 patients went on to have permanent implantation of ONS. All 23 patients reached one-year follow/up, and 14 of them (61%) exceeded two years of follow-up. Seventeen of the 23 had refractory chronic migraine (rCM), and 3 refractory occipital neuralgia (ON). 11 of the 19 rCM patients had been referred with an incorrect headache diagnosis. Nine of the rCM patients (53%) reported 50% or more reduction in headache pain intensity and or frequency at long term follow-up (11--77 months). All 3 ON patients reported more than 50% reduction in pain intensity and/or frequency at 28--31 months. Ten (43%) subjects underwent surgical revision after an average of 11 +/- 7 months from permanent implantation - in 90% of cases due to lead problems. Seven patients attended a specifically designed, multi-disciplinary, two-week pre-implant programme and showed improved scores across all measured psychological and functional parameters independent of response to subsequent ONS.Conclusions: Our retrospective review: 1) confirms the long-term ONS success rate in refractory chronic headaches, consistent with previously published studies; 2) suggests that some headaches types may respond better to ONS than others (ON vs CM); 3) calls into question the role of trial stimulation in ONS; 4) confirms the high rate of complications related to the equipment not originally designed for ONS; 5) emphasises the need for specialist multidisciplinary care in these patients.")

    @Test
    fun `import a file as a list of lines`() {

        val fileName: String? = javaClass.classLoader.getResource("testdata.tsv").file

        val lines = fileName?.let {
            ImportCSV.readFile(it)
        } ?: emptyList<String>()
        assertThat(lines.size, equalTo(20))
    }

    @Test
    fun `convert a list of lines from a TSV into a list of articles`() {

        val fileName: String? = javaClass.classLoader.getResource("testdata.tsv").file

        val lines = fileName?.let {
            ImportCSV.readFile(it)
        } ?: emptyList<String>()
        val articleList = lines.toArticleList()
        assertThat(articleList[0], equalTo(firstArticle))
    }
}
