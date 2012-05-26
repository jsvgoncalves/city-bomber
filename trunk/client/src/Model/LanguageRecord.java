package Model;

public class LanguageRecord {
	private String language_name;
	private String image_path;
	private String file_path;
	
	public LanguageRecord(String language_name, String image_path, String file_path)
	{
		this.file_path = file_path;
		this.image_path = image_path;
		this.language_name = language_name;
	}
	
	public String getLanguage_name() {
		return language_name;
	}
	public void setLanguage_name(String language_name) {
		this.language_name = language_name;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
}
