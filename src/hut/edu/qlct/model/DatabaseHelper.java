package hut.edu.qlct.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Tao lap cac bang co so du lieu
	public static final String CREATE_TABLE_ACCOUNT = "create table "
			+ "ACCOUNT" + "(" + "id_account"
			+ " integer primary key autoincrement, " + "username"
			+ " text not null, " + "password text not null" + ")";

	public static final String CREATE_TABLE_NGANSACH = "create table "
			+ "NGANSACH" + "(" + "id_ngansach"
			+ " integer primary key autoincrement, " + "id_account"
			+ " integer not null, " + "tenngansach " + " text not null, "
			+ "sotien_bandau" + " integer not null" + ")";

	public static final String CREATE_TABLE_GIAODICH = "create table "
			+ "GIAODICH" + "(" + "id_giaodich"
			+ " integer primary key autoincrement, " + "id_account"
			+ " integer not null," + "id_ngansach" + " integer not null, "
			+ "id_thuchi" + " integer not null, " + "id_thetag"
			+ " integer not null, " + "tengiaodich" + " text not null, "
			+ "soluong" + " integer not null, " + "giatien"
			+ " integer not null, " + "thoigian" + " text not null" + ")";

	public static final String CREATE_TABLE_THETAG = "create table " + "THETAG"
			+ "(" + "id_thetag" + " integer primary key autoincrement, "
			+ "tenthetag" + " text not null" + ")";

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_ACCOUNT);
		db.execSQL(CREATE_TABLE_NGANSACH);
		db.execSQL(CREATE_TABLE_GIAODICH);
		db.execSQL(CREATE_TABLE_THETAG);
		createInitData(db);
	}

	private void createInitData(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("INSERT INTO THETAG(tenthetag) VALUES('Thức ăn')");
		db.execSQL("INSERT INTO THETAG(tenthetag) VALUES('Thể thao')");
		db.execSQL("INSERT INTO THETAG(tenthetag) VALUES('Shopping')");
		db.execSQL("INSERT INTO THETAG(tenthetag) VALUES('Bảo hiểm')");
		db.execSQL("INSERT INTO THETAG(tenthetag) VALUES('Lương')");
		db.execSQL("INSERT INTO THETAG(tenthetag) VALUES('Thuế')");
		db.execSQL("INSERT INTO THETAG(tenthetag) VALUES('Tạo loại khác')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {

		_db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");

		onCreate(_db);
	}

}
