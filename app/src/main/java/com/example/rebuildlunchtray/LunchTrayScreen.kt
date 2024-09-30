package com.example.rebuildlunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rebuildlunchtray.data.DataSource
import com.example.rebuildlunchtray.ui.AccompanimentMenuScreen
import com.example.rebuildlunchtray.ui.CheckoutScreen
import com.example.rebuildlunchtray.ui.EntreeMenuScreen
import com.example.rebuildlunchtray.ui.LunchViewModel
import com.example.rebuildlunchtray.ui.SideDishScreen
import com.example.rebuildlunchtray.ui.StartOrderScreen

enum class Screen(@StringRes val title : Int) {
    START(title = R.string.start_order),
    ENTREE(title = R.string.choose_entree),
    SIDE_DISH(title = R.string.choose_side_dish),
    ACCOMPANIMENT(title = R.string.choose_accompaniment),
    CHECKOUT(title = R.string.order_checkout)
}

@Composable
fun LunchTrayScreen(
    uiViewModel : LunchViewModel = viewModel()
) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.START.name
    )

    Scaffold(
        topBar = {
            LunchTrayAppBar(
                title = currentScreen.title,
                canNavigate = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by uiViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = Screen.START.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.START.name) {
                StartOrderScreen(
                    onNextButton = {
                        navController.navigate(Screen.ENTREE.name)
                    }
                )
            }

            composable(Screen.ENTREE.name) {
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    subtotal = uiState.itemTotalPrice,
                    onSelectionChanged = {
                        uiViewModel.updateEntree(it)
                    },
                    onNextButton = {
                        navController.navigate(Screen.SIDE_DISH.name)
                    },
                    onCancelButton = {
                        uiViewModel.resetOrder()
                        navController.popBackStack(Screen.START.name, inclusive = false)
                    }
                )
            }

            composable(Screen.SIDE_DISH.name) {
                SideDishScreen(
                    options = DataSource.sideDishMenuItems,
                    subtotal = uiState.itemTotalPrice,
                    onSelectionChanged = {
                        uiViewModel.updateSideDish(it)
                    },
                    onNextButton = {
                        navController.navigate(Screen.ACCOMPANIMENT.name)
                    },
                    onCancelButton = {
                        uiViewModel.resetOrder()
                        navController.popBackStack(Screen.START.name, inclusive = false)
                    }
                )
            }

            composable(Screen.ACCOMPANIMENT.name) {
                AccompanimentMenuScreen(
                    options = DataSource.accompanimentMenuItems,
                    subtotal = uiState.itemTotalPrice,
                    onSelectionChanged = {
                        uiViewModel.updateAccompaniment(it)
                    },
                    onNextButton = {
                        navController.navigate(Screen.CHECKOUT.name)
                    },
                    onCancelButton = {
                        uiViewModel.resetOrder()
                        navController.popBackStack(Screen.START.name, inclusive = false)
                    }
                )
            }

            composable(Screen.CHECKOUT.name) {
                CheckoutScreen(
                    orderUiState = uiState,
                    onCancelButton = {
                        uiViewModel.resetOrder()
                        navController.popBackStack(Screen.START.name, inclusive = false)
                    },
                    onSubmitButton = {
                        uiViewModel.resetOrder()
                        navController.popBackStack(Screen.START.name, inclusive = false)
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LunchTrayAppBar(
    @StringRes title : Int,
    canNavigate : Boolean,
    navigateUp : () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(canNavigate) {
                    IconButton(
                        onClick = navigateUp,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Preview
@Composable
private fun LunchTrayPreview() {
    LunchTrayScreen()
}