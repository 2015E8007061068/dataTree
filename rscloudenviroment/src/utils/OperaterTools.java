package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperaterTools {

	public void OperaterTools(){
		
	}
	
	
	public List<String> removeDuplicateWithOrder (List<String> sources){
		
		List<String> reslut = new ArrayList<String>();
		 
		for(String s: sources){
		    if(Collections.frequency(reslut, s) < 1) reslut.add(s);
		}
		return reslut;
	} 
	
}
