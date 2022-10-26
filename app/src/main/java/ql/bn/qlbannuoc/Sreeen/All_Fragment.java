package ql.bn.qlbannuoc.Sreeen;

import static ql.bn.qlbannuoc.Sreeen.Login_Activity.id_user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.List;

import ql.bn.qlbannuoc.Adapter.AdapterListSP;
import ql.bn.qlbannuoc.Database.DatabaseWaterbottleHelper;
import ql.bn.qlbannuoc.Model.Waterbottle;
import ql.bn.qlbannuoc.R;
import ql.bn.qlbannuoc.databinding.FragmentAllBinding;


public class All_Fragment extends Fragment {

    public static MutableLiveData<List<Waterbottle>> mutableLiveData = new MutableLiveData<>();
    FragmentAllBinding binding;
    public All_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mutableLiveData.setValue(new DatabaseWaterbottleHelper(getContext()).getAllData(id_user));
        mutableLiveData.observe(getViewLifecycleOwner(), waterbottles -> {
                 binding.recyclerView.setAdapter(new AdapterListSP(waterbottles, getActivity()));
                 binding.recyclerView.setHasFixedSize(true);
                 binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }
}