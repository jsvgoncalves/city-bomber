package cityBomber.logic;


import java.util.ArrayList;

import cityBomber.network.Communication;

import Model.SessionRecord;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Sessions extends Activity {

	ArrayList<SessionRecord> sessions = new ArrayList<SessionRecord>();
	private String locked_icon = "locked.png", unlocked_icon="unlocked.png";
	private TextView title ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.sessionsmain);

			

		title = (TextView) findViewById(R.id.title);
		Language.setTextViewWord(Session.getLang().get("Sessionsat"), "Sessions@", title, Session.getServer().getServerName() + " (" + sessions.size() + ")");


		Button create = (Button) findViewById(R.id.create_btn);
		Language.setButtonWord(Session.getLang().get("Create Session"), "Create Session", create);

		create.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), SessionCreate.class);
				startActivityForResult(myIntent, -1);
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
		CommunicationAsync c = new CommunicationAsync();
		c.execute();
		
	}


	public void setSessionList()
	{
		
	
		
		
		final ListView listView = (ListView) findViewById(R.id.ListViewId);
		listView.setAdapter(new SessionItemAdapter(this, android.R.layout.simple_list_item_1, sessions));
		Language.setTextViewWord(Session.getLang().get("Sessionsat"), "Sessions@", title, Session.getServer().getServerName() + " (" + sessions.size() + ")");
		listView.setOnItemClickListener(new OnItemClickListener() {
			/* public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {


		        }*/

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
	}


	public class SessionItemAdapter extends ArrayAdapter<SessionRecord> {

		private ArrayList<SessionRecord> sessions;

		public SessionItemAdapter(Context context, int textViewResourceId,
				ArrayList<SessionRecord> sessions) {
			super(context, textViewResourceId, sessions);
			this.sessions = sessions;
			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if(v == null)
			{
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.sessions_list, null);
			}

			SessionRecord session = sessions.get(position);
			if(session!= null)
			{
				ImageView locked = (ImageView) v.findViewById(R.id.locked_icon);
				System.out.println(session.isPrivat());
				if(session.isPrivat())
				{
					try {

						Bitmap bitmap = BitmapFactory.decodeStream(Sessions.this.getResources().getAssets().open(locked_icon));
						locked.setImageBitmap(bitmap);
					} catch (Exception e) {

						e.printStackTrace();

					}
				}
				else
				{
					try {

						Bitmap bitmap = BitmapFactory.decodeStream(Sessions.this.getResources().getAssets().open(unlocked_icon));
						locked.setImageBitmap(bitmap);
					} catch (Exception e) {

						e.printStackTrace();

					}
				}
				TextView sessionname = (TextView) v.findViewById(R.id.sessionname);
				TextView sessioninfo = (TextView) v.findViewById(R.id.sessioninfo);
				if(sessionname != null)
				{
					sessionname.setText(session.getSessionName());
				}
				if(sessioninfo != null)
				{
					sessioninfo.setText(session.getJoinedPlayers()+ "/" + session.getMaxPlayers());
				}


			}
			return v;
		}

	}
	
	private class CommunicationAsync extends  AsyncTask<Void, Void, Void>
	{
		private ProgressDialog dialg;
		@Override
		protected void onPreExecute() {
			dialg = new ProgressDialog(Sessions.this);
			dialg.setMessage(Language.getTranslation(Session.getLang().get("ServersRetrMsg"), "Retrieving servers list. Please wait..."));
			dialg.setCancelable(false);
			dialg.show();			
		}
		@Override
		protected void onPostExecute(Void unused) {			
			dialg.dismiss();
			setSessionList();
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Communication sessionsinfo = new Communication("http://" + Session.getServer().getIp() +":"+ Session.getServer().getPort() + "/bomberman/gameserver/getsessions.php");//ALTERAR
			sessions = Controller.getSessionList(sessionsinfo.getServerResponse());			
			return null;
		}

	}
}