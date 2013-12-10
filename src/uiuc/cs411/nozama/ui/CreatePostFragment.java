package uiuc.cs411.nozama.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.content.Content;
import uiuc.cs411.nozama.listing.Post;
import uiuc.cs411.nozama.network.DatabaseTask;
import uiuc.cs411.nozama.network.URLTask;

/**
 * A fragment for creating a post. This fragment is either contained in a
 * {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class CreatePostFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	public static final int CAMERA = 1;
	
	public static final int GALLERY = 2;
	
	public static JSONArray responses;
	
	/**
	 * The dummy content this fragment is presenting.
	 */
	private Content.Item mItem;
	
	private String title;
	
	private String body;
	
	private String pic;
	
	private String id;

	private String selectedImagePath;
	
	public static String image;
	
	private String flag = "create";
	
	public static ImageView iv;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CreatePostFragment() {
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.create_post_actions, menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		String title;
		String description;
		String pathToImage;

		title = ((EditText) getActivity().findViewById(R.id.titleInput))
				.getText().toString();
		description = ((EditText) getActivity().findViewById(
				R.id.descriptionInput)).getText().toString();
		pathToImage = (String) ((ImageView) getActivity().findViewById(
				R.id.imagePreview)).getContentDescription();

		if (title.length() == 0 || description.length() == 0) {// || pathToImage
																// == null) {
			Context context = getActivity().getApplicationContext();
			CharSequence text = "Please fill in all the required fields";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} else {
			Context context = getActivity().getApplicationContext();
			// CharSequence text = "title: " + title + ", description: " +
			// description + ", path:" + pathToImage;
			CharSequence text = "Uploading post";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			if(flag.equals("create")) {
			String[] taskParams = { "" + DatabaseTask.CREATE_POST, title,
					description, image };
			new DatabaseTask().execute(taskParams);
			}
			else if(flag.equals("post")) {
				String[] taskParams = { "" + DatabaseTask.POST_EDIT, id, title,
						description, image };
				new DatabaseTask().execute(taskParams);
			}
			else if(flag.equals("reply")) {
				String[] taskParams = { "" + DatabaseTask.REPLY_EDIT, id, title,
						description, image };
				new DatabaseTask().execute(taskParams);
			}
			else if(flag.equals("createReply")) {
				String[] taskParams = { "" + DatabaseTask.CREATE_RESPONSE, ItemListActivity.username, id, title,
						description, image };
				new DatabaseTask().execute(taskParams);
			}
		}

		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getArguments()!=null) {
			if (getArguments().containsKey(ARG_ITEM_ID)) {
				// Load the dummy content specified by the fragment
				// arguments. In a real-world scenario, use a Loader
				// to load content from a content provider.
				mItem = Content.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
			}
		}
		
		Bundle args = getActivity().getIntent().getExtras();
		if(args!=null) {
			title = (String) args.get("title");
			body = (String) args.get("body");
			pic = (String) args.get("pic");
			id = (String) args.get("id");
			flag = (String) args.getString("flag");
		}
		else {
			title = "";
			body = "";
			pic = null;
			id = "";
			flag = "create";
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().managedQuery(uri, projection, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.setHasOptionsMenu(true);
		View rootView = inflater
				.inflate(R.layout.create_post, container, false);

		((TextView) rootView.findViewById(R.id.titleInput)).setText(title);
		((TextView) rootView.findViewById(R.id.descriptionInput)).setText(body);

		iv = (ImageView) rootView.findViewById(R.id.imagePreview);
		ImageButton galleryButton = (ImageButton) rootView
				.findViewById(R.id.upload_gallery);
		galleryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, GALLERY);
			}
		});
		
		ImageButton googleButton = (ImageButton) rootView
				.findViewById(R.id.upload_google);
		googleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String[] taskParams = { "" + DatabaseTask.GOOGLE, title,
						};
				new DatabaseTask().execute(taskParams);
			}
		
		});

		String fileName = "pic.jpg";
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, fileName);
		values.put(MediaStore.Images.Media.DESCRIPTION,
				"Image capture by camera");
		final Uri imageUri = getActivity().getContentResolver().insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		ImageButton cameraButton = (ImageButton) rootView
				.findViewById(R.id.upload_camera);
		cameraButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, CAMERA);
			}

		});

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		ImageView imageView = (ImageView) getActivity().findViewById(
				R.id.imagePreview);
		
		
		if (resultCode == Activity.RESULT_OK && data != null) {
			Bitmap bm = null;	
			switch (requestCode) {
			case CAMERA:
				bm = (Bitmap) data.getExtras().get("data");
				break;
			case GALLERY:
				Uri selectedImage = data.getData();
				String picturePath = getPathFromData(selectedImage);
				
				bm = BitmapFactory.decodeFile(picturePath);
				break;
			}
			float bmWHRatio = bm.getWidth() / (float) (bm.getHeight());
			
			
			//bm = Bitmap.createScaledBitmap(bm, ((int) (imageView.getWidth() * widthToHeight)), bm.getHeight(), false);
			imageView.setImageBitmap(bm);
			
			ByteArrayOutputStream sout = new ByteArrayOutputStream(); 
		    bm.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, (OutputStream) sout);
		    image = Base64.encodeToString(sout.toByteArray(), Base64.DEFAULT);
		}
		// else if (requestCode == 2 && resultCode == Activity.RESULT_OK && null
		// != data) {
		// Uri image = data.getData();
		// File f = new File(image.toString());
		// Bitmap bitmap = null;
		// try {
		// bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// ((ImageView)
		// getActivity().findViewById(R.id.imagePreview)).setImageBitmap(bitmap);
		// }

	}

	private String getPathFromData(Uri selectedImage) {
		
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getActivity().getContentResolver().query(
				selectedImage, filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}
	
	/**
	 * This function checks the results of the login request
	 */
	public static void checkResult()
	{
		String url;
		try {
			url = responses.getJSONObject(0).get("url").toString();
			Log.d("url", url);
			new URLTask(CreatePostFragment.iv).execute(url);
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
