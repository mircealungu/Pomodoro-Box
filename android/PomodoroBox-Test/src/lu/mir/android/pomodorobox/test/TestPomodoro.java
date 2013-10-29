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
	
	public void testTrue() {
		Pomodoro current = new Pomodoro("testing testing", 25, 5);
		assertEquals(current.getPomodoroName(), "testing testing");
	}

}
