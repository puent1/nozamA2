package uiuc.cs411.nozama.listing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.R.id;
import uiuc.cs411.nozama.R.layout;

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
	private ListingContent.Post mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public PageDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = ListingContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_page_detail,
				container, false);

		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.page_detail_title))
					.setText(mItem.title);
			
			((TextView) rootView.findViewById(R.id.page_detail_user))
			.setText("Post created by " + mItem.user);
			
			((TextView) rootView.findViewById(R.id.page_detail_body))
			.setText(mItem.body);
			
			String keywords = "keywords: ";
			for(int i = 0; i < mItem.keywords.length -1; i++) {
				keywords += mItem.keywords[i] + ", ";
			}
			keywords += mItem.keywords[mItem.keywords.length-1];
			((TextView) rootView.findViewById(R.id.page_detail_keywords))
			.setText(keywords);
			
		}

		return rootView;
	}
}
