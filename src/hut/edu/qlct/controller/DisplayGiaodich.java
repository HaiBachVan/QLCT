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
import android.widget.TextView;

public class DisplayGiaodich extends ListActivity {

	TextView tv_tenngansach;
	TextView tv_soluong;
	TextView tv_giatien;
	TextView tv_date;
	TextView tv_tengiaodich;

	private ListView listView;

	private DatabaseAdapter db;
	Intent intent;
	Bundle bundle;

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
		listView = (ListView) findViewById(android.R.id.list);

		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setTextFilterEnabled(true);

		db = new DatabaseAdapter(this);
		db.open();

		fillData();
		
		db.close();
	}

	public void fillData() {
		// TODO Auto-generated method stub

		intent = getIntent();
		bundle = intent.getExtras();
		long id = bundle.getLong("id");
		// System.out.println("ID = " + id);

		Cursor cursor = db.fetchInfoGiaoDich((int) id);
		startManagingCursor(cursor);
		String[] from = new String[] { db.KEY_ID_GIAODICH, db.KEY_ID_THUCHI,
				db.KEY_ID_NGANSACH, db.KEY_GIAODICH, db.KEY_SOLUONG,
				db.KEY_GIATIEN, db.KEY_DATE };
		int[] to = new int[] { R.id.tv_id_giaodich, R.id.tv_id_thuchi,
				R.id.tv_ngansach, R.id.tv_giaodich, R.id.tv_soluong,
				R.id.tv_giatien, R.id.tv_date };

		ArrayList<HashMap<String, String>> giaodichList = new ArrayList<HashMap<String, String>>();

		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> giaodich = new HashMap<String, String>();
				giaodich.put(db.KEY_ID_GIAODICH, cursor.getString(5).toString());
				if (Integer.parseInt(cursor.getString(6).toString()) == 1) {
					giaodich.put(db.KEY_ID_THUCHI, "Thu");
				} else {
					giaodich.put(db.KEY_ID_THUCHI, "Chi");
				}

				giaodich.put(db.KEY_ID_NGANSACH, db.getNameNganSach(Integer
						.parseInt(cursor.getString(0))));
				giaodich.put(db.KEY_GIAODICH, cursor.getString(1).toString());
				giaodich.put(db.KEY_SOLUONG, "Số lượng: "
						+ cursor.getString(2).toString());
				giaodich.put(db.KEY_GIATIEN, "Giá tiền: "
						+ cursor.getString(3).toString());
				giaodich.put(db.KEY_DATE, cursor.getString(4).toString());

				giaodichList.add(giaodich);
			} while (cursor.moveToNext());
		}

		cursor.close();

		SimpleAdapter adapter = new SimpleAdapter(this, giaodichList,
				R.layout.row_giaodich, from, to);
		listView.setAdapter(adapter);

		// listView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> listView, View v,
		// int position, long id) {
		// // TODO Auto-generated method stub
		// int id_giaodich = Integer.parseInt(((TextView) v
		// .findViewById(R.id.tv_id)).getText().toString());
		//
		// System.out.println("Id giaodich: " + id_giaodich);
		//
		// Intent suachua_giaodich = new Intent(getApplicationContext(),
		// SuachuaGiaodich.class);
		// bundle.putInt("id_giaodich", id_giaodich);
		// suachua_giaodich.putExtras(bundle);
		// startActivityForResult(suachua_giaodich, 100);
		// }
		// });
	}

}
