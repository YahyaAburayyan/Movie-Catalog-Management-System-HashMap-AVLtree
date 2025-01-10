package application;

import java.io.PrintWriter;
import java.util.ArrayList;

public class AVL {

private AVLTreeNode root; // just a refrence to the root 
	
	public AVL(){
		root = null;
	}
	
	private int height( AVLTreeNode e ){
		if( e == null ) 
			return -1; 
		return e.getHeight(); 
		}
	
	public int heightOfTree() {
		return this.height(root);
	}
	
	
	private int balanceFactor(AVLTreeNode current) {
	    if (current == null) {
	        return 0; // AVL tree is empty  
	    }
	    int leftHeight;
	    if (current.getLeft() != null) {
	        leftHeight = current.getLeft().getHeight();
	    } else {
	        leftHeight = -1; // means there is no left child and only right one 
	    }

	    int rightHeight;
	    if (current.getRight() != null) {
	        rightHeight = current.getRight().getHeight();
	    } else {
	        rightHeight = -1;// means there is no right child and only left one 
	    }

	    return leftHeight - rightHeight;
	}

	
	private AVLTreeNode reBalance(AVLTreeNode root) {
	    int balance = balanceFactor(root);
	    if (balance > 1) { // Left heavy
	        if (balanceFactor(root.getLeft()) >= 0) {
	        	// Left Left case
	            return rotateWithLeftChild(root);
	        } else {
	        	// Left Right case
	        	return DoubleWithLeftChild(root);
	        }
	    } else if (balance < -1) { // Right heavy
	        if (balanceFactor(root.getRight()) <= 0) {
	        	// Right Right case
	            return rotateWithRightChild(root);
	        } else {
	        	// Right Left case
	        	return DoubleWithRightChild(root);
	        }
	    }
	    return root; // Balanced subtree
	}

	
	// Left Left case (Rotate Right)
	private AVLTreeNode rotateWithLeftChild(AVLTreeNode k2){
		AVLTreeNode k1 = k2.getLeft();
		k2.setLeft(k1.getRight());
		k1.setRight(k2);
		k2.setHeight(Math.max(height(k2.getLeft()),height( k2.getRight()))+ 1);
		k1.setHeight(Math.max(height(k1.getLeft()),k2.getHeight())+ 1);
		return k1;
		}
	
	// Right Right case (Rotate Left)
	private AVLTreeNode rotateWithRightChild(AVLTreeNode k1){
		AVLTreeNode k2 = k1.getRight();
		k1.setRight(k2.getLeft());
		k2.setLeft(k1);
		k1.setHeight(Math.max(height(k1.getLeft()),height( k1.getRight()))+ 1);
		k2.setHeight(Math.max(height(k2.getRight()),k1.getHeight())+ 1);
		return k2;
		}
	
	// Left Right case (Rotate Left then Right)
	private AVLTreeNode DoubleWithLeftChild(AVLTreeNode k3){
		k3.setLeft(rotateWithRightChild( k3.getLeft())); 
		return rotateWithLeftChild( k3 );
		}
	
	// Right Left case (Rotate Right then Left)
	private AVLTreeNode DoubleWithRightChild(AVLTreeNode k1){
		k1.setRight(rotateWithLeftChild(k1.getRight()));
		return rotateWithRightChild(k1);
	}

	
	public boolean contains (String e) {
		return this.contains(e, this.root);
	}
	
	// all of my comparisons is on the lower case 
	//Check whether the element is in a tree or not
		private boolean contains (String e,AVLTreeNode current){
			if (current==null)
				return false; // Not found, empty tree.
			else if (e.toLowerCase().compareTo(current.getElement().getTitle().toLowerCase()) < 0   ) // if smaller than root.
				return contains (e,current.getLeft()); // Search left subtree
			else if (e.toLowerCase().compareTo(current.getElement().getTitle().toLowerCase()) > 0) 
				return contains (e,current.getRight()); // Search right subtree
		
			return true; // found 
		}
		
		
		public AVLTreeNode find(String element) {
			return this.find(element, this.root);
		}
		
