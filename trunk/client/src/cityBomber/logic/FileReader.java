package cityBomber.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;

import android.content.SharedPreferences;

import Model.LanguageRecord;

public class FileReader {

	public static void SwitchLanguage(InputStream in )
	{		
		try {
			if(in != null) 
			{
				String line = "";
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);
				while((line = buffreader.readLine()) != null)
				{
					String[] s = line.split("=");
					Session.addLanguageElement(s[0], s[1]);
				}
				in.close(); 
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     		
	}
	/*public static void LoadSettings(String filename )
	{		
		
		
		
		try {
			if(in != null) 
			{
				String line = "";
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);
				while((line = buffreader.readLine()) != null)
				{
					String[] s = line.split("=");
					if(s[0].equals("SOUND"))
					{
						Session.setSound(Boolean.parseBoolean(s[1]));
					}
					else if(s[0].equals("ANIMATIONS "))
					{
						Session.setAnimations(Boolean.parseBoolean(s[1]));
					}
					else if(s[0].equals("SATELLITEIMG"))
					{
						Session.setSatellite(Boolean.parseBoolean(s[1]));
					}
					else if(s[0].equals("LANGUAGE"))
					{
						System.out.println("LANGUAGE " +s[1]);
						Session.setLanguage(s[1]);
					}
				}
				in.close(); 				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     		
	}*/

	public static void LoadLanguages(InputStream in )
	{		
		try {
			Session.addLanguageRecordElement(new LanguageRecord("English", "en.jpg", ""));		
			if(in != null) 
			{
				String line = "";
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);
				while((line = buffreader.readLine()) != null)
				{
					String[] s = line.split("\" \"|\"");
					Session.addLanguageRecordElement(new LanguageRecord(s[1], s[2], s[3]));							
				}
				in.close(); 				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     		
	}
	
}
