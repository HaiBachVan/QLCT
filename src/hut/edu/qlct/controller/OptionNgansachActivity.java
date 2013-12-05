package hut.edu.qlct.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OptionNgansachActivity extends Activity implements OnClickListener {

	public Button add_ngansach;
	public Button display_ngansach;
	public Button delete_ngansach;
	public Button update_ngansach;

	public Bundle bundle;
	public Intent intent;
	public long id_account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_ngansach);

		initialize();
	}

	public void initialize() {
		add_ngansach = (Button) findViewById(R.id.add_ngansach);
		delete_ngansach = (Button) findViewById(R.id.delete_ngansach);
		update_ngansach = (Button) findViewById(R.id.update_ngansach);
		display_ngansach = (Button) findViewById(R.id.display_ngansach);

		add_ngansach.setOnClickListener(this);
		display_ngansach.setOnClickListener(this);
		update_ngansach.setOnClickListener(this);
		delete_ngansach.setOnClickListener(this);

		id_account = getIdAccount();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_ngansach:
			intent = new Intent(OptionNgansachActivity.this,
					NganSachActivity.class);
			intentIdAccount(id_account);
			break;

		case R.id.display_ngansach:
			intent = new Intent(this, DisplayNganSach.class);
			intentIdAccount(id_account);

			break;

		case R.id.delete_ngansach:
			intent = new Intent(this, DeleteNganSach.class);
			intentIdAccount(id_account);
			break;

		case R.id.update_ngansach:
			intent = new Intent(this, UpdateNgansach.class);
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
