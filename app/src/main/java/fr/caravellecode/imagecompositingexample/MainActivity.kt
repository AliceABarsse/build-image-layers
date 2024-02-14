package fr.caravellecode.imagecompositingexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
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

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier.background(color = Color(0xFFDFDFDF))
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

        val collarSize = 80.dp
        val centerXOffset = sizeInPx.width.div(2).pxToDp().minus(collarSize / 2)


        Box(modifier = Modifier
            .onGloballyPositioned {
                sizeInPx = it.size
            }
        ) {
            Image(
                modifier = Modifier.offset(x = 0.dp, y = 25.dp),
                painter = painterResource(id = baseImageBitmap),
                contentDescription = "Base raster graphic image",
                colorFilter = ColorFilter.tint(color = opaqueColor, blendMode = BlendMode.SrcAtop),
            )
            // lay atop more volume elements
            Image(
                modifier = Modifier
                    .offset(x = centerXOffset, y = 0.dp)
                    .size(collarSize),
                painter = painterResource(id = collarShapeVector),
                contentDescription = "collar",
                colorFilter = ColorFilter.tint(color = opaqueColor),
            )
            // lay atop some outline elements
            Image(
                modifier = Modifier
                    .offset(x = centerXOffset, y = 55.dp)
                    .size(collarSize),
                painter = painterResource(id = buttonsOutlineVector),
                contentDescription = "buttons",
                colorFilter = ColorFilter.tint(color = opaqueDetailsColor),
            )

            // Make an under-layer "visible" inside a cut-out zone
            Box (modifier = Modifier.offset(x = 0.dp, y = (-30).dp)
                .clip(shape = GenericShape{ size, _ ->
                    // cut out a rectangle in which the under-layer image will appear
                    moveTo(size.width * 0.65f, 0f)
                    lineTo(size.width * 0.55f, size.height*0.6f)
                    lineTo(size.width * 0.60f, size.height)
                    lineTo(size.width * 0.35f, size.height)
                    lineTo(size.width * 0.45f, size.height*0.6f)
                    lineTo(size.width * 0.40f, 0f)
                })
                .clipToBounds()
            ){
                // black underlayer
                Image(
                    modifier = Modifier
                        .size(400.dp),
                    painter = painterResource(id = R.mipmap.ic_underlayer_foreground),
                    contentDescription = "Base raster graphic image",
                    colorFilter = ColorFilter.tint(color = Color.Black)
                )
            }
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