package lu.mir.android.pomodorobox.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;

import junit.framework.TestCase;
import lu.mir.android.pomodorobox.Pomodoro;

public class TestPomodoro extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCreation() {
		Pomodoro current = new Pomodoro("testing testing, testing", 25, 5);
		assertEquals(current.getPomodoroStringRepresentation(), "testing testing, testing");
	}
	
	public void testConversionFromStringRepresentation() {
		Pomodoro a = new Pomodoro("converting pomodoros, hacking", 25, 5);
		assertEquals(a.getPomodoroName(), "converting pomodoros");
		String category = a.getPomodoroCategory();
		assertEquals(category, "hacking");
	}
	
	public void testConversionToStringRepresentation() {
		Pomodoro a = new Pomodoro ("converting pomodoros", "hacking");
		assertEquals(a.getPomodoroStringRepresentation(), "converting pomodoros, hacking");
	}
	
	public void testReadPomodorosFromString() throws IOException, ParseException {
		
		Pomodoro p = Pomodoro.fromFullString("2013/09/05 23:19, lala, lulu");
		
		assertEquals(p.getPomodoroName(), "lala");
		assertEquals(p.getPomodoroCategory(), "lulu");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(p.getDateFinished());
		assertEquals(calendar.get(Calendar.MINUTE), 19);		
	}
}
