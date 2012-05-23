package cityBomber.logic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class EntryScreen extends Activity{

	private String bomb = "bomb.png";
	private String language = "PT-PT.lang";
	public void onCreate(Bundle savedInstanceState) {
		//Toast.makeText(EntryScreen.this, "HERE1", 5).show();
		InputStream in;
		try {
			in = this.getAssets().open(language);
			if(in != null) 
			{
				String line = "";
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);
				while((line = buffreader.readLine()) != null)
				{
					String[] s = line.split("=");
					Session.addLanguageElement(s[0], s[1]);
				}
				in.close(); 
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     		
		//Toast.makeText(EntryScreen.this, "HERE3", 5);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);    
		
		ImageView logo = (ImageView) findViewById(R.id.logo);
		try {

			Bitmap bitmap = BitmapFactory.decodeStream(EntryScreen.this.getResources().getAssets().open(bomb));
			logo.setImageBitmap(bitmap);
		} catch (Exception e) {

			e.printStackTrace();

		}
		Button play_btn = (Button) findViewById(R.id.play_btn);
		Language.setButtonWord(Session.getLang().get("Play"), "Play", play_btn);
		play_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), LoginMenu.class);
				startActivityForResult(myIntent, 0);
			}

		});
		
		Button options_btn = (Button) findViewById(R.id.options_btn);
		Language.setButtonWord(Session.getLang().get("Options"), "Options", options_btn);
		options_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
			}

		});
		
		Button exit_btn = (Button) findViewById(R.id.exit_btn);
		Language.setButtonWord(Session.getLang().get("Exit"), "Exit", exit_btn);
		exit_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				System.exit(0);
			}

		});
		
	}

}
