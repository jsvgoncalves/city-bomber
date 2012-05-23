package cityBomber.logic;

import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.TextView;

public class Language {

	public static void setTextViewWord(String word, String default_word, TextView t, String s)
	{
		if(word != null)
			t.setText(word + s);
		else
			t.setText(default_word + s);
	}
	
	public static void setButtonWord(String word, String default_word, Button b)
	{
		if(word != null)
			b.setText(word);
		else
			b.setText(default_word);
	}
	
	public static void setProgDialogWord(String word, String default_word, ProgressDialog p)
	{
		if(word != null)
			p.setMessage(word);
		else
			p.setMessage(default_word);
	}
	public static String getTranslation(String word, String default_word)
	{
		if(word != null)
			return word;
		else
			return default_word;
	}
	
}
