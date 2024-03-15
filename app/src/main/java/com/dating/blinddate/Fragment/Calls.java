package com.dating.blinddate.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.blinddate.Adapter.CallsAdapter;
import com.dating.blinddate.Model.CallsModel;
import com.dating.blinddate.Model.User;
import com.dating.blinddate.databinding.FragmentCallsBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 *
 *
 */
public class Calls extends Fragment {
    FragmentCallsBinding binding;

    ArrayList<CallsModel> calls = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCallsBinding.inflate(inflater,container,false);

        calls.add(new CallsModel("","Ashu","10:10 PM","id1","Video"));
        calls.add(new CallsModel("","Najmul","10:10 PM","Id555","Audio"));
        calls.add(new CallsModel("","Mustafa","10:10 PM","id3","Video"));
        calls.add(new CallsModel("","Sizu","10:10 PM","Id555","Aduio"));
        calls.add(new CallsModel("","Shan","10:10 PM","id5","Aduio"));

        database = FirebaseDatabase.getInstance();
        CallsAdapter adapter = new CallsAdapter(calls,getContext());
        binding.callsRecycle.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.callsRecycle.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}