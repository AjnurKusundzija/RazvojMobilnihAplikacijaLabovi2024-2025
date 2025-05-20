package ba.etfrma.bookish.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.util.Locale.Category

data class BookWithRelations(
    @Embedded val book: BookEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "authorId",
        associateBy = Junction(
            value = BookAuthorCrossRef::class,
            parentColumn = "bookId",
            entityColumn = "authorId"
        )
    )
    val authors: List<Author>,

    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId",
        associateBy = Junction(
            value = BookCategoryCrossRef::class,
            parentColumn = "bookId",
            entityColumn = "categoryId"
        )
    )
    val categories: List<Category>
)