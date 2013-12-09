package uiuc.cs411.nozama.ui;

import java.util.ArrayList;

import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.listing.ListingContent.Post;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchPostListAdapter extends ArrayAdapter<Post> {
	private Post[] objects;
	Context mContext;
	int mResource;
	
	
	public SearchPostListAdapter(Context context, int resource, Post[] objects) {
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
		
		((TextView) v.findViewById(R.id.page_detail_title)).setText(objects[position].title);
		((TextView) v.findViewById(R.id.page_detail_body)).setText(objects[position].body);
				
		return v;
	
	}
}
