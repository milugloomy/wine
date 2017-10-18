package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTest {

	int pageSize=10;
	int pageNo=5;
	
	public static void main(String[] args) {
		List<Integer> list=new ArrayList<Integer>();
		for(int i=0;i<100;i++)
			list.add(i);
		
		int toIndex=list.size()-(5-1)*10;
		int fromIndex=list.size()-5*10;
		
		List<Integer> subList=list.subList(fromIndex, toIndex);
		Collections.reverse(subList);
		System.out.println(list);
		System.out.println(subList);
	}

}
