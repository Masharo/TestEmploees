package com.example.testemploees.screens.employees;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testemploees.R;
import com.example.testemploees.adapters.EmployeeAdapter;

import java.util.Objects;

public class EmployeeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private EmployeeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_main_employees);

        adapter = new EmployeeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProvider
                    .AndroidViewModelFactory
                    .getInstance(getApplication())
                    .create(EmployeeViewModel.class);

        viewModel.getEmployees().observe(this,
                employees -> {
                    adapter.setEmployees(employees);

                    if (Objects.nonNull(employees)) {
                        employees.forEach(employee ->
                            employee.getSpecialty().forEach(speciality ->
                                Log.i("Speciality: ", speciality.getName())
                            )
                        );
                    }
                });

        viewModel.getErrors().observe(this,
                error -> Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show());

        viewModel.loadData();
    }
}