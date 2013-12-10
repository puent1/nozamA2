package uiuc.cs411.nozama.network;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import uiuc.cs411.nozama.LoginActivity;
import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.RegisterActivity;
import uiuc.cs411.nozama.content.Content;
import uiuc.cs411.nozama.content.Content.Item;
import uiuc.cs411.nozama.listing.ListingContent;
import uiuc.cs411.nozama.listing.PageDetailFragment;
import uiuc.cs411.nozama.listing.ReplyContent;
import uiuc.cs411.nozama.ui.CreatePostFragment;
import uiuc.cs411.nozama.ui.ItemListActivity;
import uiuc.cs411.nozama.ui.MyPostFragment;
import uiuc.cs411.nozama.ui.SearchPostFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class DatabaseTask extends AsyncTask<String, Void, JSONObject> {

	public static final int CREATE_POST = 0;
	public static final int SEARCH_QUERY = 1;
	public static final int LOGIN = 2;
	public static final int REGISTER = 3;
	public static final int MY_POST_QUERY = 4;
	public static final int CREATE_RESPONSE = 6;
	public static final int REPLY_QUERY = 5;
	public static final int REPLY_DELETE = 7;
	public static final int REPLY_EDIT = 8;
	public static final int POST_DELETE = 9;
	public static int lock;

	private static final String DATABASE_SITE = "http://web.engr.illinois.edu/~mgathma2/noZama/noZamaDB.php";
	public static final int POST_EDIT = 10;
	public static final int GOOGLE = 11;
	

	@Override
	protected JSONObject doInBackground(String... params) {
		Log.d("TEST", "testing execute");
		int type = Integer.parseInt(params[0]);
		List<NameValuePair> nameValuePairs;
		JSONObject response = null;
		switch (type) {
		case CREATE_POST:
			nameValuePairs = new ArrayList<NameValuePair>(5);
			nameValuePairs.add(new BasicNameValuePair("title", params[1]));
			nameValuePairs.add(new BasicNameValuePair("user",
					ItemListActivity.username));
			nameValuePairs.add(new BasicNameValuePair("tag", "new post"));
			nameValuePairs.add(new BasicNameValuePair("body", params[2]));
			nameValuePairs.add(new BasicNameValuePair("pic", params[3]));
			response = sendHttpPost(CREATE_POST, nameValuePairs);
			break;
		case SEARCH_QUERY:
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("keyword", params[1]));
			nameValuePairs.add(new BasicNameValuePair("tag", "search posts"));
			response = sendHttpPost(SEARCH_QUERY, nameValuePairs);
			break;
		case LOGIN:
			nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("userName", params[1]));
			nameValuePairs.add(new BasicNameValuePair("tag", "login"));
			nameValuePairs.add(new BasicNameValuePair("password", params[2]));
			response = sendHttpPost(LOGIN, nameValuePairs);
			break;
		case REGISTER:
			nameValuePairs = new ArrayList<NameValuePair>(5);
			nameValuePairs.add(new BasicNameValuePair("userName", params[1]));
			nameValuePairs.add(new BasicNameValuePair("tag", "register"));
			nameValuePairs.add(new BasicNameValuePair("password", params[2]));
			nameValuePairs.add(new BasicNameValuePair("email", params[3]));
			nameValuePairs.add(new BasicNameValuePair("location", params[4]));
			response = sendHttpPost(REGISTER, nameValuePairs);
			break;
		case MY_POST_QUERY:
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("userName", params[1]));
			nameValuePairs.add(new BasicNameValuePair("tag", "userPosts"));
			response = sendHttpPost(MY_POST_QUERY, nameValuePairs);
			break;
		case CREATE_RESPONSE:
			nameValuePairs = new ArrayList<NameValuePair>(5);
			nameValuePairs.add(new BasicNameValuePair("userName", params[1]));
			nameValuePairs.add(new BasicNameValuePair("tag", "newRes"));
			nameValuePairs.add(new BasicNameValuePair("title", params[3]));
			nameValuePairs.add(new BasicNameValuePair("body", params[4]));
			nameValuePairs.add(new BasicNameValuePair("id", params[2]));
			nameValuePairs.add(new BasicNameValuePair("pic", params[5]));
			response = sendHttpPost(REGISTER, nameValuePairs);
		case REPLY_QUERY:
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("id", params[1]));
			nameValuePairs.add(new BasicNameValuePair("tag", "getResponses"));
			response = sendHttpPost(REPLY_QUERY, nameValuePairs);
			break;
		case REPLY_DELETE:
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("tag", "delReply"));
			nameValuePairs.add(new BasicNameValuePair("id", params[1]));
			response = sendHttpPost(REPLY_DELETE, nameValuePairs);
			break;
		case POST_DELETE:
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("tag", "delPost"));
			nameValuePairs.add(new BasicNameValuePair("id", params[1]));
			response = sendHttpPost(POST_DELETE, nameValuePairs);
			break;
		case POST_EDIT:
			nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("tag", "editPost"));
			nameValuePairs.add(new BasicNameValuePair("id", params[1]));
			nameValuePairs.add(new BasicNameValuePair("title", params[2]));
			nameValuePairs.add(new BasicNameValuePair("body", params[3]));
			response = sendHttpPost(POST_EDIT, nameValuePairs);
			break;
		case REPLY_EDIT:
			nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("tag", "editResp"));
			nameValuePairs.add(new BasicNameValuePair("resId", params[1]));
			nameValuePairs.add(new BasicNameValuePair("title", params[2]));
			nameValuePairs.add(new BasicNameValuePair("body", params[3]));
			response = sendHttpPost(REPLY_EDIT, nameValuePairs);
			break;
		case GOOGLE:
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("tag", "suggestImg"));
			nameValuePairs.add(new BasicNameValuePair("keyword", params[1]));
			response = sendHttpPost(REPLY_EDIT, nameValuePairs);
			break;
		}
		return response;
	}

	private JSONObject sendHttpPost(int postType,
			List<NameValuePair> nameValuePairs) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(DATABASE_SITE);
		JSONObject jo = null;

		try {
			Log.d("HTTP", "Sending HTTP POST");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			Log.d("HTTP", "Finished sending");
			String result = EntityUtils.toString(response.getEntity());
			Log.d("MYJSON", result);
			jo = new JSONObject(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	protected void onPostExecute(JSONObject result) {
		try {
			if (result.getString("tag").equals("search posts")) {
				JSONArray posts = result.getJSONArray("posts");
				ListingContent.init(posts);

				SearchPostFragment.adapter.notifyDataSetChanged();

			}
			if (result.getString("tag").equals("userPosts")) {
				JSONArray posts = result.getJSONArray("posts");
				ListingContent.init(posts);

				MyPostFragment.adapter.notifyDataSetChanged();
			}
			if (result.getString("tag").equals("login")) {
				Log.d("result", result.toString());
				LoginActivity.ret_val = result;
				Log.d("ret_val", LoginActivity.ret_val.toString());
				LoginActivity.checkResult();
			}
			if (result.getString("tag").equals("register")) {
				Log.d("result", result.toString());
				RegisterActivity.ret_val = result;
				Log.d("ret_val", RegisterActivity.ret_val.toString());
				RegisterActivity.checkResult();
			}
			if (result.getString("tag").equals("getResponses")) {
				if (result.getInt("success") == 1) {
					JSONArray responses = result.getJSONArray("responses");
					ReplyContent.init(responses);
					PageDetailFragment.mListView.setVisibility(View.VISIBLE);
					Log.d("getResponses", responses.toString());
				} else {
					ReplyContent.clear();
					PageDetailFragment.mListView.setVisibility(View.VISIBLE);
				}
				PageDetailFragment.replyAdapter.notifyDataSetChanged();

			}
			if (result.getString("tag").equals("suggestImg")) {
				if (result.getInt("success") == 1) {
					CreatePostFragment.responses = result.getJSONArray("urls");
					CreatePostFragment.checkResult();
					
				} else {
					
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
