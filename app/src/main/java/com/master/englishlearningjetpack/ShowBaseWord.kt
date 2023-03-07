package com.master.englishlearningjetpack

import android.app.Application
import android.content.Context
import android.support.v4.app.INotificationSideChannel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.master.englishlearningjetpack.room.DatabaseViewModel
import com.master.englishlearningjetpack.room.History
import com.master.englishlearningjetpack.room.ViewModelFactory
import com.master.englishlearningjetpack.ui.theme.EnglishLearningJetPackTheme


var id = 0

@Composable
fun baseShowWord(navController: NavController) {

    val context = LocalContext.current
    val viewModel: DatabaseViewModel =
        viewModel(factory = ViewModelFactory(context.applicationContext as Application, null))
    var historys = viewModel.getAllData.observeAsState(listOf())

    val deleteDialog = remember {
        mutableStateOf(false)
    }

    if (deleteDialog.value) {
        deleteDialogView(deleteDialog = deleteDialog, viewModel)
    }

    var newCatagoryName = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = newCatagoryName.value,
            onValueChange = {
                if (it.lines().size <= 1) {
                    newCatagoryName.value = it
                }
            },

            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 10.dp, 15.dp, 0.dp),
            label = {
                Text(
                    text = "Catagory Name", maxLines = 1
                )
            })
        Button(
            onClick = { saveValuesDatabase(newCatagoryName, context, viewModel) },
            modifier = Modifier
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
                            painter = painterResource(id = R.drawable.bookmark),
                            contentDescription = "",
                            modifier = Modifier.padding(15.dp)
                        )
                        Text(
                            text = historys.value[it].name!!,
                            modifier = Modifier.padding(10.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold, fontSize = 16.sp
                            )
                        )

                        Box(modifier = Modifier.fillMaxWidth()) {
                            Image(painter = painterResource(id = R.drawable.trash),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(15.dp)
                                    .align(Alignment.TopEnd)
                                    .clickable {
                                        id = historys.value[it].id
                                        deleteDialog.value = true
                                    })
                        }
                    }
                }
            })
        }
    }
}

fun saveValuesDatabase(name: MutableState<String>, context: Context, viewModel: DatabaseViewModel) {
    if (!name.value.equals("")) {
        val history = History(0, null, null, name.value, 0)
        viewModel.insertData(history, context, null)
        name.value = ""
    } else {
        Toast.makeText(context, "lutfen bir catagory ismi daxil ediniz!", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun deleteDialogView(
    deleteDialog: MutableState<Boolean>, viewModel: DatabaseViewModel
) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error))

    Dialog(
        onDismissRequest = { deleteDialog.value = false }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.5f),
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {


                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .padding(15.dp, top = 30.dp)
                            .fillMaxWidth(0.7f)
                            .align(Alignment.Center)
                            .fillMaxHeight(0.5f)
                    )

                }

                Text(
                    text = "Silmek Istediginden Eminmisin?",
                    modifier = Modifier.padding(20.dp, 50.dp, 20.dp, 30.dp),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = {
                            deleteDialog.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(50.dp),
                        shape = RoundedCornerShape(
                            topStart = 5.dp, topEnd = 0.dp, bottomEnd = 0.dp
                        )
                    ) {
                        Text(text = "No")
                    }
                    Button(
                        onClick = {
                            deleteDialog.value = false
                            viewModel.deleteDataById(id)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(
                            topStart = 0.dp, bottomStart = 0.dp, topEnd = 5.dp
                        )
                    ) {
                        Text(text = "Yes")
                    }
                }


            }
        }
    }

}


