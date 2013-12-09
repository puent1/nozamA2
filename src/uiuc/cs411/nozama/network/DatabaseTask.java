package uiuc.cs411.nozama.network;

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
import uiuc.cs411.nozama.ui.ItemListActivity;
import uiuc.cs411.nozama.ui.MyPostFragment;
import uiuc.cs411.nozama.ui.SearchPostFragment;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class DatabaseTask extends AsyncTask<String, Void, JSONObject> {

	public static final int CREATE_POST = 0;
	public static final int SEARCH_QUERY = 1;
	public static final int LOGIN = 2;
	public static final int REGISTER = 3;
	public static final int MY_POST_QUERY = 4;
	public static final int CREATE_RESPONSE = 6;
	public static int lock;
	
	private static final String DATABASE_SITE = "http://web.engr.illinois.edu/~mgathma2/noZama/noZamaDB.php";

	@Override
	protected JSONObject doInBackground(String... params) {
		Log.d("TEST", "testing execute");
		int type = Integer.parseInt(params[0]);
		List<NameValuePair> nameValuePairs;
		JSONObject response = null;
		switch (type) {
		case CREATE_POST:
			nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("title", params[1]));
			nameValuePairs.add(new BasicNameValuePair("user", ItemListActivity.username));
			nameValuePairs.add(new BasicNameValuePair("tag", "new post"));
			nameValuePairs.add(new BasicNameValuePair("body", params[2]));
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
			nameValuePairs.add(new BasicNameValuePair("title", params[2]));
			nameValuePairs.add(new BasicNameValuePair("body", params[3]));
			nameValuePairs.add(new BasicNameValuePair("id", params[5]));
			response = sendHttpPost(REGISTER, nameValuePairs);
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
			if(result.getString("tag").equals("search posts")) {
				JSONArray posts = result.getJSONArray("posts");
				ListingContent.init(posts);
			
				SearchPostFragment.adapter.notifyDataSetChanged();

			}
			if(result.getString("tag").equals("userPosts")) {
				JSONArray posts = result.getJSONArray("posts");
				ListingContent.init(posts);
			
				MyPostFragment.adapter.notifyDataSetChanged();
			}
			if(result.getString("tag").equals("login")){
				Log.d("result", result.toString());
				LoginActivity.ret_val = result;
				Log.d("ret_val", LoginActivity.ret_val.toString());
				LoginActivity.checkResult();
			}
			if(result.getString("tag").equals("register")){
				Log.d("result", result.toString());
				RegisterActivity.ret_val = result;
				Log.d("ret_val", RegisterActivity.ret_val.toString());
				RegisterActivity.checkResult();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
