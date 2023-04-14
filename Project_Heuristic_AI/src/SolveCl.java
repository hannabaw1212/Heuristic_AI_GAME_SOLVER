import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class SolveCl {
	public static int actions = 0;
	public static void main(String[] args) throws FileNotFoundException  {// main function that get a text file and create a state from it
		ArrayList<Integer>temp = new ArrayList<>();
		Scanner in = new Scanner(System.in);
		System.out.print("Solve: ");
		String path =in.next();
		File file = new File(path);
		Scanner x = new Scanner(file);
		while(x.hasNext()) {
			String line = x.nextLine();
			for(int i = 0 ; i < line.length() ; i++) {
				if(i > 0 && line.charAt(i-1)>=48 && line.charAt(i-1) <=57) {
					continue;
				}
				if(i == line.length() - 1 && line.charAt(i)>=48 && line.charAt(i) <=57) {
					int num = Character.getNumericValue(line.charAt(i));
					temp.add(num);
					continue;
				}
				else if(line.charAt(i)>=48 && line.charAt(i) <=57 && line.charAt(i+1)>=48 && line.charAt(i+1) <=57) {
					int num1 = Character.getNumericValue(line.charAt(i));
					int num2 = Character.getNumericValue(line.charAt(i+1));

					int num3 = num1*10 + num2;
					temp.add(num3);
					continue;
				}
				else if(line.charAt(i)>=48 && line.charAt(i) <=57) {
					int num = Character.getNumericValue(line.charAt(i));
					if(temp.contains(num) == false) {
						temp.add(num);
					}
				}
			}
		}
		
		Integer[][] mat = new Integer[(int)Math.sqrt(temp.size())][(int)Math.sqrt(temp.size())];
		int index = 0;
		for(int i = 0 ; i < mat.length ; i++) {
			for(int j = 0 ; j < mat.length ; j ++) {
				mat[i][j] = temp.get(index);
				index++;
			}
		}
		printMat(mat);
		if(checkMatrix(mat) == false) {
			System.out.println("enter a valid matrix");
			return;
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.print(" ");
		String algo = in.next();
		if(algo.equals("id")) {
			StateCl s = new StateCl(mat);
			iterativeDeep(s);
		}
		
		if(algo.equals("bfs")) {
			StateCl s = new StateCl(mat);
			bfs(s);
		}
		if(algo.equals("astar")) {
			StateCl s = new StateCl(mat);
			a_star(s);
		}
	}
	
	private static boolean checkMatrix(Integer[][] mat) {// check if the matrix is valid
		// TODO Auto-generated method stub
		ArrayList<Integer>temp = new ArrayList<>();
		for(int i = 0 ; i < temp.size() ; i++) {
			for(int j = 0 ;j < temp.size() ; j++) {
				temp.add(mat[i][j]);
			}
		}
		if(temp.size() == 9) {
			for(int i = 0 ; i < temp.size() ; i++) {
				if(temp.get(i)<=0 || temp.get(i)>9) {
					return false;
				}
			}
		}
		
		if(temp.size() == 16) {
			for(int i = 0 ; i < temp.size() ; i++) {
				if(temp.get(i)<=0 || temp.get(i)>16) {
					return false;
				}
			}
		}
		return true;
	}

	public static ArrayList<StateCl> bfs(StateCl start) {// bfs search algorithm
	// list for the hundling the nodes
		ArrayList<StateCl> can = new ArrayList<>();// state that visited
		ArrayList<StateCl> cant = new ArrayList<>();// states that we cant visit any more
		
		long steps = 0;// number of stpes or actions
		can.add(start);// adding the start state to queue
		
		while(can.isEmpty() == false) {// while the queue is not empty
			steps++;// we did another action
			Collections.sort(can);
			
			StateCl currState = can.remove(0);
			printMat(currState.mat1);
			
			//adding the current node to the cant list that said that the node is visited 
			cant.add(currState);

			if(currState.isGoal()) {//check if this state is the goal if yes lets print the path and his lenght and the nodes that we visited to it
				ArrayList<StateCl>path = new ArrayList<>();
				while(currState.equals(start) == false) {
					path.add(currState);
					currState = currState.parent;
					
				}
				System.out.println("nodes visited = " + steps);
				for(int i =  path.size() - 1 ; i >= 0 ; i--) {// reverselly print the path
					printMat(path.get(i).mat1);
				}
				System.out.println("path lenght = "+path.size());
				return path;
			}
			currState.successorFunction();// if its the goal we call the successor function to know the neighbooors of the state
			
			for(StateCl st : currState.getNeighboors()) {// lets check the neighboors of the state that we got from the successor function
				if(cant.contains(st)) {// if cant list contains the node dont add it
					continue;
				}
				if(!can.contains(st)) {// else add it but only he isn't already in the queue(can list)
					st.parent = currState;
					can.add(st);
				}
			}
			
		}
		return null;
	}
	
	public static ArrayList<StateCl> a_star(StateCl start){// A* search algorithm
		ArrayList<StateCl> can = new ArrayList<>();// can list to add states to it
		ArrayList<StateCl> cant = new ArrayList<>();// the states that we already visited and expanded them
		int deep = 0;// deep of the node (used to calc the f(n) )
		long steps = 0;// same as bfs
		can.add(start);// adding start to the list
		start.g_n = deep;// start deeep is 0 
		deep++;// deep is 1 after visiting the start node
		while(!can.isEmpty()) {// while we have states in the can list
			steps++;
			Collections.sort(can);// sort the list to get the state with lowest f(n)
			
			StateCl nodePop = can.remove(0); // get the state with the lowest f(n)
			cant.add(nodePop);// adding the node to the cant visited list
			if(nodePop.isGoal()) {// if its the goal do the same we did in bfs
				ArrayList<StateCl>path = new ArrayList<>();
				while(nodePop.equals(start) == false) {
					path.add(nodePop);
					nodePop = nodePop.parent;
					
				}
				System.out.println("nodes visited = " + steps);
				for(int i =  path.size() - 1 ; i >= 0 ; i--) {
					printMat(path.get(i).mat1);
				}
				System.out.println("path lenght = "+path.size());
				return path;
			}
			nodePop.successorFunction();// getting all the node neighboors
			for(StateCl st : nodePop.getNeighboors()) {// update the neighboor f,h and parent
				st.parent = nodePop;
				st.g_n = deep;
				st.calcEval();
				st.f_n = st.g_n + st.h_n;
			}
			
			for(StateCl st : nodePop.getNeighboors()) {// check if we can add each neighboor to the can list
				if(cant.contains(st)) {// if we have already visited it so continue dont add
					continue;
				}
				else if(permissionToAdd(can , st)) {// else check if you can add it 
					can.add(st);
				}
			}
			
			
			
		}
		
		
		return null;
	}
	
	public static boolean stop = false;// to find out if to stop the search it the iterative deepining
	public static long nodeVisit = 0;// number of visited nodes
	public static void iterativeDeep(StateCl start) {
		int limit = 0;// the current limit
		start.currdepth = 0;//start depth is 0
		
		while(stop == false) {//  search until stop change to true
			iterativeDeepUtil(start, limit++);// limit = 0 , 1 ,2 , ... , k 
			System.out.println();
		}
	}
	
	public static void iterativeDeepUtil(StateCl start, int limit) {// iterative deep until the limit
		Stack<StateCl>can = new Stack<>();// here we use stack 
		ArrayList<StateCl> cant = new ArrayList<>();// the visited nodes
		
		can.add(start);// adding the start to the stack
		start.currdepth = 0;
		while(can.isEmpty() == false) {// check if the stack is not empty
			StateCl nodePop = can.pop();// pop the head of the stack
			printMat(nodePop.mat1);
			cant.add(nodePop);
			nodeVisit++;// increase the steps by 1

			if(nodePop.isGoal()) {// check if its the goal and do the same like A* and bfs
				ArrayList<StateCl>path = new ArrayList<>();
				while(nodePop.equals(start) == false) {
					path.add(nodePop);
					nodePop = nodePop.parent;

				}
				System.out.println("nodes visited = " + nodeVisit);
				for(int i =  path.size() - 1 ; i >= 0 ; i--) {
					printMat(path.get(i).mat1);
				}
				System.out.println("path lenght = "+path.size());
				stop = true;
				return;
			}
			
			if(nodePop.currdepth >= limit) {// else we check if the node deep is bigger than the limit if yes we wont do expantion to the node
				continue;
			}
			nodePop.successorFunction();// if the limit is bigger than the node depth so expantion the node
			for(StateCl st : nodePop.getNeighboors()) {// lets update the neighboor states parent and depth
				st.parent = nodePop;
				st.currdepth = nodePop.currdepth +1;
			}
			
			
			for(int i = nodePop.getNeighboors().size() - 1 ; i >= 0 ; i--) {// check which neigboors we should add
				if(cant.contains(nodePop.getNeighboors().get(i)))
				continue;
			if(can.contains(nodePop.getNeighboors().get(i))==false)
				can.add(nodePop.getNeighboors().get(i));
			}
			
		}
	}
	
	public static boolean permissionToAdd(ArrayList<StateCl>states, StateCl state) {// method to help us know if we can add state to the list in the A* algo
		
		for(int i = 0 ; i < states.size() ; i++) {
			if(states.get(i).equals(state) && state.f_n >= states.get(i).f_n) {// if the list contains the state check if the state have lower f(n) if yes add the state else dont add
				return false;
			}
		}
		
		return true;
		
	}


	
	public static void printMat(Integer[][] mat) {
		for(int i = 0 ; i < mat.length ; i++) {
			for(int j = 0 ; j < mat.length ; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("=========================================");
	}
	
}
