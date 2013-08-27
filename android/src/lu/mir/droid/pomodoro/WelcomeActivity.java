package lu.mir.droid.pomodoro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.dropbox.sync.android.DbxAccountManager;


public class WelcomeActivity extends Activity {
	
	
	static final int REQUEST_LINK_TO_DBX = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
	public void linkToDropbox(View view) {
		DbxAccountManager mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), "3rglfd35h2aabho", "wcy337zm6m7paxs");
		mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
		
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_LINK_TO_DBX) {
	        if (resultCode == Activity.RESULT_OK) {
	    		Intent intent = new Intent(this, MainActivity.class);
	    		startActivity(intent);
	        } else {
	            // ... Link failed or was cancelled by the user.
	        }
	    } else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}
}
