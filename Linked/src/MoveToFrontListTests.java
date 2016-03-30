import static org.junit.Assert.*;

import org.junit.Test;


public class MoveToFrontListTests {

	@Test
	public void testConstruction() {
		MoveToFrontList l = new MoveToFrontList();
		assertNotNull(l);
	}
	@Test
	public void testFindOnEmptyList() {
		MoveToFrontList l = new MoveToFrontList();
		assertNull("I should't find anything in an empty list!", l.find(""));
	}
	@Test
	public void testSizeOnEmptyList() {
		MoveToFrontList l = new MoveToFrontList();
		assertEquals("Size of empty list should be 0", l.size(), 0);
	}
	@Test
	public void testRankWithNoItems() {
		MoveToFrontList l = new MoveToFrontList();
		assertEquals("Size of empty list should be 0", l.size(), 0);
		assertEquals("Check the rank for an empty list...", 
				l.rank("Hi"), 0);
	}
	@Test
	public void testAddOneItem() {
		MoveToFrontList l = new MoveToFrontList();
		l.incrementCount("Hi");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 1);
	}
	@Test
	public void testRankWithOneItem() {
		MoveToFrontList l = new MoveToFrontList();
		l.incrementCount("Hip");
		l.incrementCount("Do");
		l.incrementCount("Hi");
		l.incrementCount("Hip");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 3);
		assertEquals("The rank of a your first item should be 0", 
				l.rank("Hi"), 1);
		assertEquals("The rank of a your first item should be 0", 
				l.rank("Hip"), 0);
	}
}
