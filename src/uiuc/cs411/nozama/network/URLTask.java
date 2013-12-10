package uiuc.cs411.nozama.network;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import uiuc.cs411.nozama.ui.CreatePostFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

public class URLTask extends AsyncTask<String, Void, Bitmap> {
	  ImageView iv;

	  public URLTask(ImageView bmImage) {
	      this.iv = bmImage;
	  }

	  protected Bitmap doInBackground(String... urls) {
	      String mURL = urls[0];
	      Bitmap bm = null;
	      try {
	        InputStream in = new java.net.URL(mURL).openStream();
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 8;
	        bm = BitmapFactory.decodeStream(in, null, options);
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return bm;
	  }

	  protected void onPostExecute(Bitmap result) {
	      iv.setImageBitmap(result);
	      ByteArrayOutputStream sout = new ByteArrayOutputStream(); 
		  result.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, (OutputStream) sout);
		  CreatePostFragment.image = Base64.encodeToString(sout.toByteArray(), Base64.DEFAULT);
	  }
	}