package uiuc.cs411.nozama.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SearchPostListAdapter extends ArrayAdapter<String> {

	public SearchPostListAdapter(Context context, int resource,
			String[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}
	
	

}
