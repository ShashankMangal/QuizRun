package com.example.quizrun;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizrun.SpinWheel.LuckyWheelView;
import com.example.quizrun.SpinWheel.model.LuckyItem;
import com.example.quizrun.databinding.ActivitySpinnerBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {

    ActivitySpinnerBinding binding;
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adRequest = new AdRequest.Builder().build();

        binding.adViewSpinner1.loadAd(adRequest);
        binding.adViewSpinner2.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5127713321341585/6145666055");

        List<LuckyItem> data = new ArrayList<>();

        LuckyItem item1 = new LuckyItem();
        item1.topText = "10";
        item1.secondaryText = "Coins";
        item1.color = Color.parseColor("#ec9c43");
        item1.textColor = Color.parseColor("#ffffff");
        data.add(item1);

        LuckyItem item2 = new LuckyItem();
        item2.topText = "5";
        item2.secondaryText = "Coins";
        item2.color = Color.parseColor("#00cf00");
        item2.textColor = Color.parseColor("#ffffff");
        data.add(item2);

        LuckyItem item = new LuckyItem();
        item.topText = "25";
        item.secondaryText = "Coins";
        item.color = Color.parseColor("#05346c");
        item.textColor = Color.parseColor("#ffffff");
        data.add(item);

        LuckyItem item3 = new LuckyItem();
        item3.topText = "15";
        item3.secondaryText = "Coins";
        item3.color = Color.parseColor("#2596be");
        item3.textColor = Color.parseColor("#ffffff");
        data.add(item3);

        LuckyItem item4 = new LuckyItem();
        item4.topText = "45";
        item4.secondaryText = "Coins";
        item4.color = Color.parseColor("#b0481e");
        item4.textColor = Color.parseColor("#ffffff");
        data.add(item4);

        LuckyItem item5 = new LuckyItem();
        item5.topText = "35";
        item5.secondaryText = "Coins";
        item5.color = Color.parseColor("#b0a51e");
        item5.textColor = Color.parseColor("#ffffff");
        data.add(item5);

        LuckyItem item6 = new LuckyItem();
        item6.topText = "0";
        item6.secondaryText = "Coins";
        item6.color = Color.parseColor("#961eb0");
        item6.textColor = Color.parseColor("#ffffff");
        data.add(item6);



        binding.wheelView.setData(data);
        binding.wheelView.setRound(5);

        binding.spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                 int randomNumber = r.nextInt(7);
                 binding.wheelView.startLuckyWheelWithTargetIndex(randomNumber);
            }
        });

        binding.wheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                updateCoins(index);
            }
        });


    }

    void updateCoins(int index){
        long coin =0;
        switch(index)
        {
            case 0:
                coin = 10;
                break;

            case 1:
                coin = 5;
                break;

            case 2:
                coin = 25;
                break;

            case 3:
                coin = 15;
                break;

            case 4:
                coin = 45;
                break;

            case 5:
                coin = 35;
                break;

            case 6:
                coin = 0;
                break;
        }

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("Users").document(FirebaseAuth.getInstance().getUid()).update("coins", FieldValue.increment(coin)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SpinnerActivity.this, " Coins added.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SpinnerActivity.this , MainActivity.class));


            }
        });

    }
}