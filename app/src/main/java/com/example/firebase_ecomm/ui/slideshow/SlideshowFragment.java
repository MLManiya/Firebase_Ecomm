package com.example.firebase_ecomm.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class SlideshowFragment extends Fragment {

    ViewAdapter adapter;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return getView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}