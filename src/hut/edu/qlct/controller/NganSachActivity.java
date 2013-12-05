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

public class NganSachActivity extends Activity implements OnClickListener {
	private DatabaseAdapter dbAdapter;

	// Button Save, Cancel
	private Button btn_save;
	private Button btn_cancel;

	private EditText ed_sotienBD;
	private EditText ed_tenngansach;

	public long id_account;
	public String tenngansach;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbAdapter = new DatabaseAdapter(this);
		dbAdapter.open();

		setContentView(R.layout.ngansach);

		initialize();

		dbAdapter.close();
	}

	public void initialize() {
		ed_tenngansach = (EditText) findViewById(R.id.ed_tenngansach);
		ed_sotienBD = (EditText) findViewById(R.id.ed_sotienbd);
		// ed_sotienHT = (EditText) findViewById(R.id.ed_sotienht);

		btn_save = (Button) findViewById(R.id.btn_save);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);

		id_account = getIdAccount();

		btn_save.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_save:
			saveState();
			setResult(RESULT_OK);
			break;

		case R.id.btn_cancel:
			ed_sotienBD.setText("");
			ed_tenngansach.setText("");

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

	private void saveState() {
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
					"Tên ngân sách này đã được sử dụng", Toast.LENGTH_SHORT)
					.show();
		} else {
			int sotienBD = Integer.parseInt(ed_sotienBD.getText().toString());
			String tenNgansach = ed_tenngansach.getText().toString();

			dbAdapter.createNganSach(id_account, tenNgansach, sotienBD);
			Toast.makeText(getApplicationContext(),
					"Bạn đã tạo ngân sách thành công", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public long getIdAccount() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		long id = bundle.getLong("id");

		return id;
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

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.ngansach_menu, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // TODO Auto-generated method stub
	// switch (item.getItemId()) {
	// case R.id.displayNS:
	// Intent intent = getIntent();
	// Bundle bundle = intent.getExtras();
	// long id = bundle.getLong("id");
	//
	// Intent display = new Intent(this, DisplayNganSach.class);
	// bundle.putLong("id", id);
	// display.putExtras(bundle);
	// startActivityForResult(display, 100);
	// break;
	//
	// default:
	// break;
	// }
	//
	// return false;
	// }

}
