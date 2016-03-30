/* Brayden Roth-White
 * CS 122
 * Heap
 * Makes an array that follows the properties of a heap.
 * Can add as specified below and remove the minimum value.
 */ 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Heap {

	int[] heap;
	int size;
	int heapLim = 100000;
	
	public Heap() { //constructor
		heap = new int[heapLim];
	}
	
	public void add(int e) { //adds to the next available spot
		heap[size] = e;
		size++;
		bubbleUp(); 
	}
	
	public void bubbleUp() { //sorts from the bottom up (the end to the start)
		int temp;
		int a = size-1;
		int parent = getParent(a);
		
		while(parent != -1) { //loops from bottom to top
			if(heap[a] < heap[parent]) { //checks if the data needs to swap
				temp = heap[parent];
				heap[parent] = heap[a];
				heap[a] = temp;
			}
			a = parent;
			parent = getParent(a);
		}
	}
	
	public void bubbleDown() { //sorts from the top down (the start to the end)
		int temp;
		int a = 0;
		int child = getSmallestChild(a);
		
		while(true) { //loops from top to bottom
			if(heap[a] > heap[child]) { //checks if data needs to be swaped
				temp = heap[child];
				heap[child] = heap[a];
				heap[a] = temp;
				a = child;
				child = getSmallestChild(a);
			} else { break; }
		}
	}
	
	public int getParent(int a) { //gets the parent slot of any data slot
		if(a%2 == 1) { a = a+1; }
		if(a/2 == 0) { return -1; }
		return (a/2)-1;
	}
	
	public int getSmallestChild(int a) { //gets the smallest child of any data slot
		int leftChild;
		if(a == 0) { //case that doesn't fit the pattern (parent is the first slot)
			if(heap[1] < heap[2]) {
				return 1;
			} else {
				return 2;
			}
		} else if((a*2)+1 >= size) { //if the parent has 0 children
			return a;
		} else if((a*2)+2 >= size) { //if the parent only has one child
			return (a*2)+1;
		} else { //finds the smaller child of any other parent
			leftChild = (a*2)+1;
			if(heap[leftChild] < heap[leftChild+1]) {
				return leftChild;
			} else {
				return leftChild+1;
			}
		}
	}
	
	public void display() { //displays the heap array
		System.out.print("[");
		
		for(int i = 0; i < size; i++) {
			System.out.print(" "+heap[i]+",");
		}
		
		System.out.println(" ]\n");
	}
	
	public long removeMin() { //removes the minimum value (always from the top)
		long remove = heap[0];
		heap[0] = heap[size-1];
		heap[size-1] = 0;
		size--;
		bubbleDown();
		return remove;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Heap heap = new Heap();
		Heap heapS = new Heap();
		Scanner s = new Scanner(new File("src/values.txt"));
		String str;
		Integer val;
		long addTime = 0;
		long removeTime = 0;
		int count = 0;
		
		//small scale test
		System.out.println("Small heap test:\n");
		heap.add(12);
		heap.add(5);
		heap.add(253);
		heap.add(3);
		heap.add(44);
		heap.add(13);
		heap.add(133);
		heap.add(300);
		heap.add(13);
		heap.add(442);
		heap.add(133);
		heap.add(1343);
		heap.add(3002);
		System.out.print("Original heap: ");
		heap.display();
		heap.removeMin();
		System.out.print("After minimum removed, new heap: ");
		heap.display();
		heap.removeMin();
		System.out.print("After minimum removed, new heap: ");
		heap.display();
		System.out.println("Small heap test is done.\n");
		
		//larger scale test
		System.out.println("Large heap test:\n");
		System.out.print("Adding values from \"values.txt\"");
		long startAdd = System.nanoTime();
		while(s.hasNext() && heapS.size < heapS.heapLim) { //adds values to the heap
			str = s.next();
			val = Integer.parseInt(str);
			heapS.add(val);
			count++;
			if(count % 7001 == 0) { //felt boring without some kind of progress bar
				System.out.print(".");
			}
		}
		long endAdd = System.nanoTime();
		addTime = endAdd - startAdd;
		System.out.println("\nDone adding values.\n");
		
		System.out.print("Removing values from heap");
		count = 0;
		long startRemove = System.nanoTime();
		while(heapS.size > 0) { //removes values from the heap
			heapS.removeMin();
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
		System.out.println("\nLarge heap test is done.");
	}

}
