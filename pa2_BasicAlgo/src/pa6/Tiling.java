package pa6;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;


public class Tiling {
	static String[] tile_arr;
	static int [][] arr;
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		String target_string = scanner.nextLine();
		int number_tile = Integer.parseInt(scanner.nextLine());
		tile_arr= new String[number_tile];
		arr = new int[target_string.length()+1][number_tile+1];
		for(int i=0;i<number_tile;i++) {
			if(scanner.hasNext()) {
		    	tile_arr[i]=scanner.nextLine();
		    }else {
		    	scanner.close();
		    }
		}
		
	}
	public static void sol(String t, String[][] tile_arr1) {
		
	}

}
