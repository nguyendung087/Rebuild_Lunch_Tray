package com.example.rebuildlunchtray.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.rebuildlunchtray.R
import com.example.rebuildlunchtray.components.FormattedSubTotal
import com.example.rebuildlunchtray.data.DataSource
import com.example.rebuildlunchtray.models.MenuItem

@Composable
fun BaseMenuScreen(
    subtotal : Double,
    options : List<MenuItem>,
    onSelectionChanged : (MenuItem) -> Unit,
    onCancelButton : () -> Unit,
    onNextButton: () -> Unit
) {
    var selectedValueName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        options.forEach { item ->
            Row(
                modifier = Modifier
                    .selectable(
                    selected = selectedValueName == item.name,
                    onClick = {
                        selectedValueName = item.name
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedValueName == item.name,
                    onClick = {
                        selectedValueName = item.name
                        onSelectionChanged(item)
                    }
                )
                DishInformation(
                    item,
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_small)
                    )
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_small)
            ),
            thickness = dimensionResource(R.dimen.thickness_divider)
        )
        Text(
            text = stringResource(R.string.subtotal, subtotal),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = dimensionResource(R.dimen.padding_medium))
        )
        Spacer(modifier = Modifier.weight(1f))
        NavigationButton(
            onCancelButton = onCancelButton,
            onNextButton = onNextButton,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DishInformation(
    menuItem: MenuItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = menuItem.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = menuItem.description,
            style = MaterialTheme.typography.titleSmall,
        )
        Text(
            text = menuItem.getFormattedPrice(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}

@Composable
private fun NavigationButton(
    onCancelButton: () -> Unit,
    onNextButton : () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_medium)
        )
    ) {
        OutlinedButton(
            onClick = onCancelButton,
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.cancel)
            )
        }
        Button(
            onClick = onNextButton,
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.next)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BaseMenuPreview() {
    BaseMenuScreen(
        subtotal = 1.0,
        options = DataSource.entreeMenuItems,
        onCancelButton = {},
        onSelectionChanged = { },
        onNextButton = {}
    )
}