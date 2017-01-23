package com.taishi.foursquareapiforandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taishi.foursquareapiforandroid.Model.Explore.Item_;

import java.util.List;


/**
 * Created by yamasakitaishi on 2016/10/20.
 */

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