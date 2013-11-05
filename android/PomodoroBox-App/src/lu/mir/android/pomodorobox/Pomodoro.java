package lu.mir.android.pomodorobox;

import java.io.Serializable;

public class Pomodoro implements Serializable {
	public static final long DEFAULT_DURATION = 25;
	public static final long DEFAULT_BREAK = 5;
	private static final long serialVersionUID = 4289892333150850140L;

	private long pomodoroDuration;
	private long pomodoroBreakDuration;
	private String pomodoroStringRepresentation;
	private String pomodoroCategory;
	private String pomodoroName;

	//
	
	/*
	 * The two constructors create a pomodoro either based on it's duration
	 */
	public Pomodoro(String pomodoroStringRepresentation, long pomodoroDuration,
			long pomodoroBreakDuration) {
		super();
		this.pomodoroDuration = pomodoroDuration;
		this.pomodoroBreakDuration = pomodoroBreakDuration;
		this.pomodoroStringRepresentation = pomodoroStringRepresentation;

		// derive the name and category based on the string representation
		pomodoroName = pomodoroStringRepresentation.substring(0,
				pomodoroStringRepresentation.indexOf(","));
		pomodoroCategory = pomodoroStringRepresentation.substring(
				pomodoroStringRepresentation.indexOf(",") + 1).trim();
	}

	public Pomodoro(String stringRepresentation) {
		this(stringRepresentation, DEFAULT_DURATION, DEFAULT_BREAK);
	}

	public Pomodoro(String pomodoroName, String pomodoroCategory,
			long pomodoroDuration, long pomodoroBreakDuration) {
		super();
		this.pomodoroDuration = pomodoroDuration;
		this.pomodoroBreakDuration = pomodoroBreakDuration;
		this.pomodoroName = pomodoroName;
		this.pomodoroCategory = pomodoroCategory;
		this.pomodoroStringRepresentation = pomodoroName + ", "
				+ pomodoroCategory;
	}

	public Pomodoro(String name, String category) {
		this(name, category, DEFAULT_DURATION, DEFAULT_BREAK);
	}


	public long getPomodoroDuration() {
		return pomodoroDuration;
	}

	public long getPomodoroBreakDuration() {
		return pomodoroBreakDuration;
	}

	public String getPomodoroStringRepresentation() {
		return pomodoroStringRepresentation;
	}

	public String getPomodoroCategory() {
		return pomodoroCategory;
	}

	public String getPomodoroName() {
		return pomodoroName;
	}

	public void setPomodoroName(String pomodoroName) {
		this.pomodoroName = pomodoroName;
	}

}
