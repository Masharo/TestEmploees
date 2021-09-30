package com.example.testemploees.converters;

import androidx.room.TypeConverter;

import com.example.testemploees.pojo.Speciality;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    @TypeConverter
    public String listSpecialityToString(List<Speciality> specialities) {
        return new Gson().toJson(specialities);
    }

    @TypeConverter
    public List<Speciality> stringToListSpeciality(String listSpecialityAsString) {
        ArrayList list = new Gson().fromJson(listSpecialityAsString, ArrayList.class);
        ArrayList<Speciality> specialities = (ArrayList<Speciality>) list;
        return specialities;
    }
}
