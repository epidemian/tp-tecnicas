package model.utils;

public interface Config {

	
	public void loadConfig() throws Exception;
	
	public String getValue(String st);
}
