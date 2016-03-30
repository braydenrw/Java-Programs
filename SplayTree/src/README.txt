Brayden Roth-White
011388543
roth-white.brayden@comcast.net

SplaySymbolTable.java, README.txt

Creates a Splay symbol table that inserts a Node like a standard BST
and then splays the recently inserted Node to the root.  There are 3 main methods
one to insert, one to search, and one to remove.  After something is inserted and
searched it is splayed up to the root of the tree.  When remove is called
the Node to be removed is splayed to the root and then removed.

With memoization set to true and n = 35 k = n/2, there were 2509 comparisons and 2014
and it took the system about 0.01 seconds to complete.  With memoization set to false,
the system took 18.35 seconds to complete.

Files in .jar: SplayTree.java, SplaySymbolTable.java, Binomial.java, README.txt

Make sure all the files are in the same .jar, and go to the Binomial.java
make sure you're compiling the Binomial.java and memoization is set to true for a
faster time.  If you want the run to take exponentially longer, set memoization to
false.  Once the .java has run, you should have your binomial numbers and the run time.