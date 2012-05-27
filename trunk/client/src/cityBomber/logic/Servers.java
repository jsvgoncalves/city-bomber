package cityBomber.logic;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import cityBomber.network.Communication;
import cityBomber.network.ServersInfo;
import Model.ServerRecord;
import Model.Session;
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
import android.view.View.OnClickListener;
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

public class Servers extends Activity{
	/** Called when the activity is first created. */


	private ArrayList<ServerRecord> servers = new ArrayList<ServerRecord>();
	private ListView listView ;
	private TextView title ;
	Context c = Servers.this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.serversmain);     				

		title = (TextView) findViewById(R.id.title);		
		Language.setTextViewWord(Session.getLang().get("Server"), "Servers", title, " (" + servers.size() + ")");

		CommunicationAsync c = new CommunicationAsync();
		c.execute();


	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Create and add new menu items.
		MenuItem itemRefresh = menu.add(Language.getTranslation(Session.getLang().get("Refresh"), "Refresh"));
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
		return true;
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
				if(Session.getUserId()=="-1" || Session.getUserId()==null)
				{
					
					
					Communication com = new Communication("http://" + Session.getServer().getIp() + ":" + Session.getServer().getPort() + "/getid" );
					//System.out.println("link:" + "http://" + Session.getServer().getIp() + ":" + Session.getServer().getPort() + "/update?sid=" + Session.getId() + "&uid=" +Session.getUserId() + "&ukey=" + Session.getUserId() + "&lat="+ lat + "&lon=" + lng +"&b=" + putbomb );
					String result = com.getServerResponse();
					if(result==null)
					{
						System.out.println("é nulo");
					}
					try {
						JSONObject json=new JSONObject(result);
						Session.setUserId(json.getString("uid"));
						Session.setUsername("Guest" + Session.getUserId());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
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
			dialg = new ProgressDialog(c);
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