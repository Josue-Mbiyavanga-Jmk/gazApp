package com.sax.inc.coetegaz.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sax.inc.coetegaz.Adaptaters.RecylerAdapterMouvement;
import com.sax.inc.coetegaz.Dao.MouvementStockDao;
import com.sax.inc.coetegaz.Entites.EMouvementStock;
import com.sax.inc.coetegaz.Memory.Constant;
import com.sax.inc.coetegaz.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragMouvementList extends Fragment implements RecylerAdapterMouvement.ItemButtonListener {

    View root;
    private RecyclerView recycler_view;
    private RecylerAdapterMouvement mAdapter;
    private List<EMouvementStock> list_items;

    public FragMouvementList() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragMouvementList newInstance() {
        FragMouvementList fragment = new FragMouvementList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment  list_items = ProduitDao.getAll();
        root = inflater.inflate(R.layout.frag_list_item, container, false);

        initWidget();
        return root;
    }

    @Override
    public void onResume() {
            list_items.clear();
            list_items.addAll(MouvementStockDao.getAllByInput(Constant.INPUT));
            mAdapter.notifyDataSetChanged();
            super.onResume();
    }

    void initWidget()
    {
        list_items=new ArrayList<>();
        recycler_view = root. findViewById(R.id.generique_recyclerview);

        mAdapter = new RecylerAdapterMouvement((AppCompatActivity)getActivity(), list_items,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }



    @Override
    public void onUpdateClickListener(int position) {

    }

    @Override
    public void onItemClickListener(int position) {


    }

}