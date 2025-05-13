import ba.etfrma.bookish.data.RetrofitInstance
import ba.etfrma.bookish.dto.toBook
import ba.etfrma.bookish.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



object BookRepository {
    private val api = RetrofitInstance.api

    // postojaća metoda
    suspend fun searchBooks(query: String): List<Book> = withContext(Dispatchers.IO) {
        api.searchBooks(query)
            .items
            .map { it.toBook() }
            .take(10)
    }

    // nova metoda za detalje
    suspend fun getBookDetails(id: String): Book = withContext(Dispatchers.IO) {
        val item = api.getBookDetails(id)
        item.toBook()
    }
}
