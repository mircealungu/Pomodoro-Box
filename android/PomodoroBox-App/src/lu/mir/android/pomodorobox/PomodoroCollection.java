package lu.mir.android.pomodorobox;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PomodoroCollection {

	List <Pomodoro> poms = new ArrayList <Pomodoro>();
	
	public void add(Pomodoro p) {
		poms.add(p);
	}
	
	public Set<String> getTags() {
		Set<String> tags = new HashSet<String>();
		for (Pomodoro each: poms) {
			tags.add(each.getTag());
		}
		return tags;
	}
	
	public void loadFromFile(String filename) throws FileNotFoundException, IOException {
		loadFromReader(new FileReader(filename));
	}
	
	public void loadFromReader(Reader r) throws IOException {
		poms = new ArrayList <Pomodoro>();
		
	    BufferedReader br = new BufferedReader(r);
	    try {
	        String line = br.readLine();
	        while (line != null) {
	        	Pomodoro pom = Pomodoro.fromFullString(line);
	        	poms.add(pom);
	        	line = br.readLine();
	        }
	    } finally {
	        br.close();
	    }
	}
		
	public int pomodoroCount() {
		return poms.size();
	}
	
	public List<Pomodoro> getPomodoros() {
		return poms;
	}
}
