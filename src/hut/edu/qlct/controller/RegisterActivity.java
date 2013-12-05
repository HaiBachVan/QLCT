package hut.edu.qlct.controller;

import hut.edu.qlct.model.DatabaseAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	// EditText username, password, confirm password
	private EditText usernameResgister;
	private EditText passwordRegister;
	private EditText confirmPassword;

	// Button Register
	private Button btn_registerRes;

	//
	DatabaseAdapter registerDatabase;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		initialize();
	}

	public void initialize() {
		registerDatabase = new DatabaseAdapter(this);
		btn_registerRes = (Button) findViewById(R.id.registerRes);
		btn_registerRes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				usernameResgister = (EditText) findViewById(R.id.usernameRegister);
				passwordRegister = (EditText) findViewById(R.id.passwordRegister);
				confirmPassword = (EditText) findViewById(R.id.confirm_passRegister);

				String s_username = usernameResgister.getText().toString();
				String s_password = passwordRegister.getText().toString();
				String s_confirm_pass = confirmPassword.getText().toString();
				registerDatabase.open();

				if (s_username.equals("") || s_password.equals("")
						|| s_confirm_pass.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Có trường để trống", Toast.LENGTH_LONG).show();
				} else if (s_username.length() < 4) {
					Toast.makeText(getApplicationContext(),
							"Bạn để tên đăng nhập quá ngắn(phải >= 4)",
							Toast.LENGTH_LONG).show();
				} else if (registerDatabase.compareUserName(s_username)) {

					Toast.makeText(getApplicationContext(),
							"Username bạn sử dụng đã tồn tại",
							Toast.LENGTH_LONG).show();
				} else if (s_password.length() < 6) {
					Toast.makeText(getApplicationContext(),
							"Độ dài mật khẩu của bạn phải >= 6",
							Toast.LENGTH_LONG).show();
				} else if (!s_password.equals(s_confirm_pass)) {
					Toast.makeText(getApplicationContext(),
							"Confirm password không đúng", Toast.LENGTH_SHORT)
							.show();
					return;
				} else {
					registerDatabase.insertEntry(s_username, s_password);
					Toast.makeText(getApplicationContext(),
							"Tài khoản đã được tạo thành công",
							Toast.LENGTH_SHORT).show();
				}

				registerDatabase.close();
			}
		});
	}
}
