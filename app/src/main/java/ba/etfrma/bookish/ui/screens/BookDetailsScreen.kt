import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ba.etfrma.bookish.R
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import ba.etfrma.bookish.model.Book


@Composable
fun BookDetailsScreen(book: Book, onBack: () -> Unit){
    val context = LocalContext.current
    Column(
        modifier= Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.books),
            contentDescription = "Book cover",
            modifier = Modifier.height(200.dp).fillMaxWidth()
        )
        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth().clickable{
                try{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${book.title}"))
                    context.startActivity(intent)
                }
                catch(e: ActivityNotFoundException){
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
                try{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.infoLink))
                    context.startActivity(intent)
                }
                catch(e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
        )

        Text(
            text = book.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.clickable{
                try{
                    val sendIntent = Intent(Intent.ACTION_SEND).apply{
                        putExtra(Intent.EXTRA_TEXT,book.description)
                        type = "text/plain"
                    }
                    val chooser: Intent = Intent.createChooser(sendIntent,"Podijeli opis")
                    if (sendIntent.resolveActivity(context.packageManager) != null){
                        context.startActivity(chooser)
                    }
                }
                catch(e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
        )
    }
}