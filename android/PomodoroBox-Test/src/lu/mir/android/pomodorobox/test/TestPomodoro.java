package lu.mir.android.pomodorobox.test;

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
}
