package lu.mir.android.pomodorobox;

import java.io.Serializable;

import android.content.Context;

public class MockDB implements PomodoroDatabase, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2061706005507503329L;
	int pomodoroCount = 10;
	
	@Override
	public void logPomodoro(String message, Context appContext) {
		pomodoroCount ++;
	}

	@Override
	public int countPomodoros(Context appContext) {
		return pomodoroCount;
	}

}
