package com.example.quizrun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private Button registerButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        registerButton = findViewById(R.id.registerSubmitButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String , Object> userMap = new HashMap<>();
                userId = auth.getCurrentUser().getUid();
                DocumentReference documentReference = firestore.collection("Users").document(userId).collection("UsersInfo").document("Details");

//                userMap.put("ownerMail",ownerName);
//                userMap.put("ownerMail",ownerMail);
//                userMap.put("ownerPhone",ownerPhone);
//                userMap.put("pgAddress",OwnerAddress);
////                userMap.put("PG City",OwnerCity);
////                userMap.put("PG State",OwnerState);
//                userMap.put("pgName",OwnerCity);
//                userMap.put("ownerName",OwnerState);
//                userMap.put("pgPincode",OwnerPincode);
//                userMap.put("pgBasePrice",OwnerBasePrice);




                documentReference.set(userMap);

            }
        });


    }
}