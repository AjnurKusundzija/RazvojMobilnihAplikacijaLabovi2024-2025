package ba.etfrma.bookish

import Bookish4Theme
import BookishNavGraph
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedText = intent?.getStringExtra(Intent.EXTRA_TEXT)
        setContent {
            Bookish4Theme {
                BookishNavGraph(startText = sharedText)
            }
        }
    }
}