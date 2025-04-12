import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.foundation.lazy.grid.items
import ba.etfrma.bookish.data.BookStaticData

@Composable
fun HomeScreen(navController: NavController, initialSearch: String = ""){
    val booksList = remember { BookStaticData.getSampleBooks() }
    var searchQuery by remember { mutableStateOf(initialSearch)}
    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ){
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by title") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {

            }){
                Text("Search")
            }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(booksList) { book ->
                BookCard(book = book) {
                    navController.navigate("details/${book.title}")
                }
            } })
    }
}
