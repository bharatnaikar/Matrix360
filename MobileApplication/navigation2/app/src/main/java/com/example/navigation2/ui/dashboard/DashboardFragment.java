package com.example.navigation2.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigation2.R;
import com.example.navigation2.databinding.FragmentDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {
    TextView currentfldash, before, recived;
    Button resetdash, showdash;

    private FragmentDashboardBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    // Declare class-level variables to store the retrieved data
    private Double dataraja;
    private Double datarani;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentfldash = root.findViewById(R.id.id_cflshow);
        before = root.findViewById(R.id.id_bflshow);
        recived = root.findViewById(R.id.id_rflshow);
        showdash = root.findViewById(R.id.id_showdash);
        resetdash = root.findViewById(R.id.id_reset);

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("sensorData").child("liters");

        resetdash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve data from Firebase when showdash button is clicked
                retrieveDataAndSetViews();

            }
        });

        showdash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Call resetfun to collect the data
                resetfun();
            }
        });

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    private void retrieveDataAndSetViews() {
        // Read data from Firebase when showdash button is clicked
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve data from Firebase
                datarani = snapshot.getValue(Double.class);
                if (datarani != null) {
                    // If data is not null, display it in currentfldash TextView
                    currentfldash.setText(String.valueOf(datarani + " ml"));
                } else {
                    // If data is null, handle it accordingly
                    currentfldash.setText("No data found");
                }


                // Set dataraja value to before TextView
                if (dataraja != null) {
                    before.setText(String.valueOf(dataraja + " ml"));
                } else {
                    before.setText("");
                }

                // Calculate and display the difference between datarani and dataraja in recived TextView
                if (datarani != null && dataraja != null) {
                    double difference = datarani - dataraja;
                    recived.setText(String.valueOf(difference + " ml"));
                } else {
                    recived.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Log.e("FirebaseData", "Error: " + error.getMessage());
            }
        });
    }

    private void resetfun() {
        // Store the previous value in dataraja before retrieving new data
        dataraja = datarani;

        // Retrieve data from Firebase when resetdash button is clicked
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve data from Firebase
                datarani = snapshot.getValue(Double.class);
                if (datarani != null) {
                    // If data is not null, display it in currentfldash TextView
                    currentfldash.setText(String.valueOf(datarani + " ml"));
                } else {
                    // If data is null, handle it accordingly
                    currentfldash.setText("No data found");
                }

                // Set dataraja value to before TextView
                if (dataraja != null) {
                    before.setText(String.valueOf(dataraja + " ml"));
                } else {
                    before.setText("");
                }

                // Calculate and display the difference between datarani and dataraja in recived TextView
                if (datarani != null && dataraja != null) {
                    double difference = datarani - dataraja;
                    recived.setText(String.valueOf(difference + " ml"));
                } else {
                    recived.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Log.e("FirebaseData", "Error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
