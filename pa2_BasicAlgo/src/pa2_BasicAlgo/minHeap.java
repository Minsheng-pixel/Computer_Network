package pa2_BasicAlgo;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;

class Warrior{
	String name;
    long score;
    Warrior(String name,long score){
    	this.name=name;
    	this.score=score;
    }
}

public class minHeap {
       public static BufferedWriter output;
       public static HashMap<String, Integer> map;
       public static Warrior[] heap;
       public static int existing_soilder;
       
	   public static void main(String args[]) throws Exception {	   
			output= new BufferedWriter(new OutputStreamWriter(System.out,"ASCII"), 4096);
			Scanner scanner = new Scanner(System.in);
		    int number1=0;
			if (scanner.hasNext()) {
		        number1 = Integer.parseInt(scanner.nextLine()); 
			}
			map=new HashMap<String, Integer>();
			heap= new Warrior[number1];
			
			for(int i=0;i<number1;i++) {
				if(scanner.hasNext()) {
				   String str=scanner.nextLine();
				   int temp = str.indexOf(" ");
				   String name= str.substring(0,temp);
				   Warrior warrior=new Warrior(name, Long.parseLong(str.substring(temp+1,str.length())));
				   push(warrior, heap);
				   existing_soilder++;
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
					   Long score_improvement=Long.parseLong(str.substring(temp2+1,str.length()));
					   if(map.size()!=0) {
						   if(map.get(name_improvement)!=null) {
						        Warrior q=heap[map.get(name_improvement)];    
					   			improvement(q, score_improvement);
						   }
				      }
				   }else {
					   long bar= Long.parseLong(str.substring(temp+1,str.length()));
					   pop(bar);
					   output.write(existing_soilder+"\n");
				    }
				   
				}
			}
			scanner.close();
			output.flush();
			
	   
	   }
	   public static void push(Warrior warrior, Warrior[] heap) {
		 
		   heap[existing_soilder]=warrior;
		   map.put(warrior.name,existing_soilder);
           int position = existing_soilder;
		   while (position>=1) {
			   long child_score = heap[position].score;
			   long parent_score = heap[(position+1)/2-1].score;
			   if(child_score < parent_score) {
				   int tempPoistion=map.get(heap[position].name);
				   map.put(heap[position].name,map.get(heap[(position+1)/2-1].name));
				   map.put(heap[(position+1)/2-1].name,tempPoistion);
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
	   public static void improvement(Warrior warrior, long improvement) {
		   int position= map.get(warrior.name);
		   warrior.score+=improvement;
		   if(existing_soilder==1) {
			   return;
		   }
		   while (2*position+1<existing_soilder) {
			   long child_score2=-1;
			   long parent_score = heap[position].score;
			   long child_score1 = heap[2*position+1].score;
			   if(2*position+2<existing_soilder) {
				   child_score2 = heap[2*position+2].score;
			   }
			   Warrior w;
			   if (child_score2!=-1&&child_score2<child_score1) {
				   w=heap[2*position+2];
			   }else {
				   w=heap[2*position+1];
			   }
			   if(parent_score>w.score) {//swap
				   Warrior temp1= heap[position];
				   heap[position]=w;
				   heap[map.get(w.name)]=temp1;
				   int temp=map.get(w.name);
				   map.put(w.name,position);
				   map.put(temp1.name,temp);
				   position=temp;
			   }else {
				   break;
			   }
		   }
		   
	   }
	   public static void pop(long bar) {
		   while(true){
			    if(heap[0]==null||heap==null) {
			    	break;
			    }
			    Warrior min=heap[0];
		   		if(min.score<bar) {
				   map.remove(heap[0].name);
				   heap[0]=heap[existing_soilder-1];
				   heap[existing_soilder-1]=null;
				   existing_soilder--;
				   int position=0;
				   if(existing_soilder==1) {
					   map.put(heap[0].name,0);
				   }
				   while (2*position+1<existing_soilder) {
					   long child_score2=-1;
					   long parent_score = heap[position].score;
					   long child_score1 = heap[2*position+1].score;
					   if(2*position+2<existing_soilder) {
						   child_score2 = heap[2*position+2].score;
					   }
					   Warrior w;
					   if (child_score2!=-1&&child_score2<child_score1) {
						   w=heap[2*position+2];
					   }else {
						   w=heap[2*position+1];
					   }
					   if(parent_score>w.score) {//swap
						   Warrior temp1= heap[position];
						   heap[position]=w;
						   heap[map.get(w.name)]=temp1;
						   int temp=map.get(w.name);
						   map.put(w.name,position);
						   map.put(temp1.name,temp);
						   position=temp;
					   }else {
						   break;
					   }
				   }
		   		}else {
				     break;
				}
       		}
		} 
}
		