package lu.mir.android.pomodorobox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.View;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException.NotFound;
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

	public static int countPomodoros() {
		int lines = 0;
		DbxFileSystem dbxFs;
		DbxFile logFile = null;
		DbxPath logFileName = new DbxPath(LOGFILE);
		
		try {
			dbxFs = DbxFileSystem.forAccount(getAccountManager().getLinkedAccount());
			logFile = dbxFs.open(logFileName);
			logFile.update();
			BufferedReader reader = new BufferedReader(new InputStreamReader(logFile.getReadStream()));
			while (reader.readLine() != null) lines ++;
		} catch (Exception e1) {
			return 0;
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
