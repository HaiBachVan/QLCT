package hut.edu.qlct.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {

	// Information about database
	public final String DATABASE_NAME = "QLCT.db";
	public final int DATABASE_VERSION = 1;
	public final int NAME_COLUMN = 1;

	// KEY ACCOUNT TABLE
	public final String KEY_TABLE_TAIKHOAN = "ACCOUNT";
	public final String KEY_USER = "username";
	public final String KEY_PASS = "password";

	// KEY NGANSACH TABLE
	public final String KEY_TABLE_NGANSACH = "NGANSACH";
	public final String KEY_ID_NGANSACH = "id_ngansach";
	public final String KEY_IDACCOUNT = "id_account";
	public final String KEY_SOTIEN_BANDAU = "sotien_bandau";
	public final String KEY_TENNGANSACH = "tenngansach";

	// KEY GIAODICH TABLE
	public final String KEY_TABLE_GIAODICH = "GIAODICH";
	public final String KEY_ID_GIAODICH = "id_giaodich";
	public final String KEY_GIAODICH = "tengiaodich";
	public final String KEY_ID_THETAG = "id_thetag";
	public final String KEY_ID_THUCHI = "id_thuchi";
	public final String KEY_SOLUONG = "soluong";
	public final String KEY_GIATIEN = "giatien";
	public final String KEY_DATE = "thoigian";

	// KEY THETAG TABLE
	private final String KEY_TABLE_THETAG = "THETAG";
	public final String KEY_TENGIAODICH = "tenthetag";

	public SQLiteDatabase db;

	private final Context context;

	private DatabaseHelper dbHelper;

	public DatabaseAdapter(Context _context) {
		context = _context;
		dbHelper = new DatabaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public DatabaseAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	public void insertEntry(String userName, String password) {
		ContentValues newValues = new ContentValues();

		newValues.put(KEY_USER, userName);
		newValues.put(KEY_PASS, password);

		db.insert(KEY_TABLE_TAIKHOAN, null, newValues);

	}

	public Cursor fetchAllUserName() {
		return db.query(KEY_TABLE_TAIKHOAN, new String[] { KEY_USER }, null,
				null, null, null, null);
	}

	public int deleteEntry(String username) {

		String where = "username=?";
		int numberOFEntriesDeleted = db.delete(KEY_TABLE_TAIKHOAN, where,
				new String[] { username });

		return numberOFEntriesDeleted;
	}

	public String getSinlgeEntry(String userName) {
		Cursor cursor = db.query(KEY_TABLE_TAIKHOAN, null, " USERNAME=?",
				new String[] { userName }, null, null, null);
		if (cursor.getCount() < 1) // UserName khong ton tai
		{
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String password = cursor.getString(cursor.getColumnIndex(KEY_PASS));
		cursor.close();
		return password;
	}

	// //////
	public long getIdAccount(String userName) {
		Cursor cursor = db.query(KEY_TABLE_TAIKHOAN, null, " USERNAME=?",
				new String[] { userName }, null, null, null);
		cursor.moveToFirst();
		long id = cursor.getLong(cursor.getColumnIndex(KEY_IDACCOUNT));
		cursor.close();
		return id;
	}

	public boolean compareUserName(String userName) {
		Cursor cursor = db.query(KEY_TABLE_TAIKHOAN, null, " USERNAME=?",
				new String[] { userName }, null, null, null);
		if (cursor.getCount() < 1) // UserName khong ton tai
		{
			cursor.close();
			return false;
		}

		return true;
	}

	// ///

	public void updateEntry(String userName, String password) {

		ContentValues updatedValues = new ContentValues();

		updatedValues.put(KEY_USER, userName);
		updatedValues.put(KEY_PASS, password);

		String where = "USERNAME = ?";
		db.update("LOGIN", updatedValues, where, new String[] { userName });
	}

	public long createAccount(String username, String password) {
		ContentValues initialValues = createValuesAccount(username, password);

		return db.insert(KEY_TABLE_TAIKHOAN, null, initialValues);
	}

	private ContentValues createValuesAccount(String username, String password) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(KEY_USER, username);
		values.put(KEY_PASS, password);
		return values;
	}

	//
	public Cursor fetchAccount(long id_row) {

		return null;
	}

	// Cac thao tac voi bang Ngan sach
	public long createNganSach(Long id_taikhoan, String tenNgansach,
			int sotienBD) {
		ContentValues initialValues = createValuesNganSach(id_taikhoan,
				tenNgansach, sotienBD);
		return db.insert(KEY_TABLE_NGANSACH, null, initialValues);
	}

	public long updateNganSach(int id_ngansach, long id_account,
			String tenNgansach, int sotienBD) {
		ContentValues updateValues = createValuesNganSach(id_account,
				tenNgansach, sotienBD);
		return db.update(KEY_TABLE_NGANSACH, updateValues, KEY_ID_NGANSACH
				+ "=" + id_ngansach, null);
	}

	public long deleteNganSach(int id_ngansach) {
		return db.delete(KEY_TABLE_NGANSACH, KEY_ID_NGANSACH + "="
				+ id_ngansach, null);
	}

	public long deleteGiaodichTheoNS(int id_ngansach) {
		return db.delete(KEY_TABLE_GIAODICH, KEY_ID_NGANSACH + "="
				+ id_ngansach, null);
	}

	public Cursor fetchAllNganSach() {
		return db.query(KEY_TABLE_NGANSACH, new String[] { KEY_ID_NGANSACH,
				KEY_IDACCOUNT, KEY_TENNGANSACH, KEY_SOTIEN_BANDAU }, null,
				null, null, null, null);
	}

	public Cursor fetchNganSach(int id_ngansach) throws SQLException {
		Cursor mCursor = db.query(true, KEY_TABLE_NGANSACH, new String[] {
				KEY_IDACCOUNT, KEY_ID_NGANSACH, KEY_TENNGANSACH,
				KEY_SOTIEN_BANDAU }, KEY_ID_NGANSACH + "=" + id_ngansach, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public String getNameNganSach(int id_account) {
		Cursor mCursor = db.query(true, KEY_TABLE_NGANSACH, new String[] {
				KEY_ID_NGANSACH, KEY_TENNGANSACH }, null, null, null, null,
				null, null);

		if (mCursor.moveToFirst()) {
			do {
				if (id_account == Long.parseLong(mCursor.getString(0))) {
					return (mCursor.getString(1).toString());
				}
			} while (mCursor.moveToNext());
		}
		return null;
	}

	public Cursor getAllNameNganSach(long id_account) {
		Cursor mCursor = db.query(true, KEY_TABLE_NGANSACH,
				new String[] { KEY_TENNGANSACH }, KEY_IDACCOUNT + "="
						+ id_account, null, null, null, null, null);

		return mCursor;
	}

	public Cursor fetchNganSachTheoIdTaiKhoan(long id_account)
			throws SQLException {
		Cursor mCursor = db.query(true, KEY_TABLE_NGANSACH, new String[] {
				KEY_TENNGANSACH, KEY_SOTIEN_BANDAU, KEY_ID_NGANSACH,
				KEY_ID_NGANSACH }, KEY_IDACCOUNT + "=" + id_account, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createValuesNganSach(Long id_account,
			String tenNganSach, int sotienBD) {
		ContentValues values = new ContentValues();
		values.put(KEY_IDACCOUNT, id_account);
		values.put(KEY_TENNGANSACH, tenNganSach);
		values.put(KEY_SOTIEN_BANDAU, sotienBD);
		return values;
	}

	// Thẻ tag
	public long createLoai(String ten) {
		ContentValues initialValues = taoGiaTriLoai(ten);
		return db.insert(KEY_TABLE_THETAG, null, initialValues);
	}

	public boolean updateLoai(long rowId, String ten) {
		ContentValues updateValues = taoGiaTriLoai(ten);
		return db.update(KEY_TABLE_THETAG, updateValues, KEY_ID_THETAG + "="
				+ rowId, null) > 0;
	}

	public boolean deleteLoai(long rowId) {
		return db.delete(KEY_TABLE_THETAG, KEY_ID_THETAG + "=" + rowId, null) > 0;
	}

	public Cursor fetchLoai() {
		Cursor mCursor = db.query(true, KEY_TABLE_THETAG,
				new String[] { KEY_TENGIAODICH }, null, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor fetchLoai(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, KEY_TABLE_THETAG, new String[] {
				KEY_ID_THETAG, KEY_TENGIAODICH }, KEY_ID_THETAG + "=" + rowId,
				null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues taoGiaTriLoai(String ten) {
		ContentValues values = new ContentValues();
		values.put(KEY_TENGIAODICH, ten);
		return values;
	}

	// Thao tac voi bang GIAO DICH
	public long createGiaoDich(int id_account, int id_ngansach, int id_thuchi,
			int id_thetag, String tenGiaodich, int soluong, int giatien,
			String date) {
		ContentValues initialValues = createValueGiaoDich(id_account,
				id_ngansach, id_thuchi, id_thetag, tenGiaodich, soluong,
				giatien, date);
		System.out.println("GIAODICH: " + id_ngansach + " " + id_thuchi + " "
				+ id_thetag + " " + tenGiaodich + " " + soluong + " " + giatien
				+ " " + date);
		return db.insert(KEY_TABLE_GIAODICH, null, initialValues);
	}

	private ContentValues createValueGiaoDich(int id_account, int id_ngansach,
			int id_thuchi, int id_thetag, String tenGiaodich, int soluong,
			int giatien, String date) {
		ContentValues values = new ContentValues();

		values.put(KEY_IDACCOUNT, id_account);
		values.put(KEY_ID_NGANSACH, id_ngansach);
		values.put(KEY_GIATIEN, giatien);
		values.put(KEY_ID_THUCHI, id_thuchi);
		values.put(KEY_DATE, date);
		values.put(KEY_GIAODICH, tenGiaodich);
		values.put(KEY_SOLUONG, soluong);
		values.put(KEY_ID_THETAG, id_thetag);
		return values;
	}

	public long updateGiaoDich(int id_giaodich, int id_account,
			int id_ngansach, int id_thuchi, int id_thetag, String tenGiaodich,
			int soluong, int giatien, String date) {
		ContentValues updateValues = createValueGiaoDich(id_account,
				id_ngansach, id_thuchi, id_thetag, tenGiaodich, soluong,
				giatien, date);

		return db.update(KEY_TABLE_GIAODICH, updateValues, KEY_ID_GIAODICH
				+ " = " + id_giaodich, null);
	}

	public long deleteGiaoDich(int id_giaodich) {
		return db.delete(KEY_TABLE_GIAODICH, KEY_ID_GIAODICH + " = "
				+ id_giaodich, null);
	}

	public Cursor getNganSachTheoID(int id_ngansach) {
		Cursor mCurcor = db.query(KEY_TABLE_GIAODICH, new String[] {
				KEY_ID_NGANSACH, KEY_GIAODICH, KEY_SOLUONG, KEY_GIATIEN,
				KEY_DATE }, KEY_ID_NGANSACH + " = " + id_ngansach, null, null,
				null, null);

		if (mCurcor != null) {
			mCurcor.moveToFirst();
		}

		return mCurcor;
	}

	public Cursor fetchInfoGiaoDich(int id_account) {
		Cursor cursor = db.query(KEY_TABLE_GIAODICH, new String[] {
				KEY_ID_NGANSACH, KEY_GIAODICH, KEY_SOLUONG, KEY_GIATIEN,
				KEY_DATE, KEY_ID_GIAODICH, KEY_ID_THUCHI }, KEY_IDACCOUNT + "="
				+ id_account, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor;
	}

	public Cursor fetchGiaodichNS(long id_account, long id_ngansach,
			long id_thuchi) {
		// Cursor cursor = db.query(KEY_TABLE_GIAODICH, new String[] {
		// KEY_ID_NGANSACH, KEY_GIAODICH, KEY_SOLUONG, KEY_GIATIEN,
		// KEY_DATE, KEY_ID_GIAODICH, KEY_ID_THUCHI }, KEY_IDACCOUNT + "="
		// + id_account + " AND " + KEY_ID_GIAODICH + "=" + id_giaodich
		// + " AND " + KEY_ID_THUCHI + "=" + id_thuchi, null, null, null,
		// null);

		Cursor cursor = db
				.query(KEY_TABLE_GIAODICH,
						new String[] { KEY_ID_NGANSACH, KEY_GIAODICH,
								KEY_SOLUONG, KEY_GIATIEN, KEY_DATE,
								KEY_ID_GIAODICH, KEY_ID_THUCHI },
						KEY_IDACCOUNT + "= ? AND " + KEY_ID_GIAODICH
								+ "= ? AND " + KEY_ID_THUCHI + "= ?",
						new String[] { String.valueOf(id_account),
								String.valueOf(id_ngansach),
								String.valueOf(id_thuchi) }, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor;
	}

	// Lay ve tat ca cac thong tin cua giao dich
	public Cursor fetchAllInfoGiaoDich(int id_giaodich) {
		Cursor cursor = db.query(KEY_TABLE_GIAODICH, new String[] {
				KEY_GIAODICH, KEY_SOLUONG, KEY_GIATIEN, KEY_DATE,
				KEY_ID_THUCHI, KEY_ID_THETAG, KEY_ID_NGANSACH },
				KEY_ID_GIAODICH + "=" + id_giaodich, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor;
	}

	// Lay id the tag
	public int getIdTheTag(String tenTheTag) {
		Cursor mCursor = db.query(true, KEY_TABLE_THETAG, new String[] {
				KEY_ID_THETAG, KEY_TENGIAODICH }, null, null, null, null, null,
				null);
		if (mCursor.moveToFirst()) {
			do {
				if (mCursor.getString(1).equals(tenTheTag)) {
					return Integer.parseInt(mCursor.getString(0));
				}
			} while (mCursor.moveToNext());
		}
		return 0;
	}

	public String getTheTagTheoID(int id_thetag) {
		Cursor mCurcor = db.query(KEY_TABLE_THETAG,
				new String[] { KEY_TENGIAODICH }, KEY_ID_THETAG + " = "
						+ id_thetag, null, null, null, null);

		if (mCurcor != null) {
			mCurcor.moveToFirst();
		}

		String itemTheTag;
		itemTheTag = mCurcor.getString(0);

		return itemTheTag;
	}

	// Lay id ten ngan sach
	public int getIdNganSach(String tenngansach) {
		Cursor mCursor = db.query(true, KEY_TABLE_NGANSACH, new String[] {
				KEY_ID_NGANSACH, KEY_TENNGANSACH }, null, null, null, null,
				null, null);

		if (mCursor.moveToFirst()) {
			do {
				if (mCursor.getString(1).equals(tenngansach)) {
					return Integer.parseInt(mCursor.getString(0));
				}
			} while (mCursor.moveToNext());
		}
		return 0;
	}

	// Thao tac xu ly tim kiem
	public Cursor fetchInfoSearch(long id_account) {
		Cursor mCursor = db.query(false, KEY_TABLE_GIAODICH, new String[] {
				KEY_ID_NGANSACH, KEY_GIAODICH, KEY_SOLUONG, KEY_GIATIEN,
				KEY_DATE, KEY_ID_THUCHI, KEY_ID_THETAG }, KEY_IDACCOUNT + "="
				+ id_account, null, null, null, KEY_ID_NGANSACH, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public int getTienBD(int id_ngansach) {
		Cursor mCursor = db.query(true, KEY_TABLE_NGANSACH,
				new String[] { KEY_SOTIEN_BANDAU }, KEY_ID_NGANSACH + " = "
						+ id_ngansach, null, null, null, null, null);

		if (mCursor.moveToFirst()) {
			do {

				return Integer.parseInt(mCursor.getString(0));

			} while (mCursor.moveToNext());
		}

		return 0;

	}

	// Lay so tien hien tai theo tung ngan sach
	public int getSotienNgansach(int id_ngansach) {
		Cursor mCursor = db.query(false, KEY_TABLE_GIAODICH, new String[] {
				KEY_ID_NGANSACH, KEY_GIAODICH, KEY_SOLUONG, KEY_GIATIEN,
				KEY_DATE, KEY_ID_THUCHI }, KEY_ID_NGANSACH + " = "
				+ id_ngansach, null, null, null, null, null);
		int sotien = getTienBD(id_ngansach);

		if (mCursor.moveToFirst()) {
			do {
				int chiphi = Integer.parseInt(mCursor.getString(3))
						* Integer.parseInt(mCursor.getString(2));
				if (Integer.parseInt(mCursor.getString(5)) == 1) {
					sotien = sotien + chiphi;
				} else {
					sotien = sotien - chiphi;
				}
			} while (mCursor.moveToNext());
		}

		return sotien;
	}
}
