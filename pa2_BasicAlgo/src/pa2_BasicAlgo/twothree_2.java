package pa2_BasicAlgo;

import java.io.*;
import java.util.Scanner;
class Node {
    String guide;
    int value;
}

class InternalNode extends Node {
    Node child0, child1, child2;
}

class LeafNode extends Node {
}

class TwoThreeTree {
   Node root;
   int height;

   TwoThreeTree() {
      root = null;
      height = -1;
   }
}

class WorkSpace {
// this class is used to hold return values for the recursive doInsert
// routine (see below)

   Node newNode;
   int offset;
   boolean guideChanged;
   Node[] scratch;
}

public class twothree_2 {
   public static BufferedWriter output;
   public static void main(String[] args) throws Exception  {
	   output= new BufferedWriter(new OutputStreamWriter(System.out,"ASCII"), 4096);
	   TwoThreeTree tree= new TwoThreeTree();
	   Scanner scanner = new Scanner(System.in);
	   int number1 = Integer.parseInt(scanner.nextLine());
	   for(int i=0;i<number1;i++) {
		   if(scanner.hasNext()) {
			   String str=scanner.nextLine();
			   int temp = str.indexOf(" ");
			   int query_type= Integer.parseInt(str.substring(0,temp));
		   	   if(query_type==1) {
			   	   int temp2 = str.indexOf(" ",temp+1);//second tab
			   	   String planet= str.substring(temp+1, temp2);
			   	   int entranceFee=Integer.parseInt(str.substring(temp2+1, str.length()));
			   	   insert(planet,entranceFee, tree);
		   	   }else if(query_type==2){
		   		   String planet2=null;
		   		   int temp2 = str.indexOf(" ",temp+1);//second tab
			   	   String planet= str.substring(temp+1, temp2);
			   	   if(str.lastIndexOf(" ")!=temp2){
			   		   planet2=str.substring(temp2+1, str.lastIndexOf(" "));
			   	   }
			   	   int increment= Integer.parseInt(str.substring(str.lastIndexOf(" ")+1, str.length()));
			   	   
			   	   if(planet.compareTo(planet2)>0) {
				   		addRange(planet2,planet, increment, tree);
				   }else{
				     	addRange(planet,planet2, increment, tree);
				   }     
		   	   }else {
		   		   String planet= str.substring(temp+1);
		   		   addAll(planet,tree,tree.height);
		   	   }
		   	   
		   } 
	   }
	   scanner.close();
	   output.flush();
   }
   static void addRange(String x, String y,int increment,TwoThreeTree tree) throws Exception {
         
	     if(tree.height==-1) {
	    	 return;
	     }
	     if(tree.height==0) {
			   if(x.compareTo(tree.root.guide)<=0&&y.compareTo(tree.root.guide)>=0) {
				   tree.root.value+=increment;
				   
			   }
			   return;			   
		}
	   Node[] listX=searchX(tree.root,x, tree.height);
	   Node[] listY=searchX(tree.root,y, tree.height);
	   Node divergedNode=null;
	   int divergedIndex=0;
	   	   
	   for(int i=0;i<listX.length;i++) {
		   if(listX[i].guide.compareTo(listY[i].guide)==0) {
			   divergedNode= listX[i];
			   divergedIndex=i;
		   }else {
			   break;
		   }
	   }
	   if(divergedIndex==listX.length-1) {
		   InternalNode parent=(InternalNode)listX[listX.length-1];
		   if(x.compareTo(y)==0||y==null) {
			   if(x.compareTo(parent.child0.guide)==0) {
				   parent.child0.value+=increment;
			   }else if(x.compareTo(parent.child1.guide)==0) {
				   parent.child1.value+=increment;
			   }else {
				   parent.child2.value+=increment;
	           }
	       }else {
			  if(x.compareTo(parent.child0.guide)<0&& y.compareTo(parent.child0.guide)>0&&y.compareTo(parent.child1.guide)<0) {
				  parent.child0.value+=increment;
			  }else if(x.compareTo(parent.child0.guide)<=0&&y.compareTo(parent.child1.guide)>=0) {
				  parent.child0.value+=increment;
				  parent.child1.value+=increment;
				  if(parent.child2!=null&&y.compareTo(parent.child2.guide)>=0)
					  parent.child2.value+=increment;
			  }else if(x.compareTo(parent.child1.guide)<=0&&y.compareTo(parent.child1.guide)>=0) {
				  parent.child1.value+=increment;
				  if(parent.child2!=null&&y.compareTo(parent.child2.guide)>=0)
					  parent.child2.value+=increment;
			  }else if(parent.child2!=null&&x.compareTo(parent.child2.guide)<=0&&y.compareTo(parent.child2.guide)>0){
				  parent.child2.value+=increment;
			  }
			  
		   }
		   return;
	   }
	   //conditionally print out x
	   InternalNode xParent=(InternalNode)listX[listX.length-1];

	   if(x.compareTo(xParent.child0.guide)<=0)
		   xParent.value+=increment;
	   else if(x.compareTo(xParent.child1.guide)<=0) {
		   xParent.child1.value+=increment;
		   if(xParent.child2!=null)
			   xParent.child2.value+=increment;
	   }else if(xParent.child2!=null&&x.compareTo(xParent.child2.guide)<=0) {
		   xParent.child2.value+=increment;
	   }
	   
	   
	   
	   for(int i=listX.length-1;i>1+divergedIndex;i--) {//process the node hanging off to the right of search path of X
		   InternalNode n1 = (InternalNode)listX[i-1];			   
	   	   if(listX[i]!=null&&n1.child0.guide.compareTo(listX[i].guide)==0) {
			   n1.child1.value+=increment;
			   if(n1.child2!=null)
			        n1.child2.value+=increment;
		   }else if(listX[i]!=null&&n1.child1.guide.compareTo(listX[i].guide)==0) {
			   if(n1.child2!=null)
				   n1.child2.value+=increment;
		   }		   
	   }

	   InternalNode n2=(InternalNode)divergedNode;
	   if(n2.child2!=null && n2.child0.guide.compareTo(listX[divergedIndex+1].guide)==0 && n2.child2.guide.compareTo(listY[divergedIndex+1].guide)==0) {//process the node in the middle if there is any
		   n2.child1.value+=increment;
	   }
	   for(int i=divergedIndex;i<listY.length-2;i++) {//process the node hanging off to the left of search path of Y
		   InternalNode n1 = (InternalNode)listY[i+1];
		   if(listX[i+2]!=null&&n1.child2!=null && n1.child2.guide.compareTo(listY[i+2].guide)==0) {
			   n1.child0.value+=increment;
			   n1.child1.value+=increment;
		   }else if(listX[i+2]!=null&&n1.child1.guide.compareTo(listY[i+2].guide)==0) {
			   n1.child0.value+=increment;
		   }		   
	   }
	   InternalNode yParent=(InternalNode)listY[listY.length-1];
	   if(yParent.child2!=null&&y.compareTo(yParent.child2.guide)>=0)
		   yParent.value+=increment;
	   else if(y.compareTo(yParent.child1.guide)>=0) {
		   yParent.child0.value+=increment;
		   yParent.child1.value+=increment;
		   
	   }else if(y.compareTo(yParent.child0.guide)>=0) {
		   yParent.child0.value+=increment;
	   }
   
	   
   }

static Node[] searchX(Node a, String x, int height) {
	   
	   Node[] list1=new Node[height];

	   InternalNode tempNode=(InternalNode)a;
	   
	   while(height!=0) {
		   list1[list1.length-height]=(Node)tempNode;
		   if(height==1) {
			   break;
		   }
		   if(x.compareTo(tempNode.child0.guide)<=0) {
			   tempNode=(InternalNode)tempNode.child0;
			   
		   }else if(tempNode.child2==null||x.compareTo(tempNode.child1.guide)<=0) {
			   tempNode=(InternalNode)tempNode.child1;
			   
		   }else {
			   tempNode=(InternalNode)tempNode.child2;
		   }
		   height--;
	   }
	   return list1;
	   
}

