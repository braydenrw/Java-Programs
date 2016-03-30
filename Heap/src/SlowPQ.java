/* Brayden Roth-White
 * CS 122
 * SlowPQ
 * Makes an array of 'x' items and adds them in as specified below.  
 * Can also remove the minimum value from the array.
 */ 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SlowPQ {
	int[] array;
	int size;
	int arrayLim = 100000;
	
	public SlowPQ() { //constructor
		array = new int[arrayLim];
	}
	
	public void add( int e ) { //adds to the end of the array
		array[size] = e;
		size++;
	}
	
	public int removeMin() { //removes the minimum value and shifts the remaining to fill in
		int remove = array[0];
		int count = 0;
		
		for(int i = 0; i < size; i++) { //finds the minimum within the added values
			if(array[i] < remove) {
				remove = array[i];
				count = i;
			}
		}
		
		for(int i = count; i < size; i++) { //shifts values to the left to fill in
			array[i] = array[i+1];
		}
		
		size--;
		return remove;
	}
	
	public void display() { //displays the array
		System.out.print("[");
		
		for(int i = 0; i < size; i++) {
			System.out.print(" "+array[i]+",");
		}
		
		System.out.println(" ]\n");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		SlowPQ queue = new SlowPQ();
		SlowPQ queueS = new SlowPQ();
		Scanner s = new Scanner(new File("src/values.txt"));
		String str;
		Integer val;
		long addTime = 0;
		long removeTime = 0;
		int count = 0;
		
		//small scale test
		System.out.println("Small array test:\n");
		queue.add(29);
		queue.add(12);
		queue.add(23);
		queue.add(6);
		queue.add(3);
		queue.add(8);
		queue.add(34);
		System.out.print("Original queue: ");
		queue.display();
		queue.removeMin();
		System.out.print("After minimum removed, new queue: ");
		queue.display();
		System.out.println("Small test is done.\n");
		
		//larger scale test
		System.out.println("Large array test:\n");
		System.out.print("Adding values from \"values.txt\"");
		long startAdd = System.nanoTime();
		while(s.hasNext() && queueS.size < queueS.arrayLim) { //adds each Integer to the queue
			str = s.next();
			val = Integer.parseInt(str);
			queueS.add(val);
			count++;
			if(count % 7001 == 0) { //felt boring without some kind of progress bar
				System.out.print(".");
			}
		}
		long endAdd = System.nanoTime();
		addTime = endAdd - startAdd;
		
		System.out.println("\nDone adding values.\n");
		System.out.print("Removing values from queue");
		
		count = 0;
		long startRemove = System.nanoTime(); 
		while(queueS.size > 0) { //removes every value added to the queue
			queueS.removeMin();
			count++;
			if(count % 7001 == 0) { //felt boring without some kind of progress bar
				System.out.print(".");
			}
		}
		long endRemove = System.nanoTime();
		removeTime = endRemove - startRemove;
		
		double secondsAdd = (double)addTime / 1000000000.0;
		double secondsRemove = (double)removeTime / 1000000000.0;
		
		System.out.println("\nDone removing values.\n");
		System.out.println("It took "+addTime+" nanoseconds ("+secondsAdd+" seconds) to add values.");
		System.out.println("It took "+removeTime+" nanoseconds ("+secondsRemove+" seconds) to remove values.");
		System.out.println("\nLarge array test done.");
	}

}
