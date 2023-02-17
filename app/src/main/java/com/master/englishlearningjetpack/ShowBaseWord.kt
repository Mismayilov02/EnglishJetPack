package com.master.englishlearningjetpack

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.master.englishlearningjetpack.room.DatabaseViewModel
import com.master.englishlearningjetpack.room.History
import com.master.englishlearningjetpack.room.ViewModelFactory


@Composable
    fun baseShowWord(navController :NavController){

        val context = LocalContext.current
        val viewModel: DatabaseViewModel = viewModel(factory = ViewModelFactory(context.applicationContext as Application  , null))
        var historys = viewModel.getAllData.observeAsState(listOf())

        var newCatagoryName  = remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = newCatagoryName.value,
                onValueChange = { newCatagoryName.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 10.dp, 15.dp, 0.dp),
                label = {
                    Text(
                        text = "Catagory Name"
                    )
                })
            Button(
                onClick = { saveValuesDatabase( newCatagoryName , context , viewModel) }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .height(50.dp)
            ) {
                Text(text = "Add Catagory", textAlign = TextAlign.Center)
            }

            LazyColumn {
                items(historys.value.count(), itemContent = {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp, 0.dp, 10.dp, 15.dp)
                        .height(60.dp)
                        .clickable { navController.navigate("showWord/${historys.value[it].id}") }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_android_24),
                                contentDescription = "",
                                modifier = Modifier.padding(15.dp)
                            )
                            Text(text = historys.value[it].name!!)
                        }
                    }
                })
            }
        }
    }

fun saveValuesDatabase(name: MutableState<String>, context: Context, viewModel: DatabaseViewModel ){
    if (!name.value.equals("")){
        val history = History(0 , null , null , name.value , 0)
        viewModel.insertData(  history , context ,null )
        name.value = ""
    }else{
        Toast.makeText(context , "lutfen bir catagory ismi daxil ediniz!" , Toast.LENGTH_SHORT).show()
    }
}
