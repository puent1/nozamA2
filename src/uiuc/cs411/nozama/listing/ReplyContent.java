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
public class ReplyContent {

	public static List<Reply> ITEMS = new ArrayList<Reply>();

	public static Map<String, Reply> ITEM_MAP = new HashMap<String, Reply>();

	public static void init(JSONArray replies) {
		ITEMS.clear();
		ITEM_MAP.clear();
		
		if (replies == null) {
			return;
		}
		for (int i = 0; i < replies.length(); i++) {
			try {
				addItem(new Reply(replies.getJSONObject(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void addItem(Reply item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	public static class Reply extends Post{
		

		// Image member variable

		public Reply(JSONObject reply) {
			this.id = tryToGetString(reply, "id");
			this.title = tryToGetString(reply, "title");
			this.body = tryToGetString(reply, "body");
			this.user = tryToGetString(reply, "user");
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

		@Override
		public void delete() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void edit() {
			// TODO Auto-generated method stub
			
		}
	}

	public static void clear() {
		ITEMS.clear();
		ITEM_MAP.clear();
		
	}
}
