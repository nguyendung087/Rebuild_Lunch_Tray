package com.example.rebuildlunchtray.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun FormattedSubTotal(
    @StringRes priceLabel : Int,
    orderPrice : Double,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(priceLabel, orderPrice),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.W700,
        modifier = modifier
    )
}