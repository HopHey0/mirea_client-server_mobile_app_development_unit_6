package com.hophey.pract2.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hophey.pract2.R
import com.hophey.pract2.domain.entity.NobelPrize
import com.hophey.pract2.presentation.viewModel.FilterRowState
import com.hophey.pract2.presentation.viewModel.NobelPrizeDetailsState
import com.hophey.pract2.presentation.viewModel.NobelPrizesUiState
import com.hophey.pract2.presentation.viewModel.NobelPrizesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NobelPrizesScreenRoot(
    viewModel: NobelPrizesViewModel = koinViewModel()
) {
    val filterState = viewModel.filterRowState.collectAsStateWithLifecycle().value
    val nobelPrizesUiState = viewModel.nobelPrizesState.collectAsStateWithLifecycle().value
    val detailsState = viewModel.laureatesDetailsUiState.collectAsStateWithLifecycle().value
    Scaffold(
        topBar = { NobelPrizesTopBar() }
    ) { contentPaddings ->
        NobelPrizesScreenContent(
            filterState = filterState,
            nobelPrizesState = nobelPrizesUiState,
            detailsState = detailsState,
            onYearChanged = viewModel::changeYearFilter,
            onYearListDismiss = viewModel::onYearListDismiss,
            onYearListExpand = viewModel::onYearListExpand,
            onFilterClicked = viewModel::findNobelPrizes,
            onDetailsClick = viewModel::onNobelPrizeClicked,
            onDetailsDismiss = viewModel::onNobelPrizeDismiss,
            modifier = Modifier.padding(contentPaddings),
        )
    }
}

@Composable
fun NobelPrizesScreenContent(
    filterState: FilterRowState,
    nobelPrizesState: NobelPrizesUiState,
    detailsState: NobelPrizeDetailsState,
    onYearChanged: (String) -> Unit,
    onYearListDismiss: () -> Unit,
    onYearListExpand: () -> Unit,
    onFilterClicked: (String) -> Unit,
    onDetailsClick: (NobelPrize) -> Unit,
    onDetailsDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        FilterRow(
            state = filterState,
            onYearChanged = onYearChanged,
            onYearListDismiss = onYearListDismiss,
            onYearListExpand = onYearListExpand,
            onFilterClicked = onFilterClicked,
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (nobelPrizesState) {
            is NobelPrizesUiState.Loading -> NobelPrizesLoading()
            is NobelPrizesUiState.Success -> NobelPrizesList(nobelPrizesState.nobelPrizes, detailsState, onDetailsClick, onDetailsDismiss)
            is NobelPrizesUiState.Error -> NobelPrizesError(nobelPrizesState.error)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterRow(
    state: FilterRowState,
    onYearChanged: (String) -> Unit,
    onYearListDismiss: () -> Unit,
    onYearListExpand: () -> Unit,
    onFilterClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExposedDropdownMenuBox(
            expanded = state.yearListExpanded,
            onExpandedChange = { if (it) onYearListExpand() else onYearListDismiss() },
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = state.chosenYear,
                onValueChange = {},
                readOnly = true,
                label = { Text("Год") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.yearListExpanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = state.yearListExpanded,
                onDismissRequest = onYearListDismiss
            ) {
                state.yearsList.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year) },
                        onClick = { onYearChanged(year) }
                    )
                }
            }
        }

        Button(
            enabled = !(state.isLoading || state.chosenYear.isBlank()),
            onClick = { onFilterClicked(state.chosenYear) }
        ) {
            Text("Фильтр")
        }
    }
}

@Composable
fun NobelPrizesList(
    nobelPrizes: List<NobelPrize>,
    detailsState: NobelPrizeDetailsState,
    onDetailsClick: (NobelPrize) -> Unit,
    onDetailsDismiss: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(nobelPrizes) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailsClick(item) }
                    .padding(vertical = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${item.awardYear} — ${item.category.uppercase()}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.laureates.joinToString(separator = ", ") { it.fullName },
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
    if (detailsState.showSheet && detailsState.nobelPrize != null) {
        NobelPrizeDetailSheet(
            nobelPrize = detailsState.nobelPrize,
            onDismiss = onDetailsDismiss
        )
    }
}

@Composable
fun NobelPrizesError(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NobelPrizesLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(56.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelPrizeDetailSheet(
    nobelPrize: NobelPrize,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Год: ${nobelPrize.awardYear}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Категория: ${nobelPrize.category}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Лауреаты:",
                style = MaterialTheme.typography.bodyLarge
            )
            nobelPrize.laureates.forEachIndexed { index, laureate ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${index + 1}. ${laureate.fullName}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Мотивация: ${laureate.motivation}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelPrizesTopBar() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.top_app_bar_text)
            )
        }
    )
}