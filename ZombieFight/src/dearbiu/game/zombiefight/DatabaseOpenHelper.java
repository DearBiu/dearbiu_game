package dearbiu.game.zombiefight.database;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import dearbiu.game.zombiefight.R;
import dearbiu.game.zombiefight.utils.DateUtility;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	static final String INSERT_OR_REPLACE = "INSERT OR REPLACE ";

	private Context mContext;
	private Resources mResources;

	public DatabaseOpenHelper(Context context) {
		super(context, DatabaseContract.DATABASE_FILE, null, DatabaseContract.DATABASE_VERSION);
		mContext = context;
		mResources = mContext.getResources();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DatabaseContract.Setting.CREATE_TABLE);
		db.execSQL(DatabaseContract.Version.CREATE_TABLE);
		loadDefaultVersion(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DatabaseContract.Setting.DELETE_TABLE);
		db.execSQL(DatabaseContract.Version.DELETE_TABLE);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	private void loadDefaultVersion(SQLiteDatabase db) {

		String[] codeArray = null;
		String[] nameArray = null;
		String[] contentArray = null;
		String[] dateTimeArray = null;

		String code = "";
		String name = "";
		String content = "";
		String dateTime = "";

		codeArray = mResources.getStringArray(R.array.array_version_code);
		nameArray = mResources.getStringArray(R.array.array_version_name);
		contentArray = mResources.getStringArray(R.array.array_version_content);
		dateTimeArray = mResources.getStringArray(R.array.array_version_date);

		if ((codeArray == null) || (contentArray == null) || (dateTimeArray == null)) {
			return;
		}

		if (!((codeArray.length == contentArray.length)
				&& (codeArray.length == dateTimeArray.length))) {
			return;
		}

		for (int i = 0; i < codeArray.length; i++) {
			code = codeArray[i];
			name = nameArray[i];
			content = contentArray[i];
			dateTime = dateTimeArray[i];

			loadDefaultVersion(db, code, name, content, dateTime);
		}
	}

	private void loadDefaultVersion(SQLiteDatabase db, String code, String name, String content,
			String dateTime) {
		SQLiteStatement stmt = null;
		try {
			stmt = db.compileStatement(INSERT_OR_REPLACE + DatabaseContract.Version.TABLE_NAME + "("
					+ DatabaseContract.Version.COLUMN_CODE + ","
					+ DatabaseContract.Version.COLUMN_NAME + ","
					+ DatabaseContract.Version.COLUMN_CONTENT + ","
					+ DatabaseContract.Version.COLUMN_DATE + "," + DatabaseContract.COLUMN_CREATED
					+ ")" + " VALUES(?,?,?,?);");

			int index = 1;
			stmt.bindString(index++, code);
			stmt.bindString(index++, name);
			stmt.bindString(index++, content);
			stmt.bindString(index++, dateTime);
			stmt.bindString(index++, DateUtility.getCurrentDateTimeString());
			stmt.execute();
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}
}