package hut.edu.qlct.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OptionGiaoDichActivity extends Activity implements OnClickListener {

	public Button add_giaodich;
	public Button display_giaodich;
	public Button delete_giaodich;
	public Button update_giaodich;

	public Bundle bundle;
	public Intent intent;

	public long id_account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_giaodich);

		initialize();
	}

	public void initialize() {
		add_giaodich = (Button) findViewById(R.id.add_giaodich);
		delete_giaodich = (Button) findViewById(R.id.delete_giaodich);
		update_giaodich = (Button) findViewById(R.id.update_giaodich);
		display_giaodich = (Button) findViewById(R.id.display_giaodich);

		add_giaodich.setOnClickListener(this);
		display_giaodich.setOnClickListener(this);
		update_giaodich.setOnClickListener(this);
		delete_giaodich.setOnClickListener(this);

		id_account = getIdAccount();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_giaodich:
			intent = new Intent(OptionGiaoDichActivity.this,
					GiaodichActivity.class);
			intentIdAccount(id_account);
			break;

		case R.id.display_giaodich:
			intent = new Intent(OptionGiaoDichActivity.this,
					DisplayGiaodich.class);
			intentIdAccount(id_account);
			break;

		case R.id.delete_giaodich:
			intent = new Intent(this, DeleteGiaoDich.class);
			intentIdAccount(id_account);
			break;

		case R.id.update_giaodich:
			intent = new Intent(OptionGiaoDichActivity.this,
					UpdateGiaodich.class);
			intentIdAccount(id_account);
			break;
		}
	}

	public long getIdAccount() {
		intent = getIntent();
		bundle = intent.getExtras();
		long id_account = bundle.getLong("id");

		return id_account;
	}

	public void intentIdAccount(long id_account) {
		bundle.putLong("id", id_account);
		intent.putExtras(bundle);
		startActivityForResult(intent, 100);
	}
}
