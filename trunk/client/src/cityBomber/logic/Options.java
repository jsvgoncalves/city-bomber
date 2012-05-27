package cityBomber.logic;

import Model.LanguageRecord;
import Model.Session;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Options extends PreferenceActivity{
	private String configs = "preferences.cfg";
	private ListPreference language;
	private CheckBoxPreference sound, animations, satellite;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.optionsmain);	
		addPreferencesFromResource(R.layout.options);    
		initInterface();
	}

	@Override
	protected void onStop(){
		super.onStop();

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = getSharedPreferences(configs, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("SOUND", sound.isChecked());      
		editor.putBoolean("ANIMATIONS", animations.isChecked());
		editor.putBoolean("SATELLITEIMG", satellite.isChecked());
		editor.putString("LANGUAGE", language.getValue());
		// Commit the edits!
		editor.commit();
	}

	public void initInterface()
	{

		TextView title = (TextView) findViewById(R.id.options_title);
		Language.setTextViewWord(Session.getLang().get("Options"), "Options", title, "");

		sound = (CheckBoxPreference) findPreference("sound_check");
		sound.setTitle(Language.getTranslation(Session.getLang().get("Sound"), "Sound"));
		//sound.setChecked(Session.isSound());

		animations = (CheckBoxPreference) findPreference("animations_check");
		animations.setTitle(Language.getTranslation(Session.getLang().get("Animations"), "Animations"));
		//animations.setChecked(Session.isAnimations());

		satellite = (CheckBoxPreference) findPreference("satellite_check");
		satellite.setTitle(Language.getTranslation(Session.getLang().get("Satellite"), "Satellite Images"));
		//satellite.setChecked(Session.isSatellite());

		language = (ListPreference) findPreference("language_spinner");
		language.setTitle(Language.getTranslation(Session.getLang().get("Language"), "Language"));
		language.setSummary(Session.getLanguage());
		language.setDialogTitle(Language.getTranslation(Session.getLang().get("Language"), "Language"));
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(Options.this.getResources().getAssets().open(Session.getIconPath(Session.getLanguage())));
			Drawable d = new BitmapDrawable(bitmap);
			language.setDialogIcon(d);
		} catch (Exception e) {
			e.printStackTrace();
		}


		initLanguages();	



		language.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String value = newValue.toString();
				if(!Session.getLanguage().equals(value))
				{
					Session.setLanguage(value);
					if(value.equals("English"))
					{
						Session.clearCurrentLanguage();
					}
					else
					{
						try {
							FileReader.SwitchLanguage(Options.this.getAssets().open(Session.getFilePath(value)));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					language.setSummary(value);
					startActivity(getIntent()); 
					finish();
					return true;
				}
				else
					return false;

			}

		});

		sound.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				Session.setSound((Boolean) arg1);
				sound.setChecked((Boolean) arg1);
				return true;
			}
		});

		animations.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				Session.setAnimations((Boolean) arg1);
				animations.setChecked((Boolean) arg1);
				return true;
			}
		});

		satellite.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				Session.setSatellite((Boolean) arg1);
				satellite.setChecked((Boolean) arg1);
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Create and add new menu items.

		MenuItem itemBack = menu.add(Language.getTranslation(Session.getLang().get("Back"), "Back"));
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

	public void initLanguages()
	{
		String[] languages = new String[Session.getLanguages().size()];
		Object[] arr = Session.getLanguages().toArray();
		for(int i = 0; i < arr.length; i++)
		{
			languages[i]=(((LanguageRecord)arr[i]).getLanguage_name());
		}
		language.setEntryValues(languages);
		language.setEntries(languages);
		language.setDefaultValue(Session.getLanguage());
	}





	/*public class LanguageItemAdapter extends ArrayAdapter<LanguageRecord>
	{

		public LanguageItemAdapter(Context context, int textViewResourceId,
				ArrayList<LanguageRecord> sessions) {
			super(context, textViewResourceId, sessions);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if(v == null)
			{
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.languagelist, null);
			}

			LanguageRecord language = Session.getLanguages().get(position);
			if(language!= null)
			{
				ImageView flag = (ImageView) v.findViewById(R.id.flag);
				if(language.getImage_path() != null)
				{
					try {

						Bitmap bitmap = BitmapFactory.decodeStream(Options.this.getResources().getAssets().open(language.getImage_path()));
						flag.setImageBitmap(bitmap);
					} catch (Exception e) {

						e.printStackTrace();

					}
				}

				TextView language_lbl = (TextView) v.findViewById(R.id.language_lbl);
				if(language_lbl != null)
				{
					language_lbl.setText(language.getLanguage_name());
				}
			}
			return v;
		}
	}*/

}
