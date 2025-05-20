package ba.etfrma.bookish

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etfrma.bookish.data.BookStaticData
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BookishInstrumentedTests {

    private fun hasSendIntentInsideChooser(bookDescription: String): Matcher<Intent> {
        return allOf(
            IntentMatchers.hasAction(Intent.ACTION_CHOOSER),
            hasExtra(`is`(Intent.EXTRA_INTENT), allOf(
                IntentMatchers.hasAction(Intent.ACTION_SEND),
                hasExtra(Intent.EXTRA_TEXT, bookDescription),
                IntentMatchers.hasType("text/plain")
            ))
        )
    }

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

    @Test
    fun searchIntentIsTriggeredOnTitleClick() {
        val book = BookStaticData.getSampleBooks().first()

        init()

        composeTestRule.onNodeWithText(book.title, substring = true)
            .performClick()
        composeTestRule.onNodeWithText(book.title, substring = true)
            .performClick()

        intended(allOf(
            IntentMatchers.hasAction(Intent.ACTION_WEB_SEARCH),
            hasExtra(`is`(SearchManager.QUERY), containsString(book.title))
        ))

        release()
    }

    @Test
    fun infoLinkIntentIsTriggeredOnLinkClick() {
        val book = BookStaticData.getSampleBooks().first()

        init()

        composeTestRule.onNodeWithText(book.title, substring = true).performClick()
        composeTestRule.onNodeWithText(book.infoLink, substring = true).performClick()

        intended(allOf(
            IntentMatchers.hasAction(Intent.ACTION_VIEW),
            IntentMatchers.hasData(Uri.parse(book.infoLink))
        ))

        release()
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


}
