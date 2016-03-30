/* Brayden Roth-White
 * CS 122
 * OrderedArrayList
 * Creates a doubly linked list that can keep track of and count Strings.
 */ 
public class MoveToFrontList {

	private StringCountElement head; // the head reference
	private StringCountElement tail; // the tail reference
	private StringCountElement temp; // the temporary reference
	private int size; // the size of the list (number of valid items)

	/**
	 * _Part 1: Implement this constructor._
	 * 
	 * Creates a new, initially empty MoveToFontList. This list should be a
	 * linked data structure.
	 */
	public MoveToFrontList() {
		
	}

	/**
	 * This method increments the count associated with the specified string
	 * key. If no corresponding key currently exists in the list, a new list
	 * element is created for that key with the count of 1. When this method
	 * returns, the key will have rank 0 (i.e., the list element associated with
	 * the key will be at the front of the list)
	 * 
	 * @param key
	 *            the string whose count should be incremented
	 * @return the new count associated with the key
	 */
	public int incrementCount(String key) {
		StringCountElement s = find(key);
		if (s != null) {
			// found the key, splice it out and increment the count
			spliceOut(s);
			s.count++;
		} else {
			// need to create a new element
			s = new StringCountElement();
			s.key = key;
			s.count = 1;
		}
		// move it to the front
		spliceIn(s, 0);
		return s.count;
	}

	/**
	 * 
	 * @return the number of items in the list
	 */
	public int size() {
		return size;
	}

	/**
	 * _Part 2: Implement this method._
	 * 
	 * Find the list element associated with the specified string. That is, find
	 * the StringCountElement that a key equal to the one specified
	 * 
	 * @param key
	 *            the key to look for
	 * @return a StringCountElement in the list with the specified key or null
	 *         if no such element exists.
	 */
	public StringCountElement find(String key) {
		if(head == null) { return null; } //if empty
		temp = head;
		int i = 0;
		while(!((temp) == null) && !((temp.key).equals(key))) {
			temp = temp.next;
			i++;
		} 
		if(i == size) { return null; } //if the key isn't found
		return temp;
	}

	/**
	 * _Part 3: Implement this method._
	 * 
	 * Compute the rank of the specified key. Rank is similar to position, so
	 * the first element in the list will have rank 0, the second element will
	 * have rank 1 and so on. However, an item that does not exist in the list
	 * also has a well defined rank, which is equal to the size of the list. So,
	 * the rank of any item in an empty list is 0.
	 * 
	 * @param key
	 *            the key to look for
	 * @return the rank of that item in the rank 0...size() inclusive.
	 */
	public int rank(String key) {
		temp = head;
		int i;
		for(i = 0; !((temp) == null) && !((temp.key).equals(key)); i++) {
			temp = temp.next;
		}
		return i;
	}

	/**
	 * _Part 4: Implement this method._
	 * 
	 * Splice an element into the list at a position such that it will obtain
	 * the desired rank. The element should either be new, or have been spliced
	 * out of the list prior to being spliced in. That is, it should be the case
	 * that: s.next == null && s.prev == null
	 * 
	 * @param s
	 *            the element to be spliced in to the list
	 * @param desiredRank
	 *            the desired rank of the element
	 */
	public void spliceIn(StringCountElement s, int desiredRank) {
		StringCountElement newNode = new StringCountElement();
		newNode = s;
		temp = head;
		if(head == null) { //checks if list is empty
			tail = newNode;
			newNode.next = head;
			head = newNode;
			size++;
			return;
		}
		if(desiredRank == 0) { //special case
			head.prev = newNode;
			newNode.next = head;
			head = newNode;
		} else {
			while(rank(temp.key) != desiredRank-1) {
				if(temp.next == null) { break; } //if desiredRank is out of bounds
				temp = temp.next;
			}
			if(temp == tail) { // if desiredRank is at the end
				newNode.next = null;
				tail = newNode;
			} else {
				newNode.next = temp.next;
				temp.next.prev = newNode;
			}
			newNode.prev = temp;
			temp.next = newNode;
		}
		size++;
		return;
	}

	/**
	 * _Part 5: Implement this method._
	 * 
	 * Splice an element out of the list. When the element is spliced out, its
	 * next and prev references should be set to null so that it can safely be
	 * splicedIn later. Splicing an element out of the list should simply remove
	 * that element while maintaining the integrity of the list.
	 * 
	 * @param s
	 *            the element to be spliced out of the list
	 */
	public void spliceOut(StringCountElement s) {
		if(s.prev == null) { // if at the start of the list
			head = temp.next;
		} else { 
			s.prev.next = temp.next;
			if(temp.next == null) { //if at the end of the list
				tail = temp.prev;
			} else {
				temp = temp.next;
			}
		}
		size--;
		return;
	}

}
