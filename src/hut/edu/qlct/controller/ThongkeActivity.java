package hut.edu.qlct.controller;

import hut.edu.qlct.model.DatabaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class ThongkeActivity extends ListActivity {

	Bundle bundle;
	Intent intent;

	public long id_account;
	public long id_ngansach;
	public long id_thuchi;

	public DatabaseAdapter db;
	public Spinner spnNganSach;
	public Spinner spnThuchi;
	public Button btnThongke;

	public Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thongke_layout);

		spnNganSach = (Spinner) findViewById(R.id.spnNgansach);
		spnThuchi = (Spinner) findViewById(R.id.spnThuChi);
		btnThongke = (Button) findViewById(R.id.btnThongke);

		id_account = getIdAccount();

		db = new DatabaseAdapter(this);
		db.open();
		loadSpinnerNganSach();

		btnThongke.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fillData();
				System.out.println("TEST THANH CONG");
			}
		});

	}

	// Load spinner ngan sach tu database
	public void loadSpinnerNganSach() {

		Cursor cursor = db.fetchNganSachTheoIdTaiKhoan(id_account);
		startManagingCursor(cursor);
		ArrayList<String> ngansach = new ArrayList<String>();
		if (cursor.moveToFirst()) {
			do {
				ngansach.add(cursor.getString(0));

			} while (cursor.moveToNext());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ngansach);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnNganSach.setAdapter(dataAdapter);
	}

	public long getIdAccount() {
		intent = getIntent();
		bundle = intent.getExtras();
		long id = bundle.getLong("id");

		return id;
	}

	public void fillData() {
		if (spnThuchi.getSelectedItem().toString().equals("Thu")) {
			id_thuchi = 1;
		} else {
			id_thuchi = 0;
		}

		id_ngansach = db
				.getIdNganSach(spnNganSach.getSelectedItem().toString());

		// cursor = db.fetchGiaodichNS(id_account, id_ngansach, id_thuchi);
		cursor = db.fetchInfoGiaoDich((int) id_account);
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
				if (Integer.parseInt(cursor.getString(0).toString()) == id_ngansach
						&& Integer.parseInt(cursor.getString(6).toString()) == id_thuchi) {
					giaodich.put(db.KEY_ID_GIAODICH, cursor.getString(5)
							.toString());
					if (Integer.parseInt(cursor.getString(6).toString()) == 1) {
						giaodich.put(db.KEY_ID_THUCHI, "Thu");
					} else {
						giaodich.put(db.KEY_ID_THUCHI, "Chi");
					}

					giaodich.put(db.KEY_ID_NGANSACH, db.getNameNganSach(Integer
							.parseInt(cursor.getString(0))));
					giaodich.put(db.KEY_GIAODICH, cursor.getString(1)
							.toString());
					giaodich.put(db.KEY_SOLUONG, "Số lượng: "
							+ cursor.getString(2).toString());
					giaodich.put(db.KEY_GIATIEN, "Giá tiền: "
							+ cursor.getString(3).toString());
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
