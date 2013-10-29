package lu.mir.android.pomodorobox;

import java.io.Serializable;

import android.content.Context;

public interface PomodoroDatabase extends Serializable{
	
	public void logPomodoro(String message, Context appContext);
	public int countPomodoros(Context appContext);

}
