import java.io.*;
import java.util.*;

public class tabuSearchSmallerTabuTenure {
	private static stopwatchCPU timer1 = new stopwatchCPU();
	private static final int TABU_TENURE = 6;
	private static int[][] distanceMatrix =	{{0,1,2,3,4,1,2,3,4,5,2,3,4,5,6,3,4,5,6,7},
											{1,0,1,2,3,2,1,2,3,4,3,2,3,4,5,4,3,4,5,6},
											{2,1,0,1,2,3,2,1,2,3,4,3,2,3,4,5,4,3,4,5},
									  	  	{3,2,1,0,1,4,3,2,1,2,5,4,3,2,3,6,5,4,3,4},
									  	  	{4,3,2,1,0,5,4,3,2,1,6,5,4,3,2,7,6,5,4,3},
											{1,2,3,4,5,0,1,2,3,4,1,2,3,4,5,2,3,4,5,6},
											{2,1,2,3,4,1,0,1,2,3,2,1,2,3,4,3,2,3,4,5},
											{3,2,1,2,3,2,1,0,1,2,3,2,1,2,3,4,3,2,3,4},
											{4,3,2,1,2,3,2,1,0,1,4,3,2,1,2,5,4,3,2,3},
											{5,4,3,2,1,4,3,2,1,0,5,4,3,2,1,6,5,4,3,2},
											{2,3,4,5,6,1,2,3,4,5,0,1,2,3,4,1,2,3,4,5},
											{3,2,3,4,5,2,1,2,3,4,1,0,1,2,3,2,1,2,3,4},
											{4,3,2,3,4,3,2,1,2,3,2,1,0,1,2,3,2,1,2,3},
											{5,4,3,2,3,4,3,2,1,2,3,2,1,0,1,4,3,2,1,2},
											{6,5,4,3,2,5,4,3,2,1,4,3,2,1,0,5,4,3,2,1},
											{3,4,5,6,7,2,3,4,5,6,1,2,3,4,5,0,1,2,3,4},
											{4,3,4,5,6,3,2,3,4,5,2,1,2,3,4,1,0,1,2,3},
											{5,4,3,4,5,4,3,2,3,4,3,2,1,2,3,2,1,0,1,2},
											{6,5,4,3,4,5,4,3,2,3,4,3,2,1,2,3,2,1,0,1},
											{7,6,5,4,3,6,5,4,3,2,5,4,3,2,1,4,3,2,1,0}};


		private static int[][] flowMatrix = {{0,0,5,0,5,2,10,3,1,5,5,5,0,0,5,4,4,0,0,1},
											{0,0,3,10,5,1,5,1,2,4,2,5,0,10,10,3,0,5,10,5},
											{5,3,0,2,0,5,2,4,4,5,0,0,0,5,1,0,0,5,0,0},
											{0,10,2,0,1,0,5,2,1,0,10,2,2,0,2,1,5,2,5,5},
											{5,5,0,1,0,5,6,5,2,5,2,0,5,1,1,1,5,2,5,1},
											{2,1,5,0,5,0,5,2,1,6,0,0,10,0,2,0,1,0,1,5},
											{10,5,2,5,6,5,0,0,0,0,5,10,2,2,5,1,2,1,0,10},
											{3,1,4,2,5,2,0,0,1,1,10,10,2,0,10,2,5,2,2,10},
											{1,2,4,1,2,1,0,1,0,2,0,3,5,5,0,5,0,0,0,2},
											{5,4,5,0,5,6,0,1,2,0,5,5,0,5,1,0,0,5,5,2},
											{5,2,0,10,2,0,5,10,0,5,0,5,2,5,1,10,0,2,2,5},
											{5,5,0,2,0,0,10,10,3,5,5,0,2,10,5,0,1,1,2,5},
											{0,0,0,2,5,10,2,2,5,0,2,2,0,2,2,1,0,0,0,5},
											{0,10,5,0,1,0,2,0,5,5,5,10,2,0,5,5,1,5,5,0},
											{5,10,1,2,1,2,5,10,0,1,1,5,2,5,0,3,0,5,10,10},
											{4,3,0,1,1,0,1,2,5,0,10,0,1,5,3,0,0,0,2,0},
											{4,0,0,5,5,1,2,5,0,0,0,1,0,1,0,0,0,5,2,0},
											{0,5,5,2,2,0,1,2,0,5,2,1,0,5,5,0,5,0,1,1},
											{0,10,0,5,5,1,0,2,0,5,2,2,0,5,10,2,2,1,0,6},
											{1,5,0,5,1,5,10,10,2,2,5,5,5,0,10,0,0,1,6,0}};
	
