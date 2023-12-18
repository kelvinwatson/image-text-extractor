package com.determinasian.eventsdatagenerator.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.determinasian.eventsdatagenerator.R

@Composable
fun AddNewEvents(
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues)
    ) {

        item {

            Icon(
                painter = painterResource(id = R.drawable.twotone_tag_faces_24),
                contentDescription = null
            )

        }

        item {
            Text(text = stringResource(id = R.string.not_started_explanation))
        }
    }

}
