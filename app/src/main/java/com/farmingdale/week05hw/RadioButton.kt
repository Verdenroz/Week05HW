package com.farmingdale.week05hw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//Creates the radio group
@Composable
fun CustomRadioGroup(
    question: Question,
    questionsViewModel: QuestionsViewModel
){
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(questionsViewModel.choices[0]) }
        Column {
            //For each question choice, makes the radio button and text
            question.getChoices().forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                questionsViewModel.setChoice(text)
                            }
                        )
                        .padding(horizontal = 16.dp)
                        //if selected, makes the row green
                        .background(
                            if (text == selectedOption) {
                                Color.Green
                            } else {
                                Color.Transparent
                            }
                        )
                    ) {
                    //makes the actual button
                        RadioButton(
                            selected = (text == selectedOption),
                            modifier = Modifier.padding(8.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Red,
                                unselectedColor = Color.Gray,
                            ),
                            onClick = {
                                onOptionSelected(text)
                                questionsViewModel.setChoice(text)
                            },
                        )
                    //the display text for the radio button
                        Text(
                            text = text,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .offset(y = 20.dp)
                        )
            }
        }
    }
}
