package cityBomber.logic;

import java.util.ArrayList;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class PlayersItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private Drawable defaultm;
	private Vector<String> players=new Vector<String>();
	private Vector<Integer> kills=new Vector<Integer>(), deaths=new Vector<Integer>();
	//private Vector<Integer> kills=new Vector<>
	public PlayersItemizedOverlay(Context context) {
		super(boundCenterBottom(context.getResources().getDrawable(R.drawable.androidmarker)));
		defaultm=context.getResources().getDrawable(R.drawable.androidmarker);
		
		mContext = context;

		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	
	
	/*public void addOverlay(OverlayItem overlay,int i) {
		
		if(i+1>mOverlays.size())
		{
			mOverlays.add(overlay);	
		}else
		{
			mOverlays.set(i, overlay);
		}
		populate();
		updatenameplayer(overlay.getTitle(),i);
	    
	}*/
	
	
		private void updatenameplayer(String name, int i) {
		// TODO Auto-generated method stub
		if(i+1>players.size())
		{
			players.add(name);
		}else
		{
			players.set(i, name);
		}
	}

		@Override
	    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			 super.draw(canvas, mapView, shadow);  
			 
		    Paint paint=new Paint();  
		    //paint.setStrokeWidth(1);  
		    paint.setARGB(255, 255, 255, 255);  
		   // paint.setStyle(Paint.Style.STROKE);  
		    paint.setTextSize(18);
		    Bitmap bmp = ((BitmapDrawable) defaultm).getBitmap();
		    Point screenPts = new Point();

		    float bmpWidth = bmp.getWidth();
	        float bmpHeight = bmp.getHeight();
		    for(int i=0;i<mOverlays.size();i++)
		    {
		    	GeoPoint gp = mOverlays.get(i).getPoint();
		        mapView.getProjection().toPixels(gp, screenPts);

		        

				float left = screenPts.x - (bmpWidth / 2);
				float top = screenPts.y - bmpHeight;
				//System.out.println("nome: " + players.get(i));
		    	canvas.drawText(players.get(i),left,top-10, paint); 
		    }
		 
	    }

		public void addplayer(OverlayItem overlay, int kill,int death) {
			// TODO Auto-generated method stub
			
			for(int i=0;i<players.size();i++)
			{
				if(players.get(i).equals(overlay.getTitle()))
				{
					
					mOverlays.set(i,overlay);
					kills.set(i,kill);
					deaths.set(i,death);
					populate();
					return;
				}
			}
			
			System.out.println("adicionou user " + overlay.getTitle());
			mOverlays.add(overlay);
			players.add(overlay.getTitle());
			kills.add(kill);
			deaths.add(death);
			populate();
		}
		
		
		public void removeplayer(String name)
		{
			for(int i=0;i<players.size();i++)
			{
				if(players.get(i).equals(name))
				{
				
					mOverlays.remove(i);
					players.remove(i);
					kills.remove(i);
					deaths.remove(i);
					return;
				}
			}
		}
		
		
		
		public void update(Vector<String> usernames, Vector<Integer> lat, Vector<Integer> lng, Vector<Integer> kill, Vector<Integer> death) {
			// TODO Auto-generated method stub
			int j=0;
			OverlayItem item;
			for(int i=1;i<players.size();i++)
			{
				j=0;
				for(int q=0;q<usernames.size();q++)
				{
					if(players.get(i).equals(usernames.get(q)))
					{
						item=new OverlayItem(new GeoPoint(lat.get(q),lng.get(q)),players.get(i),"");
						addplayer(item,kill.get(q),death.get(q));
						
						j=1;
						break;
					}
				}
				
				if(j==0)
				{
					players.remove(i);
					mOverlays.remove(i);
					kills.remove(i);
					deaths.remove(i);
					i--;
				}

			}
			
			
			for(int i=0;i<usernames.size();i++)
			{
				j=0;
				for(int q=0;q<players.size();q++)
				{
					if(players.get(q).equals(usernames.get(i)))
					{
						j=1;
						break;
					}
				}
				
				if(j==0)
				{
					item=new OverlayItem(new GeoPoint(lat.get(i),lng.get(i)),usernames.get(i),"");
					addplayer(item,kill.get(i),death.get(i));
				}
			}

			
		}

		public Vector<String> getplayers() {
			// TODO Auto-generated method stub
			return players;
		}
		
		
		public int getkills(String name)
		{
			for(int i=0;i<players.size();i++)
			{
				if(players.get(i).equals(name))
				{
					return kills.get(i);
				}
			}
			
			return -1;
		}
		
		
		public int getdeaths(String name)
		{
			for(int i=0;i<players.size();i++)
			{
				if(players.get(i).equals(name))
				{
					return deaths.get(i);
				}
			}
			
			return -1;
		}
		
	   

	
	
	
	

}
