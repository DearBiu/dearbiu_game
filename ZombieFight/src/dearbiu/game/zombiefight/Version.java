package dearbiu.game.zombiefight.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Version extends DatabaseTable {

	public String mCode;
	public String mName;
	public String mContent;
	public String mDateTime;

	public Version() {
		init();
	}

	public Version(Version version) {
		set(version);
	}

	public Version(Cursor cursor) {
		set(cursor);
	}

	protected void init() {
		super.init();

		setTableName(DatabaseContract.Version.TABLE_NAME);

		mCode = "";
		mName = "";
		mContent = "";
		mDateTime = "";
	}

	public ContentValues getContentValues() {
		ContentValues contentValues = new ContentValues();

		getContentValues(contentValues);

		return contentValues;
	}

	public ContentValues getContentValues(ContentValues contentValues) {
		super.getContentValues(contentValues);

		contentValues.put(DatabaseContract.Version.COLUMN_CODE, mCode);
		contentValues.put(DatabaseContract.Version.COLUMN_NAME, mName);
		contentValues.put(DatabaseContract.Version.COLUMN_CONTENT, mContent);
		contentValues.put(DatabaseContract.Version.COLUMN_DATE, mDateTime);

		return contentValues;
	}

	void set(Version version) {
		if (version == null) {
			return;
		}

		init();

		super.set(version);

		setCode(version.getCode());
		setContent(version.getContent());
		setDateTime(version.getDateTime());
	}

	public void set(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		init();

		super.set(cursor);

		setCode(cursor);
		setContent(cursor);
		setDateTime(cursor);
	}

	public String getCode() {
		return mCode;
	}

	public void setCode(String code) {
		mCode = code;
	}

	void setCode(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		setCode(cursor.getString(cursor.getColumnIndex(DatabaseContract.Version.COLUMN_CODE)));
	}

	public String getName() {
		return mCode;
	}

	public void setName(String name) {
		mName = name;
	}

	void setName(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		setCode(cursor.getString(cursor.getColumnIndex(DatabaseContract.Version.COLUMN_NAME)));
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	void setContent(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		setContent(
				cursor.getString(cursor.getColumnIndex(DatabaseContract.Version.COLUMN_CONTENT)));
	}

	public String getDateTime() {
		return mDateTime;
	}

	public void setDateTime(String dateTime) {
		mDateTime = dateTime;
	}

	void setDateTime(Cursor cursor) {
		if (cursor == null) {
			return;
		}

		setDateTime(cursor.getString(cursor.getColumnIndex(DatabaseContract.Version.COLUMN_DATE)));
	}
}
