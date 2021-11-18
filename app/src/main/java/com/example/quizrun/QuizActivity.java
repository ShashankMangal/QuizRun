package com.example.quizrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizrun.MainModel.Question;
import com.example.quizrun.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;
    ArrayList<Question> questions;
    int index =0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();
        final String getId = getIntent().getStringExtra("catId");
        Random r = new Random();
        final int rand = r.nextInt(3);
        database.collection("categories").document(getId).collection("questions").whereGreaterThanOrEqualTo("index",rand).orderBy("index").limit(2)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() < 2)
                {
                    database.collection("categories").document(getId).collection("questions").whereLessThanOrEqualTo("index",rand).orderBy("index").limit(2)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                    Question question = snapshot.toObject(Question.class);
                                    questions.add(question);
                                }
                            nextQuestion();

                        }
                    });
                }else
                {
                    for(DocumentSnapshot snapshot : queryDocumentSnapshots)
                    {
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    nextQuestion();
                }
            }
        });




        resetTimer();


    }

    void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    void resetTimer(){
        timer = new CountDownTimer(31000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }
    void nextQuestion(){

        if(timer!=null)
            timer.cancel();



        if(index < questions.size())
        {
           timer.start();
            binding.questionCounter.setText(String.format("%d/%d",index+1 ,questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());


        }
    }

    void showAnswer(){
        if(question.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(binding.option4.getText().toString()))
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
    }

    void checkAnswer(TextView textView)
    {
        if(textView.getText().toString().equals(question.getAnswer()))
        {   correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else
        {   showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    public void onClick(View view)
    {
    switch (view.getId())
    {
        case R.id.option_1:
        case R.id.option_2:
        case R.id.option_3:
        case R.id.option_4:
            if(timer!=null)
                timer.cancel();
            TextView selectedAnswer = (TextView) view;
            checkAnswer(selectedAnswer);
            break;

        case R.id.nextBtn:
            reset();
            if(index < questions.size()-1) {
                Log.d("Question Number : ",String.valueOf(index+1));
                ++index;

                nextQuestion();
            }else{
                Intent intent = new Intent(QuizActivity.this , ResultActivity.class);
                intent.putExtra("correct",correctAnswer);
                intent.putExtra("total",questions.size());
                startActivity(intent);
                Toast.makeText(this , "Quiz Finished.", Toast.LENGTH_SHORT).show();
                }
            break;
    }
    }
}