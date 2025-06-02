package com.personal.klivvrsandroidchallenge.data.repository

import android.content.Context
import com.personal.klivvrsandroidchallenge.R
import com.personal.klivvrsandroidchallenge.domain.model.CityDomain as DomainCity
import com.personal.klivvrsandroidchallenge.domain.model.Coordinates
import com.personal.klivvrsandroidchallenge.domain.repository.CityRepository
import com.personal.klivvrsandroidchallenge.domain.exception.CityException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CityRepository {
    private var cities: List<DomainCity> = emptyList()
    private var searchIndex: Map<String, List<DomainCity>> = emptyMap()

    override suspend fun loadCities(): List<DomainCity> = withContext(dispatcher) {
        try {
            if (cities.isNotEmpty()) return@withContext cities

            val jsonString = context.resources.openRawResource(R.raw.cities)
                .bufferedReader()
                .use(BufferedReader::readText)

            val jsonArray = JSONArray(jsonString)
            cities = List(jsonArray.length()) { index ->
                val cityJson = jsonArray.getJSONObject(index)
                DomainCity(
                    id = cityJson.getLong("_id"),
                    name = cityJson.getString("name"),
                    country = cityJson.getString("country"),
                    coordinates = Coordinates(
                        latitude = cityJson.getJSONObject("coord").getDouble("lat"),
                        longitude = cityJson.getJSONObject("coord").getDouble("lon")
                    )
                )
            }.sortedWith(compareBy({ it.name }, { it.country }))

            // Build search index for optimized prefix search
            buildSearchIndex()
            cities
        } catch (e: Exception) {
            throw CityException.DataLoadError(e.message ?: "Unknown error occurred while loading cities")
        }
    }

    private fun buildSearchIndex() {
        val index = mutableMapOf<String, MutableList<DomainCity>>()
        for (city in cities) {
            val name = city.name.lowercase()
            for (i in 1..minOf(4, name.length)) {
                val prefix = name.substring(0, i)
                index.getOrPut(prefix) { mutableListOf() }.add(city)
            }
        }
        searchIndex = index
    }

    override fun searchCities(prefix: String): List<DomainCity> {
        if (prefix.isBlank()) return cities
        val searchPrefix = prefix.lowercase()
        val indexPrefix = if (searchPrefix.length > 4) searchPrefix.substring(0, 4) else searchPrefix
        val candidates = searchIndex[indexPrefix] ?: emptyList()
        return candidates.filter { it.name.lowercase().startsWith(searchPrefix) }
            .sortedWith(compareBy({ it.name }, { it.country }))
    }

}