package com.plcoding.receivecontentmodifiercompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.hasMediaType
import androidx.compose.foundation.content.receiveContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.plcoding.receivecontentmodifiercompose.ui.theme.ReceiveContentModifierComposeTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReceiveContentModifierComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        var text by remember {
                            mutableStateOf("")
                        }
                        var imageUri by remember {
                            mutableStateOf(Uri.EMPTY)
                        }

                        AsyncImage(
                            model = imageUri,
                            contentDescription = null,
                            modifier = Modifier.height(150.dp),
                            contentScale = ContentScale.FillHeight
                        )
                        BasicTextField2(
                            value = text,
                            onValueChange = { text = it },
                            textStyle = TextStyle(fontSize = 14.sp),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.LightGray)
                                .padding(16.dp)
                                .receiveContent(MediaType.All) { content ->
                                    if (content.hasMediaType(MediaType.Image)) {
                                        val clipData = content.clipEntry.clipData
                                        for (index in 0 until clipData.itemCount) {
                                            val item = clipData.getItemAt(index) ?: continue

                                            imageUri = item.uri ?: continue
                                        }
                                    }
                                    content
                                }
                        )
                    }
                }
            }
        }
    }
}