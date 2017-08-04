package dearbiu.game.zombiefight.database;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class SecurityCenterContentProvider extends ContentProvider {
	
	private static final int SETTING = 100;
	private static final int SETTING_ID = 101;

	private static final int VERSION = 200;
	private static final int VERSION_ID = 201;

	private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		mUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.Setting.TABLE_NAME,
				SETTING);
		mUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.Setting.TABLE_NAME + "/#",
				SETTING_ID);

		mUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.Version.TABLE_NAME,
				VERSION);
		mUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.Version.TABLE_NAME + "/#",
				VERSION_ID);
	}

	ContentResolver mContentResolver = null;
	DatabaseManager mDatabaseManager = null;

	@Override
	public boolean onCreate() {
		if (mContentResolver == null) {
			mContentResolver = getContext().getContentResolver();
		}

		if (mDatabaseManager == null) {
			mDatabaseManager = new DatabaseManager(getContext());
		}

		if (mDatabaseManager != null) {
			mDatabaseManager.openDatabase();
		}

		return true;
	}

	@Override
	public String getType(Uri uri) {
		String type = null;

		switch (mUriMatcher.match(uri)) {
		case SETTING:
			type = DatabaseContract.Setting.CONTENT_TYPE;
			break;
		case SETTING_ID:
			type = DatabaseContract.Setting.CONTENT_ITEM_TYPE;
			break;
		case VERSION:
			type = DatabaseContract.Version.CONTENT_TYPE;
			break;
		case VERSION_ID:
			type = DatabaseContract.Version.CONTENT_ITEM_TYPE;
			break;
		default:
			break;
		}

		return type;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		Cursor cursor = null;

		if (mDatabaseManager == null) {
			return null;
		}

		if (mDatabaseManager.mDatabase == null) {
			return null;
		}

		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

		switch (mUriMatcher.match(uri)) {
		case SETTING:
			builder.setTables(DatabaseContract.Setting.TABLE_NAME);
			break;
		case SETTING_ID:
			builder.setTables(DatabaseContract.Setting.TABLE_NAME);
			builder.appendWhere(BaseColumns._ID + " = " + uri.getLastPathSegment());
			break;
		case VERSION:
			builder.setTables(DatabaseContract.Version.TABLE_NAME);
			break;
		case VERSION_ID:
			builder.setTables(DatabaseContract.Version.TABLE_NAME);
			builder.appendWhere(BaseColumns._ID + " = " + uri.getLastPathSegment());
			break;
		default:
			break;
		}

		cursor = builder.query(mDatabaseManager.mDatabase, projection, selection, selectionArgs,
				null, null, sortOrder);

		if (cursor != null) {
			cursor.setNotificationUri(mContentResolver, uri);
		}

		return cursor;
	}

	public Uri insert(Uri uri, ContentValues contentValues, boolean notifyChange) {
		long id = 0;
		Uri itemUri = null;

		if (mDatabaseManager == null) {
			return itemUri;
		}

		if (mDatabaseManager.mDatabase == null) {
			return itemUri;
		}

		switch (mUriMatcher.match(uri)) {
		case SETTING:
			id = mDatabaseManager.mDatabase.insert(DatabaseContract.Setting.TABLE_NAME, null,
					contentValues);
			break;
		case VERSION:
			id = mDatabaseManager.mDatabase.insert(DatabaseContract.Version.TABLE_NAME, null,
					contentValues);
			break;
		default:
			break;
		}

		if (id > 0) {
			itemUri = ContentUris.withAppendedId(uri, id);

			if (notifyChange) {
				mContentResolver.notifyChange(itemUri, null);
			}
		}

		return itemUri;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		return insert(uri, contentValues, true);
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		int result = 0;

		if (mDatabaseManager == null) {
			return result;
		}

		if (mDatabaseManager.mDatabase == null) {
			return result;
		}

		mDatabaseManager.mDatabase.beginTransaction();

		try {
			for (ContentValues contentValues : values) {
				if (insert(uri, contentValues, false) != null) {
					result++;
				}
			}

			mDatabaseManager.mDatabase.setTransactionSuccessful();

			if (result > 0) {
				mContentResolver.notifyChange(uri, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mDatabaseManager.mDatabase.endTransaction();
		}

		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int result = 0;
		String whereClause;

		if (mDatabaseManager == null) {
			return result;
		}

		if (mDatabaseManager.mDatabase == null) {
			return result;
		}

		switch (mUriMatcher.match(uri)) {
		case SETTING:
			result = mDatabaseManager.mDatabase.update(DatabaseContract.Setting.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case SETTING_ID:
			whereClause = BaseColumns._ID + " = " + uri.getLastPathSegment();
			if (!TextUtils.isEmpty(selection)) {
				whereClause += " AND " + whereClause;
			}
			result = mDatabaseManager.mDatabase.update(DatabaseContract.Setting.TABLE_NAME, values,
					whereClause, selectionArgs);
			break;
		case VERSION:
			result = mDatabaseManager.mDatabase.update(DatabaseContract.Version.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case VERSION_ID:
			whereClause = BaseColumns._ID + " = " + uri.getLastPathSegment();
			if (!TextUtils.isEmpty(selection)) {
				whereClause += " AND " + whereClause;
			}
			result = mDatabaseManager.mDatabase.update(DatabaseContract.Version.TABLE_NAME, values,
					whereClause, selectionArgs);
			break;
		default:
			break;
		}

		if (result > 0) {
			mContentResolver.notifyChange(uri, null);
		}

		return result;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = 0;
		String whereClause;

		if (mDatabaseManager == null) {
			return result;
		}

		if (mDatabaseManager.mDatabase == null) {
			return result;
		}

		switch (mUriMatcher.match(uri)) {
		case SETTING:
			result = mDatabaseManager.mDatabase.delete(DatabaseContract.Setting.TABLE_NAME,
					selection, selectionArgs);
			break;

		case SETTING_ID:
			whereClause = BaseColumns._ID + " = " + uri.getLastPathSegment();
			if (!TextUtils.isEmpty(selection)) {
				whereClause += " AND " + whereClause;
			}
			result = mDatabaseManager.mDatabase.delete(DatabaseContract.Setting.TABLE_NAME,
					whereClause, selectionArgs);
			break;
		case VERSION:
			result = mDatabaseManager.mDatabase.delete(DatabaseContract.Version.TABLE_NAME,
					selection, selectionArgs);
			break;

		case VERSION_ID:
			whereClause = BaseColumns._ID + " = " + uri.getLastPathSegment();
			if (!TextUtils.isEmpty(selection)) {
				whereClause += " AND " + whereClause;
			}
			result = mDatabaseManager.mDatabase.delete(DatabaseContract.Version.TABLE_NAME,
					whereClause, selectionArgs);
			break;
		default:
			break;
		}

		if (result > 0) {
			mContentResolver.notifyChange(uri, null);
		}

		return result;
	}

	@Override
	public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
			throws OperationApplicationException {
		ContentProviderResult[] results = null;

		if (mDatabaseManager == null) {
			return results;
		}

		if (mDatabaseManager.mDatabase == null) {
			return results;
		}

		mDatabaseManager.mDatabase.beginTransaction();

		try {
			results = super.applyBatch(operations);
			mDatabaseManager.mDatabase.setTransactionSuccessful();
			return results;
		} finally {
			mDatabaseManager.mDatabase.endTransaction();
		}
	}
}
