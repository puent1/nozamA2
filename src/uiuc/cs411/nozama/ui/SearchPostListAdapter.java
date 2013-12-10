package uiuc.cs411.nozama.ui;

import java.util.ArrayList;
import java.util.List;

import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.listing.ListingContent.Listing;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchPostListAdapter extends ArrayAdapter<Listing> {
	private List<Listing> objects;
	Context mContext;
	int mResource;
	
	
	public SearchPostListAdapter(Context context, int resource, List<Listing> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.objects = objects;	
		this.mContext = context;
		this.mResource = resource;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		
		if(v == null) {
			LayoutInflater lI = ((Activity)mContext).getLayoutInflater();
			v = lI.inflate(mResource, parent, false);
		}
		
		((TextView) v.findViewById(R.id.list_item_title)).setText(objects.get(position).title);
		((TextView) v.findViewById(R.id.list_item_body)).setText(objects.get(position).body);
		return v;
	
	}
}
