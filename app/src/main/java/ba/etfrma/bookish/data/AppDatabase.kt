package ba.etfrma.bookish.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ba.etfrma.bookish.model.BookEntity


@Database(
    entities = [
        BookEntity::class,
        Author::class,
        Category::class,
        BookAuthorCrossRef::class,
        BookCategoryCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bookish_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}