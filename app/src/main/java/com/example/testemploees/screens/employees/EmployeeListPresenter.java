package com.example.testemploees.screens.employees;

import com.example.testemploees.api.ApiFactory;
import com.example.testemploees.api.ApiService;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListPresenter {

    private CompositeDisposable compositeDisposable;
    private final EmployeesListView view;

    public EmployeeListPresenter(EmployeesListView activity) {
        this.view = activity;
    }

    public void loadData() {
        ApiService apiService = ApiFactory.getInstance().getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        employeeResponse -> view.showData(employeeResponse.getResponse()),
                        throwable -> view.showError()
                );

        compositeDisposable.add(disposable);
    }

    public void disposeDisposable() {
        if (Objects.nonNull(compositeDisposable)) {
            compositeDisposable.dispose();
        }
    }
}
