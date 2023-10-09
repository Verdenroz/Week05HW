package com.farmingdale.week05hw

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farmingdale.week05hw.ui.theme.Week05HWTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //creates the splash screen
        installSplashScreen()
        setContent {
            Week05HWTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //finds current configuration and opens corresponding Navigation Component
                    val configuration = LocalConfiguration.current
                    when(configuration.orientation){
                        Configuration.ORIENTATION_LANDSCAPE -> NavigationLandscape()
                        else ->{
                            NavigationPortrait()
                        }
                    }

                }
            }
        }
    }
}
//Creates Navigation component to navigate between screens
@Composable
fun NavigationPortrait(){
    val navController = rememberNavController()
    //Navigates to QuestionScreen first
    NavHost(navController = navController, startDestination = "QuizScreen"){
        //if route is "QuizScreen" navigate to QuestionScreen
        composable("QuizScreen"){
            QuestionScreen(questionsViewModel = QuestionsViewModel(), navController)
        }
        //"if route is stats navigate to StatsScreen with money/correctAnswers NavArguments
        composable(
            route = "Stats/{final_money}/{correct_answers}",
            arguments = listOf(
                navArgument("final_money"){
                type = NavType.StringType
        },
                navArgument("correct_answers"){
                    type = NavType.StringType
                }
            )
    )
        {
            val money = it.arguments?.getString("final_money") ?: ""
            val correctAnswers = it.arguments?.getString("correct_answers") ?: ""
            StatsScreen(navController = navController, money = money, correctAnswers = correctAnswers)
        }

    }
}
//Navigation for Landscape mode
@Composable
fun NavigationLandscape(){
    val navController = rememberNavController()
    //Navigates to the QuizScreenLandscape first
    NavHost(navController = navController, startDestination = "QuizScreen"){
        composable("QuizScreen"){
            QuestionScreenLandscape(questionsViewModel = QuestionsViewModel(), navController)
        }
        composable(
            route = "Stats/{final_money}/{correct_answers}",
            arguments = listOf(
                navArgument("final_money"){
                    type = NavType.StringType
                },
                navArgument("correct_answers"){
                    type = NavType.StringType
                }
            )
        )
        {
            val money = it.arguments?.getString("final_money") ?: ""
            val correctAnswers = it.arguments?.getString("correct_answers") ?: ""
            StatsScreen(navController = navController, money = money, correctAnswers = correctAnswers)
        }

    }
}