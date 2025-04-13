package ba.etfrma.bookish.ui.screens

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ba.etfrma.bookish.R
import ba.etfrma.bookish.model.Book

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
            text = book.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    try{
                        val query = "${book.title} ${book.authors.joinToString(" ")}"
                        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                            putExtra(SearchManager.QUERY, query)
                        }
                        context.startActivity(intent)
                    }
                     catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    }
                },
            textAlign = TextAlign.Center
        )

        Text("Authors: ${book.authors.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
        Text("Publisher: ${book.publisher}", style = MaterialTheme.typography.bodyMedium)
        Text("Categories: ${book.categories.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)

        Text(
            text = book.infoLink,
            color = Color.Blue,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.infoLink))
                    context.startActivity(intent)
                }
                catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        )

        Text(
            text = book.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.clickable {
                try {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, book.description)
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(intent, "Share via"))
                }
                catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        )
    }
}
