package com.example.quizrun.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quizrun.BuildConfig;
import com.example.quizrun.MainAdapter.MainAdapter;
import com.example.quizrun.MainModel.MainModel;
import com.example.quizrun.R;
import com.example.quizrun.SpinnerActivity;
import com.example.quizrun.databinding.FragmentHomeBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private FragmentHomeBinding binding;
    private FirebaseFirestore firestore;
    private RewardedAd rewardedAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container , false);
        rewardedAd = new RewardedAd(getContext() , "ca-app-pub-5127713321341585/8021854507");
        rewardedAd.loadAd(new AdRequest.Builder().build() , new RewardedAdLoadCallback()
        {
            @Override
            public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {
                super.onRewardedAdFailedToLoad(loadAdError);

            }

            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();

            }
        });
        firestore = FirebaseFirestore.getInstance();
        ArrayList<MainModel> categories = new ArrayList<>();

        MainAdapter adapter = new MainAdapter(getContext() , categories);
        firestore.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                categories.clear();
                for(DocumentSnapshot snapshot : value.getDocuments())
                {
                    MainModel model = snapshot.toObject(MainModel.class);
                    model.setCategoryId(snapshot.getId());
                    categories.add(model);
                }
                adapter.notifyDataSetChanged();

            }
        });


        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext() , 2));
        binding.categoryList.setAdapter(adapter);

        binding.spinWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rewardedAd.isLoaded())
                {
                    rewardedAd.show(getActivity(), new RewardedAdCallback() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            startActivity(new Intent(getContext(), SpinnerActivity.class));

                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "No Ads available , Try again !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Quiz Run");
                    String shareMessage = "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID+"\n\n";
                    intent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    startActivity(Intent.createChooser(intent,"share by"));
                }catch(Exception e){
                    Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });


        return binding.getRoot();
    }
}