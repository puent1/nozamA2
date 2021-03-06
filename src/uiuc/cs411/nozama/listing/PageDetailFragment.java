package uiuc.cs411.nozama.listing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.Reply_Editor_Activity;
import uiuc.cs411.nozama.R.id;
import uiuc.cs411.nozama.R.layout;
import uiuc.cs411.nozama.network.DatabaseTask;
import uiuc.cs411.nozama.ui.ItemListActivity;
import uiuc.cs411.nozama.ui.SearchPostListAdapter;

/**
 * A fragment representing a single Page detail screen. This fragment is either
 * contained in a {@link PageListActivity} in two-pane mode (on tablets) or a
 * {@link PageDetailActivity} on handsets.
 */
public class PageDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private ListingContent.Listing mItem;

	public static ListView mListView;

	public static PostListAdapter replyAdapter;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public PageDetailFragment() {
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.page_list_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		Intent i = new Intent(this.getActivity(), Reply_Editor_Activity.class);
		i.putExtra("title", "");
		i.putExtra("body", "");
		i.putExtra("id", mItem.id);
		i.putExtra("pic", "0");
		i.putExtra("flag", "createReply");
		this.getActivity().startActivity(i);
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = ListingContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));

			new DatabaseTask().execute("" + DatabaseTask.REPLY_QUERY, mItem.id);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_page_detail,
				container, false);

		mListView = (ListView) rootView.findViewById(R.id.page_reply_list);
		mListView.setVisibility(View.INVISIBLE);

		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.page_detail_title))
					.setText(mItem.title);

			((TextView) rootView.findViewById(R.id.page_detail_user))
					.setText("Post created by " + mItem.user);

			((TextView) rootView.findViewById(R.id.page_detail_body))
					.setText(mItem.body);

			String str = mItem.pic;
			if(str != null)
				Log.d("str", str);
			
			Bitmap bm = null;
			
			if(!str.equals("0")) {
			    byte[] imageAsBytes = Base64.decode(str.getBytes(), Base64.DEFAULT);
				bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
			}
			
			((ImageView) rootView.findViewById(R.id.matt_gathman)).setImageBitmap(bm);
			((ImageView) rootView.findViewById(R.id.matt_gathman)).setVisibility(View.VISIBLE);

			String keywords = "keywords: ";
			for (int i = 0; i < mItem.keywords.length - 1; i++) {
				keywords += mItem.keywords[i] + ", ";
			}
			keywords += mItem.keywords[mItem.keywords.length - 1];
			((TextView) rootView.findViewById(R.id.page_detail_keywords))
					.setText(keywords);

			replyAdapter = new PostListAdapter(getActivity(),
					R.layout.reply_list_item, ReplyContent.ITEMS);

			mListView.setAdapter(replyAdapter);

		}

		return rootView;
	}
}
