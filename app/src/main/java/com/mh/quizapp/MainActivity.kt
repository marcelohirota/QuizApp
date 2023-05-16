package com.mh.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mh.quizapp.databinding.ActivityMainBinding
import com.mh.quizapp.model.QuestionList
import com.mh.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionList: QuestionList

    companion object{
        var result: Int = 0
        var totalQuestions: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Reseting the scores
        result = 0
        totalQuestions = 0

        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if (it.size>0){
                    questionList = it
                    Log.i("TAGY", "This is the first question: ${questionList[0].question}")

                    binding.apply {
                        txtQuestion.text = questionList!![0].question
                        radio1.text = questionList!![0].option1
                        radio2.text = questionList!![0].option2
                        radio3.text = questionList!![0].option3
                        radio4.text = questionList!![0].option4

                    }
                }
            })
        }

        var i = 1
        binding.apply {
            btnNext.setOnClickListener(View.OnClickListener {
                val selectedOption = radioGroup.checkedRadioButtonId

                if (selectedOption != -1){
                    val radbutton = findViewById<View>(selectedOption) as RadioButton

                    questionList.let {
                        if (i<it.size!!){
                            totalQuestions = it.size
                            if (radbutton.text == it[i-1].correct_option){
                                result++
                                txtResult.text = "Correct Answer: $result"
                            }

                            // Display next question
                            txtQuestion.text = "Question ${i+1}: ${it[i].question}"
                            radio1.text = it[i].option1
                            radio2.text = it[i].option2
                            radio3.text = it[i].option3
                            radio4.text = it[i].option4

                            // Checking if the last question is reached
                            if (i == it.size-1){
                                btnNext.text = "Finish"
                            }

                            radioGroup.clearCheck()
                            i++
                        } else {
                            if (radbutton.text == it[i-1].correct_option){
                                result++
                                txtResult.text = "Correct Answer: $result"
                            }
                            txtQuestion.text = "Quiz Completed"
                            radio1.visibility = View.GONE
                            radio2.visibility = View.GONE
                            radio3.visibility = View.GONE
                            radio4.visibility = View.GONE
                            btnNext.visibility = View.GONE
                            radioGroup.clearCheck()
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Please select an option", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}