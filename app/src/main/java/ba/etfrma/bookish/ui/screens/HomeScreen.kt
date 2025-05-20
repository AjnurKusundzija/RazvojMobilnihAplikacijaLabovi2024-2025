package ba.etfrma.bookish.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ba.etfrma.bookish.model.Book
import ba.etfrma.bookish.repositories.BookRepository
import ba.etfrma.bookish.ui.components.BookCard


import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, booksList: List<Book> = emptyList()) {
    val context = LocalContext.current
    val repository = remember { BookRepository(context) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // You can load books here if needed

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            // Your search UI would go here

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(booksList) { book ->
                    BookCard(book = book) {
                        scope.launch {
                            val localBook = repository.getBookByIdDatabase(book.book.id)
                            if (localBook == null) {
                                repository.saveToLocal(book)
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