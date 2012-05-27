package cityBomber.logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import Model.GameInfoRecord;
import Model.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cityBomber.logic.Sessions.SessionItemAdapter;
import cityBomber.network.Communication;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MapGame extends MapActivity{
	
	
	private PlayersItemizedOverlay players;
	private ItemsItemizedOverlay items;
	private MapView map;
	private MapController mapc;
	private LocationManager locationManager;
	private String provider,name="player";
	private int nogps=1,estado=0, seleccionarmapa=0, stateselection=0,adquiregps=0, firstgpsuse=0, xi=0, yi=0, xf=0, yf=0, mapcla,mapclo,maprla,maprlo, ib=0,lat,lng, contadorbombas=1, isplaying=0, isupdating=1, userid=0, isrunning=1;
	private AlertDialog alertDialog;
	private List<Overlay> mapOverlays;
	private Context context;
	private ProgressDialog dialog;
	private WakeLock wl;
	private GeoPoint pointcenter,pointraio;
	private Mapselectionoverlay selectoverlay;
	private Location mylocation=new Location(name);
	private boolean issatellite=true, isanimating=true, putbomb=false;
	private ArrayList<GameInfoRecord> playersinfo = new ArrayList<GameInfoRecord>();
	private Thread comunication;
	private Communication com;
	private Timer timer;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.map);
		final Button btn_back=(Button) findViewById(R.id.map_select), btn_aceitar=(Button) findViewById(R.id.map_select_aceita), btn_rejeitar=(Button) findViewById(R.id.map_select_rejeita);
		
		btn_back.getBackground().setAlpha(128);
		btn_aceitar.getBackground().setAlpha(200);
		btn_rejeitar.getBackground().setAlpha(200);
		btn_rejeitar.setVisibility(View.GONE);
		btn_aceitar.setVisibility(View.GONE);
		btn_aceitar.setText("Aceitar");
		btn_rejeitar.setText("Rejeitar");
		btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(seleccionarmapa==0)
				{
					seleccionarmapa=1;
					btn_back.setText("Voltar");
					map.setBuiltInZoomControls(false);
					
				}else
				{
					seleccionarmapa=0;
					btn_back.setText("Seleccionar mapa");
					map.setClickable(true);
					
				}
				
			}
		});
		
		btn_aceitar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//faltar enviar as definiçoes do mapa
				Intent resultIntent = new Intent();
				resultIntent.putExtra("pontocla", (int)selectoverlay.getinipoint().getLatitudeE6());
				resultIntent.putExtra("pontoclo", (int)selectoverlay.getinipoint().getLongitudeE6());
				resultIntent.putExtra("pontorla", (int)selectoverlay.getPointofcircle().getLatitudeE6());
				resultIntent.putExtra("pontorlo", (int)selectoverlay.getPointofcircle().getLongitudeE6());
				setResult(Activity.RESULT_OK, resultIntent);
				finish();

			}
		});
		
		btn_rejeitar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_rejeitar.setVisibility(View.GONE);
				btn_aceitar.setVisibility(View.GONE);
				stateselection=0;
				map.setClickable(true);
				map.getOverlays().clear();
				map.invalidate();
				btn_back.setVisibility(View.VISIBLE);
				
			}
		});
		if(Session.getUsername()!=null)
		{
			name=Session.getUsername();
		}
		estado=getIntent().getExtras().getInt("estado");
		mapcla=getIntent().getExtras().getInt("mapcla");
		mapclo=getIntent().getExtras().getInt("mapclo");
		maprla=getIntent().getExtras().getInt("maprla");
		maprlo=getIntent().getExtras().getInt("maprlo");
		issatellite=getIntent().getExtras().getBoolean("issatellite");
		isanimating=getIntent().getExtras().getBoolean("isanimating");
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();
		map = (MapView) findViewById(R.id.map);
		mapc=map.getController();
		mapc.setCenter(new GeoPoint((int)(37.421826*1E6),(int)(-122.083695*1E6)));
		if(estado==0)
		{
			btn_back.setVisibility(View.GONE);
			initmap(estado);
			comunication=new Thread(){
				

				

				@Override
				public void run()
				{
					String result;
					GameInfo gm;
					while(isrunning==1)
					{
						System.out.println("tá a correr");
						if(isplaying==1 && isupdating==1)
						{
							isupdating=0;
							System.out.println("http://" + Session.getServer().getIp() + ":" + Session.getServer().getPort() + "/update?sid=" + Session.getSession().getId() + "&uid=" +Session.getUserId() + "&ukey=" + Session.getUserId() + "&lat="+ lat + "&lon=" + lng +"&b=" + putbomb);
							com=new Communication("http://" + Session.getServer().getIp() + ":" + Session.getServer().getPort() + "/update?sid=" + Session.getSession().getId() + "&uid=" +Session.getUserId() + "&ukey=" + Session.getUserId() + "&lat="+ lat + "&lon=" + lng +"&b=" + putbomb );
							
							result=com.getServerResponse();
							System.out.println("result: " + result);
							gm=Controller.getgameinfo(result);
							
							processupdate(gm);
							putbomb=false;
							timer = new Timer();
					        timer.schedule(new TriggerUpdate(),1000);
						}else
						{
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
			};
			
			comunication.start();
		}else
		{
			btn_back.setText("Seleccionar mapa");
			
			showconfirmgpsuse();
			
		}
	
	}
	
	
	private class TriggerUpdate extends TimerTask{
		@Override
		public void run()
		{
			isupdating=1;
			timer.cancel();
		}
	}
	
	
	public void processupdate(GameInfo gm)
	{
		if(gm==null)
		{
			System.out.println("gm a nulo");
		}
		players.update(gm.getUsernames(),gm.getLat(),gm.getLng(),gm.getKills(),gm.getDeaths());
		int size=gm.getUserids().size();
		for(int i=0;i<size;i++)
		{
			if(gm.getUsernames().get(i).equals(name))
			{
				contadorbombas=gm.getMaxbomb().get(i);
				if(gm.getStatus().get(i)==0)
				{
					// faz animação para morte 
				}
				
				
			}
		}
		Vector<Integer> animate=items.update(gm.getBombsids(),gm.getLatb(),gm.getLngb(),gm.getBombr(),gm.getBombuo());
		
		System.out.println("vai analizar animações " + animate.size());
		procressanimations(animate);
	}
	
	
	public void procressanimations(Vector<Integer> animations)
	{
		GeoPoint point;
		for(int i=0;i<animations.size();i++)
		{
			point=items.getgeobyid(i);
			if(point!=null)
			{
				System.out.println("vai animar");
				processanimationbomb(point);
			}
		}
	}
	
	
	
	
	

	
	
	
	private void showconfirmgpsuse() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Quer usar o gps?");
		builder.setCancelable(false);
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   nogps=1;
		        	   initmap(1);
		           }
		       });
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   nogps=0;
		        	   initmap(2);
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}


	public void initmap(int i)
	{
		map.setBuiltInZoomControls(false);
		selectoverlay=new Mapselectionoverlay(this);
		if(i==0)
		{
			players=new PlayersItemizedOverlay(this);
			items=new ItemsItemizedOverlay(this);
			selectoverlay.setModo(1);
			
			selectoverlay.setPointofcircle(new GeoPoint(maprla,maprlo));
			selectoverlay.setinipoint(new GeoPoint(mapcla,mapclo));
			map.getOverlays().add(selectoverlay);
			map.getOverlays().add(players);
			map.getOverlays().add(items);
			
		}else
		{
			
			map.setClickable(true);
		}
		map.setSatellite(issatellite);
        if(!initlocationservice(i))
        {
        	return;
        }
        

        //initplayer();
        context=this;
        if(i!=2)
        {
        	showadquiregpsdialog();
        }
        
        map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(estado==0)
				{
					
				}
			}

			
        	
        });
        /**
         * handler para tocques no ecra nos diversos modos do jogo 
         */
        map.setOnTouchListener(new OnTouchListener() {
		  
			

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if(seleccionarmapa==1)
				{
					GeoPoint point;
					// TODO Auto-generated method stub
					
					if(event.getActionMasked()==MotionEvent.ACTION_DOWN && stateselection==0)
					{
						stateselection=1;
						point=map.getProjection().fromPixels((int)event.getX(),(int) event.getY());
						
						if(map.getOverlays().size()>0)
						{
							map.getOverlays().remove(map.getOverlays().size()-1);
						}
						OverlayItem overlayitem = new OverlayItem(point,"ponto 1","");
						selectoverlay.setinipoint(point);
						Button btn_back=(Button) findViewById(R.id.map_select);
						btn_back.setVisibility(View.GONE);
						map.getOverlays().add(selectoverlay);
						
						return false;
					}else if(event.getActionMasked()==MotionEvent.ACTION_MOVE && stateselection==1)
					{
						point=map.getProjection().fromPixels((int)event.getX(),(int) event.getY());
						if(map.getOverlays().size()>0)
						{
							map.getOverlays().remove(map.getOverlays().size()-1);
						}
						
						selectoverlay.setPointofcircle(point);
						map.getOverlays().add(selectoverlay);
						map.invalidate();
						return true;
					}else if(event.getActionMasked()==MotionEvent.ACTION_UP && stateselection==1)
					{
						stateselection=2;
						selectoverlay.setstaticc();
						Button btn_aceitar=(Button) findViewById(R.id.map_select_aceita), btn_rejeitar=(Button) findViewById(R.id.map_select_rejeita);
						btn_rejeitar.setVisibility(View.VISIBLE);
						btn_aceitar.setVisibility(View.VISIBLE);
						map.setClickable(false);
						
						return false;
					}
					
				
				}else if(estado==0)
				{
					if(event.getActionMasked()==MotionEvent.ACTION_DOWN && stateselection==0)
					{
						stateselection=1;
						xi=(int) event.getX();
						yi=(int) event.getY();
						return true;
					}else if(event.getActionMasked()==MotionEvent.ACTION_UP && stateselection==1)
					{
						stateselection=2;
						xf=(int) event.getX();
						yf=(int) event.getY();
						
						
						if(Math.abs(xi-xf)<=20 && Math.abs(yi-yf)<=20 && contadorbombas>0)
						{
							timer.cancel();
							GeoPoint p=map.getProjection().fromPixels((int)event.getX(),(int) event.getY());
							Location locc=new Location("gps"), locr=new Location("gps"), location=new Location("gps");
							locc.setLatitude(mapcla/1E6);
							locc.setLongitude(mapclo/1E6);
							locr.setLatitude(maprla/1E6);
							locr.setLongitude(maprlo/1E6);
							location.setLatitude(lat/1E6);
							location.setLongitude(lng/1E6);
							float[] f1=new float[1], f2=new float[1];
							Location.distanceBetween(lat, lng, mapcla,mapclo, f1);
							Location.distanceBetween(maprla, maprlo, mapcla,mapclo, f2);
							if(location.distanceTo(locc)<=locc.distanceTo(locr))
							{
								putbomb(p,"mybomb");
								contadorbombas--;
								putbomb=true;
								stateselection=0;
								isupdating=1;
								timer.cancel();
								return false;
							}else
							{
								return true;
							}
							
						}
						
						
						
						
						if(xi>xf)  
						{
							int min = (int) (getWindowManager().getDefaultDisplay().getWidth()*0.25);
							
							if(Math.abs(yi-yf)<50 && Math.abs(xi-xf)>min)
							{
								
								ListView info=(ListView) findViewById(R.id.map_info_games);
								RelativeLayout lay=(RelativeLayout) findViewById(R.id.map_info);
								Button btn=(Button) findViewById(R.id.map_info_back); 
								btn.setText("Voltar");
								btn.setVisibility(View.VISIBLE);
								btn.setTextColor(Color.WHITE);
								btn.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										RelativeLayout lay=(RelativeLayout) findViewById(R.id.map_info);
										lay.setVisibility(View.GONE);
									}
								});
								
								lay.setVisibility(View.VISIBLE);	
								lay.setClickable(false);
								Communication sessionsinfo = new Communication("");//ALTERAR
								playersinfo = getplayerslist();			
								info.setAdapter(new GameAdapter(MapGame.this, R.layout.gameinfo, playersinfo));
								info.setClickable(false);
								stateselection=0;
								return true;
							}
						}
						
						stateselection=0;
						return true;
					}
				}else if(seleccionarmapa==0 && estado==1)
				{
					return false;
				}
				
				return true;
				
			}
			});
        
       
		
	}
	
	public ArrayList<GameInfoRecord> getplayerslist()
	{
		int size=players.size();
		Vector<String> users=players.getplayers();
		ArrayList<GameInfoRecord> result=new ArrayList<GameInfoRecord>();
		for(int i = 0; i < size; i++)
		{
			result.add(new GameInfoRecord(users.get(i), players.getkills(users.get(i)), players.getdeaths(users.get(i))));
		}
		return result;
	}
	
	
	private class GameAdapter extends ArrayAdapter<GameInfoRecord> {

		private ArrayList<GameInfoRecord> gameinfos;
		private String file = "player.png";
		public GameAdapter(Context context, int textViewResourceId,
				ArrayList<GameInfoRecord> gameinfos) {
			super(context, textViewResourceId, gameinfos);
			this.gameinfos = gameinfos;

		}
		
		@Override
		public boolean isEnabled(int position)
		{
			return false;
		}
		
		
		
		
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			
			View v = convertView;
			if(v == null)
			{
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.gameinfo, null);
			}
			
			GameInfoRecord gameinfo = gameinfos.get(position);
			if(gameinfo!= null)
			{
				ImageView player = (ImageView) v.findViewById(R.id.player);

				try {

					Bitmap bitmap = BitmapFactory.decodeStream(MapGame.this.getResources().getAssets().open(file));
					player.setImageBitmap(bitmap);
				} catch (Exception e) {

					e.printStackTrace();
				}

				TextView playername = (TextView) v.findViewById(R.id.playername); 
				TextView kills_lbl = (TextView) v.findViewById(R.id.deaths_lbl);
				TextView scores_lbl = (TextView) v.findViewById(R.id.scores_lbl);
				if(playername != null)
				{
					playername.setText(gameinfo.getPlayer_name());
				}
				if(kills_lbl != null)
				{
					kills_lbl.setText("Kills / Deaths");
				}
				if(scores_lbl != null)
				{
					scores_lbl.setText(gameinfo.getKills() + " / " + gameinfo.getDeaths());
				}
			}
			return v;
		}

	}
	
	

	/*private void initplayer() {
		// TODO Auto-generated method stub
		mapOverlays =map.getOverlays();
		
	}*/

	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean initlocationservice(int i)
	{
		
		
		if(i==1 || i==0)
		{
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);		
			Criteria criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, false);
			Location location = locationManager.getLastKnownLocation(provider);
			System.out.println("estad1");
			// Initialize the location fields
			if (location != null) {
				System.out.println("Provider " + provider + " has been selected.");
				Toast.makeText(this, "Provider " + provider + " has been selected.",
						Toast.LENGTH_SHORT).show();
				int lat = (int) (location.getLatitude());
				int lng = (int) (location.getLongitude());
				mapc.animateTo(new GeoPoint(lat,lng));
				
			} else {
				System.out.println("Provider not avaliable");
				
			}
			
			if(location==null || provider=="gps")
			{
				if(!toggleGPS())
				{
					return false;
				}
			}
			
			if(location!=null)
			{
				locationManager.requestLocationUpdates(provider, 0,0, new GeoUpdateHandler());
			}else
			{
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, new GeoUpdateHandler());
			}
		}
		
		return true;
		
		
	}
	
	public boolean toggleGPS() {
	    String provider = Settings.Secure.getString(getContentResolver(), 
	        Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	      nogps=1;
	    if(provider.contains("gps") == false) {
	    	shownogpsdialog();
	    	nogps=0;
	    	
	    	
	    }
	    
	    if(nogps==1)
	    {
	    	return true;
	    }
	    
	    return false;
	    

	   
	}
	

	public class GeoUpdateHandler implements LocationListener {

		
		@Override
		public void onLocationChanged(Location location) {
			lat = (int) (location.getLatitude() * 1E6);
			lng = (int) (location.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(lat, lng);
			
			if(estado==0 || (estado==1 && firstgpsuse==0 && nogps==0))
			{
				
				
				mapc.animateTo(point); // mapController.setCenter(point);
				if(estado==1)
				{
					firstgpsuse=1;
				}
			}
			if(estado==0)
			{
				
				while(map.getZoomLevel()<19)
				{
					mapc.zoomIn();
				}
				
				Location locc=new Location("gps"), locr=new Location("gps");
				locc.setLatitude(mapcla/1E6);
				locc.setLongitude(mapclo/1E6);
				locr.setLatitude(maprla/1E6);
				locr.setLongitude(maprlo/1E6);
				float[] f1=new float[1], f2=new float[1];
				Location.distanceBetween(lat, lng, mapcla,mapclo, f1);
				Location.distanceBetween(maprla, maprlo, mapcla,mapclo, f2);
				if(location.distanceTo(locc)>locc.distanceTo(locr))
				{
					
					selectoverlay.setArrowmode(1, location.bearingTo(locc));
					map.getOverlays().set(0, selectoverlay);
				}else
				{
					selectoverlay.setArrowmode(0, 0);
					map.getOverlays().set(0, selectoverlay);
					
				}
				
				
				isplaying=1;
				if(map.getOverlays().size()>1)
				{
					updateplayer(name,point,players.getkills(name),players.getdeaths(name));
					map.getOverlays().set(1, players);
					
				}else
				{
					OverlayItem overlayitem = new OverlayItem(point,name,"");
					players.addplayer(overlayitem,0,0);
					map.getOverlays().add(players);
				}
				
			}
				
			if(adquiregps==0)
			{
				hideadquiregpsdialog();
				adquiregps=1;
			}
		
			
			
			
			
			
		    
		}

		@Override
		public void onProviderDisabled(String provider) {
			shownogpsdialog();
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
	
	
	
	
	
	/**
	 * mostra um alert se o gps não estiver ligado 
	 */
	public void shownogpsdialog()
	{
		
		 alertDialog = new AlertDialog.Builder(this).create();  
		    alertDialog.setTitle("Alerta");  
		    alertDialog.setMessage("Tem de ligar o gps para poder jogar");  
		    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		    	  wl.release();
		    	 
		         finish();
		    } }); 
		    
		    alertDialog.show();
		   
	}
	
	
	
	
	
	public void showadquiregpsdialog()
	{
	
		   
		   dialog = new ProgressDialog(MapGame.this);
		   dialog.setTitle("");
		   dialog.setMessage("A adquirir o sinal GPS...");
		   dialog.setButton("Cancelar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				wl.release();
				
				finish();
			}
		});
		   
		   
		   dialog.show();
	}
	
	
	public void hideadquiregpsdialog()
	{
		dialog.hide();
		dialog.cancel();
	}
	
	
	
	
	/**
	 * actualiza o jogador com nome nome para o ponto point fornecido como argumento no overlays dos players 
	 * @param nome
	 * @param point
	 * @param j 
	 * @param i 
	 */
	public void updateplayer(String nome, GeoPoint point, int i, int j)
	{
		
		OverlayItem overlayitem = new OverlayItem(point,name,"");
		System.out.println("nome a definir: " + name);
		if(i==-1)
		{
			i=0;
			j=0;
		}
		players.addplayer(overlayitem,i,j); 
		map.getOverlays().set(1, players); 
	}
	
	
	/**
	 * remove um jogador da overlay dos players de nome nome
	 * @param nome
	 */
	public void removeplayer(String nome)
	{
		players.removeplayer(nome);
		map.getOverlays().set(1, players);
	}
	
	
	
	/**
	 * irá expandir a bomba num circulo 
	 * @param bomb
	 */
	public void processanimationbomb(final GeoPoint bomb)
	{
		Thread t=new Thread(){
			
			
			@Override
			public void run()
			{
				if(isanimating)
				{
					for(int i=0;i<items.gettimebomb(bomb);i++)
					{
						if(!animatebomb(bomb)) 
						{
							return;
						}
					
						try {
							Thread.sleep(1000/items.gettimebomb(bomb));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}else
				{
					items.animatestaticitem(bomb);
					animatestaticbomb(bomb);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					items.deanimatestaticitem(bomb);
					animatestaticbomb(bomb);
				}
				
				
				
				
				/*if(items.ismybomb(bomb))
				{
					contadorbombas++;
				}*/
				
			}
		};
		
		t.start();
	}
	
	
	protected void animatestaticbomb(GeoPoint point) {
		// TODO Auto-generated method stub
		
		
		runOnUiThread(new Runnable() {
		    public void run() {
		    	map.getOverlays().set(2, items);
		    	map.invalidate();
		    }
		});
		
		
		
		
	}



	public final void putbomb(GeoPoint point, String string) {
		// TODO Auto-generated method stub
		OverlayItem item=new OverlayItem(new GeoPoint(lat,lng),string,"");

		items.updateitem(item,0,20);

		map.getOverlays().set(2, items);
			
		//processanimationbomb(point);
		

	}
	
	
	public boolean animatebomb(GeoPoint point)
	{
		if(!items.animateditem(point))
		{
			return false;
		}
		runOnUiThread(new Runnable() {
		    public void run() {
		    	map.getOverlays().set(2, items);
		    	map.invalidate();
		    }
		});
		
		
		return true;
	}
	
	
	@Override
	public void onBackPressed()
	{
		isrunning=0;
		while(comunication.isAlive());
		
		finish();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	


		

	


	
	
	

}
