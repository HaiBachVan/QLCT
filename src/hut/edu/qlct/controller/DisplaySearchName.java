package hut.edu.qlct.controller;

import hut.edu.qlct.model.DatabaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DisplaySearchName extends ListActivity {
	TextView tv_tenngansach;
	TextView tv_soluong;
	TextView tv_giatien;
	TextView tv_date;
	TextView tv_tengiaodich;

	private DatabaseAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.danhsach_giaodich);

		initialize();
	}

	public void initialize() {
		tv_tenngansach = (TextView) findViewById(R.id.tv_ngansach);
		tv_soluong = (TextView) findViewById(R.id.tv_soluong);
		tv_giatien = (TextView) findViewById(R.id.tv_giatien);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_tengiaodich = (TextView) findViewById(R.id.tv_giaodich);

		db = new DatabaseAdapter(this);
		db.open();

		fillData();

		db.close();
	}

	private void fillData() {
		// TODO Auto-generated method stub

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		long id_account = bundle.getLong("id");
		int id_ngansach = bundle.getInt("id_ngansach");

		Cursor cursor = db.fetchInfoSearch(id_account);
		startManagingCursor(cursor);
		String[] from = new String[] { db.KEY_ID_NGANSACH, db.KEY_GIAODICH,
				db.KEY_SOLUONG, db.KEY_GIATIEN, db.KEY_DATE };
		int[] to = new int[] { R.id.tv_ngansach, R.id.tv_giaodich,
				R.id.tv_soluong, R.id.tv_giatien, R.id.tv_date };

		//
		ArrayList<HashMap<String, String>> giaodichList = new ArrayList<HashMap<String, String>>();

		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> giaodich = new HashMap<String, String>();
				if (id_ngansach == Integer.parseInt(cursor.getString(0)
						.toString())) {
					giaodich.put(db.KEY_ID_NGANSACH, db.getNameNganSach(Integer
							.parseInt(cursor.getString(0))));
					giaodich.put(db.KEY_GIAODICH, cursor.getString(1)
							.toString());
					giaodich.put(db.KEY_SOLUONG, cursor.getString(2).toString());
					giaodich.put(db.KEY_GIATIEN, cursor.getString(3).toString());
					giaodich.put(db.KEY_DATE, cursor.getString(4).toString());

					giaodichList.add(giaodich);
				}

			} while (cursor.moveToNext());

		}
		cursor.close();

		SimpleAdapter adapter = new SimpleAdapter(this, giaodichList,
				R.layout.row_giaodich, from, to);
		setListAdapter(adapter);

	}
}
