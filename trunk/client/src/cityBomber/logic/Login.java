package cityBomber.logic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

import cityBomber.network.Communication;
import cityBomber.network.ServersInfo;

import com.facebook.android.Facebook;
import com.google.android.maps.GeoPoint;

import Model.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity{

	private EditText password_txt;
	private EditText username_txt;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.login);    



		TextView user_lbl = (TextView) findViewById(R.id.user_lbl);
		Language.setTextViewWord(Session.getLang().get("Email"), "Email:", user_lbl, "");


		username_txt = (EditText) findViewById(R.id.user_txt);


		TextView password_lbl = (TextView) findViewById(R.id.password_lbl);
		Language.setTextViewWord(Session.getLang().get("Password"), "Password: ", password_lbl, "");

		password_txt = (EditText) findViewById(R.id.password_txt);


		Button facelogin_btn = (Button) findViewById(R.id.facelogin_btn);

		Language.setButtonWord(Session.getLang().get("faceLogin"), "Login using facebook", facelogin_btn);
		facelogin_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {		

				
				Intent intent=new Intent(Login.this,FacebookLoginA.class);
				Login.this.startActivityForResult(intent, 0);

			}
		});			

		Button login_btn = (Button) findViewById(R.id.login_btn);
		Language.setButtonWord(Session.getLang().get("Login"), "Login", login_btn);
		login_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				new CommunicationAsync().execute();

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

	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
		super.onActivityResult(requestCode, resultCode, data); 
		if(requestCode==0)
		{
			if (resultCode == Activity.RESULT_OK) 
			{ 
				new CommunicationAsyncFace().execute();
				
				/*mpointc=new GeoPoint((int)data.getIntExtra("pontocla", 0),(int)data.getIntExtra("pontoclo", 0));
				mpointr=new GeoPoint((int)data.getIntExtra("pontorla", 0),(int)data.getIntExtra("pontorlo",0));
				sendnewsession();*/

				// System.out.println("xi: "+ mpointc.getLatitudeE6() + "yi: " + mpointc.getLongitudeE6() + "xf: " + mpointr.getLatitudeE6() + "yf: "+ mpointr.getLongitudeE6());
				// TODO Switch tabs using the index.
			}
		}else if(requestCode==1)
		{
			Session.setUsername(null);
			Session.setUserId(null);
		}
	}



	private class CommunicationAsync extends  AsyncTask<Void, Void, Void>
	{
		private ProgressDialog dialg;
		Communication registerResponse = null;
		@Override
		protected void onPreExecute() {
			dialg = new ProgressDialog(Login.this);
			dialg.setMessage(Language.getTranslation(Session.getLang().get("LogginMsg"), "Logging in. Please wait..."));
			dialg.setCancelable(false);
			dialg.show();			
		}
		@Override
		protected void onPostExecute(Void unused) {			
			dialg.dismiss();
			try {
				String response = registerResponse.getServerResponse();
				JSONObject json = new JSONObject(response);
				if(Controller.isError(json))
				{
					String error = json.getString("error");
					AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();  
					alertDialog.setTitle("Erro"); 
					alertDialog.setIcon(R.drawable.error);
					alertDialog.setMessage(error);  
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
						public void onClick(DialogInterface dialog, int which) {  
						} }); 

					alertDialog.show();
				}
				else
				{
					Controller.login(response);
					AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();  
					alertDialog.setTitle("Success"); 
					alertDialog.setMessage("Login efectuado com sucesso.");  
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
						public void onClick(DialogInterface dialog, int which) {  
							Intent intent=new Intent(Login.this,Servers.class);
							Login.this.startActivityForResult(intent,1);

						} }); 

					alertDialog.show();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub


			try {
				registerResponse = new Communication(ServersInfo.getLogin() + "?email="+username_txt.getText().toString() + "&pw=" + SHA512(password_txt.getText().toString()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

	}

	private class CommunicationAsyncFace extends  AsyncTask<Void, Void, Void>
	{
		private ProgressDialog dialg;
		Communication registerResponse = null;
		@Override
		protected void onPreExecute() {
			dialg = new ProgressDialog(Login.this);
			dialg.setMessage(Language.getTranslation(Session.getLang().get("LogginMsg"), "Logging in. Please wait..."));
			dialg.setCancelable(false);
			dialg.show();			
		}
		@Override
		protected void onPostExecute(Void unused) {			
			dialg.dismiss();
			try {
				String response = registerResponse.getServerResponse();
				JSONObject json = new JSONObject(response);
				if(Controller.isError(json))
				{
					String error = json.getString("error");
					AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();  
					alertDialog.setTitle("Erro"); 
					alertDialog.setIcon(R.drawable.error);
					alertDialog.setMessage(error);  
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
						public void onClick(DialogInterface dialog, int which) {  
						} }); 

					alertDialog.show();
				}
				else
				{
					Intent intent=new Intent(Login.this,Servers.class);
					Login.this.startActivityForResult(intent,1);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				registerResponse = new Communication(ServersInfo.getLogin() + "?fbid="+Session.getUserId() + "&email=" + Session.getEmail() + "&name=" + Session.getUsername());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

	}

	private static String convertToHex(byte[] data) { 
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) { 
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do { 
				if ((0 <= halfbyte) && (halfbyte <= 9)) 
					buf.append((char) ('0' + halfbyte));
				else 
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		} 
		return buf.toString();
	} 

	public static String SHA512(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] sha512hash = new byte[128];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha512hash = md.digest();
		return convertToHex(sha512hash);
	}
}
