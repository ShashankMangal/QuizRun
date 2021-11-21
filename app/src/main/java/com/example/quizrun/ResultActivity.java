package com.example.quizrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizrun.Fragments.HomeFragment;
import com.example.quizrun.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragment = null;

        int correctAnswers = getIntent().getIntExtra("correct",0);
        int totalQuestions = getIntent().getIntExtra("total",0);

        long points = correctAnswers * 100;

        binding.score.setText(String.format("%d/%d",correctAnswers,totalQuestions));
        binding.coins.setText(String.valueOf(points));

        FirebaseFirestore f = FirebaseFirestore.getInstance();
        f.collection("Users").document(FirebaseAuth.getInstance().getUid()).update("coins", FieldValue.increment(points));

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragment = new HomeFragment();
                startActivity(new Intent(ResultActivity.this , MainActivity.class));
                finish();

            }
        });
    }
}