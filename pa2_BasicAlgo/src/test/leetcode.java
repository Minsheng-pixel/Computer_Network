package test;
import java.util.*;

public class leetcode {
		public static void main(String[] args) {
			leetcode n = new leetcode();
			System.out.println(n.generateParenthesis(3));
			
		}
	    public List<String> generateParenthesis(int n) {
	        List<String> arr = new ArrayList<String>();
	        String str = "";
	        sol(arr,n,n, n , str);
	        return arr;
	    }
	    public void sol(List<String> arr, int open, int close, int n, String str){
	        if (open + close == 0){
	            arr.add(str);
	            return ;
	        }
	        if (open <= n && open > 0 ){
	            //str = str+ "(";
	            sol(arr,open-1,close, n , str + "(");        
	        }
	        if (close>open){
	            //str = str +")";
	            sol(arr,open,close-1, n , str + ")");
	        }
	    }

}
