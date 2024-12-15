package com.example.lemonade

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    // Variables d'état
    var step by remember { mutableStateOf(1) }
    var pressCount by remember { mutableStateOf(Random.nextInt(2, 5)) }

    // Image et texte associés à chaque étape
    val imageRes = when (step) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val textRes = when (step) {
        1 -> R.string.instruction_select_lemon_tree
        2 -> R.string.instruction_squeeze_lemon
        3 -> R.string.instruction_drink_lemonade
        else -> R.string.instruction_restart
    }

    // Interface utilisateur
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Texte d'instruction
            Text(
                text = stringResource(id = textRes),
                style = MaterialTheme.typography.titleLarge
            )

            // Image cliquable
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = stringResource(id = textRes),
                modifier = Modifier
                    .size(200.dp)
                    .clickable {
                        Log.d("LemonadeApp", "Image clicked, current step: $step")

                        when (step) {
                            1 -> {
                                // Étape 1 : Passer à l'étape suivante (Presser le citron)
                                step = 2
                            }
                            2 -> {
                                // Étape 2 : Presser le citron
                                if (pressCount > 0) {
                                    pressCount-- // Réduire le nombre de pressions restantes
                                } else {
                                    step = 3 // Passer à l'étape suivante (Boire la citronnade)
                                }
                            }
                            3 -> {
                                // Étape 3 : Boire la citronnade
                                step = 4 // Passer à l'étape suivante (Verre vide)
                            }
                            else -> {
                                // Étape 4 : Réinitialiser (Revenir au début)
                                step = 1
                                pressCount = Random.nextInt(2, 5) // Recalculer le nombre de pressions nécessaires
                            }
                        }
                    }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}
