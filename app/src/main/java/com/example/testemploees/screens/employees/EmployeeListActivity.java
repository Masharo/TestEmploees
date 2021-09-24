package com.example.testemploees.screens.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.testemploees.R;
import com.example.testemploees.adapters.EmployeeAdapter;
import com.example.testemploees.api.ApiFactory;
import com.example.testemploees.api.ApiService;
import com.example.testemploees.pojo.Employee;
import com.example.testemploees.pojo.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListActivity extends AppCompatActivity implements EmployeesListView {

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private EmployeeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_main_employees);

        adapter = new EmployeeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter = new EmployeeListPresenter(EmployeeListActivity.this);

        recyclerView.setAdapter(adapter);

        presenter.loadData();
    }

    @Override
    public void showData(List<Employee> employees) {
        adapter.setEmployees(employees);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "При загрузке данных произошла ошибка", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (Objects.nonNull(presenter)) {
            presenter.disposeDisposable();
        }

        super.onDestroy();
    }
}