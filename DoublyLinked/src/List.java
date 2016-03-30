
public class List {

	private Node head; // the head reference
	private Node tail; // the tail reference
	private Node temp; // the temporary reference
	private int size; // the size of the list (number of valid items)
	public List() {}

	public void add(String elem) {
		temp = head;
		Node newNode = new Node();
		if(head == null) {
			tail = newNode;
			newNode.next = head;
			newNode.count = 1;
			head = newNode;
		} else {
			newNode.next = null;
			tail = newNode;
			newNode.prev = temp;
			newNode.count = newNode.prev.count + 1;
			temp.next = newNode;
		}
		size++;
		return;
	}
	
	public Node find(String elem, Node n) {
		temp = n;
		if(temp.next == null) { return null; }
		if(temp.equals(elem)) { 
			return temp;
		} else {
			return find(elem, temp.next);
		}
		
	}
	
	public int size() {
		return size;
	}
	
	public static void main(String[] args) {
		List l = new List();
		l.add("Hello");
		l.add("Hip");
		l.add("Do");
		l.add("So");
		l.add("Hi");
	}
}
