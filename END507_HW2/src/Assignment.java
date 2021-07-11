
import java.util.*;

public class Assignment {
	
	private int numberOfLocation;
	private int numberOfDepartment;
	//first column represents locations, second column represents assigned departments
	private int [][] assignmentArray;
	public int[][] flowArr; //represents flows of the combinations
	public int[][] distanceArr; //represents distances of the combinations
	private int objValue;

	//constructor
	public Assignment (int numberOfDepartment, int numberOfLocation){
		this.numberOfLocation = numberOfLocation;
		this.numberOfDepartment = numberOfDepartment;
		assignmentArray = new int [this.numberOfLocation][2];
				
		//initialize assignment array
		for(int i = 0;i<numberOfLocation;i++) {
				assignmentArray[i][0]=i+1;
		}
	}
	
	//constructor
	public Assignment (Assignment ass, int numberOfLocation){
		
		this.numberOfDepartment = numberOfLocation;
		this.numberOfLocation = numberOfLocation;
		
		this.assignmentArray = new int [numberOfLocation][2];
		
		for(int i = 0;i<numberOfLocation;i++) {
			this.assignmentArray[i][0]=ass.getAssignmentArray()[i][0];;
			this.assignmentArray[i][1]=ass.getAssignmentArray()[i][1];
		}
	}
	
	
	// getter methods
	public int[][] getflowArr () {
		return flowArr;
	}
	
	public int[][] getdistanceArr () {
		return distanceArr;
	}
	
	public int[][] getAssignmentArray () {
		return assignmentArray;
	}

	public int getObjValue () {
		return objValue;
	}
	
	public String getAssignmentString() {
		String str ="";
		
		for(int i = 0; i<numberOfLocation;i++) {
			str += "d"+assignmentArray[i][0];
		}
		return str;
	}
	// setter methods
	public void setAssignmentArray (int newLoc,int dep) {
		assignmentArray[dep][0]=newLoc;
	}
	public void setAssignmentArray2 (int i,int j) {
		assignmentArray[i][1]=j;
	}
	
	/*method that represent flow matrix as a combination of departments
	/						distance matrix as a combination of locations such that
	 * (for distance){{12,5}
	 * 				  {13,2}
	 * 					 .
	 * 					 .
	 * 					 .
	 * 				  {45,5}}*/
	 
	public void combinationRepresentation(int[][] distanceMatrix,int[][] flowMatrix) {
		
		//
		distanceArr = new int[distanceMatrix.length*(distanceMatrix.length-1)/2][2];
		flowArr = new int[flowMatrix.length*(distanceMatrix.length-1)/2][2];
		
		//System.out.println(flowArr.length);
		
		int cnt = 0;
		
		for(int i=1;i<flowMatrix.length;i++) {
			for(int j=i;j<flowMatrix.length;j++) { 
				
				String str1 = Integer.toString(i);
				String str2 = Integer.toString(j+1);
				String s = str1+str2;
				//System.out.println(Integer.valueOf(s) + " " + distanceMatrix[i-1][j]);
				distanceArr[cnt][0]=Integer.valueOf(s);
				
				distanceArr[cnt][1]=distanceMatrix[i-1][j];
				//System.out.println(distanceArr[cnt][1]);
				
				flowArr[cnt][0]=Integer.valueOf(s);
				flowArr[cnt][1]=flowMatrix[i-1][j];

				
				
				//System.out.println(distanceMatrix[i-1][j]);
				//System.out.println(distanceArr[cnt][0] + " "+ distanceArr[cnt][1]);
				cnt++;
			}
			//System.out.println(cnt + "Flow: " + flowArr[cnt][0] + " "+ flowArr[cnt][1]);
		}		
	}
	
