package com.example.quizrun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quizrun.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswers = getIntent().getIntExtra("correct",0);
        int totalQuestions = getIntent().getIntExtra("total",0);

        long points = correctAnswers * 100;

        binding.score.setText(String.format("%d/%d",correctAnswers,totalQuestions));
        binding.coins.setText(String.valueOf(points));

        FirebaseFirestore f = FirebaseFirestore.getInstance();
        f.collection("Users").document(FirebaseAuth.getInstance().getUid()).update("coins", FieldValue.increment(points));
    }
}