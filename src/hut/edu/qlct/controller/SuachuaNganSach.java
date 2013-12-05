package hut.edu.qlct.controller;

import hut.edu.qlct.model.DatabaseAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SuachuaNganSach extends Activity implements OnClickListener {
	private DatabaseAdapter dbAdapter;

	// Button Save, Cancel
	private Button btn_save;
	private Button btn_cancel;

	private EditText ed_sotienBD;
	private EditText ed_tenngansach;

	public int id_ngansach;
	public int id_account;

	public Bundle bundle;
	public Intent intent;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ngansach);

		initialize();
	}

	public void initialize() {
		ed_tenngansach = (EditText) findViewById(R.id.ed_tenngansach);
		ed_sotienBD = (EditText) findViewById(R.id.ed_sotienbd);

		btn_save = (Button) findViewById(R.id.btn_save);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);

		btn_save.setText("Update");
		dbAdapter = new DatabaseAdapter(this);
		dbAdapter.open();

		id_ngansach = getIdNgansach();
		Cursor cursor = dbAdapter.fetchNganSach(id_ngansach);
		startManagingCursor(cursor);
		if (cursor.moveToFirst()) {
			do {
				id_account = Integer.parseInt(cursor.getString(0).toString());
				ed_tenngansach.setText(cursor.getString(2).toString());
				ed_sotienBD.setText(cursor.getString(3).toString());
			} while (cursor.moveToNext());
		}

		cursor.close();

		btn_save.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		dbAdapter.close();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_save:
			updateNgansach();
			setResult(RESULT_OK);
			Toast.makeText(getApplicationContext(),
					"Bạn đã cập nhật ngân sách thành công", Toast.LENGTH_SHORT)
					.show();

			break;

		case R.id.btn_cancel:
			finish();
			break;

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void updateNgansach() {
		if (ed_tenngansach.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Bạn chưa điền khai báo ngân sách", Toast.LENGTH_SHORT)
					.show();
		} else if (ed_sotienBD.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Bạn chưa khai báo số tiền ngân sách", Toast.LENGTH_SHORT)
					.show();
		} else if (checkNameNgansach()) {
			Toast.makeText(getApplicationContext(),
					"Tên ngân sách đã được sử dụng", Toast.LENGTH_SHORT).show();
		} else {

			int sotienBD = Integer.parseInt(ed_sotienBD.getText().toString());
			String tenNgansach = ed_tenngansach.getText().toString();
			dbAdapter.updateNganSach(id_ngansach, id_account, tenNgansach,
					sotienBD);
		}
	}

	public int getIdNgansach() {
		intent = getIntent();
		bundle = intent.getExtras();
		int id_ngansach = bundle.getInt("id_ngansach");

		return id_ngansach;
	}

	public boolean checkNameNgansach() {
		Cursor cursor = dbAdapter.getAllNameNganSach(id_account);
		startManagingCursor(cursor);

		if (cursor.moveToFirst()) {
			do {
				if (ed_tenngansach.getText().toString()
						.equals(cursor.getString(0).toString())) {
					return true;
				}
			} while (cursor.moveToNext());
		}

		return false;
	}

}
