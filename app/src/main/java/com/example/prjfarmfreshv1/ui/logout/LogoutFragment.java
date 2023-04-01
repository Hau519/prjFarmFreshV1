package com.example.prjfarmfreshv1.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.prjfarmfreshv1.MainActivity;
import com.example.prjfarmfreshv1.ProfileActivity;
import com.example.prjfarmfreshv1.R;
import com.google.android.material.snackbar.Snackbar;

public class LogoutFragment extends Fragment{

    private LogoutViewModel mViewModel;
    Button btnHome;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        btnHome = (Button) view.findViewById(R.id.btnBackToHome);
        btnHome.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        // TODO: Use the ViewModel

    }


}