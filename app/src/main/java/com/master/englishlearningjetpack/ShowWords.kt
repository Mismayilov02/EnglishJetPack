package com.master.englishlearningjetpack

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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


//@Composable
//fun showAllWords(){
//    val context = LocalContext.current
////    val focusManager = LocalFocusManager.current
////   val databaseViewModel :DatabaseViewModel= viewModel(factory = DatabaseV(context.applicationContext as Application))
//
//
//
//}


//@SuppressLint("CoroutineCreationDuringComposition")
//@Composable
@SuppressLint("RememberReturnType")
@Composable
fun allReadHistory(history: MutableList<History>) {

//    return historys

}


@Composable
fun showWordList(navController: NavController, myId: Int) {

    val context = LocalContext.current
    val viewModel: DatabaseViewModel =
        viewModel(factory = ViewModelFactory(context.applicationContext as Application, myId))
    var historys = viewModel.getAllData.observeAsState(listOf())

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top , horizontalArrangement = Arrangement.End) {
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .height(35.dp)
                    .clickable { navController.navigate("addWordScreen/$myId") })

            Image(
                painter = painterResource(id = R.drawable.random),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .height(35.dp)
                    .clickable { navController.navigate("addWordScreen/$myId") })

        }


        LazyColumn {
            items(count = historys.value.count(), itemContent = {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp, 10.dp, 15.dp, 0.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    backgroundColor = Color.White
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .clickable {
//                                Log.e("TAG", name[it].name)
                               /* navController.navigate("addWordScreen/${historys.value[it].id}")*/
                            }) {
                        Image(
                            painter = painterResource(id = R.drawable.word),
                            contentDescription = "",
                            modifier = Modifier.padding(10.dp).height(25.dp)
                        )
                        Text(
                            text = historys.value[it].en!!, color = Color.Red, modifier = Modifier.padding(5.dp).fillMaxWidth(0.4f)

                        )
                        Text(
                            text =" - ", color = Color.Red, modifier = Modifier.padding(5.dp)

                        )
                        Text(
                            text = historys.value[it].az!!, color = Color.Red, modifier = Modifier.padding(5.dp).fillMaxWidth(0.5f)

                        )
                    }
                }
            })
        }
    }
}


@Composable
fun allReadHistory(history: State<List<History>>) {

}
