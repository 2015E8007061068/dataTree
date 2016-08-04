package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperaterTools<E> {

	public void OperaterTools(){
		
	}
	
	
	public List<E> removeDuplicateWithOrder (List<E> sources){
		
		List<E> reslut = new ArrayList<E>();
		 
		for(E s: sources){
		    if(Collections.frequency(reslut, s) < 1) reslut.add(s);
		}
		return reslut;
	} 
	
}
