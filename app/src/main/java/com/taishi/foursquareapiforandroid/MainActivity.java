package com.taishi.foursquareapiforandroid;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.taishi.foursquareapiforandroid.Model.Explore.Explore;
import com.taishi.foursquareapiforandroid.Model.Explore.Item_;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

	EditText etGeolocation, etQuery;
	Button btnSearch;
	ListView listView;
	String Client_ID = "VIEQ0QX5GAJ1XLDJABA5WBS54XCVTNWLNY2NLAZVNB2ZDUYM";
	String Client_Secret = "COARL4531NXUEZTWDE21201TRAZXPEFIQKXFY4AJKHWHDXOT";
	String apiVersion = "20161010";
	String geoLocation = "40.7,-74";
	String query = "cafe";

	// View stuff
	private CoordinatorLayout coordinatorLayout;


	List<Item_> item_list = new ArrayList<Item_>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupViews();
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				loadVenues(etQuery.getText().toString());

			}
		});
	}
	void setupViews(){
		etGeolocation = (EditText) findViewById(R.id.et_geolocation);
		etQuery = (EditText) findViewById(R.id.et_query);
		btnSearch = (Button) findViewById(R.id.btn_search);
		listView = (ListView)findViewById(R.id.listivew);

		// Use this for SnackBar
		coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
	}


	/**
	 *  Get Venue datas (Currently you can only pass word as a query. NO geolocation..)
	 * @param query Word you want to search
	 */
	private void loadVenues(String query) {
		// Access to the seerver by using Retrofit
		final FoursquareApplication application = (FoursquareApplication) getApplication();
		// Give some words for query
		Observable<Explore> observable = application.getFoursquareService().requestExplore(Client_ID, Client_Secret, apiVersion, geoLocation, query);
		// Do Networking in IO thread and get the result on Main Thread
		observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Explore>() {
			@Override
			public void onNext(Explore explore) {
				item_list = explore.getResponse().getGroups().get(0).getItems();
				ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_list);
				listView.setAdapter(exploreListAdapter);
			}

			@Override
			public void onError(Throwable e) {
				// This is called when networking is failed
				// Show snakbar wooo!!
				Snackbar.make(coordinatorLayout, "Can't get the datas...(T.T)", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}

			@Override
			public void onCompleted() {

			}
		});


	}


}
