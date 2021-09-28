package com.example.testemploees.screens.employees;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testemploees.api.ApiFactory;
import com.example.testemploees.api.ApiService;
import com.example.testemploees.data.AppDatabase;
import com.example.testemploees.pojo.Employee;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EmployeeViewModel extends AndroidViewModel {

    private static AppDatabase db;
    private LiveData<List<Employee>> employees;
    private MutableLiveData<Throwable> errors;
    private CompositeDisposable compositeDisposable;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        employees = db.employeeDAO().getAllEmployees();
        errors = new MutableLiveData<>();
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    private static boolean isValidData(Object[] obj) {
        return Objects.nonNull(obj) && obj.length > 0;
    }

    @SuppressWarnings("unchecked")
    private void insertEmployees(List<Employee> employees) {
        new InsertEmployeesTask().execute(employees);
    }

    private static class InsertEmployeesTask extends AsyncTask<List<Employee>, Void, Void> {

        @Override
        protected Void doInBackground(List<Employee>... lists) {

            if (isValidData(lists)) {
                db.employeeDAO().insertEmployees(lists[0]);
            }

            return null;
        }
    }

    private void deleteAllEmployees() {
        new DeleteAllEmployeesTask().execute();
    }

    private static class DeleteAllEmployeesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            db.employeeDAO().deleteAllEmployees();
            return null;
        }
    }

    public void loadData() {
        ApiService apiService = ApiFactory.getInstance().getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        employeeResponse -> {
                            deleteAllEmployees();
                            insertEmployees(employeeResponse.getEmployees());
                        },
                        throwable -> {
                            errors.setValue(throwable);
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
