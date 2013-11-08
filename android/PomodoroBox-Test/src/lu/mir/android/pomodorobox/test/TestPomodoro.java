package lu.mir.android.pomodorobox.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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
		Pomodoro current = Pomodoro.fromNameAndTagString("testing testing, testing");
		assertEquals(current.getNameAndTagRepresentation(), "testing testing, testing");
	}
	
	
	public void testConversionFromStringRepresentation() {
		Pomodoro a = Pomodoro.fromNameAndTagString("converting pomodoros, hacking");
		assertEquals(a.getName(), "converting pomodoros");
		String category = a.getTag();
		assertEquals(category, "hacking");
	}
	
	
	public void testConversionToStringRepresentation() {
		Pomodoro a = new Pomodoro ("converting pomodoros", "hacking");
		assertEquals(a.getNameAndTagRepresentation(), "converting pomodoros, hacking");
	}
	

	public void testReadPomodorosFromString() throws IOException, ParseException {
		
		Pomodoro p = Pomodoro.fromFullString("2013/09/05 23:19, lala, lulu");
		
		assertEquals(p.getName(), "lala");
		assertEquals(p.getTag(), "lulu");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(p.getDateFinished());
		assertEquals(calendar.get(Calendar.MINUTE), 19);		
	}
	
	
	public void testStringRepresentation() {
		Calendar calendar = Calendar.getInstance();
		// Months are zero-indexed!!
		calendar.set(2113, Calendar.JANUARY, 10, 10, 10, 10);
		Date d = calendar.getTime();

		Pomodoro p = new Pomodoro("a", "b", d);
		assertEquals(p.stringRepresentation(), "2113/01/10 10:10, a, b");
	}
	
	public void testPomodoroCollection() {
		PomodoroCollection col = new PomodoroCollection();
		col.add(new Pomodoro("writing the first test","hacking"));
		col.add(new Pomodoro("writing the second test", "hacking"));
		assert(col.getCategories().size == 1);
	}
}
