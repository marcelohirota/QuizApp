package com.mh.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mh.quizapp.model.QuestionList
import com.mh.quizapp.repository.QuizRepository

class QuizViewModel: ViewModel() {

    var repository = QuizRepository()

    var questionsLiveData: LiveData<QuestionList> = repository.getQuestionsFromAPI()

    fun getQuestionsFromLiveData(): LiveData<QuestionList> {
        return questionsLiveData
    }
}