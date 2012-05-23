package cityBomber.logic;

import java.util.ArrayList;
import cityBomber.network.Communication;
import cityBomber.network.ServersInfo;
import Model.ServerRecord;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Servers extends Activity{
	/** Called when the activity is first created. */


	private ArrayList<ServerRecord> servers = new ArrayList<ServerRecord>();
	private ListView listView ;
	private TextView title ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.serversmain);     				

		

		title = (TextView) findViewById(R.id.title);		
		Language.setTextViewWord(Session.getLang().get("Server"), "Servers", title, " (" + servers.size() + ")");

		Button refresh_btn = (Button) findViewById(R.id.Refresh_btn);

		Language.setButtonWord(Session.getLang().get("Refresh"), "Refresh", refresh_btn);


		refresh_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommunicationAsync c = new CommunicationAsync();
				c.execute();
			}
		});
		CommunicationAsync c = new CommunicationAsync();
		c.execute();
		

	}

	public void setServerList()
	{
		/*Communication serversinfo = new Communication(ServersInfo.getAuthServer());	
		servers = Controller.getServerList(serversinfo.getServerResponse());*/
			listView = (ListView) findViewById(R.id.ListViewId);
			listView.setAdapter(new ServerItemAdapter(Servers.this, android.R.layout.simple_list_item_1, servers));
			Language.setTextViewWord(Session.getLang().get("Server"), "Servers", title, " (" + servers.size() + ")");
			listView.setOnItemClickListener(new OnItemClickListener() {
				

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
	
					Session.setServer(((ServerRecord)listView.getItemAtPosition(arg2)));
					Intent myIntent = new Intent(getApplicationContext(), Sessions.class);
					startActivityForResult(myIntent, 0);

				}                 
			});
	}

	public class ServerItemAdapter extends ArrayAdapter<ServerRecord> {

		private ArrayList<ServerRecord> servers;

		public ServerItemAdapter(Context context, int textViewResourceId,
				ArrayList<ServerRecord> servers) {
			super(context, textViewResourceId, servers);
			this.servers = servers;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if(v == null)
			{
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.server_list, null);
			}

			ServerRecord server = servers.get(position);
			if(server != null)
			{
				TextView servername = (TextView) v.findViewById(R.id.servername);

				if(servername != null)
				{
					servername.setText(server.getServerName());
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
			dialg = new ProgressDialog(Servers.this);
			dialg.setMessage(Language.getTranslation(Session.getLang().get("ServersRetrMsg"), "Retrieving servers list. Please wait..."));
			dialg.setCancelable(false);
			dialg.show();			
		}
		@Override
		protected void onPostExecute(Void unused) {			
			dialg.dismiss();
			setServerList();
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Communication serversinfo = new Communication(ServersInfo.getAuthServer());	
			servers = Controller.getServerList(serversinfo.getServerResponse());
			
			return null;
		}

	}


}