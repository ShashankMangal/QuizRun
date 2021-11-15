package com.example.quizrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizrun.MainAdapter.MainAdapter;
import com.example.quizrun.MainModel.MainModel;
import com.example.quizrun.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ArrayList<MainModel> categories = new ArrayList<>();
        categories.add(new MainModel("","Mathematics","https://cdn-icons-png.flaticon.com/512/3874/3874176.png"));
        categories.add(new MainModel("","Science","https://en.opensuse.org/images/f/fc/Icon-bug.png"));
        categories.add(new MainModel("","History","https://en.opensuse.org/images/f/fc/Icon-bug.png"));
        categories.add(new MainModel("","Language","https://en.opensuse.org/images/f/fc/Icon-bug.png"));
        categories.add(new MainModel("","GK","https://en.opensuse.org/images/f/fc/Icon-bug.png"));
        categories.add(new MainModel("","Current Affairs","https://en.opensuse.org/images/f/fc/Icon-bug.png"));
        categories.add(new MainModel("","Television","https://en.opensuse.org/images/f/fc/Icon-bug.png"));
        categories.add(new MainModel("","Sports","https://en.opensuse.org/images/f/fc/Icon-bug.png"));

        MainAdapter adapter = new MainAdapter(this , categories);
        binding.categoryList.setLayoutManager(new GridLayoutManager(this , 2));
        binding.categoryList.setAdapter(adapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.wallet){
            Toast.makeText(this, "Wallet is Clicked !", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}