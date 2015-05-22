package com.strom.strongfit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strom.strongfit.AlimentoActivity;
import com.strom.strongfit.R;
import com.strom.strongfit.adapters.AlimentosAdapter;
import com.strom.strongfit.models.Alimento;
import com.strom.strongfit.utils.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlimentosFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter alimentosAdapter;
    private static final String TAG = AlimentosFragment.class.getSimpleName();

    public AlimentosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alimentos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Alimento> alimentosArrayList = new ArrayList<Alimento>();

        Alimento taco = new Alimento();
        taco.setAlimentoID(1);
        taco.setName("Taco");
        taco.setCalories("70 kcal");
        alimentosArrayList.add(taco);

        Alimento melon = new Alimento();
        melon.setAlimentoID(2);
        melon.setName("Melon");
        melon.setCalories("90 kcal");
        alimentosArrayList.add(melon);

        alimentosAdapter = new AlimentosAdapter(alimentosArrayList, R.layout.row_alimento);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.alimentos_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(alimentosAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AlimentoActivity.class);
                startActivity(intent);
            }
        }));
    }

}
