package persistence.price;

import org.dom4j.Element;

import persistence.exceptions.InvalidTagException;

public class PriceItemPersistent {

	private String name;
	private int price;
	
	static String TAG_NAME="PriceItem";
	static String TAG_ATR_ID="id";
	static String TAG_ATR_PRICE="price";
	
	public PriceItemPersistent(String name2, int price2) {
		this.name=name2;
		this.price=price2;
	}

	public static PriceItemPersistent buildFromXML(Element elem) 
		throws InvalidTagException{
			
		
		if (!elem.getName().equals(PriceItemPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		String name=elem.attributeValue(TAG_ATR_ID);
		int price=Integer.valueOf(elem.attributeValue(TAG_ATR_PRICE));
			
		return new PriceItemPersistent(name, price);
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

}
