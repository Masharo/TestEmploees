package com.example.testemploees.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.testemploees.pojo.Employee;

import java.util.Objects;

@Database(entities = {Employee.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "employees_db";
    private static AppDatabase appDatabaseThis;

    protected AppDatabase() {
        appDatabaseThis = null;
    }

    public synchronized static AppDatabase getInstance(Context context) {

        if (Objects.isNull(appDatabaseThis)) {
            appDatabaseThis = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                                  .fallbackToDestructiveMigration()
                                  .build();
        }

        return appDatabaseThis;
    }

    public abstract EmployeeDAO employeeDAO();
}
