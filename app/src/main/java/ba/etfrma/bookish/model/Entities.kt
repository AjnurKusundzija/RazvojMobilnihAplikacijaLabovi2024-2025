package ba.etfrma.bookish.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

// ba.etfrma.bookish.model.BookEntity.kt
@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val publisher: String,
    val description: String,
    val infoLink: String,
    val thumbnail: String
)

// ba.etfrma.bookish.model.AuthorEntity.kt
@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true) val authorId: Long = 0,
    val name: String
)

// ba.etfrma.bookish.model.CategoryEntity.kt
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    val name: String
)

// Cross-refs
@Entity(primaryKeys = ["bookId","authorId"])
data class BookAuthorCrossRef(val bookId: String, val authorId: Long)

@Entity(primaryKeys = ["bookId","categoryId"])
data class BookCategoryCrossRef(val bookId: String, val categoryId: Long)

// Relacijski POJO
data class BookWithRelations(
    @Embedded val book: BookEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId",
        associateBy = Junction(BookAuthorCrossRef::class)
    )
    val authors: List<AuthorEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId",
        associateBy = Junction(BookCategoryCrossRef::class)
    )
    val categories: List<CategoryEntity>
)
