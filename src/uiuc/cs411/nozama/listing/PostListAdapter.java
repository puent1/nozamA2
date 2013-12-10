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

public class PostListAdapter extends ArrayAdapter {
	private List<? extends Post> objects;
	Context mContext;
	int mResource;
	
	
	public PostListAdapter(Context context, int resource, List<? extends Post> objects) {
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
		v.setFocusable(false);
		v.setFocusableInTouchMode(false);
		((TextView) v.findViewById(R.id.reply_item_title)).setText(objects.get(position).title);
		((TextView) v.findViewById(R.id.reply_item_body)).setText(objects.get(position).body);
		((TextView) v.findViewById(R.id.reply_item_user)).setText(objects.get(position).user);
		if(ItemListActivity.username.equals(objects.get(position).user)) {
			v.setBackgroundColor(Color.parseColor("#99CC00"));
			v.findViewById(R.id.reply_item_edit).setVisibility(View.VISIBLE);
			v.findViewById(R.id.reply_item_delete).setVisibility(View.VISIBLE);
			
			v.findViewById(R.id.reply_item_edit).setOnClickListener(new ReplyEditListener(objects.get(position)));
			v.findViewById(R.id.reply_item_delete).setOnClickListener(new ReplyDeleteListener(objects.get(position)));
		}
		else {
			v.setBackgroundColor(Color.LTGRAY);
			v.findViewById(R.id.reply_item_edit).setVisibility(View.GONE);
			v.findViewById(R.id.reply_item_delete).setVisibility(View.GONE);
		}
		return v;
	
	}
	
	private final class ReplyEditListener implements OnClickListener {
		private final Post post;

		private ReplyEditListener(Post post) {
			this.post = post;
		}

		@Override
		public void onClick(View v) {
			//((PageListActivity) mContext).switchFragment(objects.get(pos));
		}
	}

	private final class ReplyDeleteListener implements OnClickListener {
		private final Post post;
		

		private ReplyDeleteListener(Post post) {
			this.post = post;
		}

		@Override
		public void onClick(View v) {
			if(post instanceof ListingContent.Listing ) {
				new DatabaseTask().execute("" + DatabaseTask.POST_DELETE, post.id);

			} else if (post instanceof ReplyContent.Reply) {
				new DatabaseTask().execute("" + DatabaseTask.REPLY_DELETE, post.id);
			}
			
		}
	}
}
