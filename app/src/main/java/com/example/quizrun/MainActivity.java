package com.example.quizrun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizrun.Fragments.HomeFragment;
import com.example.quizrun.Fragments.LeaderboardsFragment;
import com.example.quizrun.Fragments.ProfileFragment;
import com.example.quizrun.Fragments.WalletFragment;
import com.example.quizrun.MainAdapter.MainAdapter;
import com.example.quizrun.MainModel.MainModel;
import com.example.quizrun.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment());
        transaction.commit();

        binding.bottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = null;

                switch(item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
//                        transaction.replace(R.id.content,new HomeFragment());
//                        transaction.commit();

                        break;

                    case R.id.wallet:
                        fragment = new WalletFragment();
//                        transaction.replace(R.id.content,new WalletFragment());
//                        transaction.commit();

                        break;

                    case R.id.rank:
                        fragment = new LeaderboardsFragment();
//                        transaction.replace(R.id.content,new LeaderboardsFragment());
//                        transaction.commit();

                        break;

                    case R.id.profile:
                        fragment = new ProfileFragment();
//                        transaction.replace(R.id.content,new ProfileFragment());
//                        transaction.commit();

                        break;

                    default:
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.content , fragment).commit();

                return true;
            }
        });











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