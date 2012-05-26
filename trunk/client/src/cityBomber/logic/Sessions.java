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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
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


		CommunicationAsync c = new CommunicationAsync();
		c.execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Create and add new menu items.
		MenuItem itemRefresh = menu.add(Language.getTranslation(Session.getLang().get("Refresh"), "Refresh"));
		MenuItem itemCreateSess = menu.add(Language.getTranslation(Session.getLang().get("Create Session"), "Create Session"));
		MenuItem itemBack = menu.add(Language.getTranslation(Session.getLang().get("Back"), "Back"));
		

		// Allocate shortcuts to each of them.
		itemRefresh.setIcon(R.drawable.refresh);
		itemRefresh.setShortcut('0', 'a');
		itemRefresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				CommunicationAsync c = new CommunicationAsync();
				c.execute();
				return true;
			}

		});
		itemBack.setIcon(R.drawable.back);
		itemBack.setShortcut('1', 'r');
		itemBack.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
				return true;
			}

		});
		
		itemCreateSess.setIcon(R.drawable.add);
		itemCreateSess.setShortcut('1', 'r');
		itemCreateSess.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getApplicationContext(), SessionCreate.class);
                startActivityForResult(myIntent, -1);
				return true;
			}

		});
		
		return true;
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
			dialg.setMessage(Language.getTranslation(Session.getLang().get("SessionsRetrMsg"), "Retrieving servers list. Please wait..."));
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
			Communication sessionsinfo = new Communication("http://" + Session.getServer().getIp() +":"+ Session.getServer().getPort() + "/getsessions");//ALTERAR
			sessions = Controller.getSessionList(sessionsinfo.getServerResponse());			
			return null;
		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{  // After a pause OR at startup	
		CommunicationAsync c = new CommunicationAsync();
		c.execute();
	}
}