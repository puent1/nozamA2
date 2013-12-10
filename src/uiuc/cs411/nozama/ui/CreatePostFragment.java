package uiuc.cs411.nozama.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
import android.widget.Toast;
import uiuc.cs411.nozama.R;
import uiuc.cs411.nozama.content.Content;
import uiuc.cs411.nozama.network.DatabaseTask;

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
	
	/**
	 * The dummy content this fragment is presenting.
	 */
	private Content.Item mItem;

	private String selectedImagePath;

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

			String[] taskParams = { "" + DatabaseTask.CREATE_POST, title,
					description, pathToImage };
			new DatabaseTask().execute(taskParams);
		}

		return true;
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
				i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
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
				Bundle b = data.getExtras();
				Uri cameraImage = (Uri) b.get(MediaStore.EXTRA_OUTPUT);
				String cameraPath = getPathFromData(cameraImage);
				
				bm = BitmapFactory.decodeFile(cameraPath);
				break;
			case GALLERY:
				Uri selectedImage = data.getData();
				String picturePath = getPathFromData(selectedImage);
				
				bm = BitmapFactory.decodeFile(picturePath);
				break;
			}
			imageView.setImageBitmap(bm);
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

}
