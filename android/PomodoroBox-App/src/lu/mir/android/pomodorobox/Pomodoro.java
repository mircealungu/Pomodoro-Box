package lu.mir.android.pomodorobox;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lu.mir.android.pomodorobox.persistence.DropBoxFileDB;
import lu.mir.android.pomodorobox.util.Duration;

/**
 * By default a pomodoro has 25 minutes. One can create however one that has a shorter duration with a special factory method. Only used for testing purposes
 * @author mircea
 *
 */
public class Pomodoro implements Serializable {
	public static final long DEFAULT_DURATION = 25;
	public static final long DEFAULT_BREAK = 5;
	private static final long serialVersionUID = 4289892333150850140L;

	private long duration;
	private long breakDuration; //TODO: would be nice to move this out of here
	private String tag;
	private String description;
	private Date dateFinished;

	/**
	 * Requires also the date
	 * @param name
	 * @param tag
	 * @param dateFinished
	 */
	public Pomodoro(String name, String tag, Date dateFinished) {
		super();
		this.duration = Duration.POMODORO_DURATION;
		this.breakDuration = Duration.POMODORO_BREAK_DURATION;
		this.tag = tag;
		this.description = name;
		this.dateFinished = dateFinished;
	}
	
	/**
	 * assumes the date of now
	 */
	public Pomodoro(String name, String tag) {
		this(name, tag, new Date());
	}
	
	
	// Factory Methods
	// ---------------------------------------------------------------------------
	
	/**
	 * assumes a format like "pom, tag"
	 * @param nameAndTag
	 * @return
	 */
	public static Pomodoro fromNameAndTagString(String nameAndTag) {
		// derive the name and category based on the string representation
		int commaPos = nameAndTag.indexOf(",");
		Date dateFinished = new Date();
		
		if (commaPos == -1) {
			// there is no tag
			return new Pomodoro(nameAndTag, "", dateFinished);
		}
		
		String name = nameAndTag.substring(0, commaPos);
		
		String tag = nameAndTag.substring(
				nameAndTag.indexOf(",") + 1).trim();
		
		return new Pomodoro (name, tag, dateFinished);
	}

	/**
	 * assumes that the string is well formatted
	 * @param s
	 * @return
	 */
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


	public void makeBlitz() {
		duration = Duration.BLITZ_DURATION;
		breakDuration = Duration.BLITZ_BREAK_DURATION;
	}
	
	public String stringRepresentation() {
		SimpleDateFormat sdf = new SimpleDateFormat(DropBoxFileDB.LOGFILE_DATE_FORMAT, Locale.getDefault());
		return sdf.format(dateFinished) + ", " + getNameAndTagRepresentation(); 
	}
	
	// Accessors
	// ----------------------------------------

	public long getDuration() {
		return duration;
	}

	public long getPomodoroBreakDuration() {
		return breakDuration;
	}

	public String getNameAndTagRepresentation() {
		return description + ", " + tag;
	}

	public String getTag() {
		return tag;
	}

	public String getName() {
		return description;
	}

	public void setName(String pomodoroName) {
		this.description = pomodoroName;
	}

	public Date getDateFinished() {
		return dateFinished;
	}

	public void setDateFinished(Date dateFinished) {
		this.dateFinished = dateFinished;
	}

}