		// traversing the tree in order and search
		private AVLTreeNode find(String title, AVLTreeNode current){
			if (current == null)
				return null;
			if (title.toLowerCase().compareTo(current.getElement().getTitle().toLowerCase()) < 0) // if smaller than root.
				return find(title, current.getLeft());
			else if (title.toLowerCase().compareTo(current.getElement().getTitle().toLowerCase()) > 0)// if larger than root.
				return find(title, current.getRight()); 
			else
				return current;
		}
		
		public ArrayList<Movie> getMoviesWithReleaseYear( int releaseYear){
			return this.getMoviesWithReleaseYear(root, new ArrayList<Movie>(), releaseYear);
		}
		
		// traverse the tree in order 
		private ArrayList<Movie> getMoviesWithReleaseYear(AVLTreeNode current, ArrayList<Movie> result, int releaseYear) {
			if(current != null) {
				getMoviesWithReleaseYear(current.getLeft(),result,releaseYear);
				if(current.getElement().getReleaseYear() == releaseYear) { // if the needed release year append movie to result array list
					result.add(current.getElement());
				}
				getMoviesWithReleaseYear(current.getRight(),result,releaseYear);
			}
			return result;
		}
		
		public void insert(Movie element) {
			root = this.insert(element, root);
		}
		
		private AVLTreeNode insert(Movie element, AVLTreeNode current) {
		    if (current == null) {//base case : empty tree or reached a leaf
		        return new AVLTreeNode(element);
		    }
		    if (element.getTitle().toLowerCase().compareTo(current.getElement().getTitle().toLowerCase()) < 0 ) {// if smaller than root.
		        current.setLeft(insert(element, current.getLeft()));
		    } else if (element.getTitle().toLowerCase().compareTo(current.getElement().getTitle().toLowerCase()) > 0 ) {// if larger than root.
		        current.setRight(insert(element, current.getRight()));
		    }
		    current.setHeight(1 + Math.max(height(current.getLeft()), height(current.getRight())));// updating height
		    return reBalance(current); // Ensure balance after insertion
		}
		
		
		public Movie findMin() {
			return this.findMin(root).getElement();
		}
		// left most of the tree
		private AVLTreeNode findMin (AVLTreeNode current){
			if (current==null)
				return null; 
			else if (current.getLeft()==null)
				return current;
			else
				return findMin(current.getLeft()); //keep going to the left
			}
		
		
		public Movie findMax() {
			return this.findMax(root).getElement();
		}
		// right most at the tree
		private AVLTreeNode findMax (AVLTreeNode current){
			if (current==null)
				return null; 
			else if (current.getRight()==null)
				return current;
			else
				return findMax(current.getRight()); //keep going to the right
			}
		
		public void remove (String e) {
			root = remove(e, root);
		}
		
		private AVLTreeNode remove(String title, AVLTreeNode current) {
		    if (current == null) { // not found base case 
		        return null;
		    }
		    if (title.toLowerCase().compareTo(current.getElement().getTitle().toLowerCase()) < 0 ) {// if smaller than root.
		        current.setLeft(remove(title, current.getLeft()));
		    } else if (title.toLowerCase().compareTo(current.getElement().getTitle().toLowerCase()) > 0) {// if larger than root.
		        current.setRight(remove(title, current.getRight()));
		    } else { // item to delete founded
		        if (current.getLeft() != null && current.getRight() != null) { // if item to delete has two children
		            current.setElement(findMin(current.getRight()).getElement()); // replace current with the smallest in right sub tree
		            current.setRight(remove(current.getElement().getTitle(), current.getRight()));
		        } else {													   // if item to delete has one child
		            current = (current.getLeft() != null) ? current.getLeft() : current.getRight(); // replace node to delete with its only child
		        }
		    }
		    if (current != null) {
		        current.setHeight(1 + Math.max(height(current.getLeft()), height(current.getRight()))); // update height
		        return reBalance(current); // Ensure balance after removal
		    }
		    return null;
		}
		
		public Movie getLeastRankedMovie() {
		    if (root == null) {
		        return null; //tree is empty
		    }
		    return getLeastRankedMovie(root, root.getElement());
		}
		
