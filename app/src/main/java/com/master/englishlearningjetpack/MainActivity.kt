package com.master.englishlearningjetpack

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.master.englishlearningjetpack.ui.theme.EnglishLearningJetPackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishLearningJetPackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                  sayfaGecisi()

                }
            }
        }
    }

    @Composable
    fun sayfaGecisi() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "firstScreen") {
            composable("firstScreen") {
                baseShowWord(navController = navController)
            }
            composable(
                "addWordScreen/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("id")!!
                addWordScreen(myId = id , navController = navController)
            }
            composable(
                "showWord/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("id")!!
                showWordList( navController = navController, myId = id)
            }

            composable(
                "questions/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("id")!!
                questions( navController = navController, myId = id)
            }
        }
    }

    @Composable
    fun listeleme() {
        LazyColumn {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 5.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    backgroundColor = Color.LightGray
                ) {
                    Row(modifier = Modifier.clickable {
                        Log.e("TAG", "android")
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(all = 10.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_android_24),
                                contentDescription = ""
                            )
                            Text(text = "Android", modifier = Modifier.padding(all = 10.dp))
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp, 5.dp, 30.dp, 5.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    backgroundColor = Color.LightGray
                ) {
                    Row(modifier = Modifier.clickable {
                        Log.e("TAG", "android")
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(all = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_android_24),
                                contentDescription = ""
                            )
                            Text(text = "Android", modifier = Modifier.padding(all = 10.dp))
                        }
                    }
                }
            }
        }
    }



    @Composable
    fun autoBaseList(navController: NavController) {
        val name = remember { mutableStateListOf("Nurane", "Mehemmed", "Ismayilov", "Deneme") }
        LazyColumn {
            items(count = name.count(), itemContent = {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp, 10.dp, 15.dp, 0.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    backgroundColor = Color.LightGray
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .clickable {
                                Log.e("TAG", name[it])
                                navController.navigate("addWordScreen/${name[it]}")
                            }) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_android_24),
                            contentDescription = "",
                            modifier = Modifier.padding(15.dp)
                        )
                        Text(text = name[it], modifier = Modifier.padding(all = 10.dp))
                    }
                }
            })
        }
    }


    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        EnglishLearningJetPackTheme {
           refreshDialog(refreshDailogValue = remember {
               mutableStateOf(true)
           })
        }
    }
//    holder.binding.recyclerRldescription.isVisible = true

}