package ba.etfrma.bookish.ui.screens



import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ba.etfrma.bookish.model.Book
import kotlin.collections.joinToString


@Composable
fun BookDetailsScreen(book: Book, onBack: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.books),
            contentDescription = "Book cover",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )

        Text(
            text = book.book.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Authors: ${book.authors.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Publisher: ${book.book.publisher}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Categories: ${book.categories.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = book.book.infoLink,
            color = Color.Blue,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.book.infoLink))
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        )

        Text(
            text = book.book.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.clickable {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, book.book.description)
                }
                val chooser = Intent.createChooser(shareIntent, "Share description")
                context.startActivity(chooser)
            }
        )
    }
}