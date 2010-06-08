package persistence;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;


import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.MachineType;
import model.production.ProductType;
import model.production.ProductionSequence;
import model.production.RawMaterialType;
import model.production.RawMaterials;
import model.production.ValidProductionSequences;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import persistenceLayer.InvalidTagException;
import persistenceLayer.NoProductTypeDefinedInSequenceException;
import persistenceLayer.ProductionSequenceTechnologyPersistent;

public class ProductionSequenceTechnologyPersistentTest 
					extends XMLPersistentTest{
	
	NewProductionSequenceTechnology prodSequenceTechnologyAxe;
	NewProductionSequenceTechnology prodSequenceTechnologySword;
	
	@Before
	public void setUp(){
		super.setUp();
		
		this.prodSequenceTechnologyAxe=
			createProductionSequenceTechnologyAxe();
		this.prodSequenceTechnologySword=
			createProductionSequenceTechnologySword();
	}
	
	public static NewProductionSequenceTechnology 
				createProductionSequenceTechnologyAxe(){
		
		RawMaterials rawMaterials=new RawMaterials();
		
		rawMaterials.store(new RawMaterialType("wood"), 75);
		rawMaterials.store(new RawMaterialType("gold"), 100);
		rawMaterials.store(new RawMaterialType("stone"), 200);
		
		List<MachineType> list=new ArrayList<MachineType>();
		
		list.add(new MachineType("LumberProcess"));
		list.add(new MachineType("Oven"));
		list.add(new MachineType("Forge"));
		
		ProductType prodType=new ProductType("Axe");
		
		String desc="Weapon mastered by the French (?)";
		int cost=10;
		
		return new NewProductionSequenceTechnology
			(new ProductionSequence(list,rawMaterials), prodType, 
				new ValidProductionSequences(), desc, cost);
	}
	
	public static NewProductionSequenceTechnology 
			createProductionSequenceTechnologySword(){
		
		RawMaterials rawMaterials=new RawMaterials();
		
		rawMaterials.store(new RawMaterialType("gold"), 500);	
		
		List<MachineType> list=new ArrayList<MachineType>();
		
		list.add(new MachineType("Forge"));
		
		ProductType prodType=new ProductType("Sword");
		
		String desc="Powerful as the excaliburg";
		int cost=30;
		
		return new NewProductionSequenceTechnology
		(new ProductionSequence(list,rawMaterials), prodType, 
			new ValidProductionSequences(), desc, cost);
		}
	
	
	
	
	@Ignore
	public void validProductionSequenceTechnologyAxe()
		throws DocumentException, InvalidTagException, 
		SecurityException, ClassNotFoundException, NoSuchMethodException, 
		NoProductTypeDefinedInSequenceException{		
			
			Document doc= reader.read
				("test/persistence/ValidProductionSequenceTechnologyAxe.xml");
			Element element=doc.getRootElement();
			
			NewProductionSequenceTechnology productionSequenceTechnologyRecovered = 
				ProductionSequenceTechnologyPersistent.buildFromXML(element);
			
			
			assertEquals(productionSequenceTechnologyRecovered,
					this.prodSequenceTechnologyAxe);
					
			
		
	}
	
	@Ignore
	public void validProductionSequenceTechnologySword()
		throws DocumentException, InvalidTagException, 
		SecurityException, ClassNotFoundException, NoSuchMethodException, 
		NoProductTypeDefinedInSequenceException{		
			
			Document doc= reader.read
				("test/persistence/ValidProductionSequenceTechnologySword.xml");
			Element element=doc.getRootElement();
			
			NewProductionSequenceTechnology productionSequenceTechnologyRecovered = 
				ProductionSequenceTechnologyPersistent.buildFromXML(element);
			
			
			assertEquals(productionSequenceTechnologyRecovered,
					this.prodSequenceTechnologySword);
					
			
		
	}
	
	@Test ( expected = NoProductTypeDefinedInSequenceException.class )
	public void ProductionSequenceTechnologyThatDoesNotDefineProduct()
		throws DocumentException, InvalidTagException, 
		SecurityException, ClassNotFoundException, NoSuchMethodException, 
		NoProductTypeDefinedInSequenceException{		
			
			Document doc= reader.read("test/persistence/" +
						"ProductionSequenceTechnologyThatDoesNotDefineProduct.xml");
			Element element=doc.getRootElement();
			
			NewProductionSequenceTechnology productionSequenceTechnologyRecovered = 
				ProductionSequenceTechnologyPersistent.buildFromXML(element);
			
			// It should had thrown the exception.
			fail();
						
	}
	
}
