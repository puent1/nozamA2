package uiuc.cs411.nozama;

import uiuc.cs411.nozama.listing.CreateReplyFragment;
import uiuc.cs411.nozama.ui.CreatePostFragment;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class Reply_Editor_Activity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply__editor_);
		
		if(savedInstanceState == null)
			getSupportFragmentManager().beginTransaction().add(R.id.reply_detail_container, new CreatePostFragment()).commit();
	}



}
