package com.dating.blinddate.Fragment.SideFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dating.blinddate.R;
import com.dating.blinddate.databinding.FragmentContactUsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends Fragment {
    FragmentContactUsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactUsBinding.inflate(inflater,container,false);

        binding.shareFedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.feedbackMessage.getText().toString().isEmpty()){
                    FirebaseDatabase.getInstance().getReference().child("Support").child("feedback")
                            .child(FirebaseAuth.getInstance().getUid())
                            .setValue(binding.feedbackMessage.getText().toString());
                    Toast.makeText(getContext(),"feedback send", Toast.LENGTH_SHORT).show();
                    System.out.println("feedback Send");
                }
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }
}