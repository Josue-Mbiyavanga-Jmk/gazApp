package com.sax.inc.coetegaz.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sax.inc.coetegaz.R;

import androidx.fragment.app.Fragment;


public class FragClientEmpty extends Fragment {

    View root;
    private ProgressDialog progressDialog;
    private View contenaireHelp;
    public FragClientEmpty() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragClientEmpty newInstance() {
        FragClientEmpty fragment = new FragClientEmpty();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.empty_client, container, false);
        return root;
    }
}