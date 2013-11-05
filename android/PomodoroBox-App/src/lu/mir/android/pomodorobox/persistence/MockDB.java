package lu.mir.android.pomodorobox.persistence;

import java.io.Serializable;
import java.util.ArrayList;


import android.content.Context;

public class MockDB implements PomodoroDatabase, Serializable {
	/**
	 * 
	 */
	ArrayList<String> pomodoros = new ArrayList<String>();
	private static final long serialVersionUID = 2061706005507503329L;
	
	public MockDB(){
		for (int i = 0; i <= 9; i++)
			pomodoros.add("programming on pomodorobox, programming");
	}
	
	@Override
	public void logPomodoro(String message, Context appContext) {
		pomodoros.add(message);
	}

	@Override
	public int countPomodoros(Context appContext) {
		return pomodoros.size();
	}

	@Override
	public ArrayList<String> getLoggedPomodoros(Context appContext) {
		return pomodoros;
	}

	@Override
	public String getLastLoggedPomodoro(Context appContext) {
		return pomodoros.get(pomodoros.size() - 1);
	}
	
}
