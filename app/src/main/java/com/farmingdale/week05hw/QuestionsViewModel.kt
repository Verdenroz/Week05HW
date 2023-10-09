package com.farmingdale.week05hw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class QuestionsViewModel: ViewModel() {

    var choices by mutableStateOf(arrayOf(""))
    var chosenChoice by mutableStateOf("")
        private set

    //will hold the current choice selected by the user
    fun setChoice(chose: String){
        chosenChoice = chose
    }


}