package com.example.minitiktok.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<UserEntity> loadAll();

    @Query("SELECT password FROM user WHERE _Id = :id")
    String select(String id);

    @Insert
    long addTodo(UserEntity entity);

    @Query("DELETE FROM user")
    void deleteAll();

    @Delete
    void deleteTodo(UserEntity entity);

    @Query("DELETE FROM user WHERE _Id = :id")
    void deleteTodo(String id);

}
