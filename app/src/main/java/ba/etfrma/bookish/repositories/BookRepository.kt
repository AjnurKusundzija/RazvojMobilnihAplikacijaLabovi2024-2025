
package ba.etfrma.bookish.repositories

import ba.etfrma.bookish.data.RetrofitInstance
import ba.etfrma.bookish.dto.toBook
import ba.etfrma.bookish.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object BookRepository {

    // Renamed function for manual HTTP call implementation
    suspend fun searchBooksManually(query: String): List<Book> = withContext(Dispatchers.IO) {
        val apiUrl = "https://www.googleapis.com/books/v1/volumes?q=${query}"
        val url = URL(apiUrl)

        // Open connection
        val connection = url.openConnection() as? HttpURLConnection
        val response = connection?.inputStream?.bufferedReader()?.use { it.readText() }
        connection?.disconnect()

        // Parse response if available
        response?.let { parseBooksFromJson(it) } ?: emptyList()
    }

    // JSON parsing function
    private fun parseBooksFromJson(json: String): List<Book> {
        val jsonObj = JSONObject(json)
        val itemsArray: JSONArray = jsonObj.optJSONArray("items") ?: return emptyList()

        return List(10) { i ->
            val item = itemsArray.getJSONObject(i)
            val volumeInfo = item.getJSONObject("volumeInfo")
            val imageLinks = volumeInfo.optJSONObject("imageLinks")
            val thumbnailUrl = imageLinks?.optString("thumbnail", "")
            Book(
                id = item.optString("id", "N/A"),
                title = volumeInfo.optString("title", "N/A"),
                authors = volumeInfo.optJSONArray("authors")?.let { arr ->
                    List(arr.length()) { j -> arr.getString(j) }
                } ?: listOf("Unknown"),
                publisher = volumeInfo.optString("publisher", "Unknown"),
                categories = volumeInfo.optJSONArray("categories")?.let { arr ->
                    List(arr.length()) { j -> arr.getString(j) }
                } ?: listOf("Uncategorized"),
                infoLink = volumeInfo.optString("infoLink", ""),
                description = volumeInfo.optString("description", "No description"),
                thumbnail = thumbnailUrl
            )
        }
    }

    suspend fun getBookById(id: String): Book? = withContext(Dispatchers.IO) {
        val url = URL("https://www.googleapis.com/books/v1/volumes/$id")
        val connection = url.openConnection() as? HttpURLConnection
        val response = connection?.inputStream?.bufferedReader()?.use { it.readText() }
        connection?.disconnect()

        response?.let {
            val item = JSONObject(it)
            val volumeInfo = item.getJSONObject("volumeInfo")
            val imageLinks = volumeInfo.optJSONObject("imageLinks")
            Book(
                id = id,
                title = volumeInfo.optString("title", "N/A"),
                authors = volumeInfo.optJSONArray("authors")?.let { arr ->
                    List(arr.length()) { j -> arr.getString(j) }
                } ?: listOf("Unknown"),
                publisher = volumeInfo.optString("publisher", "Unknown"),
                categories = volumeInfo.optJSONArray("categories")?.let { arr ->
                    List(arr.length()) { j -> arr.getString(j) }
                } ?: listOf("Uncategorized"),
                infoLink = volumeInfo.optString("infoLink", ""),
                description = volumeInfo.optString("description", "No description"),
                thumbnail = imageLinks?.optString("thumbnail", "")
            )
        }
    }

    private val api = RetrofitInstance.api

    // Retrofit implementation of searchBooks
    suspend fun searchBooks(query: String): List<Book> = withContext(Dispatchers.IO) {
        val response = api.searchBooks(query)
        response.items.map { it.toBook() }.take(10)
    }
}