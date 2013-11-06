package lu.mir.android.pomodorobox;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lu.mir.android.pomodorobox.persistence.DropBoxFileDB;

public class Pomodoro implements Serializable {
	public static final long DEFAULT_DURATION = 25;
	public static final long DEFAULT_BREAK = 5;
	private static final long serialVersionUID = 4289892333150850140L;

	private long pomodoroDuration;
	private long pomodoroBreakDuration;
	private String pomodoroStringRepresentation;
	private String pomodoroCategory;
	private String pomodoroName;
	private Date dateFinished;

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
	
	public static Pomodoro fromFullString(String s) {
		
		int nameIndex = s.indexOf(",");
		
		Date d = new Date();
		try {
			d = new SimpleDateFormat(DropBoxFileDB.LOGFILE_DATE_FORMAT, Locale.ENGLISH).parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int tagIndex = s.indexOf(",",nameIndex+2);
		String name = s.substring(nameIndex+2, tagIndex);
		
		String tag = s.substring(tagIndex+2);
		
		return new Pomodoro(name, tag, d);
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

	public Pomodoro(String name, String category, Date dateFinished) {
		this(name, category, DEFAULT_DURATION, DEFAULT_BREAK);
		this.setDateFinished(dateFinished);
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

	public Date getDateFinished() {
		return dateFinished;
	}

	public void setDateFinished(Date dateFinished) {
		this.dateFinished = dateFinished;
	}

}
