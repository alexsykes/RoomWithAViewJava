package com.alexsykes.roomwithaviewjava;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Trial.class}, version = 2, exportSchema = false)
public abstract class TrialRoomDatabase extends RoomDatabase {

     abstract TrialDao trialDao();

    private static volatile TrialRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
     static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

     static TrialRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TrialRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TrialRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.

                TrialDao trialDao = INSTANCE.trialDao();
                Trial trial = new Trial("Evening Series");
                trialDao.insert(trial);
                 trial = new Trial("Club Championship");
                trialDao.insert(trial);
            });
        }
    };
}