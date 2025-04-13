
package ba.etfrma.bookish

import android.app.SearchManager
import android.content.Intent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etfrma.bookish.data.BookStaticData
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookishInstrumentedTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun allBooksAreDisplayedOnHomeScreen() {
        val books = BookStaticData.getSampleBooks()

        books.forEach { book ->
            composeTestRule.onNodeWithText(book.title, substring = true)
                .assertExists("Book '${book.title}' is not displayed")
        }
    }

    @Test
    fun clickingBookNavigatesToDetailsAndDisplaysCorrectInfo() {
        val book = BookStaticData.getSampleBooks().first()


        composeTestRule.onNodeWithText(book.title, substring = true)
            .performClick()


        composeTestRule.onNodeWithText(book.title, substring = true).assertExists()
        composeTestRule.onNodeWithText("Authors: ${book.authors.joinToString(", ")}").assertExists()
        composeTestRule.onNodeWithText("Publisher: ${book.publisher}").assertExists()
        composeTestRule.onNodeWithText("Categories: ${book.categories.joinToString(", ")}").assertExists()
        composeTestRule.onNodeWithText(book.description, substring = true).assertExists()
    }

    private fun hasSendIntentInsideChooser(bookDescription: String): Matcher<Intent> {
        return allOf(
            IntentMatchers.hasAction(Intent.ACTION_CHOOSER),
            IntentMatchers.hasExtra(`is`(Intent.EXTRA_INTENT), allOf(
                IntentMatchers.hasAction(Intent.ACTION_SEND),
                IntentMatchers.hasExtra(Intent.EXTRA_TEXT, bookDescription),
                IntentMatchers.hasType("text/plain")
            ))
        )
    }

    @Test
    fun shareIntentIsTriggeredOnDescriptionClick() {
        val book = BookStaticData.getSampleBooks().first()


        init()


        composeTestRule.onNodeWithText(book.title, substring = true).performClick()
        composeTestRule.onNodeWithText(book.description, substring = true).performClick()


        intended(hasSendIntentInsideChooser(book.description))


        release()
    }
    @Test
    fun clickingBookTitleInDetailsOpensWebSearch() {
        val book = BookStaticData.getSampleBooks().first()
        composeTestRule.onNodeWithText(book.title, substring = true).performClick()
        init()
        composeTestRule.onNodeWithText(book.title, substring = true).performClick()
        intended(allOf(
            IntentMatchers.hasAction(Intent.ACTION_WEB_SEARCH),
            IntentMatchers.hasExtra(SearchManager.QUERY, book.title)
        ))
        release()
    }

    @Test
    fun clickingBookLinkInDetailsOpensWebPage() {
        val book = BookStaticData.getSampleBooks().first()
        composeTestRule.onNodeWithText(book.title, substring = true).performClick()
        init()
        composeTestRule.onNodeWithText(book.infoLink, substring = true).performClick()
        intended(allOf(
            IntentMatchers.hasAction(Intent.ACTION_VIEW),
            IntentMatchers.hasData(book.infoLink)
        ))
        release()
    }
}