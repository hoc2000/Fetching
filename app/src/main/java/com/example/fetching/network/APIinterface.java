package com.example.fetching.network;


import com.example.fetching.model.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIinterface {

    @GET("jsons/users.json")
    Call<List<Person>> getPerson();
}
