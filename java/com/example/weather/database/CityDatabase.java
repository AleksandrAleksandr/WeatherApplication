package com.example.weather.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.weather.model.LocalCity;
import com.example.weather.model.PopularCity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LocalCity.class, PopularCity.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    public abstract ICityDao Dao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public void populateInitialData(){
        databaseWriteExecutor.execute(() -> {
            if (Dao().count() == 0){
                ArrayList<PopularCity> popular = new ArrayList<>();
                popular.add(new PopularCity("London", "GB", 51.5085, -0.1257));
                popular.add(new PopularCity("Madrid", "ES", 40.4165, -3.7026));
                popular.add(new PopularCity("Paris", "FR", 48.8534, 2.3488));
                popular.add(new PopularCity("Rome", "IT", 41.8947, 12.4839));
                popular.add(new PopularCity("Athens", "GR", 37.9795, 23.7162));
                popular.add(new PopularCity("Oslo", "NO", 59.9127, 10.7461));
                popular.add(new PopularCity("Cairo", "EG", 30.0626, 31.2497));
                popular.add(new PopularCity("Tokyo", "JP", 35.6895, 139.6917));
                popular.add(new PopularCity("Moscow", "RU", 55.7522, 37.6156));

                Dao().insertAll(popular);
            }
        });
    }

//    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                WordDao dao = INSTANCE.wordDao();
//                dao.deleteAll();
//                Dao().
//
//                Word word = new Word("Hello");
//                dao.insert(word);
//                word = new Word("World");
//                dao.insert(word);
//
//                ArrayList<PopularCity> popular = new ArrayList<>();
//                popular.add(new PopularCity("London", "GB", 51.5085, -0.1257));
//                popular.add(new PopularCity("Madrid", "ES", 40.4165, -3.7026));
//                popular.add(new PopularCity("Paris", "FR", 48.8534, 2.3488));
//                popular.add(new PopularCity("Rome", "IT", 41.8947, 12.4839));
//                popular.add(new PopularCity("Athens", "GR", 37.9795, 23.7162));
//                popular.add(new PopularCity("Oslo", "NO", 59.9127, 10.7461));
//                popular.add(new PopularCity("Cairo", "EG", 30.0626, 31.2497));
//
//
//            });
//        }
//    };

}
