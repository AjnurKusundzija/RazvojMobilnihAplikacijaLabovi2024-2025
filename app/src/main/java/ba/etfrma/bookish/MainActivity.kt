package ba.etfrma.bookish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.etfrma.bookish.ui.theme.BookishTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookishTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BookishAppLayout()
                }
            }
        }
    }
}

@Composable
fun BookishAppLayout() {
    var bookTitle by remember { mutableStateOf("") }
    var books by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp, vertical = 10.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditNameField(
            value = bookTitle,
            onValueChange = { bookTitle = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
                if (bookTitle.isNotBlank()) {
                    books = books + bookTitle
                    bookTitle = ""
                }
            }
        ) {
            Text("Add Book")
        }
        Text(
            text = "My favorite books are:",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start),
            textAlign = TextAlign.Center,
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(books) { book ->
                Text(
                    text = book,
                    fontSize = 18.sp,
                    color = Color.Blue,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun EditNameField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        label = { Text("Book title") },
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBookishAppLayout() {
    BookishTheme {
        BookishAppLayout()
    }
}
