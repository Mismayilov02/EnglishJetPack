package com.master.englishlearningjetpack

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.master.englishlearningjetpack.room.DatabaseViewModel
import com.master.englishlearningjetpack.room.History
import com.master.englishlearningjetpack.room.ViewModelFactory


var textValues: MutableList<MutableState<String>> = mutableListOf()
var colorValues: MutableList<MutableState<Color>> = mutableListOf()

var trueAnsvarPosiyions: MutableList<Int> = mutableListOf()
lateinit var historyR: State<List<History>>
var answerConum: Int = 0
var first = true
var enValue = ""
var azValue = ""
var randomNumber: Int = 0
var baseMyId: Int = 0
var defaultEn = true
lateinit var navControllerBase: NavController

@Composable
fun questions(navController: NavController, myId: Int) {

    val secilenIndeks = remember {
        mutableStateOf(0)
    }
    val radioButtonListe = listOf("En", "Az")

    val openWrongDialogValue = remember {
        mutableStateOf(false)
    }
    val refreshDailogValue = remember {
        mutableStateOf(false)
    }

    navControllerBase = navController
    baseMyId = myId

    addValuesText()
    addColorValues()


    val context = LocalContext.current
    val viewModel: DatabaseViewModel =
        viewModel(factory = ViewModelFactory(context.applicationContext as Application, myId))
    historyR = viewModel.getAllData.observeAsState(listOf())
    generateGuestions(refreshDailogValue)

    if (openWrongDialogValue.value) {
        customDialoq(open = openWrongDialogValue, refreshDailogValue)
    }
    if (refreshDailogValue.value) {
        refreshDialog(refreshDailogValue)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp, 10.dp, 150.dp), verticalArrangement = Arrangement.Bottom
    ) {

            Column(modifier = Modifier
                .weight(0.4f)
                .padding(start = 5.dp, 15.dp)) {
                radioButtonListe.forEachIndexed { i, sellect ->
                    Row(/*modifier = Modifier.fillMaxSize()*/) {
                        RadioButton(
                            selected = sellect == radioButtonListe[secilenIndeks.value],
                            onClick = {
                                            secilenIndeks.value = i
                                            defaultEn = i == 0
                                        })
                        Text(text = sellect, modifier = Modifier.padding(10.dp))
                    }
                }
            }



        Text(
            text = textValues[4].value,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.3f))
        Row(
            modifier = Modifier
//            .fillMaxSize()
                .fillMaxWidth()
            /*      .background(color = Color.LightGray)*/, verticalAlignment = Alignment.Bottom
        ) {

            questionCardViewDesign(
                text = textValues.get(0).value,
                colorValues[0].value,
                0.5f,
                0,
                openWrongDialogValue,
                refreshDailogValue
            )
            questionCardViewDesign(
                text = textValues.get(1).value,
                colorValues[1].value,
                1f,
                1,
                openWrongDialogValue,
                refreshDailogValue
            )
        }

        Row(
            modifier = Modifier
//            .fillMaxSize()
                .fillMaxWidth()
            /* .background(color = Color.LightGray)*/, verticalAlignment = Alignment.Bottom
        ) {
            questionCardViewDesign(
                text = textValues.get(2).value,
                colorValues[2].value,
                0.5f,
                2,
                openWrongDialogValue,
                refreshDailogValue
            )
            questionCardViewDesign(
                text = textValues.get(3).value,
                colorValues[3].value,
                1f,
                3,
                openWrongDialogValue,
                refreshDailogValue
            )
        }
    }

}

@Composable
fun questionCardViewDesign(
    text: String,
    color: Color,
    width: Float,
    position: Int,
    open: MutableState<Boolean>, refreshDailogValue: MutableState<Boolean>
) {


    Card(
        modifier = Modifier
            .fillMaxWidth(width)
            .clickable {
                chechValueSetColor(checkBtnPosition = position, open, refreshDailogValue)
            }
            .height(70.dp)
            .padding(2.dp)
            .border(0.dp, Transparent),
        backgroundColor = color,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(0.dp, Transparent),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text, textAlign = TextAlign.Center, color = Color.White)
        }
    }


}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun customDialoq(open: MutableState<Boolean>, refreshDailogValue: MutableState<Boolean>) {
//    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Bottom ) {
    Dialog(
        onDismissRequest = { open.value = false }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {


        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Card(
                elevation = 5.dp, shape = RoundedCornerShape(15.dp), modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)/* , backgroundColor = Color.Green*/
            ) {

                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = enValue,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(15.dp, 10.dp, 15.dp, 0.dp)
                    )
                    Text(
                        text = azValue,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(15.dp, 5.dp, 15.dp, 15.dp)
                    )

                    Button(
                        onClick = {
                            open.value = false
                            first = true
                            generateGuestions(refreshDailogValue)
                            setBtnColorDefault()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x52FFFFFFF))
                    ) {
                        Text(text = "NEXT", textAlign = TextAlign.Center, color = Color.White)
                    }
                }
            }
        }

    }


}

