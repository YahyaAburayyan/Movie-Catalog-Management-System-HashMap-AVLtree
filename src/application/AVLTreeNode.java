package application;

public class AVLTreeNode {

	private Movie element; //store data
	private AVLTreeNode left; // left child
	private AVLTreeNode right; //right child
	private int height; //Height
	
	// Constructors 
	public AVLTreeNode(Movie element){
		this(element, null, null);
	}
	public AVLTreeNode(Movie element, AVLTreeNode left, AVLTreeNode right){
		this.element=element;
		this.left=left;
		this.right=right;
		this.height=0;
	}
	// Setters and Getters
	public Movie getElement() {
		return element;
	}
	public void setElement(Movie element) {
		this.element = element;
	}
	public AVLTreeNode getLeft() {
		return left;
	}
	public void setLeft(AVLTreeNode left) {
		this.left = left;
	}
	public AVLTreeNode getRight() {
		return right;
	}
	public void setRight(AVLTreeNode right) {
		this.right = right;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	// toString override
	@Override
	public String toString() {
		return "AVLTreeNode [element=" + element + "]";
	}
	
	
}
