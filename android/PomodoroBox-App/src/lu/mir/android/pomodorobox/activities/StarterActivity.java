package lu.mir.android.pomodorobox.activities;

import lu.mir.android.pomodorobox.persistence.DropBoxConnection;
import lu.mir.android.pomodorobox.persistence.DropBoxFileDB;
import lu.mir.android.pomodorobox.persistence.PomodoroDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StarterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (DropBoxConnection.getAccountManager().hasLinkedAccount()) {
			PomodoroDatabase dbdb = new DropBoxFileDB();
			Intent intent = new Intent(this, MainActivity.class);
			Bundle b = new Bundle();
			b.putSerializable(MainActivity.DB, dbdb); // Pass the db object
			intent.putExtras(b);
			startActivity(intent);
		} else {
			 startActivity(new Intent(this, WelcomeActivity.class));
		}
		finish();
	}
}