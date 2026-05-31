package com.hophey.pract1.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.hophey.pract1.R
import com.hophey.pract1.domain.entity.Photo
import com.hophey.pract1.presentation.theme.Pract1Theme
import com.hophey.pract1.presentation.ui.viewModel.PhotoGridUiState
import com.hophey.pract1.presentation.ui.viewModel.PhotoGridViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhotoGridScreenRoot(
    photoGridViewModel: PhotoGridViewModel = koinViewModel()
){
    val photoGridState = photoGridViewModel.uiState.collectAsStateWithLifecycle().value
    Scaffold(
        topBar = { TopPhotosScreenBar() }
    ) { contentPadding ->
        when (photoGridState){
            is PhotoGridUiState.Success -> {
                PhotoGrid(
                    modifier = Modifier.padding(contentPadding),
                    photos = photoGridState.photos,
                    onCardClicked = { }
                    )
            }
            is PhotoGridUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(contentPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = photoGridState.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = photoGridViewModel::loadPhotos
                    ) {
                        Text(
                            text = stringResource(R.string.retry_button_text)
                        )
                    }
                }
            }
            is PhotoGridUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }
        }
    }
}

@Composable
fun PhotoGrid(
    modifier: Modifier = Modifier,
    photos: List<Photo> = emptyList(),
    onCardClicked: () -> Unit
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(
            photos
        ) { photo ->
            PhotoCard(
                photo,
                onCardClicked
            )
        }
    }
}

@Composable
fun PhotoCard(
    photo: Photo,
    onClicked: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .clickable(
                onClick = onClicked
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column (
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = photo.author
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://picsum.photos/id/${photo.id}/400/300")
                    .memoryCacheKey(photo.id)
                    .diskCacheKey(photo.id)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                error = rememberVectorPainter(Icons.Default.Close),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopPhotosScreenBar(){
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.top_photos_screen_bar_label),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
                }
    )
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PHONE
)
@Composable
fun PhotoGridScreenPreview(){
    Pract1Theme {
        PhotoGridScreenRoot()
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PhotoCardPreview(){
    Pract1Theme {
        PhotoCard(
            Photo(
                id = "0",
                author = "Lol",
                width = 1080,
                height = 1080,
                url = "https://unsplash.com/photos/N7XodRrbzS0",
                downloadUrl = "https://picsum.photos/id/2/5000/3333"
            ),
            onClicked = { }
        )
    }
}