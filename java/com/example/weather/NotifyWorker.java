package com.example.weather;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.hilt.Assisted;
import androidx.hilt.work.WorkerInject;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.weather.model.server.Forecast;
import com.example.weather.model.server.ServerResponce;
import com.example.weather.network.IServerClient;
import com.example.weather.repositories.ForecastsRepository;

import retrofit2.Call;
import retrofit2.Response;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class NotifyWorker extends Worker {

    private final static String NOTIFICATION_CHANNEL = "weather_channel";
    public static final String cityTag = "city_tag";
    private final static int NOTIFICATION_ID = 1;
    private IServerClient mServerClient;
    //private ForecastsRepository mRepository;

    @WorkerInject
    public NotifyWorker(@Assisted @NonNull Context context, @Assisted @NonNull WorkerParameters params, IServerClient serverClient) {
        super(context, params);
        this.mServerClient = serverClient;
    }

    @NonNull
    @Override
    public Result doWork() {

        String cityName = getInputData().getString(cityTag);

        Call<ServerResponce> call = mServerClient.getService().getForecasts(cityName, "da8d4701bff23d868ff44b24852181db");
        Forecast forecast = null;

        try {
            Response<ServerResponce> serverResponce = call.execute();
            forecast = serverResponce.body().forecasts.get(1);
            Log.i("work_test", forecast.dtTxt);
        }
        catch (Exception ex) { ex.printStackTrace(); }

        showNotification(cityName, forecast);

        return Result.success();
    }

    private void showNotification(String cityName, Forecast forecast){
        //final String notification_channel = "channel_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL, importance);

            channel.setLightColor(Color.MAGENTA);

            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);


        String notificationTitle = cityName + ", " + forecast.dtTxt.substring(11, 16);

        int temp = (int)Math.round(forecast.main.temp);
        String desc = forecast.weather.get(0).description;
        String description = Character.toUpperCase(desc.charAt(0)) + desc.substring(1);
        String notificationText = String.valueOf(temp) + " \u2103" + ", " + description;
        String notificationInfo = "Прогноз погоды ";
        String path = "https://openweathermap.org/img/wn/" + forecast.weather.get(0).icon + "@2x.png";

        //build the notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL)
                        .setSmallIcon(R.drawable.ic_moon)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationText)
                        .setContentInfo(notificationInfo)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //trigger the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

//        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        Glide.with(getApplicationContext()).asBitmap().load(path).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                notificationBuilder.setLargeIcon(resource);
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }

        });
    }
}
