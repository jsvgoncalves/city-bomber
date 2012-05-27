package cityBomber.logic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import cityBomber.network.Communication;
import cityBomber.network.ServersInfo;
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

public class Register  extends Activity{

	private EditText password_txt;
	private EditText username_txt;
	private EditText email_txt;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.register);    

		TextView email_lbl = (TextView) findViewById(R.id.email_lbl);
		Language.setTextViewWord(Session.getLang().get("email"), "Email:", email_lbl, "");


		email_txt = (EditText) findViewById(R.id.email_txt);

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
	private class CommunicationAsync extends  AsyncTask<Void, Void, Void>
	{
		Communication registerResponse = null;
		private ProgressDialog dialg;
		@Override
		protected void onPreExecute() {
			dialg = new ProgressDialog(Register.this);
			dialg.setMessage(Language.getTranslation(Session.getLang().get("ServersRegstrMsg"), "Registering. Please wait..."));
			dialg.setCancelable(false);
			dialg.show();			
		}
		@Override
		protected void onPostExecute(Void unused) {			
			dialg.dismiss();
			try {
				JSONObject json = new JSONObject(registerResponse.getServerResponse());
				if(Controller.isError(json))
				{
					String error = json.getString("error");
					AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();  
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
					AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();  
					alertDialog.setTitle("Success"); 
					alertDialog.setMessage("Registration completed successfully");  
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
						public void onClick(DialogInterface dialog, int which) {  
							finish();
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
			//http://paginas.fe.up.pt/~ei09136/bomber/auth/api/registeruser.php?username=Joao&pw=1&email=joao@joao.pt
			
			
			try {
				registerResponse = new Communication(ServersInfo.getRegister() + "?username="+username_txt.getText().toString() + "&pw=" + SHA512(password_txt.getText().toString()) + "&email="+email_txt.getText().toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return null;
		}

	}
}

