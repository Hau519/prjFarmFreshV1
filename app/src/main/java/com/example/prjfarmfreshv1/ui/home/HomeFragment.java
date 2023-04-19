package com.example.prjfarmfreshv1.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prjfarmfreshv1.MainActivity;
import com.example.prjfarmfreshv1.ProductActivity;
import com.example.prjfarmfreshv1.SendUsMessageActivity;
import com.example.prjfarmfreshv1.databinding.FragmentHomeBinding;
import com.example.prjfarmfreshv1.models.User;

public class HomeFragment extends Fragment implements View.OnClickListener {

    Button btnShop;
    User user=null;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        //the link hau sent
//        btnShop = binding.btnShopHome;
//        btnShop.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), ProductActivity.class);
        startActivity(i);
    }
}