package com.example.quizrun.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizrun.MainAdapter.MainAdapter;
import com.example.quizrun.MainModel.MainModel;
import com.example.quizrun.R;
import com.example.quizrun.databinding.FragmentHomeBinding;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container , false);
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

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}