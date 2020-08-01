package com.yogadimas.ymovie.roomdatabase.movie;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yogadimas.ymovie.model.movie.ResultsMovie;

@Database(entities = ResultsMovie.class, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase movieDatabase;

    public static MovieDatabase getMovieDatabase(Context context) {
        synchronized (MovieDatabase.class) {
            if (movieDatabase == null) {
                movieDatabase = Room.databaseBuilder(context, MovieDatabase.class, "db_movie").allowMainThreadQueries().build();
            }
        }
        return movieDatabase;
    }

    public abstract MovieDao movieDao();
}
