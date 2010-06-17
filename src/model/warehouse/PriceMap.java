package model.warehouse;

import static model.utils.ArgumentUtils.*;
import java.util.HashMap;
import java.util.Map;

import model.exception.BusinessLogicException;
import model.production.Product;

public class PriceMap {

	Map<String,Integer> map;
	
	public PriceMap(){
		this.map=new HashMap<String, Integer>();
	}
	
	public PriceMap(Map<String,Integer> map){
		this.map=map;
	}
	
	public int getPrice(Product prod) {
		String name = prod.getProductType().getName();
		return getPriceByName(name);
	}
	
	public int getPriceByName(String name) {
		if(map.containsKey(name)){
			return map.get(name).intValue();
		}
		else{
			throw new BusinessLogicException("Product " + name + " does not exist");
		}
	}
	
	public void setMap(Map<String,Integer> map){
		checkNotNull(map, "price map");
		this.map=map;
	}
}
