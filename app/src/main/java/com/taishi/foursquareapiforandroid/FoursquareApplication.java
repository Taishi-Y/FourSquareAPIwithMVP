package com.taishi.foursquareapiforandroid;

import android.app.Application;
import android.util.Log;

import com.taishi.foursquareapiforandroid.Service.FoursquareService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yamasakitaishi on 2017/01/24.
 *
 * @author Taishi Yamasaki ( https://github.com/Taishi-Y )
 */

public class FoursquareApplication extends Application {
	private Retrofit retrofit;
	private FoursquareService foursquareService;

	@Override
	public void onCreate() {
		super.onCreate();
		// Define the API to make it accessable from any Activities
		setupAPIClient();
	}

	private void setupAPIClient() {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(String message) {
				Log.d("API LOFG", message);
			}
		});

		logging.setLevel((HttpLoggingInterceptor.Level.BASIC));

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

		retrofit = new Retrofit.Builder()
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl("https://api.foursquare.com/v2/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();

		foursquareService = retrofit.create(FoursquareService.class);
	}

	public FoursquareService getFoursquareService() {
		return foursquareService;
	}
}
