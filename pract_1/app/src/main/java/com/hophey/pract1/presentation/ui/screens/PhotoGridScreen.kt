package com.hophey.pract1.presentation.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
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
import java.io.OutputStream

@Composable
fun PhotoGridScreenRoot(
    photoGridViewModel: PhotoGridViewModel = koinViewModel()
){
    val photoGridState = photoGridViewModel.uiState.collectAsStateWithLifecycle().value
    val detailsSheetState = photoGridViewModel.sheetState.collectAsStateWithLifecycle().value
    Scaffold(
        topBar = { TopPhotosScreenBar() }
    ) { contentPadding ->
        when (photoGridState){
            is PhotoGridUiState.Success -> {
                PhotoGrid(
                    modifier = Modifier.padding(contentPadding),
                    photos = photoGridState.photos,
                    detailedPhoto = detailsSheetState.photo,
                    showSheet = detailsSheetState.showSheet,
                    isSaving = detailsSheetState.isSaving,
                    onCardClicked = photoGridViewModel::onCardClick,
                    onDetailsDismiss = photoGridViewModel::dismissSheet,
                    onSavePhoto = photoGridViewModel::savePhoto
                    )
            }
            is PhotoGridUiState.Error -> {
                PhotoGridError(
                    modifier = Modifier.padding(contentPadding),
                    errorMessage = photoGridState.error,
                    retryLoadPhotos = photoGridViewModel::loadPhotos
                )
            }
            is PhotoGridUiState.Loading -> {
                PhotoGridLoad(modifier = Modifier.padding(contentPadding))
            }
        }
    }
}

@Composable
private fun PhotoGrid(
    photos: List<Photo>,
    detailedPhoto: Photo?,
    showSheet: Boolean,
    isSaving: Boolean,
    onCardClicked: (Photo) -> Unit,
    onDetailsDismiss : () -> Unit,
    onSavePhoto: (String, OutputStream) -> Unit,
    modifier: Modifier = Modifier,
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

    if (showSheet){
        PhotoDetailsSheet(
            detailedPhoto = detailedPhoto,
            onDismiss = onDetailsDismiss,
            onSavePhoto = onSavePhoto,
            isSaving = isSaving
        )
    }
}

@Composable
private fun PhotoCard(
    photo: Photo,
    onClicked: (Photo) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                onClick = { onClicked(photo) }
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

@Composable
private fun PhotoGridError(
    errorMessage: String,
    retryLoadPhotos: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = retryLoadPhotos
        ) {
            Text(
                text = stringResource(R.string.retry_button_text)
            )
        }
    }
}

@Composable
private fun PhotoGridLoad(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhotoDetailsSheet(
    detailedPhoto: Photo?,
    isSaving: Boolean,
    onDismiss: () -> Unit,
    onSavePhoto: (String, OutputStream) -> Unit
){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (detailedPhoto == null){
        return
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = null,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        val context = LocalContext.current

        val createFileLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument("image/jpeg")
        ) { uri ->
            uri?.let {
                val outputStream = context.contentResolver.openOutputStream(uri)
                outputStream?.let {
                    onSavePhoto(detailedPhoto.downloadUrl, outputStream)
                }
            }
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxWidth = 400.dp, maxHeight = 400.dp)
                    //.aspectRatio(detailedPhoto.width.toFloat() / detailedPhoto.height.toFloat())
                    .padding(16.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(detailedPhoto.downloadUrl)
                    .memoryCacheKey(detailedPhoto.id)
                    .diskCacheKey(detailedPhoto.id)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                error = rememberVectorPainter(Icons.Default.Close),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = StringBuilder()
                    .append(stringResource(R.string.detailed_photo_description_author))
                    .append("${detailedPhoto.author}\n")
                    .append(stringResource(R.string.detailed_photo_description_dimensions))
                    .append(detailedPhoto.width)
                    .append("x")
                    .append(detailedPhoto.height)
                    .toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                enabled = !isSaving,
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { createFileLauncher.launch("photo_${detailedPhoto.author.replace(" ","")}_${detailedPhoto.id}.jpg") }
            ) {
                if (isSaving){
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text(
                        text = stringResource(R.string.download_button_text)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopPhotosScreenBar(){
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
        PhotoGrid(
            modifier = Modifier.padding(PaddingValues(16.dp)),
            photos = listOf(
                Photo(
                    id = "1",
                    author = "Sample Author",
                    width = 444,
                    height = 222,
                    url = "url",
                    downloadUrl = "downloadUrl"
                )
            ),
            onCardClicked = { },
            showSheet = false,
            onDetailsDismiss = { },
            detailedPhoto = null,
            isSaving = false,
            onSavePhoto = {_, _ -> }
        )
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

@Preview(
    showBackground = true
)
@Composable
fun DetailsSheetPreview(){
    Pract1Theme {
        PhotoDetailsSheet(
            detailedPhoto = Photo(
                id = "0",
                author = "Lol",
                width = 1080,
                height = 1080,
                url = "https://unsplash.com/photos/N7XodRrbzS0",
                downloadUrl = "https://picsum.photos/id/2/5000/3333"
            ),
            onDismiss = { },
            isSaving = false,
            onSavePhoto = {_, _ -> }
        )
    }
}