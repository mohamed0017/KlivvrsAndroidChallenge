package com.personal.klivvrsandroidchallenge.data.repository

import android.content.Context
import com.personal.klivvrsandroidchallenge.R
import com.personal.klivvrsandroidchallenge.data.model.City
import com.personal.klivvrsandroidchallenge.data.model.Coordinates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader

interface CityRepository {
    suspend fun loadCities(): List<City>
    fun searchCities(prefix: String): List<City>
}

class CityRepositoryImpl(private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : CityRepository {
    private var cities: List<City> = emptyList()
    private var searchIndex: Map<String, List<City>> = emptyMap()

    override suspend fun loadCities(): List<City> = withContext(dispatcher) {
        val jsonString = context.resources.openRawResource(R.raw.cities)
            .bufferedReader()
            .use(BufferedReader::readText)
        
        val jsonArray = JSONArray(jsonString)
        cities = List(jsonArray.length()) { index ->
            val cityJson = jsonArray.getJSONObject(index)
            City(
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
    }

    private fun buildSearchIndex() {
        val index = mutableMapOf<String, MutableList<City>>()
        for (city in cities) {
            val name = city.name.lowercase()
            for (i in 1..minOf(4, name.length)) {
                val prefix = name.substring(0, i)
                index.getOrPut(prefix) { mutableListOf() }.add(city)
            }
        }
        searchIndex = index
    }

    override fun searchCities(prefix: String): List<City> {
        if (prefix.isEmpty()) return cities
        val searchPrefix = prefix.lowercase()
        val indexPrefix = if (searchPrefix.length > 4) searchPrefix.substring(0, 4) else searchPrefix
        val candidates = searchIndex[indexPrefix] ?: emptyList()
        return candidates.filter { it.name.lowercase().startsWith(searchPrefix) }
            .sortedWith(compareBy({ it.name }, { it.country }))
    }
} 