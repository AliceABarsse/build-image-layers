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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
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

    var sizeInPx by remember { mutableStateOf(IntSize.Zero) }

    Column(modifier = Modifier.background(color = Color(0xFFDFDFDF))
        ) {
        Text(
            text = "$title!",
            modifier = modifier
        )
        val baseImageBitmap = R.drawable.wraptop_lightened
        val opaqueColor = Color.Green
        val opaqueDetailsColor = Color.White
        val collarShapeVector = R.drawable.baseline_join_full_24
        val buttonsOutlineVector = R.drawable.outline_buttons_24
        Box (modifier = Modifier
            .onGloballyPositioned {
                sizeInPx = it.size
            }) {
            Image(
                modifier = Modifier.offset(x = 0.dp, y = 25.dp),
                painter = painterResource(id = baseImageBitmap),
                contentDescription = "Base raster graphic image",
                colorFilter = ColorFilter.tint(color = opaqueColor.copy(alpha = 0.3f), blendMode = BlendMode.SrcAtop),
            )
            val collarSize = 80.dp
            val centerXOffset = sizeInPx.width.div(2).pxToDp().minus(collarSize/2)

            // lay atop more volume elements
            Image(
                modifier = Modifier.offset(x = centerXOffset, y = 0.dp)
                    .size(collarSize)
                ,
                painter = painterResource(id = collarShapeVector),
                contentDescription = "collar",
                colorFilter = ColorFilter.tint(color = opaqueColor),
            )
            // lay atop some outline elements
            Image(
                modifier = Modifier.offset(x = centerXOffset, y = 55.dp)
                    .size(collarSize)
                ,
                painter = painterResource(id = buttonsOutlineVector),
                contentDescription = "buttons",
                colorFilter = ColorFilter.tint(color = opaqueDetailsColor),
            )
        }
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) {
    this@pxToDp.toDp()
}

@Preview(showBackground = true)
@Composable
fun ComposeImagesPreview() {
    ImageCompositingExampleTheme {
        ComposeImages("Preview")
    }
}