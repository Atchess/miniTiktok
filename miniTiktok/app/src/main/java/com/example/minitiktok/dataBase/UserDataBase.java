package com.example.minitiktok.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Database(entities = {UserEntity.class}, version = 3, exportSchema = false)

public abstract class UserDataBase extends RoomDatabase {
    private static volatile UserDataBase INSTANCE;

    public abstract UserDao userDao();

    public UserDataBase() {

    }

    public static UserDataBase inst(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class, "todo.db").build();
                }
            }
        }
        return INSTANCE;
    }
}