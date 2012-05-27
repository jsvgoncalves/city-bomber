package cityBomber.logic;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import Model.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class FacebookLoginA extends Activity {
	
	
	
	Facebook facebook = new Facebook("222377161214377");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.facelogin);

        facebook.authorize(this, new DialogListener() {
            @Override
            public void onComplete(Bundle values) { 
            	 AlertDialog alertDialog = new AlertDialog.Builder(FacebookLoginA.this).create();    
       		    alertDialog.setIcon(R.drawable.ok); 
       		    alertDialog.setMessage("Login Efectuado com sucesso!");  
       		    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
       		      public void onClick(DialogInterface dialog, int which) {  
       		    	   
       		    	Intent resultIntent = new Intent();
    				setResult(Activity.RESULT_OK, resultIntent); 
       		         finish();
       		    } }); 
       		    
       		    alertDialog.show();
            }

            @Override
            public void onFacebookError(FacebookError error) {
            	 AlertDialog alertDialog = new AlertDialog.Builder(FacebookLoginA.this).create();  
     		    alertDialog.setTitle("Erro"); 
     		   alertDialog.setIcon(R.drawable.error);
     		    alertDialog.setMessage("Erro: " + error.getMessage());  
     		    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
     		      public void onClick(DialogInterface dialog, int which) {  
     		    	  
     		    	 
     		         finish();
     		    } }); 
     		    
     		    alertDialog.show();
            	
            }

            @Override
            public void onError(DialogError e) {
            	 AlertDialog alertDialog = new AlertDialog.Builder(FacebookLoginA.this).create();  
      		    alertDialog.setTitle("Erro");  
      		    alertDialog.setIcon(R.drawable.error);
      		    alertDialog.setMessage("Erro: " + e.getMessage());  
      		    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
      		      public void onClick(DialogInterface dialog, int which) {  
      		    	  
      		    	 
      		         finish();
      		    } }); 
      		    
      		    alertDialog.show();
            }
            	
            @Override
            public void onCancel() {
            	finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
        JSONObject me;
		try {
			me = new JSONObject(facebook.request("me"));
			System.out.println("json: " + me);
			  Session.setUsername(me.getString("username"));
			  Session.setName(me.getString("name"));
			  Session.setUserId(me.getString("id"));
			  Session.setEmail(me.getString("email"));
			  System.out.println("email" + Session.getEmail());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      

    }

}
