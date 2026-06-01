package data.repository

import data.dto.LaureateShortDto
import data.dto.LocalizedStringDto
import data.dto.NobelPrizeDto
import data.dto.toEntity
import domain.entity.Laureate
import domain.entity.NobelPrize
import domain.repository.NobelPrizeRepository

class InMemoryNobelPrizeRepository : NobelPrizeRepository {

    private val prizes: List<NobelPrize> = buildDataset()

    override fun getAllPrizes(): List<NobelPrize> = prizes

    override fun getPrize(year: String, category: String): NobelPrize? =
        prizes.find {
            it.awardYear == year && it.category.equals(category, ignoreCase = true)
        }

    override fun getLaureates(year: String, category: String): List<Laureate>? =
        getPrize(year, category)?.laureates

    private fun buildDataset(): List<NobelPrize> {
        val raw = listOf(
            NobelPrizeDto(
                awardYear = "2023",
                category = LocalizedStringDto("physics"),
                laureates = listOf(
                    LaureateShortDto(
                        "1017", fullName = LocalizedStringDto("Pierre Agostini"),
                        motivation = LocalizedStringDto("for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter")
                    ),
                    LaureateShortDto(
                        "1018", fullName = LocalizedStringDto("Ferenc Krausz"),
                        motivation = LocalizedStringDto("for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter")
                    ),
                    LaureateShortDto(
                        "1019", fullName = LocalizedStringDto("Anne L'Huillier"),
                        motivation = LocalizedStringDto("for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2023",
                category = LocalizedStringDto("chemistry"),
                laureates = listOf(
                    LaureateShortDto(
                        "1020", fullName = LocalizedStringDto("Moungi G. Bawendi"),
                        motivation = LocalizedStringDto("for the discovery and synthesis of quantum dots")
                    ),
                    LaureateShortDto(
                        "1021", fullName = LocalizedStringDto("Louis E. Brus"),
                        motivation = LocalizedStringDto("for the discovery and synthesis of quantum dots")
                    ),
                    LaureateShortDto(
                        "1022", fullName = LocalizedStringDto("Alexei I. Ekimov"),
                        motivation = LocalizedStringDto("for the discovery and synthesis of quantum dots")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2023",
                category = LocalizedStringDto("medicine"),
                laureates = listOf(
                    LaureateShortDto(
                        "1023", fullName = LocalizedStringDto("Katalin Karikó"),
                        motivation = LocalizedStringDto("for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19")
                    ),
                    LaureateShortDto(
                        "1024", fullName = LocalizedStringDto("Drew Weissman"),
                        motivation = LocalizedStringDto("for their discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2023",
                category = LocalizedStringDto("literature"),
                laureates = listOf(
                    LaureateShortDto(
                        "1025", fullName = LocalizedStringDto("Jon Fosse"),
                        motivation = LocalizedStringDto("for his innovative plays and prose which give voice to the unsayable")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2023",
                category = LocalizedStringDto("peace"),
                laureates = listOf(
                    LaureateShortDto(
                        "1026", fullName = LocalizedStringDto("Narges Mohammadi"),
                        motivation = LocalizedStringDto("for her fight against the oppression of women in Iran and her efforts to promote human rights and freedom for all")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2023",
                category = LocalizedStringDto("economic-sciences"),
                laureates = listOf(
                    LaureateShortDto(
                        "1027", fullName = LocalizedStringDto("Claudia Goldin"),
                        motivation = LocalizedStringDto("for having advanced our understanding of women's labour market outcomes")
                    )
                )
            ),
            // 2022
            NobelPrizeDto(
                awardYear = "2022",
                category = LocalizedStringDto("physics"),
                laureates = listOf(
                    LaureateShortDto(
                        "1007", fullName = LocalizedStringDto("Alain Aspect"),
                        motivation = LocalizedStringDto("for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science")
                    ),
                    LaureateShortDto(
                        "1008", fullName = LocalizedStringDto("John F. Clauser"),
                        motivation = LocalizedStringDto("for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science")
                    ),
                    LaureateShortDto(
                        "1009", fullName = LocalizedStringDto("Anton Zeilinger"),
                        motivation = LocalizedStringDto("for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2022",
                category = LocalizedStringDto("chemistry"),
                laureates = listOf(
                    LaureateShortDto(
                        "1010", fullName = LocalizedStringDto("Carolyn R. Bertozzi"),
                        motivation = LocalizedStringDto("for the development of click chemistry and bioorthogonal chemistry")
                    ),
                    LaureateShortDto(
                        "1011", fullName = LocalizedStringDto("Morten Meldal"),
                        motivation = LocalizedStringDto("for the development of click chemistry and bioorthogonal chemistry")
                    ),
                    LaureateShortDto(
                        "1012", fullName = LocalizedStringDto("K. Barry Sharpless"),
                        motivation = LocalizedStringDto("for the development of click chemistry and bioorthogonal chemistry")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2022",
                category = LocalizedStringDto("medicine"),
                laureates = listOf(
                    LaureateShortDto(
                        "1013", fullName = LocalizedStringDto("Svante Pääbo"),
                        motivation = LocalizedStringDto("for his discoveries concerning the genomes of extinct hominins and human evolution")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2022",
                category = LocalizedStringDto("literature"),
                laureates = listOf(
                    LaureateShortDto(
                        "1014", fullName = LocalizedStringDto("Annie Ernaux"),
                        motivation = LocalizedStringDto("for the courage and clinical acuity with which she uncovers the roots, estrangements and collective restraints of personal memory")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2022",
                category = LocalizedStringDto("peace"),
                laureates = listOf(
                    LaureateShortDto(
                        "1015", orgName = LocalizedStringDto("Memorial"),
                        motivation = LocalizedStringDto("for the outstanding effort to document war crimes, human rights abuses and the abuse of power")
                    ),
                    LaureateShortDto(
                        "1016", orgName = LocalizedStringDto("Center for Civil Liberties"),
                        motivation = LocalizedStringDto("for the outstanding effort to document war crimes, human rights abuses and the abuse of power")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2022",
                category = LocalizedStringDto("economic-sciences"),
                laureates = listOf(
                    LaureateShortDto(
                        "1028", fullName = LocalizedStringDto("Ben S. Bernanke"),
                        motivation = LocalizedStringDto("for research on banks and financial crises")
                    ),
                    LaureateShortDto(
                        "1029", fullName = LocalizedStringDto("Douglas W. Diamond"),
                        motivation = LocalizedStringDto("for research on banks and financial crises")
                    ),
                    LaureateShortDto(
                        "1030", fullName = LocalizedStringDto("Philip H. Dybvig"),
                        motivation = LocalizedStringDto("for research on banks and financial crises")
                    )
                )
            ),
            // 2021
            NobelPrizeDto(
                awardYear = "2021",
                category = LocalizedStringDto("physics"),
                laureates = listOf(
                    LaureateShortDto(
                        "1000", fullName = LocalizedStringDto("Syukuro Manabe"),
                        motivation = LocalizedStringDto("for the physical modelling of Earth's climate, quantifying variability and reliably predicting global warming")
                    ),
                    LaureateShortDto(
                        "1001", fullName = LocalizedStringDto("Klaus Hasselmann"),
                        motivation = LocalizedStringDto("for the physical modelling of Earth's climate, quantifying variability and reliably predicting global warming")
                    ),
                    LaureateShortDto(
                        "1002", fullName = LocalizedStringDto("Giorgio Parisi"),
                        motivation = LocalizedStringDto("for the discovery of the interplay of disorder and fluctuations in physical systems from atomic to planetary scales")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2021",
                category = LocalizedStringDto("chemistry"),
                laureates = listOf(
                    LaureateShortDto(
                        "1003", fullName = LocalizedStringDto("Benjamin List"),
                        motivation = LocalizedStringDto("for the development of asymmetric organocatalysis")
                    ),
                    LaureateShortDto(
                        "1004", fullName = LocalizedStringDto("David W.C. MacMillan"),
                        motivation = LocalizedStringDto("for the development of asymmetric organocatalysis")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2021",
                category = LocalizedStringDto("medicine"),
                laureates = listOf(
                    LaureateShortDto(
                        "1005", fullName = LocalizedStringDto("David Julius"),
                        motivation = LocalizedStringDto("for their discoveries of receptors for temperature and touch")
                    ),
                    LaureateShortDto(
                        "1006", fullName = LocalizedStringDto("Ardem Patapoutian"),
                        motivation = LocalizedStringDto("for their discoveries of receptors for temperature and touch")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2021",
                category = LocalizedStringDto("literature"),
                laureates = listOf(
                    LaureateShortDto(
                        "1031", fullName = LocalizedStringDto("Abdulrazak Gurnah"),
                        motivation = LocalizedStringDto("for his uncompromising and compassionate penetration of the effects of colonialism and the fate of the refugee in the gulf between cultures and continents")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2021",
                category = LocalizedStringDto("peace"),
                laureates = listOf(
                    LaureateShortDto(
                        "1032", fullName = LocalizedStringDto("Maria Ressa"),
                        motivation = LocalizedStringDto("for their efforts to safeguard freedom of expression, which is a precondition for democracy and lasting peace")
                    ),
                    LaureateShortDto(
                        "1033", fullName = LocalizedStringDto("Dmitry Muratov"),
                        motivation = LocalizedStringDto("for their efforts to safeguard freedom of expression, which is a precondition for democracy and lasting peace")
                    )
                )
            ),
            NobelPrizeDto(
                awardYear = "2021",
                category = LocalizedStringDto("economic-sciences"),
                laureates = listOf(
                    LaureateShortDto(
                        "1034", fullName = LocalizedStringDto("David Card"),
                        motivation = LocalizedStringDto("for his empirical contributions to labour economics")
                    ),
                    LaureateShortDto(
                        "1035", fullName = LocalizedStringDto("Joshua D. Angrist"),
                        motivation = LocalizedStringDto("for their methodological contributions to the analysis of causal relationships")
                    ),
                    LaureateShortDto(
                        "1036", fullName = LocalizedStringDto("Guido W. Imbens"),
                        motivation = LocalizedStringDto("for their methodological contributions to the analysis of causal relationships")
                    )
                )
            )
        )

        return raw.map { it.toEntity() }
    }
}
