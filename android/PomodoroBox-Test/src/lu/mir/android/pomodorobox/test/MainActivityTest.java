package lu.mir.android.pomodorobox.test;

import lu.mir.android.pomodorobox.R;
import lu.mir.android.pomodorobox.activities.MainActivity;
import lu.mir.android.pomodorobox.persistence.MockDB;
import lu.mir.android.pomodorobox.persistence.PomodoroDatabase;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	private Context ctx;
	private EditText mEditText;
	private PomodoroDatabase pomoBase;
	
	@SuppressWarnings("deprecation")
	public MainActivityTest() {
		super("lu.mir.android.pomodorobox",MainActivity.class);
	}


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		pomoBase = new MockDB();
		
		Intent intent = new Intent();
		Bundle b = new Bundle();
		b.putSerializable(MainActivity.DB, pomoBase); //Your id
		intent.putExtras(b); //Put your id to your next Intent
		
		setActivityIntent(intent);
		
		mActivity = getActivity();
		ctx = mActivity.getApplicationContext();
		mEditText = (EditText) mActivity.findViewById(R.id.edit_message);
	}

	
	public void testPreConditions() {
		assertTrue(mActivity != null);
		assertTrue(mEditText != null);
	} 
	
	public void testWithMockDB() {
		assertEquals(pomoBase.countPomodoros(ctx), 10);
		pomoBase.logPomodoro("testing", ctx);
		assertEquals(pomoBase.countPomodoros(ctx), 11);
	}

	
	/*
	 * Beware! This one tests  with the actual database!
	 */
	public void testGetPomodoroListFromMockDB() {
		String newPomodoro = "testing pomodorobox, testing";
		pomoBase.logPomodoro(newPomodoro,  ctx);
		String readPomodoro = pomoBase.getLastLoggedPomodoro(ctx);
		assertTrue(readPomodoro.equals(newPomodoro));
	}
	

}
