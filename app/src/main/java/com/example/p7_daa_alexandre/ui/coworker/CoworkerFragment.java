package com.example.p7_daa_alexandre.ui.coworker;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p7_daa_alexandre.databinding.FragmentCoworkerBinding;
import com.example.p7_daa_alexandre.model.Coworker;
import com.example.p7_daa_alexandre.ui.list.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class CoworkerFragment extends Fragment {

    private FragmentCoworkerBinding binding;

    private CoworkerAdapter adapter;

    private List<Coworker> coworkers = new ArrayList<>();

    private CoworkerViewModel viewModel;


    @NonNull
    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCoworkerBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CoworkerViewModel.class);

        viewModel.getAllCoworkers().observe(getViewLifecycleOwner(), new Observer<List<Coworker>>() {
            @Override
            public void onChanged(List<Coworker> coworkers) {
                // Mettre à jour la liste de meetings
                updateList(coworkers);
            }
        });

        initRecyclerViews();

    }

    private void initRecyclerViews() {
        adapter = new CoworkerAdapter(coworkers);
        adapter.setOnItemClickListener(new CoworkerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Ici, tu peux gérer les clics sur les éléments de la liste
                // Avant la suppression ou effectuer d'autres actions si nécessaire
                //deleteMeeting(position);
            }
        });

        RecyclerView recyclerView = binding.listCoworkers;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void updateList(List<Coworker> listCoworkers){
        coworkers.clear();
        coworkers.addAll(listCoworkers);
        updateCoworkers();
    }

    private void updateCoworkers() {
        if (coworkers.isEmpty()) {
            binding.textViewNoCoworker.setVisibility(View.VISIBLE);
            binding.listCoworkers.setVisibility(View.GONE);
        } else {
            binding.textViewNoCoworker.setVisibility(View.GONE);
            binding.listCoworkers.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }
}