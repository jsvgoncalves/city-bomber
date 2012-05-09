package cityBomber.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import cityBomber.network.Communication;
import cityBomber.network.ServersInfo;

import Model.ServerRecord;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

public class CityBomberActivity extends Activity {
	/** Called when the activity is first created. */

	private HashMap<String, String> lang = new HashMap<String, String>();
	private String language = "PT-PT.lang";
	private TextView title ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);        

		InputStream in;
		try {
			in = openFileInput(language);
			if(in != null) 
			{
				String line = "";
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);
				while((line = buffreader.readLine()) != null)
				{
					String[] s = line.split("=");
					lang.put(s[0], s[1]);
				}
				in.close(); 
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      

		title = (TextView) findViewById(R.id.title);
		
		setServerList();
		

		Button refresh_btn = (Button) findViewById(R.id.Refresh_btn);
		refresh_btn.setText(lang.get("Refresh"));
		refresh_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setServerList();
			}
		});



	}

	public void setServerList()
	{
		ArrayList<ServerRecord> servers = new ArrayList<ServerRecord>();
		Communication serversinfo = new Communication(ServersInfo.getAuthServer());
		servers = Controller.getServerList(serversinfo.getServerResponse());
		final ListView listView = (ListView) findViewById(R.id.ListViewId);
		listView.setAdapter(new ServerItemAdapter(this, android.R.layout.simple_list_item_1, servers));
		title.setText(lang.get("Server")+" ("+ servers.size() +")");
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


}