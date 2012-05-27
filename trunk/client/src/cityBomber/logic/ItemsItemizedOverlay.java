package cityBomber.logic;

import java.util.ArrayList;
import java.util.Vector;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class ItemsItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private static Drawable defaultMarker;
	private ArrayList<OverlayItem> items=new ArrayList<OverlayItem>();
	private Vector<String> itemsnames=new Vector<String>();
	private Vector<Integer> activatedanim=new Vector<Integer>(), typeitems=new Vector<Integer>(), timeitems=new Vector<Integer>();
	private Context context;
	private int tipoitem=0;

	public ItemsItemizedOverlay(Context context) {
		super(boundCenterBottom(context.getResources().getDrawable(R.drawable.bomb)));
		this.context=context;
		defaultMarker=context.getResources().getDrawable(R.drawable.bomb);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		//System.out.println("vai buscar");
		return items.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return items.size();
	}
	
	public void updateitems(Vector<OverlayItem> items1)
	{
		
	}
	public void updateitem(OverlayItem item, int type, int time)
	{
		for(int i=0;i<items.size();i++)
		{
			if(items.get(i).getPoint().getLatitudeE6()==item.getPoint().getLatitudeE6() && items.get(i).getPoint().getLongitudeE6()==item.getPoint().getLongitudeE6())
			{
				items.set(i,item);
				typeitems.set(i, type);
				timeitems.set(i,time);
				populate();
				return;
			}
		}
		
		items.add(item);
		itemsnames.add(item.getTitle());
		activatedanim.add(0);
		typeitems.add(type);
		timeitems.add(time);
		populate();
		//System.out.println("size: " + items.size() + " posx: " + item.getPoint().getLatitudeE6() + " posy: " + item.getPoint().getLongitudeE6());
	}
	
	
	public boolean animateditem(GeoPoint point)
	{
		for(int i=0;i<items.size();i++)
		{
			if(items.get(i).getPoint().getLatitudeE6()==point.getLatitudeE6() && items.get(i).getPoint().getLongitudeE6()==point.getLongitudeE6())
			{
				//items.set(i,item);
				//populate();
				activatedanim.set(i,activatedanim.get(i)+1);
				return true;
			}
		}
		
		return false;
	}
	
	
	
	@Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		//System.out.println("começa");
		// super.draw(canvas, mapView, shadow);  
		 //System.out.println("desenha item");
		
	      
	  for(int i=0;i<activatedanim.size();i++)
	  {

		  if((activatedanim.get(i)>0 && activatedanim.get(i)<timeitems.get(i)) || activatedanim.get(i)==-1)  /// desenha uma incrementação do circulo, logo que o contador chege a 20 este item é automaticamente removido 
		  {
			  Point  point=new Point();
		        Projection projection = mapView.getProjection();
		        projection.toPixels(items.get(i).getPoint(), point);
		        float radius;
		        if(activatedanim.get(i)==-1)
		        {
		        	radius=(int) projection.metersToEquatorPixels(timeitems.get(i));
		        }else
		        {
		        	radius=(int) projection.metersToEquatorPixels(activatedanim.get(i));
		        }
		       // radius=(float) Math.sqrt(Math.pow((float)pointc.x - point.x, 2) + Math.pow((float)pointc.y - point.y, 2));
		        //System.out.println("radius: " + radius + " count: " + activatedanim.get(i));
		        Paint paint = new Paint();
		        paint.setStyle(Paint.Style.FILL);
		        paint.setARGB(100, 255,0 , 0);
		        canvas.drawCircle(point.x, point.y, radius, paint);
		  }else if(activatedanim.get(i)>=timeitems.get(i))
		  {
			  items.remove(i);
			  itemsnames.remove(i);
			  activatedanim.remove(i);
			  typeitems.remove(i);
			  timeitems.remove(i);
		  }else
		  {
			  Bitmap bitm;
			  if(typeitems.get(i)==0)
			  {
				  bitm = BitmapFactory.decodeResource(context.getResources(),R.drawable.bomb);
			  }else if(typeitems.get(i)==1)
			  {
				  bitm = BitmapFactory.decodeResource(context.getResources(),R.drawable.morebomb);
			  }else
			  {
				  bitm = BitmapFactory.decodeResource(context.getResources(),R.drawable.morepower);
			  }
				// Create blank bitmap of equal size
				//Bitmap canvasBitmap = arrowBitmap.copy(Bitmap.Config.ARGB_8888, true);
				//canvasBitmap.eraseColor(0x00000000);
				
				// Create canvas
				//Canvas canvast = new Canvas(arrowBitmap);
				
				// Create rotation matrix
			
				Point p=new Point();
				
				mapView.getProjection().toPixels(items.get(i).getPoint(),p);
				Matrix matrix = new Matrix();
				matrix.postTranslate(p.x-bitm.getWidth()/2, p.y-bitm.getHeight()/2);
				
				// Draw bitmap onto canvas using matrix
				canvas.drawBitmap(bitm, matrix, null);
		  }
	  }
	 
	 
    }

	public void animatestaticitem(GeoPoint point) {
		// TODO Auto-generated method stub
		for(int i=0;i<items.size();i++)
		{
			if(items.get(i).getPoint().getLatitudeE6()==point.getLatitudeE6() && items.get(i).getPoint().getLongitudeE6()==point.getLongitudeE6())
			{
				activatedanim.set(i, -1);
				return;
			}
		}
	}
	
	
	public void deanimatestaticitem(GeoPoint point) {
		// TODO Auto-generated method stub
		for(int i=0;i<items.size();i++)
		{
			if(items.get(i).getPoint().getLatitudeE6()==point.getLatitudeE6() && items.get(i).getPoint().getLongitudeE6()==point.getLongitudeE6())
			{
				items.remove(i);
				  itemsnames.remove(i);
				  activatedanim.remove(i);
				  typeitems.remove(i);
				  timeitems.remove(i);
				  return;
			}
		}
	}
	
	
	public int gettimebomb(GeoPoint point)
	{
		for(int i=0;i<itemsnames.size();i++)
		{
			if(items.get(i).getPoint().getLatitudeE6()==point.getLatitudeE6() && items.get(i).getPoint().getLongitudeE6()==point.getLongitudeE6())
			{
				return timeitems.get(i);
			}
		}
		
		return -1;
	}

	public boolean ismybomb(GeoPoint point) {
		// TODO Auto-generated method stub
		for(int i=0;i<itemsnames.size();i++)
		{
			if(items.get(i).getPoint().getLatitudeE6()==point.getLatitudeE6() && items.get(i).getPoint().getLongitudeE6()==point.getLongitudeE6())
			{
				if(itemsnames.get(i).equals("mybomb"))
				{
					return true;
				}else
				{
					return false;
				}
				
			}
		}
		return false;
	}

	public Vector<Integer> update(Vector<String> ids, Vector<Integer> lat,
			Vector<Integer> lng, Vector<Integer> r, Vector<String> bombuo) {
		// TODO Auto-generated method stub
		Vector<Integer> result=new Vector<Integer>();
		int q=0;
		for(int i=0;i<items.size();i++)
		{
			q=0;
			for(int j=0;j<ids.size();j++)
			{
				System.out.println(items.get(i).getPoint().getLatitudeE6() + " " + lat.get(j) + items.get(i).getPoint().getLongitudeE6() + " " + lng.get(j));
				if(items.get(i).getPoint().getLatitudeE6()==lat.get(j) && items.get(i).getPoint().getLongitudeE6()==lng.get(j))
				{
					q=1;
					break;
				}
			}
			
			if(q==0)
			{
				result.add(i);
			}
		}
		
		
		for(int i=0;i<ids.size();i++)
		{
			q=0;
			for(int j=0;j<items.size();j++)
			{
				if(items.get(j).getPoint().getLatitudeE6()==lat.get(i) && items.get(j).getPoint().getLongitudeE6()==lng.get(i))
				{
					q=1;
					break;
				}
			}
			
			if(q==0)
			{
				OverlayItem item=new OverlayItem(new GeoPoint(lat.get(i),lng.get(i)),ids.get(i),"");
				updateitem(item,0,r.get(i));
			}
		}
		
		return result;
	}
	
	
	public GeoPoint getgeobyid(int i)
	{
		if(i<items.size())
		return items.get(i).getPoint();
		else
			return null;
	}



}