@Composable
fun addValuesText() {
    textValues.add(remember {
        mutableStateOf("")
    })
    textValues.add(remember {
        mutableStateOf("")
    })
    textValues.add(remember {
        mutableStateOf("")
    })
    textValues.add(remember {
        mutableStateOf("")
    })
    textValues.add(remember {
        mutableStateOf("")
    })
}

//
@Composable
fun addColorValues() {
    colorValues.add(remember {
        mutableStateOf(Color(0x5CFFFFFF))
    })
    colorValues.add(remember {
        mutableStateOf(Color(0x80009688))
    })
    colorValues.add(remember {
        mutableStateOf(Color(0x80009688))
    })
    colorValues.add(remember {
        mutableStateOf(Color(0x5CFFFFFF))
    })
}


fun chechValueSetColor(
    checkBtnPosition: Int,
    open: MutableState<Boolean>,
    refreshDailogValue: MutableState<Boolean>
) {
    if (checkBtnPosition == answerConum) {
        colorValues.get(checkBtnPosition).value = Color(0x5C14CAF50)
        trueAnsvarPosiyions.add(randomNumber)
        setBtnColorDefault()
        first = true
        generateGuestions(refreshDailogValue)

    } else {
        colorValues.get(checkBtnPosition).value = Color(0x5BFFF0000)
        open.value = true
    }
}

fun setBtnColorDefault() {
    colorValues.get(0).value = Color(0x5CFFFFFF)
    colorValues.get(1).value = Color(0x80009688)
    colorValues.get(2).value = Color(0x80009688)
    colorValues.get(3).value = Color(0x5CFFFFFF)
}


fun generateGuestions(refreshDailogValue: MutableState<Boolean>) {

    var historyG: List<History>? = mutableListOf()
    try {
        historyG = historyR.value
    } catch (e: Exception) {
        println(e)
    }


    if (historyG?.size != 0 && first && historyG?.size!! > trueAnsvarPosiyions.size) {
        first = false
        val sellectDefaultNumber = mutableListOf<Int>()

        do {
            randomNumber = (0..(historyG!!.count() - 1)).random()
        } while (trueAnsvarPosiyions.contains(randomNumber))

        sellectDefaultNumber.add(randomNumber)

        textValues[4].value = checkAzOrEn(!defaultEn , randomNumber)
        azValue =  checkAzOrEn(defaultEn , randomNumber)
        enValue =checkAzOrEn(!defaultEn , randomNumber)

        answerConum = (0..3).random()

        for (i in 0..3) {
            if (answerConum == i) {
                textValues[i].value =  checkAzOrEn(defaultEn , randomNumber)
            } else {
                var defaultAnsver: Int
                do {
                    defaultAnsver = (0..(historyG.count() - 1)).random()
                    textValues[i].value = checkAzOrEn(defaultEn , defaultAnsver)
                } while (sellectDefaultNumber.contains(defaultAnsver))
                sellectDefaultNumber.add(defaultAnsver)
            }
        }
    } else if (historyG?.size!! == trueAnsvarPosiyions.size && historyG?.size != 0) {
        refreshDailogValue.value = true
    }

}

fun checkAzOrEn(defaultEn:Boolean , id:Int ):String{
    if (defaultEn) {
        return historyR.value[id].az!!
    }
    return historyR.value[id].en!!
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun refreshDialog(refreshDailogValue: MutableState<Boolean>) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.guestion))
    Dialog(
        onDismissRequest = { refreshDailogValue.value = false }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.5f), shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {

                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth(0.6f)
                            .align(Alignment.Center)
                            .fillMaxHeight(0.4f)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Soru Bitdi. Yeniden Baslamaq istermisin",
                    modifier = Modifier.padding(30.dp, 20.dp, 30.dp, 10.dp),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(), verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = {
                            navControllerBase.navigate("showWord/$baseMyId") {
                                popUpTo("questions") { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(50.dp),
                        shape = RoundedCornerShape(
                            topStart = 5.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            refreshDailogValue.value = false
                            trueAnsvarPosiyions.clear()
                            generateGuestions(refreshDailogValue)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            bottomStart = 0.dp,
                            topEnd = 5.dp
                        )
                    ) {
                        Text(text = "Refresh")
                    }
                }


            }
        }
    }
}