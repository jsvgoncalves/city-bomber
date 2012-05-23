package cityBomber.logic;

import java.util.Vector;

import cityBomber.network.Communication;

import Model.ServerRecord;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SessionCreate extends Activity{

	private Spinner typespinner, maxspinner;
	private TextView password_lbl;
	private EditText password_txt;
	private EditText sessionname_txt;
	private Communication serversinfo ;
	private boolean b= true;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.create_session);


		TextView title = (TextView) findViewById(R.id.SessionCreate_lbl);
		Language.setTextViewWord(Session.getLang().get("Session Creation"), "Session Creation", title, "");

		TextView lblsession = (TextView) findViewById(R.id.SessionName_lbl);
		Language.setTextViewWord(Session.getLang().get("Session Name"), "Session Name: ", lblsession, "");

		sessionname_txt = (EditText) findViewById(R.id.SessionName_txt);

		TextView lblmax = (TextView) findViewById(R.id.maxPlayers_lbl);
		Language.setTextViewWord(Session.getLang().get("Max Players"), "Max Players: ", lblmax, "");

		TextView SessionType_lbl = (TextView) findViewById(R.id.SessionType_lbl);
		Language.setTextViewWord(Session.getLang().get("SessionType"), "Session Type: ", SessionType_lbl, "");

		password_lbl = (TextView) findViewById(R.id.Password_lbl);
		Language.setTextViewWord(Session.getLang().get("Password"), "Password: ", password_lbl, "");
		password_lbl.setVisibility(View.INVISIBLE);

		password_txt = (EditText) findViewById(R.id.password_txt);
		password_txt.setVisibility(View.INVISIBLE);


		initializeSpinnerValues();
		Button create = (Button) findViewById(R.id.submit_btn);
		Language.setButtonWord(Session.getLang().get("Create"), "Create", create);
		create.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(sessionname_txt.getText().length() == 0)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(SessionCreate.this);
					builder.setMessage(Language.getTranslation(Session.getLang().get("SessionNameErr"), "Please specify a session name."));
					builder.setCancelable(false);
					builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
					builder.setIcon(R.drawable.ic_launcher);       
					AlertDialog alert = builder.create();
					alert.show();
				}
				else
				{
					new CommunicationAsync().execute();						
				}


			}
		});

		Button back = (Button) findViewById(R.id.back_btn2);
		Language.setButtonWord(Session.getLang().get("Back"), "Back", back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}

		});
	}


	public void initializeSpinnerValues()
	{
		Vector<Integer> values = new Vector<Integer>();
		for(int i = 2; i <= 24; i++)
		{
			values.add(i);
		}
		maxspinner = (Spinner) findViewById(R.id.maxPlayers);
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, values);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		maxspinner.setAdapter(adapter);

		String[] sessiontype = {Language.getTranslation(Session.getLang().get("Public"), "Public"),Language.getTranslation(Session.getLang().get("Private"), "Private")};
		typespinner = (Spinner) findViewById(R.id.sessiontype);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, sessiontype);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typespinner.setAdapter(adapter2);

		typespinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// your code here
				if(position == 1)
				{
					b=true;
					password_lbl.setVisibility(View.VISIBLE);
					password_txt.setVisibility(View.VISIBLE);
				}
				else
				{
					b=false;
					password_lbl.setVisibility(View.INVISIBLE);
					password_txt.setVisibility(View.INVISIBLE);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
	}

	private class CommunicationAsync extends  AsyncTask<Void, Void, Void>
	{
		String response = "";
		private ProgressDialog dialg;
		@Override
		protected void onPreExecute() {
			dialg = new ProgressDialog(SessionCreate.this);
			dialg.setMessage(Language.getTranslation(Session.getLang().get("SessionCreateMsg"), "Creating Session. Please wait..."));
			dialg.setCancelable(false);
			dialg.show();			
		}
		@Override
		protected void onPostExecute(Void unused) {			
			dialg.dismiss();
			if(!Controller.isError())
			{
				Intent myIntent = new Intent(getApplicationContext(), Game.class);
				startActivityForResult(myIntent, 0);
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(SessionCreate.this);
				builder.setMessage(Language.getTranslation(Session.getLang().get(""), "An error Ocurred"));
				builder.setCancelable(false);
				builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				builder.setIcon(R.drawable.ic_launcher);       
				AlertDialog alert = builder.create();
				alert.show();
			}

		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServerRecord s = Session.getServer();
			String text = "http://" + s.getIp() + ":" + s.getPort() + "/bomberman/gameserver/newsession.php?name=" + sessionname_txt.getText() + "&max=" + maxspinner.getSelectedItem().toString() + "&userid=" + 1 + "&private=" + b + "&password=" + password_txt.getText();
			serversinfo = new Communication(text);		
			response = Controller.getSessionCreateResponse(serversinfo.getServerResponse());

			return null;
		}

	}

}
