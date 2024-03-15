package com.dating.blinddate.Fragment;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dating.blinddate.Adapter.StoryAdapter;
import com.dating.blinddate.Adapter.UserAdapter;
import com.dating.blinddate.Model.CallsModel;
import com.dating.blinddate.Model.StoryModel;
import com.dating.blinddate.R;
import com.dating.blinddate.databinding.FragmentStroiesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Stroies extends Fragment {

    FragmentStroiesBinding binding;
    ArrayList<StoryModel> Story = new ArrayList<>();
    FloatingActionButton floatButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            binding = FragmentStroiesBinding.inflate(inflater,container,false);
            floatButton = getActivity().findViewById(R.id.find);

            Story.add(new StoryModel("","Rashid","10:45 PM","A","23", "23","5","25","63",""));
            Story.add(new StoryModel("","Dilshad","10:45 PM","A","23", "23","5","25","63",""));
            Story.add(new StoryModel("","Jamshad","10:45 PM","A","23", "23","5","25","63",""));

            StoryAdapter adapter = new StoryAdapter(Story,getContext());
        binding.storyRecycle.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.storyRecycle.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}