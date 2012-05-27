package model; 
 
import java.util.Timer; 
import java.util.TimerTask; 
 
public class BombTimer { 
 
     Timer timer;
     private int bombid;
     private int sessionid;
 
     public BombTimer(int millis, int bombId, int sessionId) { 
          timer = new Timer(); 
          this.bombid = bombId;
          this.sessionid = sessionId;
          timer.schedule(new RemindTask(), millis);
     } 
      
     public void stopTimer() 
     { 
          if(timer!=null) 
               timer.cancel(); 
     } 
      
 
     class RemindTask extends TimerTask { 
          public void run() { 
        	  ServerSessions.explodeBomb(sessionid, bombid);
          } 
     }
 
}