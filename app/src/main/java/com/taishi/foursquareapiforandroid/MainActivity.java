package com.taishi.foursquareapiforandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.taishi.foursquareapiforandroid.Model.Explore.Explore;
import com.taishi.foursquareapiforandroid.Model.Explore.Item_;
import com.taishi.foursquareapiforandroid.Service.FourSquareService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	EditText etGeolocation, etQuery;
	Button btnSearch;
	ListView listView;
	String Client_ID = "VIEQ0QX5GAJ1XLDJABA5WBS54XCVTNWLNY2NLAZVNB2ZDUYM";
	String Client_Secret = "COARL4531NXUEZTWDE21201TRAZXPEFIQKXFY4AJKHWHDXOT";
	String apiVersion = "20161010";
	String geoLocation = "40.7,-74";
	String query = "cafe";


	List<Item_> item_list = new ArrayList<Item_>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewByIds();
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				ExploreAsyncTask exploreAsyncTask = new ExploreAsyncTask();
//				exploreAsyncTask.execute();

				FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
				final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, geoLocation, query);
				call.enqueue(new Callback<Explore>() {
					@Override
					public void onResponse(Call<Explore> call, Response<Explore> response) {
						item_list = response.body().getResponse().getGroups().get(0).getItems();
						ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_list);
						listView.setAdapter(exploreListAdapter);
					}

					@Override
					public void onFailure(Call<Explore> call, Throwable t) {

					}
				});
			}
		});
	}
	void findViewByIds(){
		etGeolocation = (EditText) findViewById(R.id.et_geolocation);
		etQuery = (EditText) findViewById(R.id.et_query);
		btnSearch = (Button) findViewById(R.id.btn_search);
		listView = (ListView)findViewById(R.id.listivew);
	}

	public class ExploreAsyncTask extends AsyncTask<Void,Void,List<Item_>> {

		public ExploreAsyncTask() {
			super();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<Item_> doInBackground(Void... voids) {
			FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
			final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, geoLocation, query);

			try {
				Explore explore =  call.execute().body();
				item_list = explore.getResponse().getGroups().get(0).getItems();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return item_list;
		}

		@Override
		protected void onPostExecute(List<Item_> item_s) {
			super.onPostExecute(item_s);
			ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_s);
			listView.setAdapter(exploreListAdapter);
		}
	}
}
