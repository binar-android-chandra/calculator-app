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
    val hasResult = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }

    fun handleBackSpace() {
        if (text.value.isNotEmpty()) text.value = text.value.substring(0, text.value.length - 1)
    }

    fun handleAllClear() {
        isAddOperator.value = false
        hasResult.value = false
        text.value = ""
        result.value = ""
    }

    fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex) {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }

    fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when (operator) {
                    'x' -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' -> {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else -> {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if (i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while (list.contains('x') || list.contains('/')) {
            list = calcTimesDiv(list)
        }
        return list
    }

    fun digitsOperators(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in text.value) {
            if (character.isDigit())
                currentDigit += character
            else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if (currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }

    fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toInt().toString()
    }

    fun equalsAction() {
        result.value = calculateResults()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Column(
                modifier = Modifier.height(100.dp),
                horizontalAlignment = Alignment.End
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
                Text(
                    text = if (result.value.isNotEmpty()) "=${result.value}" else "",
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
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "delete",
                                    modifier = Modifier.height(40.dp),
                                    tint = MaterialTheme.colors.primary
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
                                onClick = {
                                    equalsAction()
                                    hasResult.value = true
                                },
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
                    items(1) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(5.dp),
                        ) {
                            Button(
                                onClick = { handleAllClear() },
                                modifier = Modifier.fillMaxSize(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                            ) {
                                Text(
                                    text = "AC",
                                    fontSize = 25.sp,
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
                                if (hasResult.value) text.value = result.value
                                isAddOperator.value = true
                                hasResult.value = false
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
                                if (hasResult.value) text.value = result.value
                                isAddOperator.value = true
                                hasResult.value = false
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
                                if (hasResult.value) text.value = result.value
                                isAddOperator.value = true
                                hasResult.value = false
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
                                if (hasResult.value) text.value = result.value
                                isAddOperator.value = true
                                hasResult.value = false
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