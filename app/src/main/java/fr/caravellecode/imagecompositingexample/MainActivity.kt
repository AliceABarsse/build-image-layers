package fr.caravellecode.imagecompositingexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.caravellecode.imagecompositingexample.ui.theme.ImageCompositingExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageCompositingExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposeImages("Image composition example")
                }
            }
        }
    }
}

@Composable
fun ComposeImages(title: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.background(color = Color.LightGray)) {
        Text(
            text = "$title!",
            modifier = modifier
        )
        val baseImageBitmap = R.drawable.wraptop_lightened
        val opaqueColor = Color.Green
        val opaqueDetailsColor = Color.White
        val collarShapeVector = R.drawable.baseline_join_full_24
        val buttonsOutlineVector = R.drawable.outline_buttons_24
        Box () {
            Image(
                painter = painterResource(id = baseImageBitmap),
                contentDescription = "Base raster graphic image",
                colorFilter = ColorFilter.tint(color = opaqueColor),
            )
            // lay atop more volume elements
            Image(
                modifier = Modifier.offset(x = 150.dp, y = -25.dp)
                    .size(80.dp)
                ,
                painter = painterResource(id = collarShapeVector),
                contentDescription = "collar",
                colorFilter = ColorFilter.tint(color = opaqueColor),
            )
            // lay atop some outline elements
            Image(
                modifier = Modifier.offset(x = 150.dp, y = 20.dp)
                    .size(80.dp)
                ,
                painter = painterResource(id = buttonsOutlineVector),
                contentDescription = "buttons",
                colorFilter = ColorFilter.tint(color = opaqueDetailsColor),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageCompositingExampleTheme {
        ComposeImages("Preview")
    }
}