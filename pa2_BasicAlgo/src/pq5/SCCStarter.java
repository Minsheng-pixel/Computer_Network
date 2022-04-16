package pq5;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;

//Data structure for a node in a linked list
class Item {
	int data;
	Item next;
	
	Item(int data, Item next) {
	   this.data = data;
	   this.next = next;
	}
}

//Data structure for representing a graph
class Graph {
	int n;  // # of nodes in the graph
	
	Item[] A; 
	// For u in [0..n), A[u] is the adjecency list for u
	
	Graph(int n) {
	   // initialize a graph with n vertices and no edges
	   this.n = n;
	   A = new Item[n];
	}
	
	void addEdge(int u, int v) {
	   // add an edge u -> v to the graph
	
	   A[u] = new Item(v, A[u]);
	}
}

//Data structure holding data computed by DFS
class DFSInfo {
	int k; 
	// # of trees in DFS forest
	
	int[] T;
	// For u in [0..n), T[u] is initially 0, but when DFS discovers
	// u, T[u] is set to the index (which is in [1..k]) of the tree 
	// in DFS forest in which u belongs.
	
	int[] L;
	// List of nodes in order of decreasing finishing time
	
	int count;
	// initially set to n, and is decremented every time
	// DFS finishes with a node and is recorded in L
	
	DFSInfo(Graph graph) {
	   int n = graph.n;
	   k = 0;
	   T = new int[n];
	   L = new int[n];
	   count = n;
	}
}


//your "main program" should look something like this:

public class SCCStarter {
	public static BufferedWriter output;
	public static int n;
	public static HashMap<Integer, Integer> map;
	
	static void recDFS(int u, Graph graph, DFSInfo info) {
		if(graph.A[u]==null) {
			info.T[u]=info.k;
			info.L[info.count-1] =u;
			info.count--;
			return;
		}
		Item a= graph.A[u];
		while (true) {
			if(info.T[a.data]==-1) {
				info.T[a.data]=info.k;
				recDFS(a.data,graph,info);	
			}else if(a.data==u||info.T[a.data]!=-1) {
	//			break;
			}
			if (a.next==null) {
				break;
			}else {
				a=a.next;
			}
		}
		info.L[info.count-1] =u;
		info.count--;
		return;
	}
	
	static DFSInfo DFS(int[] order, Graph graph) {
	   // performs a "full" DFS on given graph, processing 
	   // nodes in the order specified (i.e., order[0], order[1], ...)
	   // in the main loop.  
		DFSInfo info = new DFSInfo(graph);
		for (int a=0 ;a<info.T.length;a++) {
	    	info.T[a]=-1;
	    }
		for (int i=0; i< graph.A.length; i++) {
			if(info.T[order[i]]==-1) {	
				info.T[order[i]]=info.k;// Set the starter node's T value to be not 1 so it is marked visited
				recDFS(order[i],graph,info);
				info.k++;
			}
		}	
	   // performs a "full" DFS on given graph	
	   return info;
	}
	
	static boolean[] computeSafeNodes(Graph graph, DFSInfo info) {
	   // returns a boolean array indicating which nodes
	   // are safe nodes.  The DFSInfo is that computed from the
	   // second DFS.
	   boolean[][] com_edge= new boolean[info.k][info.k];
	   Graph scc_graph= new Graph(info.k);

	   for (int i=0;i<graph.n;i++) {
		   Item a_i;
		   int a;
		   if(graph.A[i]!=null) {
			   a=graph.A[i].data;
			   a_i=graph.A[i];
		   }else {
			   continue;
		   }
		   while (true) {
			   if (info.T[a]!=info.T[i]&&com_edge[info.T[i]][info.T[a]]==false) {
				   scc_graph.addEdge(info.T[i], info.T[a]);
				   com_edge[info.T[i]][info.T[a]]=true;
				   //add a edge between component
			   }
			   if(a_i.next==null) {
				   break;
			   }
			   a_i= a_i.next;
			   a=a_i.data;
		   }
	   }
	   boolean[] safeNode= new boolean[n];
	   boolean[] scc_0out= new boolean[info.k];
	   for (int i=0;i<info.k;i++){
		   for (int j=0;j<info.k;j++){
			   if(com_edge[i][j]==true){
				   scc_0out[i]=true;
				   break;
			   }
		   }
	   }
	   for (int j=0;j<n;j++) {
		   if(scc_0out[info.T[j]]==false) {
			   safeNode[j]=true;
		   }
	   }
	   
	   
/*	   
	   DFSInfo sccinfo= new DFSInfo(scc_graph);
	   int[] scc_order=new int[info.k];
	   for(int i=0;i<info.k;i++) {
		   scc_order[i]=info.k-i-1;
	   }


	   sccinfo=DFS(scc_order,scc_graph);
	   boolean[] safeNode= new boolean[n];
	  
	   if (sccinfo.k>1) {
		   return safeNode;
	   }else {
		   int component_index=info.k-1;
		   for (int i=0;i<n;i++) {
			   if(info.T[i]==component_index) {
				   safeNode[i]=true;
			   }
		   }
	   }
	   */
	   
	   return safeNode;
	}
	
	static Graph reverse(Graph graph) {
	   // returns the reverse of the given graph
	   return null;
	}
	
	public static void main(String[] args) throws Exception {
		output= new BufferedWriter(new OutputStreamWriter(System.out,"ASCII"), 4096);
		map= new HashMap<Integer, Integer>();
		Scanner scanner = new Scanner(System.in);
		int number_vertice = scanner.nextInt();
		int number_edge = scanner.nextInt();
		n= number_vertice;
		Graph graph= new Graph(number_vertice);
		Graph reversed_graph= new Graph(number_vertice);
		if(number_edge==0) {
			for (int i=0;i<n;i++) {
		    		output.write(i+" ");
		    }
			scanner.close();
			return;
		}
		for(int i=0;i<number_edge;i++) {
			int node1=0;
			int node2=0;
		    if(scanner.hasNext()) {
		    	node1=scanner.nextInt();
		    }if(scanner.hasNext()) {
		    	node2=scanner.nextInt();
		    }
		    	graph.addEdge(node1, node2);
		    	reversed_graph.addEdge(node2,node1);

		    
		}
		int normal_order[] =new int[n];
		for (int a=0; a<n;a++) {
			normal_order[a]=a;
			
		}
	    DFSInfo dInfo;
	    dInfo = DFS(normal_order,reversed_graph);
	    DFSInfo d2Info;
	    d2Info = DFS(dInfo.L,graph);
	    boolean[] safeList= new boolean[n];
	    safeList= computeSafeNodes(graph, d2Info);
	    for (int i=0;i<n;i++) {
	    	if (safeList[i]==true) {
	    		output.write(i+" ");
	    	}
	    	
	    }
		output.flush();
		scanner.close();
	}

}
