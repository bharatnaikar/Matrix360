package com.example.navigation2.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigation2.R;
import com.example.navigation2.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private TextView flevel;
    private Button homefshow;

    private FragmentHomeBinding binding;
    private DatabaseReference reference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        flevel = binding.currentleveldisplayhome;
        homefshow = binding.fshowhomebtn;

        // Initialize Firebase Database reference
        reference = FirebaseDatabase.getInstance().getReference("sensorData").child("liters");

        homefshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch data from Firebase when the button is clicked
                fetchDataFromFirebase();
            }
        });

        // Set up the continuous listener to update the TextView when data changes
        setupContinuousListener();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void fetchDataFromFirebase() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assuming the data is a Double
                    Double data = dataSnapshot.getValue(Double.class);
                    if (data != null) {
                        flevel.setText(String.valueOf(data) + " ml");
                    } else {
                        flevel.setText("No data found");
                    }
                } else {
                    flevel.setText("No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read data", databaseError.toException());
            }
        });
    }

    private void setupContinuousListener() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assuming the data is a Double
                    Double data = dataSnapshot.getValue(Double.class);
                    if (data != null) {
                        flevel.setText(String.valueOf(data) + " ml");
                    } else {
                        flevel.setText("No data found");
                    }
                } else {
                    flevel.setText("No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read data", databaseError.toException());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
