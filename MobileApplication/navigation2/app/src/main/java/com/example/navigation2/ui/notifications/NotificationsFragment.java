package com.example.navigation2.ui.notifications;

import static android.content.Context.MODE_PRIVATE;
import static com.example.navigation2.login2.SHARED_PREF;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigation2.R;
import com.example.navigation2.databinding.FragmentNotificationsBinding;
import com.example.navigation2.login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NotificationsFragment extends Fragment {
    Button logout;
    TextView twname,twphnumber,twemail;


    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the logout button after inflating the layout
        logout = root.findViewById(R.id.logoutbtn);
        twemail=root.findViewById(R.id.textemail);
        twname=root.findViewById(R.id.textname);
        twphnumber=root.findViewById(R.id.textphnumber);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutuser();
            }
        });

        // ViewModel initialization and text observation
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        final TextView textView = binding.fragmenttext;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get a reference to the Firebase database
            Query userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());

            // Read data from Firebase
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Retrieve user data
                    String name = dataSnapshot.child("username").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String email = dataSnapshot.child("mail").getValue(String.class);

                    // Set data to TextViews
                    twname.setText(name);
                    twphnumber.setText(phoneNumber);
                    twemail.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        return root;
    }

    private void logoutuser() {
        FirebaseAuth.getInstance().signOut();
        // Clear SharedPreferences
        SharedPreferences sharedPreferences =requireContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Hide logout button after logout
        logout.setVisibility(View.GONE);
        Toast.makeText(getContext(), "logout sucessfull", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(), login.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
