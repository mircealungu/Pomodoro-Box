package lu.mir.android.pomodorobox.persistence;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

public interface PomodoroDatabase extends Serializable{
	
	public void logPomodoro(String message, Context appContext);
	public int countPomodoros(Context appContext);
	public ArrayList<String> getLoggedPomodoros(Context appContext);
	public String getLastLoggedPomodoro(Context appContext);
}
