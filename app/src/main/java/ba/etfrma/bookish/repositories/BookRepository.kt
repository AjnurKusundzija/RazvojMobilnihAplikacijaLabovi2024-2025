package ba.etfrma.bookish.repositories

import android.content.Context
import android.icu.util.ULocale.Category
import ba.etfrma.bookish.data.AppDatabase
import ba.etfrma.bookish.model.Book
import ba.etfrma.bookish.model.BookAuthorCrossRef
import ba.etfrma.bookish.model.BookCategoryCrossRef
import ba.etfrma.bookish.model.BookEntity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.support.label.Category
import java.util.Locale.Category
import kotlin.collections.map

class BookRepository(private val context: Context) {
    private val dao by lazy {
        AppDatabase.getDatabase(context).bookDao()
    }

    // Your existing repository methods here

    suspend fun getBookByIdDatabase(id: String): Book? = withContext(Dispatchers.IO) {
        val bookWithRelations = dao.getBookWithRelations(id)
        return@withContext bookWithRelations?.let { convertToBook(it) }
    }

    suspend fun saveToLocal(book: Book) = withContext(Dispatchers.IO) {
        // Convert Book to BookEntity
        val bookEntity = BookEntity(
            id = book.book.id,
            title = book.book.title,
            publisher = book.book.publisher,
            infoLink = book.book.infoLink,
            description = book.book.description,
            thumbnail = book.book.thumbnail
        )

        // Insert book entity
        dao.insertBook(bookEntity)

        // Insert authors and get their IDs
        val authors = book.authors.map { Author(name = it) }
        val authorIds = dao.insertAuthors(authors)

        // Insert categories and get their IDs
        val categories = book.categories.map { Category(name = book.categories) }
        val categoryIds = dao.insertCategories(categories)

        // Create and insert cross references
        val authorRefs = authorIds.mapIndexed { index, id ->
            BookAuthorCrossRef(book.book.id, id)
        }

        val categoryRefs = categoryIds.mapIndexed { index, id ->
            BookCategoryCrossRef(book.book.id, id)
        }

        dao.insertBookAuthorCrossRefs(authorRefs)
        dao.insertBookCategoryCrossRefs(categoryRefs)
    }

    private fun convertToBook(bookWithRelations: Book): Book {
        return Book(
            id = bookWithRelations.book.id,
            title = bookWithRelations.book.title,
            authors = bookWithRelations.authors.map { it.name },
            publisher = bookWithRelations.book.publisher,
            categories = bookWithRelations.categories.map { it.name },
            infoLink = bookWithRelations.book.infoLink,
            description = bookWithRelations.book.description,
            thumbnail = bookWithRelations.book.thumbnail
        )
    }
}