package model.utils;

import java.util.HashMap;
import java.util.Map;

import model.exception.BusinessLogicException;



public class ConfigMock extends Config {

	private Map<String,String> map;
	
	public ConfigMock(){
		map=new HashMap<String, String>();
		map.put("BROKEN_PRICE_REPAIR_COEF","0.03");
		map.put("DAMAGED_PRICE_REPAIR_COEF","0.02");
		map.put("DAMAGED_SALE_PRICE_COEF","0.5");
		map.put("DAMAGED_DEFECTIVE_PRODUCT_CHANCE","0.15");
		map.put("HEALTHY_SALE_PRICE_COEF","0.5");
		map.put("HEALTHY_DEFECTIVE_PRODUCT_CHANCE","0.05");
		map.put("CONVEYOR_SALE_PRICE","0");
		map.put("CONVEYOR_PURCHASE_PRICE","10");
		map.put("INPUT_SALE_PRICE","0");
		map.put("INPUT_PURCHASE_PRICE","0");
		map.put("DAYS_PER_MONTH","10");
		map.put("DAYS_PER_WEEK","3");
		map.put("DAYS_PER_MONTH","12");
		map.put("MAX_DAILY_FUND_RAISING","500");
		map.put("GROUND_SALE_PRICE_COEF","0.8");
		map.put("GROUND_RENT_PRICE_COEF","0.01");
	}
	@Override
	public String getValue(String st) {
		return map.get(st);
	}

	@Override
	public void loadConfig() throws Exception {
		throw new BusinessLogicException("Cannot load Mock!");
	}
	
	
	
}
