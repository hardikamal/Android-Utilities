package treeset.extensions.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.*;

import treeset.extensions.logging.DLog;

/**
 * Abstract SQL helper handles loading database from assets on create/upgrade
 * Does not support in-memory  databases!!
 *
 * Created by daemontus on 15/1/14.
 */
public abstract class AbstractAssetSQLiteHelper extends SQLiteOpenHelper {

	public static final String DB_PREFERENCES = "DatabaseSharedPreferences";
	public static final String DB_LAST_ASSET_VERSION = "lastAssetVersion_";

	private boolean assetConsistency = true;
	private Exception assetInconsistencyCause = null;

	protected abstract int getAssetDatabaseVersion();

	protected AbstractAssetSQLiteHelper(Context ctx, String dbName, SQLiteDatabase.CursorFactory factory, int dbVersion) {
		super(ctx, dbName, factory, dbVersion);
		if (ctx == null) throw new IllegalArgumentException("Context parameter can't be null");
		if (dbName == null) throw new IllegalArgumentException("Name parameter can't be null");
		if (dbName.isEmpty()) throw new IllegalArgumentException("Name can't be empty");
		if (dbVersion < 0) throw new IllegalArgumentException("Version can't be negative number");

		File dataDirectory = ctx.getFilesDir();
		if (dataDirectory != null && dataDirectory.getParentFile() != null) {

			String dbFolderPath = dataDirectory.getParentFile().getPath()+"/databases/";
			SharedPreferences dbPreferences = ctx.getSharedPreferences(DB_PREFERENCES, Context.MODE_PRIVATE);
			int lastAssetVersion = dbPreferences.getInt(DB_LAST_ASSET_VERSION+dbName, 0);

			if (getAssetDatabaseVersion() > lastAssetVersion && getAssetDatabaseVersion() > dbVersion) {
				try {
					DLog.info("Database update from asset required: "+dbName);
					copyDatabase(ctx, dbFolderPath, dbName);
					dbPreferences.edit().putInt(DB_LAST_ASSET_VERSION+dbName, getAssetDatabaseVersion()).commit();
					DLog.info("Database successfully refreshed from assets: "+dbName);
				} catch (DatabaseInitException e) {
					DLog.error(e.toString());
					assetConsistency = false;
					assetInconsistencyCause = e;
				}
			}

		} else {
			DLog.error("Database Folder is unreachable");
			assetConsistency = false;
			assetInconsistencyCause = new DatabaseInitException("Database Folder is unreachable: "+ (dataDirectory != null ? dataDirectory.getPath() : null));
		}
	}

	/**
	 * @return If db exists and is same or newer then in assets return true
	 */
	public boolean isAssetConsistent() { return assetConsistency; }

	/**
	 * @return If db is older then in assets or failed to initialize, return cause, otherwise null
	 */
	public Exception getAssetInconsistencyCause() { return assetInconsistencyCause; }

	/**
	 * Check database existence
	 * @return true if db file exists
	 */
	private boolean databaseExists(String dbFolderPath, String dbName) {
		File DBfile = new File(dbFolderPath+dbName);
		return DBfile.exists();
	}

	/**
	 * Copy database from Assets to /data/data/package/database/
	 */
	private void copyDatabase(Context ctx, String dbFolderPath, String dbName) throws DatabaseInitException {
		InputStream mInput = null;
		OutputStream mOutput = null;
		try {
			File dbDir = new File(dbFolderPath);
			if (!dbDir.exists() && dbDir.mkdir()) DLog.info("Database folder created in: "+dbFolderPath);
			mInput = ctx.getAssets().open(dbName);
			String outFilename = dbFolderPath + dbName;
			mOutput = new FileOutputStream(outFilename);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = mInput.read(buffer))>0) {
				mOutput.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
			throw new DatabaseInitException("Can't write to database file", e);
		} catch (IOException e) {
			throw new DatabaseInitException("Can't copy database: "+e.toString(), e);
		} finally {
			if (mInput != null) {
				try {
					mInput.close();
				} catch (IOException e) {
					DLog.exception(e);
				}
			}
			if (mOutput != null) {
				try {
					mOutput.flush();
					mOutput.close();
				} catch (IOException e) {
					DLog.exception(e);
				}
			}
		}
	}

}
