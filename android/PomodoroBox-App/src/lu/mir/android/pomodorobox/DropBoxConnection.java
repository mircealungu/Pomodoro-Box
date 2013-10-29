package lu.mir.android.pomodorobox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.view.View;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.NotFound;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;

public class DropBoxConnection {

	private static final String LOGFILE = "box.txt";
	private static final String LOGFILE_DATE_FORMAT = "yyyy/MM/dd HH:mm";

	public final static String DBX_APP_KEY = "91kr6dmol3ta60l";
	public final static String DBX_APP_SECRET = "4wkjx70xpfl0yqu";

	/*
	 * needed for any operation on files
	 */
	public static DbxAccountManager getAccountManager() {
		DbxAccountManager dbxAcctMgr = DbxAccountManager.getInstance(
				PomodoroBoxApplication.context(), DBX_APP_KEY, DBX_APP_SECRET);
		return dbxAcctMgr;
	}

	public static void logPomodoroToDropbox(String message) throws InvalidPathException, IOException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(LOGFILE_DATE_FORMAT);
		DbxPath logFileName = new DbxPath(LOGFILE);
		DbxFileSystem dbxFs;

		DbxFile logFile = null;
		dbxFs = DbxFileSystem.forAccount(getAccountManager().getLinkedAccount());

		try {
			logFile = dbxFs.open(logFileName);
			logFile.update();
			logFile.appendString(sdf.format(date) + ", " + message + "\n");
		} catch (NotFound e) {
			logFile = dbxFs.create(logFileName);
			logFile.appendString(sdf.format(date) + ", " + message + "\n");
		} finally {
			logFile.close();	
		}
		
	}
	
	public static List<String> last3Pomodoros() {
		ArrayList <String> lines = new ArrayList<String>();
		
		DbxFileSystem dbxFs;
		DbxFile logFile = null;
		DbxPath logFileName = new DbxPath(LOGFILE);
		String lastLine;
		
		try {
			dbxFs = DbxFileSystem.forAccount(getAccountManager().getLinkedAccount());
			if (dbxFs.exists(logFileName)){
				logFile = dbxFs.open(logFileName);
			} else {
				logFile = dbxFs.create(logFileName);
			}
			logFile.update();
			BufferedReader reader = new BufferedReader(new InputStreamReader(logFile.getReadStream()));
			while ((lastLine=reader.readLine()) != null) lines.add(lastLine);
		} catch (Unauthorized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			logFile.close();
		}
				
		return lines.subList(lines.size()-3, lines.size());
	}

	public static int countPomodoros() {
		int lines = 0;
		DbxFileSystem dbxFs;
		DbxFile logFile = null;
		DbxPath logFileName = new DbxPath(LOGFILE);
		
		try {
			dbxFs = DbxFileSystem.forAccount(getAccountManager().getLinkedAccount());
			if (dbxFs.exists(logFileName)) {
				logFile = dbxFs.open(logFileName);
			} else {
				logFile = dbxFs.create(logFileName);
			}
			
			logFile.update();
			BufferedReader reader = new BufferedReader(new InputStreamReader(logFile.getReadStream()));
			while (reader.readLine() != null) lines ++;
		} catch (Unauthorized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			logFile.close();
		}
				
		return lines;
	}

	public void unlinkFromDropbox(View view) {
		DbxAccountManager mDbxAcctMgr = DbxAccountManager.getInstance(
				PomodoroBoxApplication.context(), DBX_APP_KEY, DBX_APP_SECRET);
		mDbxAcctMgr.unlink();
	}

}
