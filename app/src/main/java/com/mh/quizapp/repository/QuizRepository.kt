package com.mh.quizapp.repository

import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mh.quizapp.model.QuestionList
import com.mh.quizapp.retrofit.QuestionsAPI
import com.mh.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {

    var questionsAPI: QuestionsAPI = RetrofitInstance().getRetrofitInstance().create(QuestionsAPI::class.java)

    fun getQuestionsFromAPI(): LiveData<QuestionList> {
        var data = MutableLiveData<QuestionList>()

        var questionList: QuestionList

        GlobalScope.launch(Dispatchers.IO) {
            val response = questionsAPI.getQuestions()

            if (response.isSuccessful) {
                questionList = response.body()!!
                data.postValue(questionList)
                Log.i("TAGY", "${data.value}")
            }
        }

        return data
    }
}