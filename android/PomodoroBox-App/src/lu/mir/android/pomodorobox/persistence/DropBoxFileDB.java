package lu.mir.android.pomodorobox.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.annotation.SuppressLint;
import android.content.Context;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.NotFound;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

@SuppressLint("SimpleDateFormat")
public class DropBoxFileDB implements PomodoroDatabase, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2792003446819213380L;
	
	private static final String LOGFILE = "box.txt";
	private static final String LOGFILE_DATE_FORMAT = "yyyy/MM/dd HH:mm";

	public final static String DBX_APP_KEY = "91kr6dmol3ta60l";
	public final static String DBX_APP_SECRET = "4wkjx70xpfl0yqu";

	
	public DropBoxFileDB() {
		super();
	}
	
	@Override
	public void logPomodoro(String message, Context appContext) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(LOGFILE_DATE_FORMAT);
		DbxPath logFileName = new DbxPath(LOGFILE);
		
		DbxFileSystem dbxFs = null;
		DbxFile logFile = null;

		try {
			dbxFs = DbxFileSystem.forAccount(getAccountManager(appContext).getLinkedAccount());
			logFile = dbxFs.open(logFileName);
			logFile.update();
			logFile.appendString(sdf.format(date) + ", " + message + "\n");
		} catch (NotFound e) {
			try {
				logFile = dbxFs.create(logFileName);
				logFile.appendString(sdf.format(date) + ", " + message + "\n");
			} catch (DbxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}  catch (Unauthorized e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			logFile.close();	
		}

	}

	public DbxAccountManager getAccountManager(Context appContext) {
		DbxAccountManager dbxAcctMgr = DbxAccountManager.getInstance(
				appContext, DBX_APP_KEY, DBX_APP_SECRET);
		return dbxAcctMgr;
	}
	
	@Override
	public int countPomodoros(Context appContext) {
		int lines = 0;
		DbxFileSystem dbxFs;
		DbxFile logFile = null;
		DbxPath logFileName = new DbxPath(LOGFILE);
		
		try {
			dbxFs = DbxFileSystem.forAccount(getAccountManager(appContext).getLinkedAccount());
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
			if (logFile != null)
				logFile.close();
		}
				
		return lines;
	}

}
