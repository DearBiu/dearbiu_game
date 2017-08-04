package dearbiu.game.zombiefight.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {
	
	public static final String AUTHORITY = "dearbiu.game.zombiefight";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "zombiefight";
	public static final String DATABASE_FILE = DATABASE_NAME + ".db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String FLOAT_TYPE = " FLOAT";
	private static final String DOUBLE_TYPE = " DOUBLE";
	private static final String BLOB_TYPE = " BLOB";
	private static final String PRIMARY_KEY = " PRIMARY KEY";
	private static final String UNIQUE_TYPE = " UNIQUE";
	private static final String COMMA_SEP = ",";

	public static final String COLUMN_ID = BaseColumns._ID;
	public static final String COLUMN_CREATED = "created";
	public static final String COLUMN_MODIFIED = "modified";

	private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

	public static final String ORDER_BY = " ORDER BY ";
	public static final String ORDER_DIRECTION_ASC = " ASC ";
	public static final String ORDER_DIRECTION_DESC = " DESC ";

	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	private DatabaseContract() {
	}

	public static abstract class Setting implements BaseColumns {
		public static final String TABLE_NAME = "setting";

		public static final Uri CONTENT_URI = Uri.withAppendedPath(DatabaseContract.CONTENT_URI,
				TABLE_NAME);
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
				+ DATABASE_NAME + "/" + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
				+ DATABASE_NAME + "/" + TABLE_NAME;

		public static final String COLUMN_KEY = "key";
		public static final String COLUMN_VALUE = "value";

		public static final String SORT_ORDER_DEFAULT = COLUMN_KEY + " ASC";

		public static final String[] PROJECTION_ALL = { _ID, COLUMN_KEY, COLUMN_VALUE,
				COLUMN_CREATED, COLUMN_MODIFIED };

		public static final String CREATE_TABLE_CONTENT = " (" + _ID + INTEGER_TYPE + PRIMARY_KEY
				+ COMMA_SEP + COLUMN_KEY + TEXT_TYPE + COMMA_SEP + COLUMN_VALUE + TEXT_TYPE
				+ COMMA_SEP + COLUMN_CREATED + TEXT_TYPE + COMMA_SEP + COLUMN_MODIFIED + TEXT_TYPE
				+ " )";

		public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
				+ CREATE_TABLE_CONTENT;

		public static final String DELETE_TABLE = DROP_TABLE_IF_EXISTS + TABLE_NAME;
	}

	public static abstract class Version implements BaseColumns {
		public static final String TABLE_NAME = "version";

		public static final Uri CONTENT_URI = Uri.withAppendedPath(DatabaseContract.CONTENT_URI,
				TABLE_NAME);
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
				+ DATABASE_NAME + "/" + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
				+ DATABASE_NAME + "/" + TABLE_NAME;

		public static final String COLUMN_CODE = "code";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_CONTENT = "content";
		public static final String COLUMN_DATE = "date";

		public static final String SORT_ORDER_DEFAULT = COLUMN_CODE + " ASC";

		public static final String[] PROJECTION_ALL = { _ID, COLUMN_CODE, COLUMN_NAME,
				COLUMN_CONTENT, COLUMN_DATE, COLUMN_CREATED, COLUMN_MODIFIED };

		public static final String CREATE_TABLE_CONTENT = " (" + _ID + INTEGER_TYPE + PRIMARY_KEY
				+ COMMA_SEP + COLUMN_CODE + TEXT_TYPE + COMMA_SEP + COLUMN_NAME + TEXT_TYPE
				+ COMMA_SEP + COLUMN_CONTENT + TEXT_TYPE + COMMA_SEP + COLUMN_DATE + TEXT_TYPE
				+ COMMA_SEP + COLUMN_CREATED + TEXT_TYPE + COMMA_SEP + COLUMN_MODIFIED + TEXT_TYPE
				+ " )";

		public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
				+ CREATE_TABLE_CONTENT;

		public static final String DELETE_TABLE = DROP_TABLE_IF_EXISTS + TABLE_NAME;
	}
}