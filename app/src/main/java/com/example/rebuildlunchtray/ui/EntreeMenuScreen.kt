package com.example.rebuildlunchtray.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.rebuildlunchtray.data.DataSource
import com.example.rebuildlunchtray.models.MenuItem

@Composable
fun EntreeMenuScreen(
    options : List<MenuItem.EntreeItem>,
    subtotal : Double,
    onSelectionChanged : (MenuItem.EntreeItem) -> Unit,
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
private fun EntreeMenuPreview() {
    EntreeMenuScreen(
        options = DataSource.entreeMenuItems,
        subtotal = 1.0,
        onSelectionChanged = {},
        onNextButton = { },
        onCancelButton = {}
    )
}
