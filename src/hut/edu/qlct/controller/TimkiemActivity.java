package hut.edu.qlct.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hut.edu.qlct.model.DatabaseAdapter;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class TimkiemActivity extends Activity implements OnClickListener {

	public DatabaseAdapter db;
	private Cursor cursor;
	private Spinner spnTag;
	private Spinner spnTheTag;

	private Button btnSearchTime;
	private Button btnSearchName;
	private Button btnSearchTheTag;

	private Button btnNgayTK;
	private TextView tv_ngayTK;
	Calendar cal;
	Date dateFinish;

	public Intent intent;
	public Bundle bundle;

	String date;

	public long id_account, id_ngansach, id_thetag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timkiem_layout);

		initialize();
	}

	public void initialize() {
		tv_ngayTK = (TextView) findViewById(R.id.ngaytimkiem);
		btnNgayTK = (Button) findViewById(R.id.btnNgayTK);
		btnSearchName = (Button) findViewById(R.id.btnNameSearch);
		btnSearchTime = (Button) findViewById(R.id.btnTimeSearch);
		btnSearchTheTag = (Button) findViewById(R.id.btnSearchTheTag);

		btnNgayTK.setOnClickListener(this);
		btnSearchName.setOnClickListener(this);
		btnSearchTime.setOnClickListener(this);
		btnSearchTheTag.setOnClickListener(this);

		db = new DatabaseAdapter(this);
		db.open();

		id_account = getIdAccount();

		spnTheTag = (Spinner) findViewById(R.id.searchThetag);
		spnTag = (Spinner) findViewById(R.id.spn_search_name);

		loadSpinnerNganSach();
		loadSpinnerData();

		getDefaultInfor();

		db.close();
	}

	// Load spinner ngan sach tu database
	public void loadSpinnerNganSach() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		long id = bundle.getLong("id");

		cursor = db.fetchNganSachTheoIdTaiKhoan(id);
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
		spnTag.setAdapter(dataAdapter);
	}

	// Load spinner loai tu database
	private void loadSpinnerData() {
		// Spinner Drop down elements
		Cursor cursor = db.fetchLoai();
		startManagingCursor(cursor);
		ArrayList<String> labels = new ArrayList<String>();
		if (cursor.moveToFirst()) {
			do {
				if (!cursor.getString(0).toString().equals("Tạo loại khác")) {
					labels.add(cursor.getString(0));
				}

			} while (cursor.moveToNext());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, labels);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnTheTag.setAdapter(dataAdapter);
	}

	// Dat mac dinh giao dien la ngay hien tai
	public void getDefaultInfor() {
		// Lay ngay hien tai cua thang
		cal = Calendar.getInstance();
		SimpleDateFormat dft = null;

		// Dinh dang ngay thang nam
		dft = new SimpleDateFormat("d/MM/yyyy", Locale.getDefault());
		String strDate = dft.format(cal.getTime());

		// Hien thi ngay hien tai
		tv_ngayTK.setText(strDate);
		dateFinish = cal.getTime();
	}

	/**
	 * Ham hien thi DatePicker dialog
	 */
	public void showDatePickerDialog() {
		OnDateSetListener callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {

				tv_ngayTK.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/"
						+ year);

				cal.set(year, monthOfYear, dayOfMonth);
				dateFinish = cal.getTime();
			}
		};

		String s = tv_ngayTK.getText() + "";
		String strArrtmp[] = s.split("/");
		int ngay = Integer.parseInt(strArrtmp[0]);
		int thang = Integer.parseInt(strArrtmp[1]) - 1;
		int nam = Integer.parseInt(strArrtmp[2]);
		DatePickerDialog pic = new DatePickerDialog(TimkiemActivity.this,
				callback, nam, thang, ngay);
		pic.setTitle("Chọn ngày hoàn thành");
		pic.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnNgayTK:
			showDatePickerDialog();
			break;
		case R.id.btnTimeSearch:
			intent = new Intent(this, DisplayTimeSearch.class);
			date = tv_ngayTK.getText().toString();
			bundle.putLong("id", id_account);
			bundle.putString("Ngay", date);
			intent.putExtras(bundle);
			startActivityForResult(intent, 100);

			break;

		case R.id.btnNameSearch:
			intent = new Intent(this, DisplaySearchName.class);
			id_ngansach = db.getIdNganSach(spnTag.getSelectedItem().toString());
			bundle.putLong("id", id_account);
			bundle.putInt("id_ngansach", (int) id_ngansach);
			intent.putExtras(bundle);
			startActivityForResult(intent, 100);

			break;

		case R.id.btnSearchTheTag:
			intent = new Intent(this, DisplaySearchTheTag.class);
			id_thetag = db.getIdTheTag(spnTheTag.getSelectedItem().toString());
			bundle.putLong("id", id_account);
			bundle.putInt("id_thetag", (int) id_thetag);
			intent.putExtras(bundle);
			startActivityForResult(intent, 100);

			break;
		}

	}

	public long getIdAccount() {
		intent = getIntent();
		bundle = intent.getExtras();
		long id_account = bundle.getLong("id");

		return id_account;
	}

}
