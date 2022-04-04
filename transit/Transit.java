package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
	
		trainZero = new TNode(0, null, new TNode(0, null, new TNode(0, null, null)));

	
		for(int i = 0; i < locations.length; i++)
			trainZero.getDown().setDown(addLastNode(trainZero.getDown().getDown(), new TNode(locations[i], null, null)));
		
		
		
			for(int i = 0; i < busStops.length; i++)
			trainZero.setDown(addLastNode(trainZero.getDown(), new TNode(busStops[i], null, locator(trainZero.getDown().getDown(), busStops[i]))));
		
		
		
			for(int i = 0; i < trainStations.length; i++)
			trainZero = addLastNode(trainZero, new TNode(trainStations[i], null, locator(trainZero.getDown(), trainStations[i])));
	}
		
				
				
	
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
	       TNode currentStop = trainZero.getNext();
               
		  
		   TNode previous =trainZero;
                while(currentStop!=null){
                        if (currentStop.getLocation()==station){
                                previous.setNext(currentStop.getNext());
                        }
                        previous = currentStop;
                        currentStop = currentStop.getNext();
                }
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    
	    TNode ptr1= trainZero.getDown().getDown(); 
		
		TNode ptr2= trainZero.getDown(); 
		while(ptr1!=null){
			 
			if((ptr2.getDown() ==null)||(ptr1.getLocation() < ptr2.getNext().getLocation())){
				ptr1= ptr1.getNext();
				StdOut.println(ptr1.getLocation());
			} 
			else{
				ptr2 = ptr2.getNext();		
				StdOut.println(ptr2.getLocation());
			}


			if((ptr2.getNext()!=null)&&(ptr1.getLocation() == ptr2.getNext().getLocation())&&(ptr1.getLocation()==busStop)){
				break;
			}
			else if(ptr1.getLocation() == busStop){ 
				
				TNode temp = new TNode(busStop,ptr2.getNext(),ptr1);
				ptr2.setNext(temp);
				break;
			}
		}
	}
	

	
	private TNode walkTo(TNode down, int busStop) {
		return null;
	}

	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> out= new ArrayList<TNode>();
		TNode ptr= trainZero; 
		while(ptr.getLocation()<=destination){
			out.add(ptr);
			if((ptr.getNext()!=null)&&(ptr.getNext().getLocation() <=destination)){
				ptr=ptr.getNext();
			}
			else{
				ptr = ptr.getDown();
			}
			if(ptr==null){
				break;
			}
		}
		return out;
	    
	    //return null;
	}
  
	private TNode addLastNode(TNode head, TNode i){
		TNode newTNode = head;

		while(head.getNext() != null)
			head = head.getNext();

		head.setNext(i);
		return newTNode;
	}

	public TNode locator(TNode header, int i){
		while(header != null){
			if(header.getLocation() == i)
				return header;
			header = header.getNext();
		}
		System.out.println(" ");
		return null;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
      
		TNode temp_head = trainZero;

		if(trainZero.getDown().getDown().getDown() == null){
			
			if(trainZero == null)
				return null;
			
				TNode newHead = new TNode(0, null, new TNode(0, null, new TNode(0, null, null)));
			
				trainZero = trainZero.getDown().getDown().getNext();
			
			while(trainZero != null){
				int location = trainZero.getLocation();
				
				
				
				newHead.getDown().setDown(addLastNode(newHead.getDown().getDown(), new TNode(location, null, null)));
				
				
				trainZero = trainZero.getNext();
			}
			trainZero = temp_head.getDown().getNext();
			
			  while(trainZero != null){
				int location = trainZero.getLocation();
				 
				newHead.setDown(addLastNode(newHead.getDown(), new TNode(location, null, locator(newHead.getDown().getDown(), location))));
				      trainZero = trainZero.getNext();
			}
			trainZero = temp_head.getNext();
		
			while(trainZero != null){
				
				
				  int location = trainZero.getLocation();
				
				  newHead = addLastNode(newHead, new TNode(location, null, locator(newHead.getDown(), location)));
				
				       trainZero = trainZero.getNext();
			}
			
			trainZero = temp_head;
			return newHead;
		}
		TNode newHead = new TNode(0, null, new TNode(0, null, new TNode(0, null, null)));
		
		trainZero = trainZero.getDown().getDown().getDown().getNext();
		
		while(trainZero != null){
			int location = trainZero.getLocation();
		
			newHead.getDown().getDown().setDown(addLastNode(newHead.getDown().getDown().getDown(), new TNode(location, null, null)));
		
			trainZero = trainZero.getNext();
		}
		trainZero = temp_head.getDown().getDown().getNext();
		while(trainZero != null){
			int location = trainZero.getLocation();
		
			newHead.getDown().setDown(addLastNode(newHead.getDown().getDown(), new TNode(location, null, locator(newHead.getDown().getDown().getDown(), location))));
			
			trainZero = trainZero.getNext();


		}
		trainZero = temp_head.getDown().getNext();
		
		while(trainZero != null){
			int location = trainZero.getLocation();
			
			newHead.setDown(addLastNode(newHead.getDown(), new TNode(location, null, locator(newHead.getDown().getDown(), location))));
			trainZero = trainZero.getNext();
		}
		trainZero = temp_head.getNext();
	
		while(trainZero != null){
			int location = trainZero.getLocation();
			
			newHead = addLastNode(newHead, new TNode(location, null, locator(newHead.getDown(), location)));
			
			trainZero = trainZero.getNext();
		}
		trainZero = temp_head;
		return newHead;

	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

	    ArrayList<Integer> scoot = new ArrayList<Integer>();
		   scoot.add(0);

		for(int i = 0; i < scooterStops.length; i++){
			scoot.add(scooterStops[i]);
		}

		TNode head=null;
		int sizeSc = scoot.size();
		for(int j = sizeSc -1;j>=0;j--){
			TNode temp = new TNode(scoot.get(j),head,null);
			head=temp;
		}

		TNode ptr1 = trainZero.getDown().getDown(); 
		TNode ptr2 = head; 
		 
		while((ptr1!=null)&&(ptr2!=null)){
			
			if(ptr1.getLocation()==ptr2.getLocation()){
				ptr2.setDown(ptr1);  
				ptr2=ptr2.getNext();
			}
			else{
				ptr1= ptr1.getNext();
			}
		}
	
		TNode tempA=head; 
		
		TNode tempB=trainZero.getDown(); 
		while((tempA!=null)&&(tempB!=null)){
			if(tempA.getLocation() ==tempB.getLocation()){
				tempB.setDown(tempA);
				tempB = tempB.getNext();
			}
			else{
				tempA= tempA.getNext();
			}
		}

	}
	

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
