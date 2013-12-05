package hut.edu.qlct.controller;

import hut.edu.qlct.model.DatabaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GiaodichActivity extends Activity implements OnClickListener {

	//
	private int id_ngansach;

	private int id_thuchi;
	private int id_thetag;
	private String tengiaodich;
	private int soluong;
	private int giatien;
	private String date;

	private EditText ed_tengiaodich;
	private EditText ed_soluong;
	private EditText ed_giatien;
	private Spinner spnLoaiThuChi;
	private Spinner spnTheTag;
	private Spinner spnNganSach;
	private TextView tvNgay;

	private Button btnNgay;
	private Button btnSave;
	private Button btnCancel;

	private static final int DIALOG_THEM_GIAO_DICH = 0;

	DatabaseAdapter db;

	Calendar cal;
	Date dateFinish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.giaodich_activity);

		initialize();
		getDefaultInfor();
	}

	public void initialize() {
		ed_tengiaodich = (EditText) findViewById(R.id.ed_tengiaodich);
		ed_soluong = (EditText) findViewById(R.id.ed_soluong);
		ed_giatien = (EditText) findViewById(R.id.ed_giatien);
		spnLoaiThuChi = (Spinner) findViewById(R.id.spnLoaiThuChi);
		spnTheTag = (Spinner) findViewById(R.id.spn_thetag);
		spnNganSach = (Spinner) findViewById(R.id.spn_ngansach);

		btnNgay = (Button) findViewById(R.id.btnNgay);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		tvNgay = (TextView) findViewById(R.id.ngaybd);

		btnNgay.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		db = new DatabaseAdapter(this);
		db.open();

		loadSpinnerData();
		loadSpinnerNganSach();

		spnTheTag.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (spnTheTag.getSelectedItem().toString()
						.equals("Tạo loại khác")) {
					showDialog(DIALOG_THEM_GIAO_DICH);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		db.close();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnNgay:
			showDatePickerDialog();
			break;
		case R.id.btnSave:
			// Xac dinh id thu chi
			db = new DatabaseAdapter(this);
			db.open();

			if (ed_tengiaodich.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(), "Tên giao dịch trống",
						Toast.LENGTH_SHORT).show();
			} else if (ed_soluong.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(),
						"Trường số lượng trống", Toast.LENGTH_SHORT).show();
			} else if (ed_giatien.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(),
						"Trường giá tiền trống", Toast.LENGTH_SHORT).show();
			} else {
				tengiaodich = ed_tengiaodich.getText().toString();
				giatien = Integer.parseInt(ed_giatien.getText().toString());
				soluong = Integer.parseInt(ed_soluong.getText().toString());
				id_thetag = db.getIdTheTag(spnTheTag.getSelectedItem()
						.toString());
				id_ngansach = db.getIdNganSach(spnNganSach.getSelectedItem()
						.toString());
				date = tvNgay.getText().toString();

				if (spnLoaiThuChi.getSelectedItem().toString().equals("Thu")) {
					id_thuchi = 1;
				} else {
					id_thuchi = 0;
				}

				Intent intent = getIntent();
				Bundle bundle = intent.getExtras();
				long id = bundle.getLong("id");

				db.createGiaoDich((int) id, id_ngansach, id_thuchi, id_thetag,
						tengiaodich, soluong, giatien, date);
				Toast.makeText(getApplicationContext(),
						"Giao dịch tạo thành công", Toast.LENGTH_SHORT).show();

			}
			db.close();

			break;

		default:
			break;
		}
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
		tvNgay.setText(strDate);
		dateFinish = cal.getTime();
	}

	/**
	 * Ham hien thi DatePicker dialog
	 */
	public void showDatePickerDialog() {
		OnDateSetListener callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {

				tvNgay.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/"
						+ year);

				cal.set(year, monthOfYear, dayOfMonth);
				dateFinish = cal.getTime();
			}
		};

		String s = tvNgay.getText() + "";
		String strArrtmp[] = s.split("/");
		int ngay = Integer.parseInt(strArrtmp[0]);
		int thang = Integer.parseInt(strArrtmp[1]) - 1;
		int nam = Integer.parseInt(strArrtmp[2]);
		DatePickerDialog pic = new DatePickerDialog(GiaodichActivity.this,
				callback, nam, thang, ngay);
		pic.setTitle("Chọn ngày hoàn thành");
		pic.show();
	}

	// Load spinner loai tu database
	private void loadSpinnerData() {
		// Spinner Drop down elements
		Cursor cursor = db.fetchLoai();
		startManagingCursor(cursor);
		ArrayList<String> labels = new ArrayList<String>();
		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, labels);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnTheTag.setAdapter(dataAdapter);
	}

	// Load spinner ngan sach tu database
	public void loadSpinnerNganSach() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		long id = bundle.getLong("id");

		Cursor cursor = db.fetchNganSachTheoIdTaiKhoan(id);
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

	// Dialog them the tag
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_THEM_GIAO_DICH:
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(R.layout.dialog_taoloai,
					null);
			final EditText txTenLoaiAdd = (EditText) textEntryView
					.findViewById(R.id.tenLoai_edit);
			return new AlertDialog.Builder(GiaodichActivity.this)

					.setTitle("Tạo thẻ tag mới")
					.setView(textEntryView)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									db.createLoai(txTenLoaiAdd.getText()
											.toString());
									loadSpinnerData();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									closeContextMenu();

								}
							}).create();
		}
		return null;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.giaodich_menu, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // TODO Auto-generated method stub
	// switch (item.getItemId()) {
	// case R.id.displayGD:
	// Intent intent = getIntent();
	// Bundle bundle = intent.getExtras();
	// long id = bundle.getLong("id");
	//
	// Intent display = new Intent(this, DisplayGiaodich.class);
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
