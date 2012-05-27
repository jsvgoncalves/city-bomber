package cityBomber.logic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class Mapselectionoverlay extends Overlay {
	private int drawcircle=0;
	private GeoPoint pointofcircle, inipoint;
	private float radius;
	private int staticd=0, modo=0, arrowmode=0;
	private float angle;
	private Context context;

	
	public Mapselectionoverlay(Context context)
	{
		this.context=context;
	}
	
	public GeoPoint getPointofcircle() {
		return pointofcircle;
	}

	public void setPointofcircle(GeoPoint pointofcircle) {
		this.pointofcircle = pointofcircle;
		drawcircle=1;
	}

	
	@Override
	 public void draw(Canvas canvas, MapView mapView, boolean shadow){
	        if((drawcircle==1 || staticd==1) && modo==0)
	        {
	        	drawcircle=0;
		        Point pointc=new Point(), point=new Point();
		        Projection projection = mapView.getProjection();
		        projection.toPixels(inipoint, point);
		        projection.toPixels(pointofcircle, pointc);
		        radius=(float) Math.sqrt(Math.pow((float)pointc.x - point.x, 2) + Math.pow((float)pointc.y - point.y, 2));
		        //System.out.println("radius: " + radius);
		        Paint paint = new Paint();
		        paint.setStyle(Paint.Style.FILL);
		        paint.setARGB(100, 255, 2555, 255);
		        canvas.drawCircle(point.x, point.y, radius, paint);
	        }else if(modo==1)
	        {
	        	Point pointc=new Point(), point=new Point();
		        Projection projection = mapView.getProjection();
		        projection.toPixels(inipoint, point);
		        projection.toPixels(pointofcircle, pointc);
		        if(pointc.x>3000 || point.x>3000 || pointc.y>3000 || point.y>3000)
		        {
		        	return;
		        }
		        radius=(float) Math.sqrt(Math.pow((float)pointc.x - point.x, 2) + Math.pow((float)pointc.y - point.y, 2));
		        System.out.println("radius: " + radius + "x: " + point.x + " y: " + point.y + " x: " + pointc.x + " y: " + pointc.y);
		        Paint paint = new Paint();
		        paint.setStyle(Paint.Style.STROKE);
		        paint.setStrokeWidth(5);
		        paint.setARGB(200, 255, 0,0);
		        canvas.drawCircle(point.x, point.y, radius, paint);
		        if(arrowmode==1)
		        {
		        	rotateDrawable(canvas,angle);
		        	//arrowmode=0;
		        }
	        }
	    }

	
	public void setstaticc() {
		// TODO Auto-generated method stub
		staticd=1;
	}
	
	
	public GeoPoint getinipoint()
	{
		return inipoint;
	}

	public void setinipoint(GeoPoint point) {
		// TODO Auto-generated method stub
		inipoint=point;
		staticd=0;
	}

	public int getModo() {
		return modo;
	}

	public void setModo(int modo) {
		this.modo = modo;
	}

	public int getArrowmode() {
		return arrowmode;
	}

	public void setArrowmode(int arrowmode, float angle) {
		this.arrowmode = arrowmode;
		this.angle=angle; //+(float)90.0;
	}
	
	
	
	
	public void rotateDrawable(Canvas canvas,float angle)
	{
	  Bitmap arrowBitmap = BitmapFactory.decodeResource(context.getResources(), 
	                                                    R.drawable.arrow);
	  // Create blank bitmap of equal size
	  //Bitmap canvasBitmap = arrowBitmap.copy(Bitmap.Config.ARGB_8888, true);
	  //canvasBitmap.eraseColor(0x00000000);

	  // Create canvas
	  //Canvas canvast = new Canvas(arrowBitmap);

	// Create rotation matrix
	  WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	  Display display = wm.getDefaultDisplay();
	  
	  Matrix matrix = new Matrix();
	  matrix.setRotate(angle, arrowBitmap.getWidth()/2, arrowBitmap.getHeight()/2);
	  matrix.postTranslate(canvas.getWidth()/2-arrowBitmap.getWidth()/2, canvas.getHeight()/2-arrowBitmap.getHeight()/2);
	  
	  // Draw bitmap onto canvas using matrix
	  canvas.drawBitmap(arrowBitmap, matrix, null);
	
	}


	
    
	

}
