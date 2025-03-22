package ba.etfrma.zsr1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.etfrma.zsr1.ui.theme.ZSR1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZSR1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color.Green),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Uvod()
                        Spacer(modifier = Modifier.height(120.dp))
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.height(1005.dp))
                        Naziv()
                    }
                }
            }
        }
    }
}

@Composable
fun Uvod() {
    Text(
        text = "My favourite book is:",
        fontSize = 20.sp,
        textAlign = TextAlign.Left,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun Naziv() {
    Text(
        text = "Stranac",
        fontSize = 30.sp,
        color = Color.Blue,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZSR1Theme {
        Uvod()
        Naziv()
    }
}
