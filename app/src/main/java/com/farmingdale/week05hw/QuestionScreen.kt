package com.farmingdale.week05hw

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//Main Quiz Screen to display questions and choices
@Composable
fun QuestionScreen(questionsViewModel: QuestionsViewModel, navController: NavHostController){
    //keep track of question index, total money, and how many questions answered correctly
    var index by rememberSaveable { mutableStateOf(0) }
    var money by rememberSaveable { mutableStateOf(0)}
    var correctAnswers by rememberSaveable { mutableStateOf(0)}

    val context = LocalContext.current
    //initialize first question
    var currQuestion = Question()
    currQuestion.updateQuestion(index, context)

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 50.dp)
    ) {
        //Creates the header to show amount of money earned
        Text(
            text = stringResource(id = R.string.header) + " $money",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Divider(modifier = Modifier.padding(top = 50.dp, bottom = 50.dp))
        //Creates the question text
        Text(
            text = currQuestion.getText(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(bottom = 25.dp)
                .fillMaxWidth(.8f)
        )

        //Creates the radio buttons
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Spacer(modifier = Modifier.fillMaxWidth(.2f))
            CustomRadioGroup(question = currQuestion, questionsViewModel = questionsViewModel)
        }


    }
    //For the button to be bottom center
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier.padding(top = 30.dp),
            onClick = {
                //if chosen choice is the answer, increment the index, the money, and the answers
                if (questionsViewModel.chosenChoice == currQuestion.getAnswer()) {
                    index++
                    money += 100 * index * index
                    correctAnswers++

                    //if index is less than 7, update question text and choices
                    if (index < 7) {
                        currQuestion.updateQuestion(index, context = context)
                        //makes the toast to show answer is correct
                        Toast.makeText(
                            context,
                            context.getString(R.string.correctAnswerToast) + "$${100 * index * index}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //if index is 7, navigate to final stats screen
                        navController.navigate("Stats/$money/$correctAnswers")
                    }
                } else {
                    //increment index no matter if answer is incorrect
                    index++
                    //if index is less than 7, update question text and choices
                    if (index < 7) {
                        currQuestion.updateQuestion(index, context = context)
                        //makes the toast
                        Toast.makeText(context, context.getString(R.string.incorrectAnswerToast), Toast.LENGTH_SHORT).show()
                    } else {
                        //if index is 7, navigate to final stats screen
                        navController.navigate("Stats/$money$correctAnswers")
                    }
                }
            }
        ) {
            Text(text = stringResource(id = R.string.confirmButtonText))
        }
    }

}
//Question Screen to fit Landscape
@Composable
fun QuestionScreenLandscape(questionsViewModel: QuestionsViewModel, navController: NavHostController){
    var index by rememberSaveable { mutableStateOf(0) }
    var money by rememberSaveable { mutableStateOf(0)}
    var correctAnswers by rememberSaveable { mutableStateOf(0)}

    val context = LocalContext.current
    var currQuestion = Question()
    currQuestion.updateQuestion(index, context)

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.header) + " $money",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Divider(modifier = Modifier.padding(top = 25.dp, bottom = 25.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.fillMaxWidth(.1f))
            Text(
                text = currQuestion.getText(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .fillMaxWidth(.3f)
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Spacer(modifier = Modifier.fillMaxWidth(.5f))
                CustomRadioGroup(question = currQuestion, questionsViewModel = questionsViewModel)
            }

        }


    }
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier.padding(top = 30.dp),
            onClick = {
                if (questionsViewModel.chosenChoice == currQuestion.getAnswer()) {
                    index++
                    money += 100 * index * index
                    correctAnswers++

                    if (index < 7) {
                        currQuestion.updateQuestion(index, context = context)
                        Toast.makeText(
                            context,
                            context.getString(R.string.correctAnswerToast) + "$${100 * index * index}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        navController.navigate("Stats/$money/$correctAnswers")
                    }
                } else {
                    index++
                    if (index < 7) {
                        currQuestion.updateQuestion(index, context = context)
                        Toast.makeText(context, context.getString(R.string.incorrectAnswerToast), Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate("Stats/$money$correctAnswers")
                    }
                }
            }
        ) {
            Text(text = stringResource(id = R.string.confirmButtonText))
        }
    }

}