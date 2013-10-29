package lu.mir.android.pomodorobox.persistence;

import lu.mir.android.pomodorobox.PomodoroBoxApplication;
import android.view.View;

import com.dropbox.sync.android.DbxAccountManager;

public class DropBoxConnection {

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
	
	public void unlinkFromDropbox(View view) {
		DbxAccountManager mDbxAcctMgr = DbxAccountManager.getInstance(
				PomodoroBoxApplication.context(), DBX_APP_KEY, DBX_APP_SECRET);
		mDbxAcctMgr.unlink();
	}

}
