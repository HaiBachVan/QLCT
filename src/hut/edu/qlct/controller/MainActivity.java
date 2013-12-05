package hut.edu.qlct.controller;

import hut.edu.qlct.model.DatabaseAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	// Button login, register
	private Button btn_login;
	private Button btn_register;

	// EditText username, password
	private EditText usernameLogin;
	private EditText passwordLogin;

	//
	DatabaseAdapter loginDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();
	}

	public void initialize() {
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);

		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			usernameLogin = (EditText) findViewById(R.id.ed_usernameLogin);
			passwordLogin = (EditText) findViewById(R.id.ed_passwordLogin);

			String s_username = usernameLogin.getText().toString();
			String s_password = passwordLogin.getText().toString();

			loginDatabase = new DatabaseAdapter(this);
			loginDatabase.open();
			String storePass = loginDatabase.getSinlgeEntry(s_username);
			if (s_password.equals(storePass)) {
				Toast.makeText(getApplicationContext(),
						"Xin chao " + s_username, Toast.LENGTH_LONG).show();

				long id = loginDatabase.getIdAccount(s_username);
				Log.i("ID ACCOUNT = ", id + "id");
				Intent options_activity = new Intent(this,
						OptionsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putLong("id", id);
				options_activity.putExtras(bundle);
				startActivityForResult(options_activity, 100);

			} else {
				Toast.makeText(getApplicationContext(),
						"Tài khoản không tồn tại", Toast.LENGTH_LONG).show();
			}

			break;

		case R.id.btn_register:
			Intent register = new Intent(this, RegisterActivity.class);
			startActivity(register);
			break;
		}
	}
}
