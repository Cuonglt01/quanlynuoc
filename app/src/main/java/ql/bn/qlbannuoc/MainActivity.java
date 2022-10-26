package ql.bn.qlbannuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.Objects;

import ql.bn.qlbannuoc.Adapter.AdapterView;
import ql.bn.qlbannuoc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private AdapterView adapterView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        adapterView = new AdapterView(getSupportFragmentManager());
        setSupportActionBar( binding.ActivityToolbar );
        Objects.requireNonNull(getSupportActionBar()).setTitle("Quản lý bán nước");
        binding.ActivityMainTabPager.setAdapter(adapterView);
        binding.maintabs.setupWithViewPager(binding.ActivityMainTabPager);
    }
}