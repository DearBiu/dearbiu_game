package dearbiu.game.zombiefight.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import dearbiu.game.zombiefight.utils.DateUtility;

public class DatabaseTable implements Parcelable {
	
	String mTableName;
	long mID;
	String mCreated;
	String mModified;

	public DatabaseTable() {
		init();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void readFromParcel(Parcel in) {
		mTableName = in.readString();
		mID = in.readLong();
		mCreated = in.readString();
		mModified = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mTableName);
		dest.writeLong(mID);
		dest.writeString(mCreated);
		dest.writeString(mModified);
	}

	protected void init() {
		mTableName = "";
		mID = 0;
		mCreated = "";
		mModified = "";
	}

	protected ContentValues getContentValues(ContentValues contentValues) {
		mModified = DateUtility.getCurrentDateTimeString();

		contentValues.put(DatabaseContract.COLUMN_CREATED, mCreated);
		contentValues.put(DatabaseContract.COLUMN_MODIFIED, mModified);

		return contentValues;
	}

	protected void set(DatabaseTable databaseTable) {
		if (databaseTable == null) {
			return;
		}

		init();

		setID(databaseTable.mID);
		setCreated(databaseTable.mCreated);
		setModified(databaseTable.mModified);
	}

	protected void set(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		init();

		setID(cursor);
		setCreated(cursor);
		setModified(cursor);
	}

	public String getTableName() {
		return mTableName;
	}

	protected void setTableName(String tableName) {
		mTableName = tableName;
	}

	public long getID() {
		return mID;
	}

	public void setID(long id) {
		mID = id;
	}

	void setID(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		setID(cursor.getLong(cursor.getColumnIndex(DatabaseContract.COLUMN_ID)));
	}

	public String getCreated() {
		return mCreated;
	}

	public void setCreated(String created) {
		mCreated = created;
	}

	void setCreated(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		setCreated(cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_CREATED)));
	}

	public String getModified() {
		return mModified;
	}

	public void setModified(String modified) {
		mModified = modified;
	}

	void setModified(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		setModified(cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_MODIFIED)));
	}
}
