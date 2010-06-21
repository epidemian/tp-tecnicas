package persistence;

import java.util.Map;

import persistence.exceptions.InvalidTagException;

public interface Config {

	
	public void loadConfig() throws Exception;
	
	public String getValue(String st);
}
