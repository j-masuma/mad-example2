package com.example.task7

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.task7.ui.theme.Task7Theme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Check Self Permissions

        setContent {
            Task7Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "home" ){
        composable(route = "home"){
            MainScreen(navController = navController)}

        composable(route = "profile"){
            SecondScreen(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier,
               context: Context = LocalContext.current,
               navController : NavHostController){

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()){
            isGranted -> if(isGranted){
        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
    }else{
        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
    }

    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Main Screen") })},
        bottomBar = { BottomAppBar {
            Text("Bottom Bar")
        }}
    ) {
            paddingValues -> Column(modifier = Modifier.padding(paddingValues)) {
        Text(text = "Main Screen")
        Button(onClick = {
            navController.navigate("profile")
        }) {
            Text("Next Screen")
        }
        Button(onClick = {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(context, "Permission is already Granted", Toast.LENGTH_SHORT).show()
            }
            else{
                requestPermissionLauncher.
                launch(android.Manifest.permission.CAMERA)
            }

        }) {
            Text("Open Camera")
        }
    }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(modifier: Modifier = Modifier,
                 context: Context = LocalContext.current,
                 navController: NavHostController
){
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Second Screen") })},
        bottomBar = { BottomAppBar {
            Text("Bottom Bar")
        }}
    ) {
            paddingValues -> Column(modifier = Modifier.padding(paddingValues)) {
        Text(text = "Second Screen")
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Go Back")
        }
    }
    }
}