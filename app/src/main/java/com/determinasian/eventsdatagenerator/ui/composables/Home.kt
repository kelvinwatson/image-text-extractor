package com.determinasian.eventsdatagenerator.ui.composables

import android.net.Uri
import androidx.activity.result.ActivityResultCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.determinasian.eventsdatagenerator.R
import com.determinasian.eventsdatagenerator.ui.state.UiState
import com.determinasian.eventsdatagenerator.ui.theme.ImageTextExtractorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(onUris: ActivityResultCallback<List<Uri>>, uiState: UiState) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = stringResource(R.string.app_name)) }
            )
        },
        bottomBar = {

        }
    ) { paddingValues ->

        var tabIndex by remember { mutableIntStateOf(0) }

        val tabs = listOf("Create model", "Add events")

        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight()) {

            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                        }
                    )
                }
            }

            when (tabIndex) {
                0 -> AddNewEvents(paddingValues = paddingValues)
                1 -> TfModelCreation(
                    paddingValues = paddingValues,
                    uiState = uiState,
                    onUris = onUris
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ImageTextExtractorTheme {
        Home({}, UiState.NotStarted)
    }
}