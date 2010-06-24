package model.warehouse;

import java.util.Collections;
import static model.utils.ArgumentUtils.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import model.exception.BusinessLogicException;
import model.production.Product;

public class MarketPrices {

	Map<String,Integer> map;
	
	public MarketPrices(){
		this.map=new HashMap<String, Integer>();
	}
	
	public MarketPrices(Map<String,Integer> map){
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

        public Map<String,Integer> getMap(){
            return Collections.unmodifiableMap(map);
        }

	public void setMap(Map<String,Integer> map){
		checkNotNull(map, "price map");
		this.map=map;
	}
}
