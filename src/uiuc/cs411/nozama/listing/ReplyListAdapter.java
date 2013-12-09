package uiuc.cs411.nozama.listing;

import java.util.List;

import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.listing.ReplyContent.Reply;
import uiuc.cs411.nozama.network.DatabaseTask;
import uiuc.cs411.nozama.ui.ItemListActivity;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		
		((TextView) v.findViewById(R.id.reply_item_title)).setText(objects.get(position).title);
		((TextView) v.findViewById(R.id.reply_item_body)).setText(objects.get(position).body);
		((TextView) v.findViewById(R.id.reply_item_user)).setText(objects.get(position).user);
		if(ItemListActivity.username.equals(objects.get(position).user)) {
			v.setBackgroundColor(Color.parseColor("#99CC00"));
			v.findViewById(R.id.reply_item_edit).setVisibility(View.VISIBLE);
			v.findViewById(R.id.reply_item_delete).setVisibility(View.VISIBLE);
			
			final int pos = position;
			v.findViewById(R.id.reply_item_edit).setOnClickListener(new ReplyEditListener(pos));
			v.findViewById(R.id.reply_item_delete).setOnClickListener(new ReplyDeleteListener(pos));
		}
		else {
			v.setBackgroundColor(Color.LTGRAY);
			v.findViewById(R.id.reply_item_edit).setVisibility(View.GONE);
			v.findViewById(R.id.reply_item_delete).setVisibility(View.GONE);
		}
		return v;
	
	}
	
	private final class ReplyEditListener implements OnClickListener {
		private final int pos;

		private ReplyEditListener(int pos) {
			this.pos = pos;
		}

		@Override
		public void onClick(View v) {
			((PageListActivity) mContext).switchFragment(objects.get(pos));
		}
	}

	private final class ReplyDeleteListener implements OnClickListener {
		private final int pos;

		private ReplyDeleteListener(int pos) {
			this.pos = pos;
		}

		@Override
		public void onClick(View v) {
			new DatabaseTask().execute("" + DatabaseTask.REPLY_DELETE, objects.get(pos).id);
		}
	}
}
