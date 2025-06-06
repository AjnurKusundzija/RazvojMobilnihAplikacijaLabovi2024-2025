package ba.etfrma.bookish.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.etfrma.bookish.data.BookStaticData
import ba.etfrma.bookish.model.Book
import ba.etfrma.bookish.repositories.BookRepository
import ba.etfrma.bookish.ui.components.BookCard
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, initialSearch: String = "") {
    val context = LocalContext.current
    val repository = remember { BookRepository(context) }
    val booksList = remember { mutableStateListOf<Book>() }
    var searchQuery by remember { mutableStateOf(initialSearch) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }



    LaunchedEffect(initialSearch) {
        if (initialSearch.isBlank()) {
            isLoading = true
            val localBooks = repository.loadBooksFromContentProvider(context)
            booksList.clear()
            booksList.addAll(localBooks)
            isLoading = false

            if (localBooks.isEmpty()) {
                snackbarHostState.showSnackbar("There are no local books.")
            }
        }
        else{
            isLoading = true
            val books = repository.searchBooks(initialSearch)
            booksList.clear()
            booksList.addAll(books)
            isLoading = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {

            // UI dio za unos pretrage
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
                Button(onClick = {
                    scope.launch {
                        isLoading = true
                        booksList.clear()
                        if (searchQuery.isBlank()) {
                            val local = repository.loadBooksFromContentProvider(context)
                            booksList.addAll(local)
                            if (local.isEmpty()) {
                                snackbarHostState.showSnackbar("There are no local books.")
                            }
                        } else {
                            booksList.addAll(repository.searchBooks(searchQuery))
                        }
                        isLoading = false
                    }
                }) {
                    Text("Search")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Prikaz loading indikatora
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            // Prikaz rezultata
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(booksList) { book ->
                    BookCard(book = book) {
                        scope.launch {
                            val localBook = repository.getBookByIdFromProvider(book.book.id,context)
                            if (localBook == null) {
                                repository.saveToProvider(book,context)
                                snackbarHostState.showSnackbar("The book is saved to database.")
                            }
                            navController.navigate("details/${book.book.id}")
                        }
                    }
                }
            }
        }
    }
}
