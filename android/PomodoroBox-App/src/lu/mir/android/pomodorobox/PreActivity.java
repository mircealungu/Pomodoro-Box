package lu.mir.android.pomodorobox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (DropBoxConnection.getAccountManager().hasLinkedAccount()) {
			 startActivity(new Intent(this, MainActivity.class));
		} else {
			 startActivity(new Intent(this, WelcomeActivity.class));
		}
		finish();
	}
}
