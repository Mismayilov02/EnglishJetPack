package com.master.englishlearningjetpack

import android.app.Application
import android.content.Context
import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.master.englishlearningjetpack.room.DatabaseViewModel
import com.master.englishlearningjetpack.room.History
import com.master.englishlearningjetpack.room.MyRoomDatabase
import com.master.englishlearningjetpack.room.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Composable
fun addWordScreen(navController: NavController, myId: Int) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
//    val myRoomDatabase = MyRoomDatabase.getDatabase(context)

    val viewModel: DatabaseViewModel =
        viewModel(factory = ViewModelFactory(context.applicationContext as Application, null))

    val azValues = remember { mutableStateOf("") }
    val enValues = remember { mutableStateOf("") }


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.left),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.TopStart)
                .height(60.dp)
                .padding(15.dp)
                .clickable {
                    navController.navigate("showWord/$myId") {
                        popUpTo("addWordScreen") {
                            inclusive = true
                        }
                    }
                })
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = myId.toString(),
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        TextField(
            value = enValues.value,
            onValueChange = {
                if (it.lines().size <= 1) {
                    enValues.value = it
                }
            },
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            maxLines = 1,
            label = {
                Text(
                    text = "En"
                )
            },
            colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Red)
        )

        TextField(
            value = azValues.value,
            onValueChange = {
                if (it.lines().size <= 1) {
                    azValues.value = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            maxLines = 1,
            label = {
                Text(
                    text = "Az"
                )
            },
            colors = TextFieldDefaults.textFieldColors(disabledLabelColor = Color.Red)
        )

        Button(
            onClick = {
                insertWordHistory(
                    viewModel,
                    azValues,
                    enValues,
                    context,
                    myId,
                    focusManager
                )
            },
            Modifier
                .fillMaxWidth()
                .padding(15.dp, 15.dp, 15.dp, 65.dp)
                .height(50.dp)
        ) {
            Text(text = "Save")

        }
    }
}

fun insertWordHistory(
    viewModel: DatabaseViewModel,
    azValues: MutableState<String>,
    enValues: MutableState<String>,
    context: Context,
    myId: Int,
    focusManager: FocusManager
) {

    if (!enValues.value.equals("") && !azValues.value.equals("")) {
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            val history = History(0, azValues.value, enValues.value, null, myId)
//            myRoomDatabase.historyDao().insert(history)
            viewModel.insertData(history, context, myId)
            azValues.value = ""
            enValues.value = ""
//            Toast.makeText(context , "save" , Toast.LENGTH_LONG).show()

        }
    } else {
        Toast.makeText(context, "Xanalar Bos Qala Bilmez", Toast.LENGTH_LONG).show()
    }
    focusManager.clearFocus()

}


