package ba.etfrma.bookish.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.etfrma.bookish.data.BookStaticData
import ba.etfrma.bookish.ui.components.BookCard


@Composable
fun HomeScreen(navController: NavController, initialSearch: String = "") {
    val booksList = remember { BookStaticData.getSampleBooks() }
    var searchQuery by remember { mutableStateOf(initialSearch) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by title") },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { /* Optionally filter */ }) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(booksList) { book ->
                BookCard(book = book) {
                    navController.navigate("details/${book.title}")
                }
            }
        })
    }
}
