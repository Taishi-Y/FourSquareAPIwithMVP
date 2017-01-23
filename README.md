## Foursquare API client sample app for Android with Retrofit

![main screen](https://github.com/Taishi-Y/Foursquare-API-client-sample-app-for-Android/blob/master/images/screenshot.png?raw=true)


### Overview

This project is getting venue datas from Foursquare API with Retrofit.( https://developer.foursquare.com/overview/ )
Retrofit is an HTTP library.

You can just clone this sample repository and know how it works very easily.
Personally I think Foursquare API is good for learning how to get and parse JSON data.

Before I've used volley library to handle JSON. Volley is an HTTP library that makes networking for Android apps easier and most importantly, faster. 
BUT I switched to Retrofit. When I saw Retrofit document I feel this is difficult to approach but once tried some tutorials, I was able to reduce messy code and make it cleaner. 
Also Retrofit is faster than Volley. ( http://stackoverflow.com/questions/16902716/comparison-of-android-networking-libraries-okhttp-retrofit-volley )


### Set up

Get your "Client_ID" and "Client_Secret" from this link:
https://ja.foursquare.com/developers/apps

Set your  "Client_ID" and "Client_Secret":
MainActivity.java
```java
public class MainActivity extends AppCompatActivity {
	String Client_ID = "YOUR CLIENT ID";
	String Client_Secret = "YOUR CLIENT SECRET";	
//....
}
```

Make sure to require Internet permissions in your AndroidManifest.xml file:
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET" />
</manifest>
```

We will use Retrofit library( https://square.github.io/retrofit/ )
Add the following to your app/build.gradle file:
```java
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.retrofit2:converter-gson:2.1.0'

// This is for 
provided 'org.glassfish:javax.annotation:10.0-b28'
```



### 1. Creating the Retrofit instance
```java
public interface FourSquareService {
	Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.foursquare.com/v2/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
}
```
### 2. Define the Endpoints
For instance, the interface defines each endpoint in the following way:
```java
public interface FourSquareService {

	@GET("venues/explore/")
	Call<Explore> requestExplore(
			@Query("client_id") String client_id,
			@Query("client_secret") String client_secret,
			@Query("v") String v,
			@Query("ll") String ll,
			@Query("query") String query);


	Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://api.foursquare.com/v2/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();
}
```

### 3. Make model classes
You can get venues data json response from this link.
(https://developer.foursquare.com/docs/venues/explore)


Make model classes by using jsonschema2pojo. (http://www.jsonschema2pojo.org/)
You can learn how to make pojo automatically from this website.
After you make model classes you should download as a zip file and put all model classes into your project.(E.g. YOURPROJECT>app>src>main>java>com.exmaple.yourproject>model)
https://guides.codepath.com/android/Consuming-APIs-with-Retrofit#create-java-classes-for-resources


### 4. Make layout files

activity_main.xlm :
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:orientation="vertical"
    tools:context="com.taishi.foursquareapiforandroid.MainActivity">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Latitude and Longitude"/>

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"
            android:text="40.7,-74"
            android:ems="10"
            android:id="@+id/et_geolocation"/>
    </LinearLayout>

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="query"/>

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"
            android:text="cafe"
            android:ems="10"
            android:id="@+id/et_query"/>
    </LinearLayout>

    <Button
        android:text="Search"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btn_search"/>

    <ListView
        android:id="@+id/listivew"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>


</LinearLayout>

```



This is a layout of item for listview in MainActivity.
item_list.xml:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:orientation="vertical"
    tools:context="com.taishi.foursquareapiforandroid.MainActivity">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Latitude and Longitude"/>

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"
            android:text="40.7,-74"
            android:ems="10"
            android:id="@+id/et_geolocation"/>
    </LinearLayout>

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="query"/>

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"
            android:text="cafe"
            android:ems="10"
            android:id="@+id/et_query"/>
    </LinearLayout>

    <Button
        android:text="Search"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btn_search"/>

    <ListView
        android:id="@+id/listivew"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>


</LinearLayout>
```



### 5. Make MainActivity
At this time we are going to call Rest API in AsyncTask class.
AsyncTask enables proper and easy use of the UI thread. This class allows you to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.


I will introduce you how can you call Rest API without AsyncTask later in this tutorial.

MainActivity.java:
```java
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
				// Execute ExploreAsyncTask 
				ExploreAsyncTask exploreAsyncTask = new ExploreAsyncTask();
				exploreAsyncTask.execute();
			}
		});
	}
	
	// Initialize some elements (I just want to organize it. If you feel this is so troublesome.. You can look at Butter knife library.)
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


