package ba.etfrma.bookish.data

import ba.etfrma.bookish.dto.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String
    ): BookResponse
}