		private Movie getLeastRankedMovie(AVLTreeNode current, Movie least) {
		    if (current == null) {
		        return least; // Base case , root as the least 
		    }
		    if (current.getElement().getRating() < least.getRating()) { 
		        least = current.getElement(); // Update least if current is smaller
		    }
		    // Traverse left and right subtrees, and update least as necessary
		    Movie leftLeast = getLeastRankedMovie(current.getLeft(), least); // least in left sub tree
		    Movie rightLeast = getLeastRankedMovie(current.getRight(), least); // least in right sub tree
		    // checking the least between left ,right, and current
		    if (leftLeast.getRating() < least.getRating()) {
		        least = leftLeast;
		    }
		    if (rightLeast.getRating() < least.getRating()) {
		        least = rightLeast;
		    }

		    return least;
		}
		
		public Movie getMostRankedMovie() {
		    if (root == null) {
		        return null; //tree is empty
		    }
		    return getMostRankedMovie(root, root.getElement());
		}
		
		private Movie getMostRankedMovie(AVLTreeNode current, Movie most) {
		    if (current == null) {
		        return most; // Base case, root as the most
		    }
		    if (current.getElement().getRating() > most.getRating()) {
		        most = current.getElement(); // Update most if current is bigger
		    }
		    Movie leftLeast = getMostRankedMovie(current.getLeft(), most); // most in left sub tree
		    Movie rightLeast = getMostRankedMovie(current.getRight(), most);// most in right sub tree

		    // biggest between current, left, and right
		    if (leftLeast.getRating() > most.getRating()) {
		        most = leftLeast;
		    }
		    if (rightLeast.getRating() > most.getRating()) {
		        most = rightLeast;
		    }

		    return most;
		}
		
		
		public void saveToFile (PrintWriter out) {
			this.printTreeInOrderToFile(root, out);
		}
		
		// printing the tree to file in order 
		private void printTreeInOrderToFile(AVLTreeNode current, PrintWriter out) {
			if(current != null) {
				printTreeInOrderToFile(current.getLeft(),out);
				out.println(current.getElement());
				printTreeInOrderToFile(current.getRight(),out);
			}
		}
		
		public void printTreePre() {
			this.printTreePre(root);
		}
		
		// print tree pre order
		private void printTreePre(AVLTreeNode current) {
			if(current != null) {
				System.out.println(current.getElement());
				printTreePre(current.getLeft());
				printTreePre(current.getRight());
			}
		}
		
		public void printTreeIn() {
			this.printTreeIn(root);
		}
		// print tree in order
		private void printTreeIn(AVLTreeNode current) {
			if(current != null) {
				printTreeIn(current.getLeft());
				System.out.println(current.getElement());
				printTreeIn(current.getRight());
			}
		}
		
		public ArrayList<Movie> printTreeReversed() {
			return this.printTreeReversed(root, new ArrayList<Movie>());
		}
		// traverse the tree from right to left and add its elements to arraylist
		private ArrayList<Movie> printTreeReversed(AVLTreeNode current, ArrayList<Movie> result) {
			if(current != null) {
				printTreeReversed(current.getRight(),result);
				result.add(current.getElement());
				printTreeReversed(current.getLeft(),result);
			}
			return result;
		}
		
		public ArrayList<Movie> printTreeInOrder() {
			return this.printTreeInOrder(root, new ArrayList<Movie>());
		}
		// traverse the tree from lest to right (InOrder) and add its elements to arraylist
		private ArrayList<Movie> printTreeInOrder(AVLTreeNode current, ArrayList<Movie> result) {
			if(current != null) {
				printTreeInOrder(current.getLeft(),result);
				result.add(current.getElement());
				printTreeInOrder(current.getRight(),result);
			}
			return result;
		}
		
		public void printTreePost() {
			this.printTreePost(root);
		}
		
		
		private void printTreePost(AVLTreeNode current) {
			if(current != null) {
				printTreePost(current.getLeft());
				printTreePost(current.getRight());
				System.out.println(current.getElement());
			}
		}
		
		public boolean isEmpty() {
			return this.root == null;
		}
		
	
}
