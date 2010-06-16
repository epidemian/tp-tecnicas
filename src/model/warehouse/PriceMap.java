package model.warehouse;

import java.util.HashMap;
import java.util.Map;

import model.production.Product;

public class PriceMap {

	Map<String,Integer> map;
	
	public PriceMap(){
		this.map=new HashMap<String, Integer>();
	}
	
	public PriceMap(Map<String,Integer> map){
		this.map=map;
	}
	
	public int getPrice(Product prod) throws PriceProductDoesNotExistException{
		if(map.containsKey(prod.getProductType().getName())){
			return map.get(prod.getProductType().getName()).intValue();
		}
		else{
			throw new PriceProductDoesNotExistException();
		}
	}
}
