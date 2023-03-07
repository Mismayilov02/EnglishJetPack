package com.master.englishlearningjetpack

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.master.englishlearningjetpack.room.DatabaseViewModel
import com.master.englishlearningjetpack.room.History
import com.master.englishlearningjetpack.room.ViewModelFactory
import java.util.*


var clickShowWord: MutableState<MutableList<Boolean>> = mutableStateOf(mutableListOf())
var showWordListIsEmpty = true
var showTextValues: MutableList<MutableState<String>> = mutableListOf()

@Composable
fun showWordList(navController: NavController, myId: Int) {


    val context = LocalContext.current
    val viewModel: DatabaseViewModel =
        viewModel(factory = ViewModelFactory(context.applicationContext as Application, myId))
    var historyss = viewModel.getAllData.observeAsState(listOf())

    setList(size = historyss.value.count())

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.7f)) {
                Image(
                    painter = painterResource(id = R.drawable.left),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .height(26.dp).align(Alignment.TopStart)
                        .clickable { navController.navigate("firstScreen") })

            }
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .height(26.dp)
                    .clickable { navController.navigate("addWordScreen/$myId") })

            Image(
                painter = painterResource(id = R.drawable.random),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .height(26.dp)
                    .clickable { if (historyss.value.size >= 4) navController.navigate("questions/$myId") else Toast.makeText(context , "Catagory minimum 4 sozden ibaret olmalidir!" , Toast.LENGTH_SHORT).show() })

        }


        LazyColumn(modifier = Modifier.padding(5.dp)) {
            items(count = historyss.value.count(), itemContent = {

                val textAz = remember {
                    mutableStateOf(showTextValuesChech(it, historyss))
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp, 10.dp, 5.dp, 0.dp)
                        .clickable {
                            clickShowWord.value[it] = !clickShowWord.value[it]
                            textAz.value = showTextValuesChech(it = it, historyss)
                        },
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    backgroundColor = Color.White
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(all = 5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.word),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(10.dp)
                                .height(25.dp)
                        )
                        Text(
                            text = historyss.value[it].en!!, color = Color.Red, modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(0.4f)

                        )
                        Text(
                            text = " - ", color = Color.Red, modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = textAz.value!!,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(0.9f)

                        )
                    }
                }
            })
        }
    }
}


fun showTextValuesChech(it: Int, historyss: State<List<History>>): String {
    return if (clickShowWord.value[it]) historyss.value[it].az!! else ""
}


@Composable
fun setList(size: Int) {
    if (size != 0 && showWordListIsEmpty) {
        showWordListIsEmpty = false
        clickShowWord.value =   /*remember {*/
//            mutableStateOf(
            kotlin.collections.ArrayList(
                Collections.nCopies(
                    size,
                    false
                )
//                )
            )
//        }
    }
}




