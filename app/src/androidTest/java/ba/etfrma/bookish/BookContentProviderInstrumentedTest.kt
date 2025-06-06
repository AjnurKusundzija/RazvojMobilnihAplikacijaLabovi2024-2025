package ba.etfrma.bookish



import android.content.ContentValues
import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etfrma.bookish.data.AppDatabase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookContentProviderInstrumentedTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {

        val db = AppDatabase.getDatabase(context)
        db.clearAllTables()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun insertAndQueryBookThroughContentProvider() {

        val values = ContentValues().apply {
            put("id", "TEST_ID")
            put("title", "Test Title")
            put("publisher", "Test Publisher")
            put("infoLink", "https://test.link")
            put("description", "Test Description")
            put("thumbnail", "test.png")
            put("authors", "Test Author")
            put("categories", "Test Category")
        }


        val insertUri = Uri.parse("content://ba.etfrma.bookish.provider/books")
        val newUri = context.contentResolver.insert(insertUri, values)
        Assert.assertNotNull("Insert uri should not be null", newUri)


        val queryUri = Uri.parse("content://ba.etfrma.bookish.provider/books/TEST_ID")
        val cursor = context.contentResolver.query(queryUri, null, null, null, null)

        Assert.assertNotNull(cursor)
        Assert.assertTrue(cursor!!.moveToFirst())

        val titleIndex = cursor.getColumnIndex("title")
        val authorIndex = cursor.getColumnIndex("authors")
        val categoryIndex = cursor.getColumnIndex("categories")

        Assert.assertEquals("Test Title", cursor.getString(titleIndex))
        Assert.assertEquals("Test Author", cursor.getString(authorIndex))
        Assert.assertEquals("Test Category", cursor.getString(categoryIndex))

        cursor.close()
    }
}
