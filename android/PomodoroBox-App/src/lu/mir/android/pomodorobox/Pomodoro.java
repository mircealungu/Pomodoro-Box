package lu.mir.android.pomodorobox;

import java.io.Serializable;

public class Pomodoro implements Serializable {
	
	public Pomodoro(String pomodoroName, long pomodoroDuration, long pomodoroBreakDuration) {
		super();
		this.pomodoroDuration = pomodoroDuration;
		this.pomodoroBreakDuration = pomodoroBreakDuration;
		this.pomodoroName = pomodoroName;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4289892333150850140L;
	
	private long pomodoroDuration;
	private long pomodoroBreakDuration;
	private String pomodoroName;
	
	//
	
	public long getPomodoroDuration() {
		return pomodoroDuration;
	}
	public void setPomodoroDuration(long pomodoroDuration) {
		this.pomodoroDuration = pomodoroDuration;
	}
	public long getPomodoroBreakDuration() {
		return pomodoroBreakDuration;
	}
	public void setPomodoroBreakDuration(long pomodoroBreakDuration) {
		this.pomodoroBreakDuration = pomodoroBreakDuration;
	}
	public String getPomodoroName() {
		return pomodoroName;
	}
	public void setPomodoroName(String pomodoroName) {
		this.pomodoroName = pomodoroName;
	}
	
}