```
### 6. Make adapter class for ListView

In Android development, any time we want to show a vertical list of scrollable items we will use a ListView which has data populated using an Adapter.

When using an adapter and a ListView, we need to make sure to understand how view recycling works.

When your ListView is connected to an adapter, the adapter will instantiate rows until the ListView has been fully populated with enough items to fill the full height of the list. At that point, no additional row items are created in memory.

Instead, as the user scroll through the list, items that leave the screen are kept in memory for later use and then every new row that enters the screen reuses an older row kept around in memory. In this way, even for a list of 1000 items, only ~7 item view rows are ever instantiated or held in memory. 
[This](https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#row-view-recycling) explain about ListView and ArrayAdapter and how to use these.

```java
public class ExploreListAdapter extends ArrayAdapter<Item_> {
	private LayoutInflater layoutInflater_;

	public ExploreListAdapter(Context context, int layout, List<Item_> objects) {
		super(context, layout, objects);
		layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		Item_ item_ = getItem(position);
		
		// Check if an existing view is being reused, otherwise inflate the view
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.item_list, null);
		}
		
		// Lookup view for data population
		TextView tvName = (TextView) convertView.findViewById(R.id.tv_item_name);
		TextView tvPoint = (TextView) convertView.findViewById(R.id.tv_list_point);
		TextView tvGenre = (TextView) convertView.findViewById(R.id.tv_item_genre);
		TextView tvDistance = (TextView) convertView.findViewById(R.id.tv_item_distance);
		TextView tvComment = (TextView) convertView.findViewById(R.id.tv_item_comment);

		// Get each data from "item_"
		String name = item_.getVenue().getName();
		double point = item_.getVenue().getRating();
		String genre = item_.getVenue().getCategories().get(0).getName();
		int distance = item_.getVenue().getLocation().getDistance();
		String comment = item_.getTips().get(0).getText();
		
		// Populate the data into the template view using the data object
		tvName.setText(name);
		tvPoint.setText(String.valueOf(point));
		tvGenre.setText(genre);
		tvDistance.setText(String.valueOf(distance));
		tvComment.setText(comment);

		return convertView;
	}
}
```

### 7. Improving Performance with the ViewHolder Pattern
To improve performance, we should modify the custom adapter by applying the ViewHolder pattern which speeds up the population of the ListView considerably by caching view lookups for smoother, faster item loading:

```java
public class ExploreListAdapter extends ArrayAdapter<Item_> {
	private LayoutInflater layoutInflater_;

	// View lookup cache
	private static class ViewHolder {
		TextView tvName;
		TextView tvPoint;
		TextView tvGenre;
		TextView tvDistance;
		TextView tvComment;
	}

	public ExploreListAdapter(Context context, int layout, List<Item_> objects) {
		super(context, layout, objects);
		layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		Item_ item_ = getItem(position);

		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			// If there's no view to re-use, inflate a brand new view for row
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.item_list, parent, false);

			// Lookup view for data population
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_item_name);
			viewHolder.tvPoint = (TextView) convertView.findViewById(R.id.tv_list_point);
			viewHolder.tvGenre = (TextView) convertView.findViewById(R.id.tv_item_genre);
			viewHolder.tvDistance = (TextView) convertView.findViewById(R.id.tv_item_distance);
			viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tv_item_comment);

			// Cache the viewHolder object inside the fresh view
			convertView.setTag(viewHolder);
		} else {
			// View is being recycled, retrieve the viewHolder object from tag
			viewHolder = (ViewHolder) convertView.getTag();
		}


		// Get each data from "item_"
		String name = item_.getVenue().getName();
		double point = item_.getVenue().getRating();
		String genre = item_.getVenue().getCategories().get(0).getName();
		int distance = item_.getVenue().getLocation().getDistance();
		String comment = item_.getTips().get(0).getText();

		// Populate the data from the data object via the viewHolder object
		// into the template view.

		viewHolder.tvName.setText(name);
		viewHolder.tvPoint.setText(String.valueOf(point));
		viewHolder.tvGenre.setText(genre);
		viewHolder.tvDistance.setText(String.valueOf(distance));
		viewHolder.tvComment.setText(comment);

		// Return the completed view to render on screen
		return convertView;
	}
}
```

### 8. Consume the API asynchronously without AsyncTask
If we want to consume the API asynchronously, we call the service as follows:

```java
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
}
```

Thanks!
Feel free to ask me! :)
https://twitter.com/taishi0917
