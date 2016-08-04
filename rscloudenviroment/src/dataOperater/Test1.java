package dataOperater;

import java.sql.Date;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date times = new Date(115,6,7);
		String[] t = times.toString().split("-");
		System.out.println(t[0]+t[1]+t[2]);
		System.out.println(times);
	}

}
