package cityBomber.logic;


import java.util.ArrayList;

import cityBomber.network.Communication;

import Model.SessionRecord;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

public class Sessions extends Activity {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.sessionsmain);

		TextView title = (TextView) findViewById(R.id.title);
		title.setText("Server: " + Session.getServer().getServerName());
		setSessionList();
		Button create = (Button) findViewById(R.id.create_btn);
		create.setText("Create Session");
		create.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(getApplicationContext(), SessionCreate.class);
                startActivityForResult(myIntent, -1);
			}

		});
		
		Button back = (Button) findViewById(R.id.back_btn);
		back.setText("Voltar");
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}

		});
	}


	public void setSessionList()
	{
		ArrayList<SessionRecord> sessions = new ArrayList<SessionRecord>();
		Communication sessionsinfo = new Communication("http://" + Session.getServer().getIp() +":"+ Session.getServer().getPort() + "");//ALTERAR
		sessions = Controller.getSessionList(sessionsinfo.getServerResponse());
		final ListView listView = (ListView) findViewById(R.id.ListViewId);
		listView.setAdapter(new SessionItemAdapter(this, android.R.layout.simple_list_item_1, sessions));

		listView.setOnItemClickListener(new OnItemClickListener() {
			/* public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {


		        }*/

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				/*String server = ((ServerRecord)listView.getItemAtPosition(arg2)).getServerName();
				Toast toast = Toast.makeText(getApplicationContext(), server, 5);
				toast.show();*/
				/*Session.setS(((SessionRecord)listView.getItemAtPosition(arg2)));
				Intent myIntent = new Intent(getApplicationContext(), Sessions.class);
                startActivityForResult(myIntent, 0);*/
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
}