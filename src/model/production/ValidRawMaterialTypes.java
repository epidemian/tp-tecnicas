package model.production;

import java.util.ArrayList;
import java.util.List;

public class ValidRawMaterialTypes {

	List<RawMaterialType> rawMaterialList;
	
	public ValidRawMaterialTypes(List<RawMaterialType> list){
		this.rawMaterialList=list;
	}
	
	public ValidRawMaterialTypes(){
		this.rawMaterialList=new ArrayList<RawMaterialType>();
	}
	
	public void add(RawMaterialType rawMaterialType){
		this.rawMaterialList.add(rawMaterialType);
	}

}
