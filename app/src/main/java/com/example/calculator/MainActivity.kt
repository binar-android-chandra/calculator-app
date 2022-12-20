package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val numbers = (1..9).toList()
    val isAddOperator = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf("") }

    fun handleBackSpace() {
        text.value = text.value.substring(0, text.value.length - 1)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Box(
                modifier = Modifier.height(60.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = text.value.ifEmpty { "0" },
                    fontSize = 40.sp,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
            }
            Row {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .width(300.dp)
                ) {
                    items(numbers.size) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Button(
                                onClick = {
                                    text.value = "${text.value}${numbers[it]}"
                                    isAddOperator.value = false
                                },
                                modifier = Modifier.fillMaxSize(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                            ) {
                                Text(
                                    text = "${numbers[it]}",
                                    fontSize = 30.sp,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                        }
                    }
                    items(1) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Button(
                                onClick = { handleBackSpace() },
                                modifier = Modifier.fillMaxSize(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                                enabled = text.value.isNotEmpty()
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "delete",
                                    modifier = Modifier.height(40.dp)
                                )
                            }
                        }
                    }
                    items(1) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(5.dp),
                        ) {
                            Button(
                                onClick = {
                                    text.value = "${text.value}0"
                                    isAddOperator.value = false
                                },
                                modifier = Modifier.fillMaxSize(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                            ) {
                                Text(
                                    text = "0",
                                    fontSize = 30.sp,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                        }
                    }
                    items(1) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(5.dp),
                        ) {
                            Button(
                                onClick = { },
                                modifier = Modifier.fillMaxSize(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                            ) {
                                Text(
                                    text = "=",
                                    fontSize = 30.sp,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.width(100.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Button(
                            onClick = {
                                if (isAddOperator.value) handleBackSpace()
                                isAddOperator.value = true
                                text.value = "${text.value}+"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                        ) {
                            Text(
                                text = "+",
                                fontSize = 30.sp,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Button(
                            onClick = {
                                if (isAddOperator.value) handleBackSpace()
                                isAddOperator.value = true
                                text.value = "${text.value}-"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                        ) {
                            Text(
                                text = "-",
                                fontSize = 30.sp,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Button(
                            onClick = {
                                if (isAddOperator.value) handleBackSpace()
                                isAddOperator.value = true
                                text.value = "${text.value}x"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                        ) {
                            Text(
                                text = "x",
                                fontSize = 30.sp,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Button(
                            onClick = {
                                if (isAddOperator.value) handleBackSpace()
                                isAddOperator.value = true
                                text.value = "${text.value}/"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                        ) {
                            Text(
                                text = "/",
                                fontSize = 30.sp,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                }

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTheme {
        MainScreen()
    }
}