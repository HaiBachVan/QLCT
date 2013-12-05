package hut.edu.qlct.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OptionsActivity extends Activity implements OnClickListener {

	private Button btn_ngansach;
	private Button btn_giaodich;
	private Button btn_thongke;
	private Button btn_timkiem;
	private Button btn_thoat;

	Bundle bundle;
	Intent intent;
	long id_account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options_activity);

		initialize();
	}

	public void initialize() {
		btn_ngansach = (Button) findViewById(R.id.create_ngansach);
		btn_giaodich = (Button) findViewById(R.id.create_giaodich);
		btn_thongke = (Button) findViewById(R.id.btn_thongke);
		btn_timkiem = (Button) findViewById(R.id.btn_timkiem);
		btn_thoat = (Button) findViewById(R.id.btn_thoat);

		btn_ngansach.setOnClickListener(this);
		btn_giaodich.setOnClickListener(this);
		btn_thongke.setOnClickListener(this);
		btn_timkiem.setOnClickListener(this);
		btn_thoat.setOnClickListener(this);

		id_account = getIdAccount();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.create_ngansach:

			intent = new Intent(this, OptionNgansachActivity.class);
			intentIdAccount(id_account);
			break;
		case R.id.create_giaodich:

			intent = new Intent(this, OptionGiaoDichActivity.class);
			intentIdAccount(id_account);
			break;

		case R.id.btn_thongke:

			intent = new Intent(this, ThongkeActivity.class);
			intentIdAccount(id_account);
			break;
		case R.id.btn_timkiem:

			intent = new Intent(this, TimkiemActivity.class);
			intentIdAccount(id_account);
			break;

		case R.id.btn_thoat:
			finish();
			break;
		}
	}

	public long getIdAccount() {
		intent = getIntent();
		bundle = intent.getExtras();
		id_account = bundle.getLong("id");

		return id_account;
	}

	public void intentIdAccount(long id_account) {
		bundle.putLong("id", id_account);
		intent.putExtras(bundle);
		startActivityForResult(intent, 100);
	}
}
