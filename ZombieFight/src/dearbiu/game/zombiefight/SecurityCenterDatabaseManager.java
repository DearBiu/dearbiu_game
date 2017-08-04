package dearbiu.game.zombiefight.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import dearbiu.game.zombiefight.utils.DateUtility;

public class SecurityCenterDatabaseManager {

	private static SecurityCenterDatabaseManager mInstance = null;
	ContentResolver mContentResolver = null;

	public static synchronized SecurityCenterDatabaseManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SecurityCenterDatabaseManager(context);
		}
		return mInstance;
	}

	private SecurityCenterDatabaseManager(Context context) {
		if (mContentResolver == null) {
			mContentResolver = context.getContentResolver();
		}
	}

	public void closeCursor(Cursor cursor) {
		try {
			if (cursor != null) {
				if (!cursor.isClosed()) {
					cursor.close();
					cursor = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Uri insertSetting(Setting setting) {
		Uri uri = null;

		if (setting == null) {
			return uri;
		}

		if (mContentResolver == null) {
			return uri;
		}

		uri = mContentResolver.insert(DatabaseContract.Setting.CONTENT_URI,
				setting.getContentValues());

		return uri;
	}

	public Cursor querySetting(String selection, String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;

		if (mContentResolver == null) {
			return cursor;
		}

		cursor = mContentResolver.query(DatabaseContract.Setting.CONTENT_URI,
				DatabaseContract.Setting.PROJECTION_ALL, selection, selectionArgs, sortOrder);

		return cursor;
	}

	public Cursor querySetting(Setting setting) {
		Cursor cursor = null;

		if (setting == null) {
			return cursor;
		}

		String selection = DatabaseContract.Setting.COLUMN_KEY + " = " + "\'" + setting.getKey()
				+ "\'";

		cursor = querySetting(selection, null, null);

		return cursor;
	}

	public boolean isSettingExist(Setting setting) {
		boolean result = false;
		Cursor cursor = null;

		if (setting == null) {
			return result;
		}

		try {
			cursor = querySetting(setting);

			if ((cursor != null) && (cursor.getCount() > 0)) {
				cursor.moveToNext();
				setting.setCreated(cursor);
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}

		return result;
	}

	public int updateSetting(Setting setting) {
		int result = 0;

		if (setting == null) {
			return result;
		}

		if (mContentResolver == null) {
			return result;
		}

		String where = DatabaseContract.Setting.COLUMN_KEY + " = " + "\'" + setting.getKey() + "\'";

		result = mContentResolver.update(DatabaseContract.Setting.CONTENT_URI,
				setting.getContentValues(), where, null);

		return result;
	}

	public void saveSetting(Setting setting) {
		String now = DateUtility.getCurrentDateTimeString();

		if (setting == null) {
			return;
		}

		try {
			if (!isSettingExist(setting)) {
				setting.setCreated(now);
				insertSetting(setting);
			} else {
				setting.setModified(now);
				updateSetting(setting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveSetting(String key, boolean value) {
		Setting setting = new Setting();

		setting.setKey(key);
		if (value) {
			setting.setValue("1");
		} else {
			setting.setValue("0");
		}

		saveSetting(setting);
	}

	public void saveSetting(String key, String value) {
		Setting setting = new Setting();

		setting.setKey(key);
		setting.setValue(value);

		saveSetting(setting);
	}

	public String getSettingString(String key) {
		String value = "";
		Cursor cursor = null;
		Setting setting = new Setting();

		setting.setKey(key);

		try {
			cursor = querySetting(setting);

			if ((cursor != null) && (cursor.getCount() > 0)) {
				cursor.moveToNext();
				setting.set(cursor);
				value = setting.getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}

		return value;
	}

	public int getSettingInt(String key) {
		int result = 0;
		String value = getSettingString(key);

		if (!TextUtils.isEmpty(value)) {
			try {
				result = Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean getSettingBoolean(String key, boolean defaultValue) {
		boolean result = defaultValue;
		String value = getSettingString(key);

		if (!TextUtils.isEmpty(value)) {
			if ("1".equals(value)) {
				result = true;
			} else {
				result = false;
			}
		}

		return result;
	}

	public int getSettingInt(String key, int defaultValue) {
		int result = defaultValue;
		String value = getSettingString(key);

		if (!TextUtils.isEmpty(value)) {
			try {
				result = Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public long getSettingLong(String key, long defaultValue) {
		long result = defaultValue;
		String value = getSettingString(key);

		if (!TextUtils.isEmpty(value)) {
			try {
				result = Long.parseLong(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public double getSettingDouble(String key, double defaultValue) {
		double result = defaultValue;
		String value = getSettingString(key);

		if (!TextUtils.isEmpty(value)) {
			try {
				result = Double.parseDouble(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Cursor queryVersion(String selection, String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;

		if (mContentResolver == null) {
			return cursor;
		}

		cursor = mContentResolver.query(DatabaseContract.Version.CONTENT_URI,
				DatabaseContract.Version.PROJECTION_ALL, selection, selectionArgs, sortOrder);

		return cursor;
	}

	public Cursor queryVersion(Version version) {
		Cursor cursor = null;

		if (version == null) {
			return cursor;
		}

		String selection = null;

		cursor = queryVersion(selection, null, null);

		return cursor;
	}

	public boolean isVersionExist(Version version) {
		boolean result = false;
		Cursor cursor = null;

		if (version == null) {
			return result;
		}

		try {
			cursor = queryVersion(version);

			if ((cursor != null) && (cursor.getCount() > 0)) {
				cursor.moveToNext();
				version.setCreated(cursor);
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(cursor);
		}

		return result;
	}
}
