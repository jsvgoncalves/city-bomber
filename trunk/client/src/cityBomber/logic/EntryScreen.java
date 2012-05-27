package cityBomber.logic;

import Model.Session;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EntryScreen extends Activity{

	private String bomb = "bomb.png";
	private String languages = "languages.txt";
	private String configs = "preferences.cfg";
	private Button play_btn, options_btn, exit_btn;


	public void onCreate(Bundle savedInstanceState) {
		//Toast.makeText(EntryScreen.this, "HERE1", 5).show();
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = getSharedPreferences(configs, 0);
		boolean sound = settings.getBoolean("SOUND", true);
		Session.setSound(sound);
		
		boolean animations = settings.getBoolean("ANIMATIONS", true);
		Session.setAnimations(animations);
		
		boolean satellite = settings.getBoolean("SATELLITEIMG", true);
		Session.setSatellite(satellite);
		
		String language = settings.getString("LANGUAGE", "English");
		Session.setLanguage(language);
		try {
			FileReader.LoadLanguages(this.getAssets().open(languages));
			if(!Session.getLanguage().equals("English"))
			{
				FileReader.SwitchLanguage(this.getAssets().open(Session.getFilePath(Session.getLanguage())));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		setContentView(R.layout.main);    
		init();		

	}
	public void init()
	{
		ImageView logo = (ImageView) findViewById(R.id.logo);
		try {

			Bitmap bitmap = BitmapFactory.decodeStream(EntryScreen.this.getResources().getAssets().open(bomb));
			logo.setImageBitmap(bitmap);
		} catch (Exception e) {

			e.printStackTrace();

		}
		play_btn = (Button) findViewById(R.id.play_btn);
		Language.setButtonWord(Session.getLang().get("Play"), "Play", play_btn);
		play_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), LoginMenu.class);
				startActivityForResult(myIntent, 0);
			}

		});

		options_btn = (Button) findViewById(R.id.options_btn);
		Language.setButtonWord(Session.getLang().get("Options"), "Options", options_btn);
		options_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), Options.class);
				startActivityForResult(myIntent, 0);
			}

		});

		exit_btn = (Button) findViewById(R.id.exit_btn);
		Language.setButtonWord(Session.getLang().get("Exit"), "Exit", exit_btn);
		exit_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				System.exit(0);
			}

		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{  // After a pause OR at startup	
		setContentView(R.layout.main);  
		init();
		//Refresh your stuff here
	}
}
