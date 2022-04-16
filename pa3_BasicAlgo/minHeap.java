package PA_BasicAlgo;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Warrior{
	String name;
    int score;
    Warrior(String name){
    	this.name=name;
    }
}


public class minHeap {
       public static BufferedWriter output;
       public static HashMap<String, Integer> map;
       public static Warrior[] heap;
	   public void main(String args[]) throws Exception {	   
			output= new BufferedWriter(new OutputStreamWriter(System.out,"ASCII"), 4096);
			Scanner scanner = new Scanner(System.in);
			int number1 = Integer.parseInt(scanner.nextLine()); 
			map=new HashMap<String, Integer>();
			heap= new Warrior[number1];
			for(int i=0;i<number1;i++) {
				if(scanner.hasNext()) {
				   String str=scanner.nextLine();
				   int temp = str.indexOf(" ");
				   String name= str.substring(0,temp);
				   Warrior warrior=new Warrior(name);
				   warrior.score= Integer.parseInt(str.substring(temp+1,str.length()));
				  // map.put(name,initial_score);
				   push(warrior, heap);
				}
			}
			int number2 = Integer.parseInt(scanner.nextLine());   
			for(int i=0;i<number2;i++) {
				if(scanner.hasNext()) {
				   String str=scanner.nextLine();
				   int temp = str.indexOf(" ");
				   int query_type = Integer.parseInt(str.substring(0,temp));
				   if(query_type==1) {
					   int temp2 = str.indexOf(" ",temp+1);
					   String name_improvement=str.substring(temp+1,temp2);
					   Warrior warrior=new Warrior(name_improvement);
					   int score_improvement=Integer.parseInt(str.substring(temp2+1,str.length()));
					   improvement(warrior, score_improvement);
				   }else {
					   int temp3 = str.indexOf(" ");
					   int bar= Integer.parseInt(str.substring(0,temp3));
					   pop(bar);
					   output.write(countingSoilder()+"\n");
				   }
				}
			}
			scanner.close();
			
	   
	   }
	   public static void push(Warrior warrior, Warrior[] heap) {
		   int position=0;
		   for(int i=0;i<heap.length;i++) {
			   if(heap[i]==null);
				   heap[i]=warrior;
				   position=i;
		   }
		   while (position>=1) {
			   int child_score = heap[position].score;
			   int parent_score = heap[(position+1)/2-1].score;
			   if(child_score < parent_score) {
				   Warrior temp= heap[position];
				   heap[position]=heap[(position+1)/2-1];
				   heap[(position+1)/2-1]=temp;
			   }else {
				   break;
			   }
			   position=(position+1)/2-1;		   
		   }
		   map.put(warrior.name,position);
	   }
	   public static void improvement(Warrior warrior, int improvement) {
		   int position= map.get(warrior.name);
		   warrior.score+=improvement;
		   while (position>=1) {
			   int child_score = heap[position].score;
			   int parent_score = heap[(position+1)/2-1].score;
			   if(child_score < parent_score) {
				   Warrior temp= heap[position];
				   heap[position]=heap[(position+1)/2-1];
				   heap[(position+1)/2-1]=temp;
			   }else {
				   break;
			   }
			   position=(position+1)/2-1;		   
		   }
		   map.put(warrior.name,position);	   
	   }
	   public static void pop(int bar) {
		   int lastElement=0;
		   for(int i=0;i<heap.length;i++) {
			   if(heap[i]==null);
				   lastElement=i-1;
		   }
		   while(true){
			    Warrior min=heap[0];
		   		if(min.score<bar) {
				   map.remove(heap[0].name);
				   heap[0]=heap[lastElement];
				   heap[lastElement]=null;
				   int pos=map.get(heap[0].name);
				   while(heap[2*(pos)+1]!=null ||heap[2*(pos)+2]!=null ) {
				       if(heap[2*(pos)+2]==null&&heap[pos].score<heap[2*pos+1].score) {
				    	   Warrior temp= heap[pos];
						   heap[pos]=heap[2*(pos)+1];
						   heap[2*(pos)+1]=temp;
				       }else if(heap[2*(pos)+2].score<heap[2*(pos)+1].score&&heap[pos].score<heap[2*pos+2].score){
						   Warrior temp= heap[pos];
						   heap[pos]=heap[2*(pos)+2];
						   heap[2*(pos)+2]=temp;
					   }else if(heap[2*(pos)+1].score<heap[2*(pos)+2].score&&heap[pos].score<heap[2*pos+1].score) {
						   Warrior temp= heap[pos];
						   heap[pos]=heap[2*(pos)+1];
						   heap[2*(pos)+1]=temp;
					   }
				       else {
						   break;
					   }
				       lastElement--;
				   }
			   }else {
				   return;
			   }
		   }
	   }
	   public static int countingSoilder() {
		   int count = 0;
		   for(int i=0;i<heap.length;i++) {
			   if(heap[i]==null);
				  count=i-1;
		   }
		   return count;
	   }
	   	 
}
		