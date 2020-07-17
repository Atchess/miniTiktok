package com.example.minitiktok.dataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Entity(tableName = "user")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long mId;

    @ColumnInfo(name = "name")
    public String mName;

    @ColumnInfo(name = "_Id")
    public String _id;

    @ColumnInfo(name = "password")
    public String mPassword;

    public UserEntity(){}
    public UserEntity(String mName, String mId, String mPassword) {
        this.mName = mName;
        this._id = mId;
        this.mPassword = mPassword;
    }

    public String get_id() {
        return _id;
    }

    public void setmName(String mContent) {
        this.mName = mContent;
    }

    public String getmName() {
        return mName;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String password) {
        this.mPassword = password;
    }
}