   static void insert(String key, int value, TwoThreeTree tree) {
   // insert a key value pair into tree (overwrite existsing value
   // if key is already present)

      int h = tree.height;

      if (h == -1) {
          LeafNode newLeaf = new LeafNode();
          newLeaf.guide = key;
          newLeaf.value = value;
          tree.root = newLeaf; 
          tree.height = 0;
      }
      else {
         WorkSpace ws = doInsert(key, value, tree.root, h);

         if (ws != null && ws.newNode != null) {
         // create a new root

            InternalNode newRoot = new InternalNode();
            if (ws.offset == 0) {
               newRoot.child0 = ws.newNode; 
               newRoot.child1 = tree.root;
            }
            else {
               newRoot.child0 = tree.root; 
               newRoot.child1 = ws.newNode;
            }
            resetGuide(newRoot);
            tree.root = newRoot;
            tree.height = h+1;
         }
      }
   }

   static WorkSpace doInsert(String key, int value, Node p, int h) {
   // auxiliary recursive routine for insert

      if (h == 0) {
         // we're at the leaf level, so compare and 
         // either update value or insert new leaf

         LeafNode leaf = (LeafNode) p; //downcast
         int cmp = key.compareTo(leaf.guide);

         if (cmp == 0) {
            leaf.value = value; 
            return null;
         }

         // create new leaf node and insert into tree
         LeafNode newLeaf = new LeafNode();
         newLeaf.guide = key; 
         newLeaf.value = value;

         int offset = (cmp < 0) ? 0 : 1;
         // offset == 0 => newLeaf inserted as left sibling
         // offset == 1 => newLeaf inserted as right sibling

         WorkSpace ws = new WorkSpace();
         ws.newNode = newLeaf;
         ws.offset = offset;
         ws.scratch = new Node[4];

         return ws;
      }
      else {
         InternalNode q = (InternalNode) p; // downcast
         int pos;
         WorkSpace ws;

         if (key.compareTo(q.child0.guide) <= 0) {
            pos = 0; 
            q.child0.value+=q.value;            
            q.child1.value+=q.value;
            if(q.child2!=null)
            	q.child2.value+=q.value;
            q.value=0;
            ws = doInsert(key, value, q.child0, h-1);
            
         }
         else if (key.compareTo(q.child1.guide) <= 0 || q.child2 == null) {
            pos = 1;
            q.child0.value+=q.value;
            q.child1.value+=q.value;
            if(q.child2!=null)
            	q.child2.value+=q.value;
            q.value=0;
            ws = doInsert(key, value, q.child1, h-1);
         }
         else {
            pos = 2; 
            q.child1.value+=q.value;
            q.child0.value+=q.value;
            q.child2.value+=q.value;
            q.value=0;
            ws = doInsert(key, value, q.child2, h-1);
         }

         if (ws != null) {
            if (ws.newNode != null) {
               // make ws.newNode child # pos + ws.offset of q

               int sz = copyOutChildren(q, ws.scratch);
               insertNode(ws.scratch, ws.newNode, sz, pos + ws.offset);//copy all children into x[]
               if (sz == 2) {// if originally 2 children
                  ws.newNode = null;
                  ws.guideChanged = resetChildren(q, ws.scratch, 0, 3);
               }
               else {
                  ws.newNode = new InternalNode();
                  ws.offset = 1;
                  resetChildren(q, ws.scratch, 0, 2);
                  resetChildren((InternalNode) ws.newNode, ws.scratch, 2, 2);
               }
            }
            else if (ws.guideChanged) {
               ws.guideChanged = resetGuide(q);
            }
         }

         return ws;
      }
   }


