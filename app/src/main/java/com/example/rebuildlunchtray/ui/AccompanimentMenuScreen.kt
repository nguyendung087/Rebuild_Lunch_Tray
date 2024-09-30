package com.example.rebuildlunchtray.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.rebuildlunchtray.data.DataSource
import com.example.rebuildlunchtray.models.MenuItem

@Composable
fun AccompanimentMenuScreen(
    options : List<MenuItem.AccompanimentItem>,
    subtotal : Double,
    onSelectionChanged : (MenuItem.AccompanimentItem) -> Unit,
    onNextButton : () -> Unit,
    onCancelButton : () -> Unit
) {
    BaseMenuScreen(
        subtotal = subtotal,
        options = options,
        onSelectionChanged = onSelectionChanged as (MenuItem) -> Unit,
        onCancelButton = onCancelButton,
        onNextButton = onNextButton
    )
}

@Preview(showBackground = true)
@Composable
private fun AccompanimentMenuPreview() {
    AccompanimentMenuScreen(
        options = DataSource.accompanimentMenuItems,
        subtotal = 1.0,
        onSelectionChanged = {},
        onNextButton = { },
        onCancelButton = {}
    )
}