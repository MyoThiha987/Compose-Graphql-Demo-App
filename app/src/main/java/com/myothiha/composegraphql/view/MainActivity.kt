package com.myothiha.composegraphql.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.myothiha.composegraphql.domain.Ship
import com.myothiha.composegraphql.ui.theme.ComposeGraphqlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: SpacexShipViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeGraphqlTheme {

                val shipsPagingItems = viewModel.ships.collectAsLazyPagingItems()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListItem(
                        modifier = Modifier.padding(innerPadding),
                        headlineContent = { Text(text = "SpaceX Ships") },
                        supportingContent = {

                            when (shipsPagingItems?.loadState?.refresh) {
                                is LoadState.Loading -> {
                                    LoadingView()
                                }

                                is LoadState.Error -> {
                                    val error =
                                        shipsPagingItems.loadState.refresh as LoadState.Error
                                    error.error.localizedMessage?.let { it1 ->
                                        ErrorMessage(message = it1) {
                                            shipsPagingItems.retry()
                                        }
                                    }
                                }

                                else -> {
                                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                        items(count = shipsPagingItems.itemCount) { index ->
                                            shipsPagingItems.get(index)
                                                ?.let { ShipItemView(ship = it) }
                                        }
                                    }

                                    if (shipsPagingItems.loadState.append is LoadState.Loading) {
                                        LoadingView()
                                    } else if (shipsPagingItems.loadState.append is LoadState.Error) {
                                        ErrorMessage(message = "Retry") {

                                        }
                                    }

                                }
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun ShipItemView(
    ship: Ship
) {
    val context = LocalContext.current

    Card(
        onClick = { }, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context)
                    .data(ship.image)
                    .crossfade(true).build(),
                contentDescription = null,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentSize()
                    )
                }
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Ship name - ${ship.name}", color = Color.Black)
                Text(text = "Active - ${ship.active}", color = Color.Black)
            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
            maxLines = 2
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Retry")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShipItemViewPreview() {
    ComposeGraphqlTheme {
        ShipItemView(
            ship = Ship(
                active = true,
                image = "https://i.imgur.com/jmj8Sh2.jpg",
                id = "11",
                name = "dffdf",
                model = ""
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeGraphqlTheme {
        Greeting("Android")
    }
}