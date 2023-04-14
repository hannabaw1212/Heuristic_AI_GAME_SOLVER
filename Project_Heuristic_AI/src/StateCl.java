import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StateCl implements Comparable<StateCl>{
	Integer[][] mat1;
	int h_n; // heurtic
	int g_n; // keshet
	int f_n; // f = h+g
	StateCl parent = null;// saving the parent for each node
	int currdepth = 0;//for the itertaive deppening
	ArrayList<StateCl> neighboors;// neighboors of our state

	public StateCl(Integer[][] mat1) {
		super();
		if(!checkMatrix(mat1)) {
			return;
		}
		setMat1(mat1);
		this.calcEval();
		this.neighboors = new ArrayList<StateCl>();
	}


	public void calcEval() {//number of elements that is not in thier place our heaurstic
		Integer[][] mat = this.mat1;
		ArrayList<Integer> temp = new ArrayList<>();
		int count = 0 ;
		for(int i = 0 ; i < mat.length ; i++) {
			for(int j = 0 ; j < mat.length ; j++) {
				temp.add(mat[i][j]);
			}
		}
		for(int i = 0 ; i < temp.size() ; i++) {
			if(temp.get(i) - 1 != i )
				count++;
		}
		this.h_n = count;
	}


	private boolean checkMatrix(Integer[][] mat12) {// function to check if the givven matrix is legall
		// TODO Auto-generated method stub
		int x = mat12.length;
		if(x == 9) {
			for(int i = 0 ; i < 3 ; i ++ ) {
				for(int j = 0 ; j < 3 ; j++) {
					if(mat12[i][j] <= 0 || mat12[i][j] > 9) {
						return false;
					}
				}
			}
		}
		if(x == 16) {
			for(int i = 0 ; i < 4 ; i ++ ) {
				for(int j = 0 ; j < 4 ; j++) {
					if(mat12[i][j] <= 0 || mat12[i][j] > 16) {
						return false;
					}
				}
			}
		}
		return true;

	}

	public void setMat1(Integer[][] mat2) {// set the mat
		this.mat1 = new Integer[mat2.length][mat2.length];
		for(int i = 0 ; i < mat2.length ; i++) {
			for(int j = 0 ; j < mat2.length ; j++) {
				this.mat1[i][j] = mat2[i][j];
			}
		}
	}


	public boolean isGoal() {// check if the state contain is the goal matrix
		if(this.h_n == 0)
			return true;
		ArrayList<Integer>temp = new ArrayList<>();
		Integer[][]mat = this.mat1;
		int n;
		boolean isSorted = true;
		if(mat.length == 16) {
			n = 4;
		}
		else {
			n = 3;
		}
		for(int i = 0 ; i < n ; i++) {
			for(int j = 0 ; j < n ; j++) {
				temp.add(mat[i][j]);
			}
		}
		for(int i = 0 ; i < temp.size() - 1; i++) {
			if(temp.get(i) != i) {
				isSorted = false;
			}
		}
		if(isSorted == false)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(mat1);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateCl other = (StateCl) obj;
		return Arrays.deepEquals(mat1, other.mat1);
	}


	public void successorFunction(){// successor funtion to get all the state neighboors
		ArrayList<StateCl>next_StateCls = new ArrayList<>();
		Integer mat[][] = this.mat1;
		if(mat1.length == 3) {
			for(int i = 0 ; i  < 3 ; i ++) {
				for(int j = 0 ; j < 3 ; j ++ ) {
					next_StateCls.addAll(this.createMat3On3(mat, i, j));// creating all the matrixs that we can get from this state

				}
			}
			this.setNeighboors(next_StateCls);
		}

		if(mat1.length == 4) {
			for(int i = 0 ; i  < 4 ; i ++) {
				for(int j = 0 ; j < 4 ; j ++ ) {
					next_StateCls.addAll(this.createMat4On4(mat, i, j));

				}
			}
			this.setNeighboors(next_StateCls);
		}

	}

	public ArrayList<StateCl> createMat3On3(Integer mat[][], int i, int j){// helper method for the successor function
		Integer temp[][] = new Integer[3][3];
		ArrayList<StateCl>StateCls = new ArrayList<>();
		for(int k = 0 ; k < 3 ; k++) {
			for(int m = 0 ; m < 3 ; m++) {
				temp[k][m] = mat[k][m];
			}
		}

		if(i + 1 < 3) {
			int tempNumber = temp[i][j];
			temp[i][j] = temp[i+1][j];
			temp[i+1][j] = tempNumber;
			StateCl s = new StateCl(temp);
			StateCls.add(s);
			temp = this.reloadMat(mat, 3, 3);
		}
		if(j + 1 < 3) {
			int tempNumber = temp[i][j];
			temp[i][j] = temp[i][j+1];
			temp[i][j+1] = tempNumber;
			StateCl s = new StateCl(temp);
			StateCls.add(s);
			temp = this.reloadMat(mat, 3, 3);
		}
		if(i-1 >= 0) {
			int tempNumber = temp[i][j];
			temp[i][j] = temp[i-1][j];
			temp[i-1][j] = tempNumber;
			StateCl s = new StateCl(temp);
			StateCls.add(s);
			temp = this.reloadMat(mat, 3, 3);
		}
		if(j-1 >= 0) {
			int tempNumber = temp[i][j];
			temp[i][j] = temp[i][j-1];
			temp[i][j-1] = tempNumber;
			StateCl s = new StateCl(temp);
			StateCls.add(s);
			temp = this.reloadMat(mat, 3, 3);
		}

		return StateCls;

	}

	public ArrayList<StateCl> createMat4On4(Integer mat[][], int i, int j){// helper method for the successor function
		Integer temp[][] = new Integer[4][4];
		ArrayList<StateCl>StateCls = new ArrayList<>();
		for(int k = 0 ; k < 4 ; k++) {
			for(int m = 0 ; m < 4 ; m++) {
				temp[k][m] = mat[k][m];
			}
		}

		if(i + 1 < 4) {
			int tempNumber = temp[i][j];
			temp[i][j] = temp[i+1][j];
			temp[i+1][j] = tempNumber;
			StateCl s = new StateCl(temp);
			StateCls.add(s);
			temp = this.reloadMat(mat, 4, 4);
		}
		if(j + 1 < 4) {
			int tempNumber = temp[i][j];
			temp[i][j] = temp[i][j+1];
			temp[i][j+1] = tempNumber;
			StateCl s = new StateCl(temp);
			StateCls.add(s);
			temp = this.reloadMat(mat, 4,4);
		}
		if(i-1 >= 0) {
			int tempNumber = temp[i][j];
			temp[i][j] = temp[i-1][j];
			temp[i-1][j] = tempNumber;
			StateCl s = new StateCl(temp);
			StateCls.add(s);
			temp = this.reloadMat(mat, 4, 4);
		}
		if(j-1 >= 0) {
			int tempNumber = temp[i][j];
			temp[i][j] = temp[i][j-1];
			temp[i][j-1] = tempNumber;
			StateCl s = new StateCl(temp);
			StateCls.add(s);
			temp = this.reloadMat(mat, 4, 4);
		}

		return StateCls;

	}
	public Integer[][] reloadMat(Integer[][] mat, int i, int j){// helper method
		Integer temp[][] = new Integer[i][j];
		for(int k= 0 ; k < mat.length ; k ++) {
			for(int m= 0 ; m < mat.length ; m ++) {
				temp[k][m] = mat[k][m];
			}
		}
		return temp;

	}

	public void addNeighboor(StateCl s) {
		this.neighboors.add(s);
	}

	public Integer[][] getMat1() {
		return mat1;
	}
	public int getH_n() {
		return h_n;
	}

	public void setH_n(int h_n) {
		this.h_n = h_n;
	}

	public int getG_n() {
		return g_n;
	}

	public void setG_n(int g_n) {
		this.g_n = g_n;
	}

	public int getF_n() {
		return f_n;
	}

	public void setF_n(int f_n) {
		this.f_n = f_n;
	}

	public ArrayList<StateCl> getNeighboors() {
		return neighboors;
	}

	public void setNeighboors(ArrayList<StateCl> neighboors) {
		for(int i = 0 ; i < neighboors.size() ; i++) {
			this.neighboors.add(neighboors.get(i));
			neighboors.get(i).parent = this;
		}
	}
	
	public static void main(String[] args) {
		Integer[][] mat1 = { {1,2,3}, 
									{5,4,6},
									{7,9,8}};
		
		StateCl s1 = new StateCl(mat1);
		int g = 0;
		s1.calcEval();
		s1.f_n = s1.getH_n() + g++;
		Integer[][] mat2 = { {1,2,3}, 
									{4,5,6},
									{7,9,8}};
		StateCl s2 = new StateCl(mat2);
		s2.f_n = s2.getH_n() + g++;
		
		Integer[][] mat3 = { {1,2,3}, 
									{4,5,6},
									{7,8,9}};
		StateCl s3 = new StateCl(mat3);
		s3.f_n = s3.getH_n() + g++;
		Integer[][] mat4 = { {1,2,3}, 
									{5,4,8},
									{7,9,6}};
		
		StateCl s4 = new StateCl(mat4);
		s4.f_n = s4.getH_n() + g++;
		
		ArrayList<StateCl> priorityqueue = new ArrayList<>();
		priorityqueue.add(s1);
		priorityqueue.add(s2);
		priorityqueue.add(s3);
		priorityqueue.add(s4);
		
		Collections.sort(priorityqueue);
		
		printMat(priorityqueue.get(0).mat1);
		System.out.println("priorityqueue.get(0).f(n)= " + priorityqueue.get(0).f_n);
		printMat(priorityqueue.get(1).mat1);
		System.out.println("priorityqueue.get(1).f(n)= " + priorityqueue.get(1).f_n);
		printMat(priorityqueue.get(2).mat1);
		System.out.println("priorityqueue.get(2).f(n)= " + priorityqueue.get(2).f_n);
		printMat(priorityqueue.get(3).mat1);
		System.out.println("priorityqueue.get(3).f(n)= " + priorityqueue.get(3).f_n);
	}
	
	public static void printMat(Integer[][] mat) {
		for(int i = 0 ; i < mat.length ; i++) {
			for(int j = 0 ; j < mat.length ; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("======================================");
	}


	@Override
	public int compareTo(StateCl o) {// compare states by thier f(n)
		int fi= ((StateCl)o).getF_n();
		return this.getF_n()- fi;
	}
	
	

}
