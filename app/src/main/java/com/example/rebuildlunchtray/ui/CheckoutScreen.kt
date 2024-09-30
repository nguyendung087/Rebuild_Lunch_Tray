package com.example.rebuildlunchtray.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.rebuildlunchtray.R
import com.example.rebuildlunchtray.models.LunchUiState
import com.example.rebuildlunchtray.models.MenuItem

@Composable
fun CheckoutScreen(

    orderUiState : LunchUiState,
    onCancelButton : () -> Unit,
    onSubmitButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.order_summary),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        CheckoutDetails(
            orderUiState.entreeItem?.name,
            orderUiState.entreeItem?.getFormattedPrice()
        )
        CheckoutDetails(
            orderUiState.sideDishItem?.name,
            orderUiState.sideDishItem?.getFormattedPrice()
        )
        CheckoutDetails(
            orderUiState.accompanimentItem?.name,
            orderUiState.accompanimentItem?.getFormattedPrice()
        )
        HorizontalDivider(
            thickness = dimensionResource(R.dimen.thickness_divider),
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
        )
        SubCostTotal(
            priceLabel = R.string.total,
            orderPrice = orderUiState.itemTotalPrice,
            modifier = Modifier
                .align(Alignment.End)
        )
        SubCostTotal(
            priceLabel = R.string.tax,
            orderPrice = orderUiState.orderTax,
            modifier = Modifier
                .align(Alignment.End)
        )
        SubCostTotal(
            priceLabel = R.string.subtotal,
            orderPrice = orderUiState.orderTotalPrice,
            modifier = Modifier
                .align(Alignment.End)
        )
        Spacer(modifier = Modifier.weight(1f))
        NavigationButton(
            onCancelButton = onCancelButton,
            onSubmitButton = onSubmitButton,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CheckoutDetails(
    menuItemName : String?,
    menuItemPrice : String?
) {
    Row(
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small)),
    ) {
        Text(
            text = menuItemName ?: "",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = menuItemPrice ?: "",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.W500,
            color = Color.Red
        )
    }
}

@Composable
private fun NavigationButton(
    onCancelButton: () -> Unit,
    onSubmitButton : () -> Unit,
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
            onClick = onSubmitButton,
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.submit)
            )
        }
    }
}

@Composable
private fun SubCostTotal(
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

@Preview(showBackground = true)
@Composable
private fun CheckoutPreview() {

}