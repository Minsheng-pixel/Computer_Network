package pa2_BasicAlgo;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
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

	// node colors
	static final int WHITE = 0;
	static final int GRAY  = 1;
	static final int BLACK = 2;
	
	int[] color;  // variable storing the color
	              // of each node during DFS
	              // (WHITE, GRAY, or BLACK)
	
	int[] parent; // variable storing the parent 
	              // of each node in the DFS forest
	
	int d[];      // variable storing the discovery time 
	              // of each node in the DFS forest
	
	int f[];      // variable storing the finish time 
	              // of each node in the DFS forest
	
	
	DFSInfo(Graph graph) {
	   int n = graph.n;
	   color = new int[n];
	   parent = new int[n];
	   d = new int[n];
	   f = new int[n];
	}
}


//your "main program" should look something like this:

public class DFSStarter {
	public static BufferedWriter output;
	public static int time_stamp=0;
	static void recDFS(int u, Graph graph, DFSInfo info) {
		if(graph.A[u]==null) {
			info.color[u] = info.BLACK;
			info.d[u] = ++time_stamp;
			info.f[u] = ++time_stamp;
//			System.out.println(info.f[u]);
			return;
		}
		info.color[u] = info.GRAY;
		info.d[u] = ++time_stamp;
		Item a= graph.A[u];
		while (true) {
			if(info.color[a.data]==info.WHITE) {
				info.parent[a.data]=u;
				recDFS(a.data,graph,info);	
				
			}else if(a.data==u) {
//				if(info.parent[a.data]==-1) {
//					info.parent[a.data]=u;// you cannot change the parents twice so a backedge may reroute to another cycle
//				//skip
//				}	
				break;
			}
			else if(info.color[a.data]==info.GRAY) {
//				if(info.parent[a.data]==-1) {
//					info.parent[a.data]=u;// /you cannot change the parents twice so a backedge may reroute to another cycle
				//skip
//				}	
				break;
			}

			if (a.next==null) {
				break;
			}else {
				a=a.next;
			}
		}
		info.f[u]= ++time_stamp;
		info.color[u]=info.BLACK;
		return;
	   // perform a recursive DFS, starting at u
	}
	
	static DFSInfo DFS(Graph graph) {
		DFSInfo info = new DFSInfo(graph);
	    for (int a=0 ;a<info.parent.length;a++) {
	    	info.parent[a]=-1;
	    }
		for (int i=0; i< graph.A.length; i++) {
			if(info.color[i]==0&& graph.A[i]!=null) {
//				System.out.println(graph.A[i].data);
				
				recDFS(i,graph,info);
			}
		}	
	   // performs a "full" DFS on given graph
	
	   return info;
	}
	
	static Item findCycle(Graph graph, DFSInfo info) {
		
		for (int i=0; i< graph.A.length; i++) {
			if(graph.A[i]==null)
				continue;
			Item a=graph.A[i];
			while(true) { 
				if(info.d[a.data]<=info.d[i]&&(info.d[i]<info.f[i])&&(info.f[i]<=info.f[a.data])) {
					
					return new Item(i, a);
					//return a;
				}
				if(a.next!=null) {
					a=a.next;
				}else {
					break;
				}
			}
			
		}
	   // If graph contains a cycle x_1 -> ... x_k -> x_1,
	   // return a pointer to the head of the linked list
	   // (x_1,..., x_k); otherwise, return null.
	   // NOTE: if there is a cycle, you should just return
	   // one cycle --- it does not matter which one.
	
	   // To do this, scan through the edges of graph,
	   // using info.f to locate a back edge.
	   // Once you find a back edge, use info.parent
	   // to build the list of nodes in the cycle
	   // in the correct order.
	
	   return null;
	}
	
	public static void main(String[] args) throws Exception {
		output= new BufferedWriter(new OutputStreamWriter(System.out,"ASCII"), 4096);
		Scanner scanner = new Scanner(System.in);
		int number_vertice = scanner.nextInt();
		Graph graph= new Graph(number_vertice);
		int number_edge = scanner.nextInt();
		for(int i=0;i<number_edge;i++) {
			int node1=0;
			int node2=0;
		    if(scanner.hasNext()) {
		    	node1=scanner.nextInt();
		    if(scanner.hasNext()) {
		    	node2=scanner.nextInt();
		    }
		    	
//		    	System.out.println(node1);
		    	node1--;
		    	node2--;
		    	graph.addEdge(node1, node2);
		    }
		}
	    DFSInfo dInfo;
	    dInfo = DFS(graph);

	    Item item = findCycle(graph, dInfo);
	    if(item==null) {
	    	output.write("0");
	    }else {
	    	output.write("1"+"\n");
		    int start= item.data;
		    int head=item.next.data;
		    int parent=0;
		    int[] list=new int[number_vertice];    
		    int length=0;
		    list[0]=start+1;
	    	parent= dInfo.parent[start];
		    for(int i=1;i < number_vertice;i++) {
		    	list[i]=parent+1;
		    	if(parent==head) {
		    		length=i+1;
		    		break;
		    	}else {
		    		start=parent;
		    		parent= dInfo.parent[start];
		    	}
		    }
//		    for(int i=0;i < number_vertice;i++) {
//		    	
//		    	list[i]=start+1;
//		    	parent=dInfo.parent[start];
//		    	if(parent!= head) {
//		    		start=parent;
//		    	}else {
//		    		length=i+1;
//		    		break;
//		    	}
//		    }
		   
			for(int i=length-1;i>=0; i--) {
				output.write(list[i]+" ");
				//System.out.print(list[i]+" ");
			}
	    }
		output.flush();
		scanner.close();
	}
	

}