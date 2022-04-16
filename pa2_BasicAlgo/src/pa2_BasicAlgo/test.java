package pa2_BasicAlgo;

class W{
	String name;
    int score;
    W(String name){
    	this.name=name;
    }
}
public class test {
	public static void main(String args[]) {
		as();
	}
	static int as() {
		    int[] arr= {1,2,2,2,3,3,4,4,4,4,5,5,5,5,5};
		        
	        int a=0; 
	        int b=1;
	        while(b< arr.length-1){
	            if (arr[b]!= arr[a] && (b-a)>1){
	                arr[a]=arr[b];
	                a++;
	            }else if (arr[b]!= arr[a]){
	                a++;
	            }
	            b++;
	        }
	        System.out.println(a);
	       
	        return a;
		    
		     
	}

}
