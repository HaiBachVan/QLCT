package hut.edu.qlct.controller;

import java.util.ArrayList;
import java.util.HashMap;

import hut.edu.qlct.model.DatabaseAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DisplayNganSach extends ListActivity {

	public DatabaseAdapter dbAdapter;
	private Cursor cursor;
	public ListView listview;
	public Bundle bundle;

	public long id_account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dsngansach);
		this.getListView().setDividerHeight(2);
		dbAdapter = new DatabaseAdapter(this);
		dbAdapter.open();
		registerForContextMenu(getListView());
		fillData();
		dbAdapter.close();
	}

	public void fillData() {
		// TODO Auto-generated method stub

		id_account = getIdAccount();

		cursor = dbAdapter.fetchNganSachTheoIdTaiKhoan(id_account);
		startManagingCursor(cursor);
		String[] from = new String[] { dbAdapter.KEY_ID_NGANSACH,
				dbAdapter.KEY_TENNGANSACH, dbAdapter.KEY_SOTIEN_BANDAU,
				"sotienhientai" };
		int[] to = new int[] { R.id.tv_id_ngansach, R.id.tv_tenngansach,
				R.id.tv_sotienBD, R.id.tv_sotienHT };

		ArrayList<HashMap<String, String>> ngansachList = new ArrayList<HashMap<String, String>>();

		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> ngansach = new HashMap<String, String>();
				ngansach.put(dbAdapter.KEY_ID_NGANSACH, cursor.getString(2));
				ngansach.put(dbAdapter.KEY_TENNGANSACH, cursor.getString(0));
				ngansach.put(dbAdapter.KEY_SOTIEN_BANDAU, cursor.getString(1));

				ngansachList.add(ngansach);

				int id_ngansach = Integer.parseInt(cursor.getString(2)
						.toString());
				String sotienHT = String.valueOf(dbAdapter
						.getSotienNgansach(id_ngansach));
				ngansach.put("sotienhientai", sotienHT);

			} while (cursor.moveToNext());

		}
		cursor.close();

		SimpleAdapter adapter = new SimpleAdapter(this, ngansachList,
				R.layout.row_ngansach, from, to);

		setListAdapter(adapter);

	}

	public long getIdAccount() {
		Intent intent = getIntent();
		bundle = intent.getExtras();
		long id = bundle.getLong("id");

		return id;
	}
}
