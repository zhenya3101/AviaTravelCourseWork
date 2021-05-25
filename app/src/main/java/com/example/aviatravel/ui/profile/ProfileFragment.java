package com.example.aviatravel.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aviatravel.AboutFragment;
import com.example.aviatravel.LoadActivity;
import com.example.aviatravel.PassengerFragment;
import com.example.aviatravel.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    RelativeLayout btn_sign_out;
    RelativeLayout btn_pass;
    RelativeLayout btn_about;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        /*final TextView textView = root.findViewById(R.id.text_notifications);
        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        btn_pass = root.findViewById(R.id.b1);
        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassengerFragment fragment = new PassengerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        btn_about = root.findViewById(R.id.b3);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutFragment fragment = new AboutFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        btn_sign_out = root.findViewById(R.id.b4);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                signOut();
            }
        });

        return root;
    }

    private void signOut() {
        startActivity(new Intent(this.getActivity(), LoadActivity.class));
        this.getActivity().finish();
    }
}