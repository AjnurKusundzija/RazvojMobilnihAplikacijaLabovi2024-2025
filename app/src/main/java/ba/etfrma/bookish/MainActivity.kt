package ba.etfrma.bookish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
    var title by remember {mutableStateOf("")}
    var result by remember {mutableStateOf("")}
    Column(
        modifier = Modifier.statusBarsPadding()
            .padding(horizontal = 40.dp, vertical = 10.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditNameField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = { result = title }
        )
        {
            Text("Change")
        }
        Text(
            text = "My favorite book is: ",
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp).align(alignment = Alignment.Start),
            textAlign = TextAlign.Center,
        )
        Text(
            text = result,
            fontSize = 30.sp,
            lineHeight = 30.sp,
            color = Color.Blue,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )
    }
}
@Composable
fun EditNameField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        label = { Text("Book title")},
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun BookishPreview() {
    BookishTheme {
        BookishAppLayout()
    }
}