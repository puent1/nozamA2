package uiuc.cs411.nozama.listing;

import java.util.ArrayList;
import java.util.List;

import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.listing.ReplyContent.Reply;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReplyListAdapter extends ArrayAdapter<Reply> {
	private List<Reply> objects;
	Context mContext;
	int mResource;
	
	
	public ReplyListAdapter(Context context, int resource, List<Reply> objects) {
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
		v.setBackgroundColor(Color.parseColor("#7ADCF0"));
		return v;
	
	}
}
