package uiuc.cs411.nozama.listing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * 
 */
public class ListingContent {

	public static List<Post> ITEMS = new ArrayList<Post>();

	public static Map<String, Post> ITEM_MAP = new HashMap<String, Post>();

	public static void init(JSONArray posts) {
		ITEMS.clear();
		ITEM_MAP.clear();
		
		if (posts == null) {
			return;
		}
		for (int i = 0; i < posts.length(); i++) {
			try {
				addItem(new Post(posts.getJSONObject(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void addItem(Post item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	public static class Post {
		public String id;
		public String[] keywords;
		public String title;
		public String body;
		public String user;

		// Image member variable

		public Post(JSONObject post) {
			this.id = tryToGetString(post, "id");
			this.title = tryToGetString(post, "title");
			this.body = tryToGetString(post, "body");
			this.user = tryToGetString(post, "user");
			
			try {
				JSONArray jsonKeywords = post.getJSONArray("keywords");
			
				keywords = new String[jsonKeywords.length()];
				for (int i = 0; i < keywords.length; i++) {
					this.keywords[i] = jsonKeywords.getString(i);
				}
			} catch (Exception e) {
				String unsplit = tryToGetString(post, "keywords");
				if(!unsplit.equals("JSONException"))
					keywords = unsplit.split(" ");
				else
					keywords = null;
			}
		}

		private String tryToGetString(JSONObject post, String key) {
			try {
				return post.getString(key);
			} catch (Exception e) {
				e.printStackTrace();
				return "JSONException";
			}
		}
			
		@Override
		public String toString() {
			return title + ": " + body;

		}
	}
}
