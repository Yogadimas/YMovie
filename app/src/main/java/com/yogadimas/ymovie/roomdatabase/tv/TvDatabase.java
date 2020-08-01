package com.yogadimas.ymovie.roomdatabase.tv;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yogadimas.ymovie.model.tv.ResultsTv;

@Database(entities = ResultsTv.class, version = 1)
public abstract class TvDatabase extends RoomDatabase {

    private static TvDatabase tvDatabase;

    public static TvDatabase getTvDatabase(Context context) {
        synchronized (TvDatabase.class) {
            if (tvDatabase == null) {
                tvDatabase = Room.databaseBuilder(context, TvDatabase.class, "db_tv").allowMainThreadQueries().build();
            }
        }
        return tvDatabase;
    }

    public abstract TvDao tvDao();

}
