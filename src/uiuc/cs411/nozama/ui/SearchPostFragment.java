package uiuc.cs411.nozama.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.content.Content;
import uiuc.cs411.nozama.listing.ListingContent;
import uiuc.cs411.nozama.listing.ListingContent.Post;
import uiuc.cs411.nozama.listing.PageListActivity;
import uiuc.cs411.nozama.network.DatabaseTask;

/**
 * A fragment for creating a post. This fragment is either contained in a
 * {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class SearchPostFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Content.Item mItem;

	public static ArrayAdapter<Post> adapter;

	public static ArrayList<Content.Item> titles = new ArrayList<Content.Item>();
	public static ArrayList<Content.Item> bodies = new ArrayList<Content.Item>();

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SearchPostFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = Content.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.search_post, container, false);
		EditText search = (EditText) rootView.findViewById(R.id.searchQuery);

		ListView resultList = (ListView) rootView
				.findViewById(R.id.queryResults);

		// TODO: replace with a real list adapter.
		adapter = new ArrayAdapter<ListingContent.Post>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, ListingContent.ITEMS);

		resultList.setAdapter(adapter);

		resultList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long itemId) {
				Intent args = new Intent(getActivity(), PageListActivity.class);
				args.putExtra("position", pos);
				startActivity(args);

			}

		});

		// resultList.setAdapter(new ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_list_item_activated_2,
		// android.R.id.text1) {
		//
		// @Override
		// public View getView(int position, View convertView, ViewGroup parent)
		// {
		// View view = super.getView(position, convertView, parent);
		// TextView text1 = (TextView) view
		// .findViewById(android.R.id.text1);
		// TextView text2 = (TextView) view
		// .findViewById(android.R.id.text2);
		//
		// String title = titles.get(position);
		// String body = bodies.get(position);
		//
		//
		// text1.setText(title);
		// text2.setText(body);
		// return view;
		// }
		// });

		search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH
						|| actionId == EditorInfo.IME_ACTION_NEXT) {
					String query = v.getText().toString();
					if (query.length() > 0) {

						new DatabaseTask().execute(""
								+ DatabaseTask.SEARCH_QUERY, query);

						return true;
					}
				}
				return false;
			}
		});
		return rootView;
	}

	public static void emptyLists() {
		bodies.clear();
		titles.clear();

	}
}
