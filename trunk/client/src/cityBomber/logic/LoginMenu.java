package cityBomber.logic;

import org.json.JSONException;
import org.json.JSONObject;

import cityBomber.network.Communication;
import Model.Session;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class LoginMenu extends Activity{
	
	private String bomb = "bomb.png";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.loginmenu);    
		
		ImageView logo = (ImageView) findViewById(R.id.logo);
		try {

			Bitmap bitmap = BitmapFactory.decodeStream(LoginMenu.this.getResources().getAssets().open(bomb));
			logo.setImageBitmap(bitmap);
		} catch (Exception e) {

			e.printStackTrace();

		}
		
		
		Button login_btn = (Button) findViewById(R.id.login_btn);
		Language.setButtonWord(Session.getLang().get("Login"), "Login", login_btn);
		login_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), Login.class);
				startActivityForResult(myIntent, 0);
			}

		});
		
		
		Button register_btn = (Button) findViewById(R.id.register_btn);
		Language.setButtonWord(Session.getLang().get("Register"), "Register", register_btn);
		register_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), Register.class);
				startActivityForResult(myIntent, 0);
			}

		});
		
		Button guest_btn = (Button) findViewById(R.id.guest_btn);
		Language.setButtonWord(Session.getLang().get("Guest"), "Guest", guest_btn);
		guest_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				Intent myIntent = new Intent(getApplicationContext(), Servers.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		Button back_btn = (Button) findViewById(R.id.main_menu_btn);
		Language.setButtonWord(Session.getLang().get("Back"), "Back", back_btn);
		back_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		
		
	}

}
