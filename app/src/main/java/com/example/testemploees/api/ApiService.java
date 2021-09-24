package com.example.testemploees.api;

import com.example.testemploees.pojo.EmployeeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("testTask.json")
    Observable<EmployeeResponse> getEmployees();
}
