package ba.etfrma.bookish.data

import ba.etfrma.bookish.dto.BookItem
import ba.etfrma.bookish.dto.BookResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 40
    ): BookResponse

    @GET("volumes/{volumeId}")
    suspend fun getBookById(
        @Path("volumeId") volumeId: String
    ): BookItem
}