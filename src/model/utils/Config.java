package model.utils;

public abstract class Config {

	
	public abstract void loadConfig() throws Exception;
	
	public abstract String getValue(String st);

	public Integer getIntegerValue(String string){
		return Integer.valueOf(this.getValue(string));
	}
	
	public Float getFloatValue(String string){
		return Float.valueOf(this.getValue(string));
	}
	
	public Double getDoubleValue(String string){
		return Double.valueOf(this.getValue(string));
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