   static int copyOutChildren(InternalNode q, Node[] x) {
   // copy children of q into x, and return # of children

      int sz = 2;
      x[0] = q.child0; x[1] = q.child1;
      if (q.child2 != null) {
         x[2] = q.child2; 
         sz = 3;
      }
      return sz;
   }

   static void insertNode(Node[] x, Node p, int sz, int pos) {
   // insert p in x[0..sz) at position pos,
   // moving existing extries to the right

      for (int i = sz; i > pos; i--)
         x[i] = x[i-1];

      x[pos] = p;
   }

   static boolean resetGuide(InternalNode q) {
   // reset q.guide, and return true if it changes.

      String oldGuide = q.guide;
      if (q.child2 != null)
         q.guide = q.child2.guide;
      else
         q.guide = q.child1.guide;

      return q.guide != oldGuide;
   }


   static boolean resetChildren(InternalNode q, Node[] x, int pos, int sz) {
   // reset q's children to x[pos..pos+sz), where sz is 2 or 3.
   // also resets guide, and returns the result of that

      q.child0 = x[pos]; 
      q.child1 = x[pos+1];

      if (sz == 3) 
         q.child2 = x[pos+2];
      else
         q.child2 = null;

      return resetGuide(q);
   }

   	static void addAll(String x, TwoThreeTree tree, int height) throws Exception {
 	   int sum=tree.root.value;
 	   if(tree.height<0) {
 		   sum=-1;
 		   output.write(sum+"\n");
 		   return;
 	   }
 	   if(tree.height==0) {  
 		   if(tree.root.guide.compareTo(x)==0) {
 			   
 		   }
 		   else {
			   sum=-1;
		    }
 		   output.write(sum+"\n");
 		   return;
 	   }

   	   InternalNode tempNode=(InternalNode)tree.root;
 	   while(height!=1) {
 		   if(x.compareTo(tempNode.child0.guide)<=0) {
 			   sum=sum+tempNode.child0.value;
 			   tempNode=(InternalNode)tempNode.child0;	  
 		   }else if(tempNode.child2==null||x.compareTo(tempNode.child1.guide)<=0) {
 			   sum=sum+tempNode.child1.value;
 			   tempNode=(InternalNode)tempNode.child1;

 		   }else {
 			   sum=sum+tempNode.child2.value;
 			   tempNode=(InternalNode)tempNode.child2;
 			   
 		   }
 		   height--;
 	   }
 	   if(x.compareTo(tempNode.child0.guide)==0) {
 		   sum+=tempNode.child0.value;	
 	   }else if(x.compareTo(tempNode.child1.guide)==0) {
 		  sum+=tempNode.child1.value;	
 	   }else if(tempNode.child2!=null&&x.compareTo(tempNode.child2.guide)==0) {
 		  sum+=tempNode.child2.value;	
 	   }else {
 		   sum=-1;
 	   }
 	   output.write(sum+"\n");
 	   
   	}
}