    public static void sortAsc(int[][] array, int columnNumber){
        Arrays.sort(array, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
               if(first[columnNumber-1] > second[columnNumber-1]) return 1;
               else return -1;
            }
        });
    }
    
    public static void sortDes(int[][] array, int columnNumber){
        Arrays.sort(array, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
               if(first[columnNumber-1] < second[columnNumber-1]) return 1;
               else return -1;
            }
        });
    }    
    
    /*method that gets flow with maximum flow and
      assigns the departmants to mininum value distance.
      solution is represented as a string object so that
      only the departments that are not assigned are forced to
      be assigned.
     */
    
	public void findInitialSolution() {
		
		int toBeAssigned1 = 0;
		int toBeAssigned2 = 0;		
	
		int candLoc1 = 0;
		int candLoc2 = 0;
		
		ArrayList<Integer> assignedDep = new ArrayList<>();
		ArrayList<Integer> assignedLoc = new ArrayList<>();
		
		int sum = 15;
		int sumAssignedLoc = 0;
		int sumAssignedDep = 0;

		int numOfDigit1;
		int numOfDigit2;
		
		while (assignedDep.size()<20) {
			for(int i = 0; i<flowArr.length;i++) {
				for(int j = 0;j<distanceArr.length;j++) {
			
					numOfDigit1 = String.valueOf(flowArr[i][0]).length();
					numOfDigit2 = String.valueOf(distanceArr[j][0]).length();
					
					if(numOfDigit1 == 2) {
						toBeAssigned1 = flowArr[i][0]/10;
						toBeAssigned2 = flowArr[i][0]%10;
					}
					
					if(numOfDigit2 == 2) {
						candLoc1 = distanceArr[j][0]/10;
						candLoc2 = distanceArr[j][0]%10;
					}
					
					if(numOfDigit1 == 3 || numOfDigit1 == 4) {
						toBeAssigned1 = flowArr[i][0]/100;
						toBeAssigned2 = flowArr[i][0]%100;
					}
					
					if(numOfDigit2 == 3 || numOfDigit2 == 4) {
						candLoc1 = distanceArr[j][0]/100;
						candLoc2 = distanceArr[j][0]%100;
					}
	
					if(assignedDep.size()==19) {
						assignmentArray[sum-sumAssignedDep-1][1]=sum-sumAssignedLoc;
					}
					
					if(!assignedDep.contains(Integer.valueOf(toBeAssigned1))&&
					   !assignedDep.contains(Integer.valueOf(toBeAssigned2))&&
					   !assignedLoc.contains(Integer.valueOf(candLoc1))&&
					   !assignedLoc.contains(Integer.valueOf(candLoc2))){
						
						sumAssignedLoc += candLoc1;
						sumAssignedLoc += candLoc2;
						
						sumAssignedDep += toBeAssigned1;
						sumAssignedDep += toBeAssigned2;
						
						assignedDep.add(Integer.valueOf(toBeAssigned1)) ;
						assignedDep.add(Integer.valueOf(toBeAssigned2)) ;
						
						assignedLoc.add(Integer.valueOf(candLoc1));
						assignedLoc.add(Integer.valueOf(candLoc2));
						
						assignmentArray [candLoc1-1][1]=toBeAssigned1;
						assignmentArray [candLoc2-1][1]=toBeAssigned2;
						
						flowArr[i][1]=0;
						distanceArr[j][1]=999;
						
						Assignment.sortDes(flowArr, 2);
						Assignment.sortAsc(distanceArr, 2);
						
					}
					
					if(assignedDep.size()==20)
						j=distanceArr.length;
				}
				if(assignedDep.size()==20)
					i=flowArr.length;
			}
		}
		System.out.println("-------------Initial Solution-------------\n");
	}
	
	public void calculateObjValue(int[][] distanceMatrix,int[][] flowMatrix) {
		
		Assignment.sortAsc(assignmentArray, 2);
		
		objValue = 0;
		
		for(int i = 0;i<numberOfLocation;i++) {
			for(int j = i+1;j<numberOfLocation;j++) {
				if(i!=j) {
					objValue += flowMatrix[i][j]*distanceMatrix[assignmentArray[i][0]-1][assignmentArray[j][0]-1];
				}
			}	
		}
	}
	

	public String toString(){
		
		String str = "";
		
		for(int i=0;i<numberOfDepartment;i++) {
					str += "  Department " + assignmentArray[i][1] + " is assigned to location " + assignmentArray[i][0] +".";
					str += "\n";				
			}
		
		str += "\n------------Objective value: " + objValue+"-----------\n";

		return str;
	}

}
