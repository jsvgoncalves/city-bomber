package cityBomber.logic;

import java.util.Vector;

import com.google.android.maps.GeoPoint;

import cityBomber.network.Communication;

import Model.ServerRecord;
import Model.Session;
import Model.SessionRecord;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
	private GeoPoint mpointc;
	private GeoPoint mpointr;
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

	}

	/*	@Override
	public void onStop()
	{
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		intent.putExtra("estado",0);
		finish();
	}*/


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Create and add new menu items.
		MenuItem itemCreateSess = menu.add(Language.getTranslation(Session.getLang().get("Criar"), "Criar"));
		MenuItem itemBack = menu.add(Language.getTranslation(Session.getLang().get("Back"), "Back"));


		itemBack.setIcon(R.drawable.back);
		itemBack.setShortcut('1', 'r');
		itemBack.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				intent.putExtra("estado",0);
				finish();
				return true;
			}

		});

		itemCreateSess.setIcon(R.drawable.add);
		itemCreateSess.setShortcut('1', 'r');
		itemCreateSess.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if(sessionname_txt.getText().length() == 0)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(SessionCreate.this);
					builder.setTitle(Language.getTranslation(Session.getLang().get("Warning"), "Warning"));
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
					Intent intent=new Intent(SessionCreate.this,MapGame.class);
					intent.putExtra("estado",1);
					intent.putExtra("issatellite", Session.isSatellite());
					intent.putExtra("isanimating", Session.isAnimations());
					SessionCreate.this.startActivityForResult(intent,1);
				}
				return true;
				// TODO Auto-generated method stub

			}

		});

		return true;
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
				Intent resultIntent = new Intent();
				resultIntent.putExtra("estado",1);
				resultIntent.putExtra("pontocla", (int)mpointc.getLatitudeE6());
				resultIntent.putExtra("pontoclo", (int)mpointc.getLongitudeE6());
				resultIntent.putExtra("pontorla", (int)mpointr.getLatitudeE6());
				resultIntent.putExtra("pontorlo", (int)mpointr.getLongitudeE6());
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
			else
			{
				System.out.println("ERRROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

				AlertDialog.Builder builder = new AlertDialog.Builder(SessionCreate.this);
				builder.setMessage(Language.getTranslation(Session.getLang().get(""), response));
				builder.setTitle(Language.getTranslation(Session.getLang().get("Error"), "Error"));
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
			System.out.println("esta a enviar get");
			String text = "";
			if(password_txt.getText().toString().length()!=0)			
				text = "http://" + s.getIp() + ":" + s.getPort() + "/createsession?name=" + sessionname_txt.getText() + "&max=" + maxspinner.getSelectedItem().toString() + "&userid=" + Session.getUserId() + "&private=" + b + "&password=" + password_txt.getText() + "&lat=" + mpointc.getLatitudeE6() + "&lon=" + mpointc.getLongitudeE6() + "&rlat=" + mpointr.getLatitudeE6() + "&rlon=" + mpointr.getLongitudeE6()+"&uname="+Session.getUsername() ;
			else
				text = "http://" + s.getIp() + ":" + s.getPort() + "/createsession?name=" + sessionname_txt.getText() + "&max=" + maxspinner.getSelectedItem().toString() + "&userid=" + Session.getUserId()  + "&private=" + b + "&lat=" + mpointc.getLatitudeE6() + "&lon=" + mpointc.getLongitudeE6() + "&rlat=" + mpointr.getLatitudeE6() + "&rlon=" + mpointr.getLongitudeE6()+"&uname="+Session.getUsername() ;
			//http://<game_server_host>/createsession?name=<name>&max=<max>&userid=<userid>&private=<isprivate> &password=<password>&lat=<latitude>&lon=<longitude>&rlat=<radiusLatitude>&rlon=<radiusLongitude>
			serversinfo = new Communication(text);				
			SessionRecord ses=new SessionRecord(sessionname_txt.getText().toString(), Integer.parseInt(maxspinner.getSelectedItem().toString()), Integer.parseInt(Session.getUserId()), b);
			Session.setSession(ses);
			response = Controller.getSessionCreateResponse(serversinfo.getServerResponse());

			return null;
		}

	}

	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
		super.onActivityResult(requestCode, resultCode, data); 
		if(requestCode==1)
		{
			if (resultCode == Activity.RESULT_OK) 
			{ 
				mpointc=new GeoPoint((int)data.getIntExtra("pontocla", 0),(int)data.getIntExtra("pontoclo", 0));
				mpointr=new GeoPoint((int)data.getIntExtra("pontorla", 0),(int)data.getIntExtra("pontorlo",0));
				sendnewsession();

				// System.out.println("xi: "+ mpointc.getLatitudeE6() + "yi: " + mpointc.getLongitudeE6() + "xf: " + mpointr.getLatitudeE6() + "yf: "+ mpointr.getLongitudeE6());
				// TODO Switch tabs using the index.
			}
		}
	}


	public void sendnewsession()
	{
		CommunicationAsync c = new CommunicationAsync();
		c.execute();

	}




}