	public static void main(String[] args) throws IOException{
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
	
		int cnt = 0;
		int cnt2 = 0;
		int cnt3 = 0;
		
		ArrayList<String> tabuList = new ArrayList<>();

		int numberOfDepartment = distanceMatrix.length;
		int numberOfLocation = flowMatrix.length;
		
		Assignment initialSolution = new Assignment(numberOfDepartment,numberOfLocation);
		initialSolution.combinationRepresentation(distanceMatrix, flowMatrix);
		
		Assignment.sortAsc(initialSolution.getdistanceArr(), 2);
		Assignment.sortDes(initialSolution.getflowArr(), 1);
		
		initialSolution.findInitialSolution();
		initialSolution.calculateObjValue(distanceMatrix, flowMatrix);
		
		System.out.println(initialSolution);
		
		Assignment currentSolution = new Assignment(initialSolution,numberOfLocation);
		currentSolution.calculateObjValue(distanceMatrix, flowMatrix);
		
		Assignment best = new Assignment(currentSolution, numberOfLocation);
		best.calculateObjValue(distanceMatrix, flowMatrix);
		
		String tabu = "";
		String str = "";
		
		double time1 = timer1.elapsedTime();
		
		while(cnt<1500) {
			Assignment bestNeighbor = new Assignment(numberOfLocation,numberOfDepartment);
	        
			if(cnt>=100) {
				if(cnt3 == 0)
					tabuList.clear();
				for(int i = 0; i < numberOfLocation; i++) {
					for(int j = i+1; j < numberOfLocation; j++) {
						Assignment neighbor = new Assignment(tabuSearch2.findNeighbor(currentSolution, i, j),numberOfDepartment);
						neighbor.calculateObjValue(distanceMatrix, flowMatrix);
						str = i + " " + j;
						if((!tabuList.contains(str)||neighbor.getObjValue()<best.getObjValue())&&(neighbor.getObjValue()<bestNeighbor.getObjValue()||bestNeighbor.getObjValue()==0)) {
							bestNeighbor = new Assignment(neighbor,numberOfLocation);
							bestNeighbor.calculateObjValue(distanceMatrix, flowMatrix);
							tabu = String.valueOf(str);
						}
					}
				}
				cnt2=0;
				cnt3++;
			}
			
			if(cnt<100) {
				if(cnt2==0) {
					tabuList.clear();
				}
				for(int i = 0; i < numberOfLocation; i++) {
					for(int j = i+1; j < numberOfLocation; j++) {
						Assignment neighbor = new Assignment(tabuSearch2.findNeighbor2(currentSolution, i, j),numberOfDepartment);
						neighbor.calculateObjValue(distanceMatrix, flowMatrix);
						str = i + " " + j;
						if((!tabuList.contains(str)||neighbor.getObjValue()<best.getObjValue())&&(neighbor.getObjValue()<bestNeighbor.getObjValue()||bestNeighbor.getObjValue()==0)) {
							bestNeighbor = new Assignment(neighbor,numberOfLocation);
							bestNeighbor.calculateObjValue(distanceMatrix, flowMatrix);
							tabu = String.valueOf(str);
						}
					}
				}
				cnt3=0;
				cnt2++;
			}
			
			if(bestNeighbor.getObjValue()<best.getObjValue()) {
				best = new Assignment(bestNeighbor,numberOfLocation);
				best.calculateObjValue(distanceMatrix, flowMatrix);
				cnt=0;
			}
			
			currentSolution = new Assignment(bestNeighbor,numberOfLocation);
			currentSolution.calculateObjValue(distanceMatrix, flowMatrix);
			
			if(tabuList.size()>=TABU_TENURE) {
				tabuList.remove(0);
			}
			
			tabuList.add(tabu);
			
			time1 = timer1.elapsedTime();
			
			cnt++;
			tabu = "";

		}
		
		System.out.printf("CPU Time in seconds: %f \n",time1);
		System.out.println(best);

	}
	
	public static Assignment findNeighbor(Assignment ass, int ind1, int ind2) {
		
		Assignment assNew = new Assignment(ass,20);
		assNew.calculateObjValue(distanceMatrix,flowMatrix);
		
		int temp1 = 0;
		
		temp1 = assNew.getAssignmentArray()[ind1][0];
		
		assNew.setAssignmentArray(ass.getAssignmentArray()[ind2][0], ind1);
		assNew.setAssignmentArray(temp1, ind2);
		assNew.calculateObjValue(distanceMatrix,flowMatrix);
		
		return assNew;
	}
	
	public static Assignment findNeighbor2(Assignment ass, int ind1, int ind2) {
		
		Assignment assNew = new Assignment(ass,20);
		assNew.calculateObjValue(distanceMatrix,flowMatrix);
		
		int last = ind2;
		
		for(int i = ind1; i<=ind2;i++) {
			assNew.setAssignmentArray(ass.getAssignmentArray()[last][0], i);
			last--;
		}
		
		assNew.calculateObjValue(distanceMatrix,flowMatrix);
		return assNew;
	}
}
