package cityBomber.logic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register  extends Activity{

	private EditText password_txt;
	private EditText username_txt;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.register);    



		TextView user_lbl = (TextView) findViewById(R.id.user_lbl);
		Language.setTextViewWord(Session.getLang().get("Username"), "Username:", user_lbl, "");


		username_txt = (EditText) findViewById(R.id.user_txt);


		TextView password_lbl = (TextView) findViewById(R.id.password_lbl);
		Language.setTextViewWord(Session.getLang().get("Password"), "Password: ", password_lbl, "");

		password_txt = (EditText) findViewById(R.id.password_txt);


		Button register_btn = (Button) findViewById(R.id.register_btn);
		Language.setButtonWord(Session.getLang().get("Register"), "Register", register_btn);
		register_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

			}
		});

		Button back = (Button) findViewById(R.id.back_btn);
		Language.setButtonWord(Session.getLang().get("Back"), "Back", back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}

		});

	}
}

