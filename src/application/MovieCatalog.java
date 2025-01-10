package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MovieCatalog {
	private HashMap catalog;
	private int current = 0;
	
	// allocate functionality
	public MovieCatalog (int expectedInitialSize) {
		catalog = new HashMap(expectedInitialSize); // the allocate functionality implemented inside the hash map 
	}
	
	// put functionality
	public boolean insert(Movie movie) {
		//we need to search first if the movie in the system using its title 
		Movie isEixists = this.search(movie.getTitle());
		if(isEixists == null) {
			catalog.insert(movie.getTitle(), movie); // inserting to the hash map 
			int x = catalog.getIndexOfTree(movie.getTitle()); // updating the current
			if(x == -1) {
				// means not found 
			}else {
				current = x;
			}
			return true;
		}else {// the movie is in the system
			System.out.println("There is a movie with this title in the system = " + movie.getTitle());
			return false;
		}
		
	}
	
	// get functionality
	public Movie search(String title) { // searching using the title 
		Movie isExisits = catalog.search(title);
		if(isExisits == null ) {
			return null;
		}else {
			current = catalog.getIndexOfTree(title); // update the current to the hash entry we mapped to 
			return isExisits;
		}
	}
	
	public ArrayList<Movie> search (int releaseYear) {  	// searching using the release year 
		ArrayList<Movie> result = catalog.search(releaseYear);
		return result;
	}
	
	//erase functionality
	public boolean remove (String title ) { // find a movie using its title and remove it 
		return this.catalog.delete(title);
				
	}
	
	// put functionality
	// updating methods 
	public Movie updateDescription(String title, String newDescription) {
		Movie isExisits = this.search(title);
		if(isExisits != null) {
			isExisits.setDescription(newDescription);
		}
		return isExisits;
	}
	
	public Movie updateReleaseYear(String title, int newReleaseYear) {
		Movie isExisits = this.search(title);
		if(isExisits != null) {
			isExisits.setReleaseYear(newReleaseYear);
		}
		return isExisits;
	}
	
	public Movie updateRating(String title, double newRating) {
		Movie isExisits = this.search(title);
		if(isExisits != null) {
			isExisits.setRating(newRating);
		}
		return isExisits;
	}
	
	// least and most rating movies in current hash entry
	public Movie getLeastRatingMovie() {
		return catalog.getHashTabel()[current].getValue().getLeastRankedMovie();
	}
	
	public Movie getMostRatingMovie() {
		return catalog.getHashTabel()[current].getValue().getMostRankedMovie();
	}
	
	
	// load movies from file 
	public void loadMoviesFromFile() throws FileNotFoundException {
	    File file = new File("movies.txt");
	    if (!file.exists()) {
	        System.out.println("File not found: movies.txt");
	        return;
	    }
	    
	    try (Scanner in = new Scanner(file)) {
	        while (in.hasNextLine()) {
	            try {
	                // Skip blank lines before the next movie entry
	                String line = in.nextLine().trim();
	                while (line.isEmpty() && in.hasNextLine()) {
	                    line = in.nextLine().trim();
	                }
	                String l1 = line; 				// each movie data stored in 4 lines 
	                String l2 = in.nextLine().trim(); 
	                String l3 = in.nextLine().trim(); 
	                String l4 = in.nextLine().trim(); 
	                // Skip if any field is empty
	                if (l1.isEmpty() || l2.isEmpty() || l3.isEmpty() || l4.isEmpty()) {
	                    System.out.println("Skipping incomplete movie entry.");
	                    continue;
	                }
	                // extracting the title
	                String title = "";
	                if (l1.startsWith("Title:")) {// check if this is the Title line 
	                	int index = l1.indexOf(':');
	                    title = l1.substring(index+1).trim();
	                    //System.out.println(title);
	                } else {
	                    System.out.println("line dont start with Title");
	                    continue;
	                }
	                // extracting the  description
	                String description = "";
	                if (l2.startsWith("Description:")) {
	                	int index = l2.indexOf(':');
	                    description = l2.substring(index+1).trim();
	                    //System.out.println(description);
	                } else {
	                    System.out.println("line dont start with Description");
	                    continue;
	                }

	                // extracting the release year
	                int releaseYear = 0;
	                if (l3.startsWith("Release Year:")) {
	                    try {
	                    	int index = l3.indexOf(':');
	                        releaseYear = Integer.parseInt(l3.substring(index+1).trim());
	                        //System.out.println(releaseYear);
	                    } catch (NumberFormatException e) {
	                        System.out.println("Invalid Release Year need to a number ");
	                        continue;
	                    }
	                } else {
	                    System.out.println("line dont start with Release Year");
	                    continue;
	                }

	                // extracting the rating
	                double rating = 0.0;
	                if (l4.startsWith("Rating:")) {
	                    try {
	                    	int index = l4.indexOf(':');
	                        rating = Double.parseDouble(l4.substring(index+1).trim());
	                       // System.out.println(rating);
	                        if (rating < 0 || rating > 10) {
	                            System.out.println("Rating out of range: " + rating);
	                            continue;
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("Invalid Rating needs to be number ");
	                        continue;
	                    }
	                } else {
	                    System.out.println("line dont start with Rating");
	                    continue;
	                }

	                // Create and insert the movie
	                Movie newMovie = new Movie(title, description, releaseYear, rating);
	                this.insert(newMovie);
	                current = 0;
	            } catch (NoSuchElementException e) {
	                System.out.println("Incomplete movie entry found. Ending file processing.");
	                break; // Exit if fewer than 4 lines remain
	            }
	        }
	    }
	    
	}

	// save movies to file 
	public void saveMoviesToFile() throws FileNotFoundException {
		File file = new File("movies.txt");
		PrintWriter out = new PrintWriter(file);
		catalog.saveMoviesToFile(out);
		out.close();
	}
	
	// printing current avl tree in ascending order 
	public ArrayList<Movie> printASC () {
		if(catalog.getHashTabel()[current].getValue() != null) {
			return catalog.getHashTabel()[current].getValue().printTreeInOrder();
		}
		return null;
	}
	
	// printing current avl tree in descending order 
	public ArrayList<Movie> printDESC () {
		if(catalog.getHashTabel()[current].getValue() != null) {
			return catalog.getHashTabel()[current].getValue().printTreeReversed();
		}
		return null;
	}
	
	
	public void printCatalog() {
		catalog.printHashMap();
	}
	
	// the hight of current AVL tree 
	public int getHeightOfCurrentEntry() {
		if(this.catalog.getHashTabel()[current].getValue() == null) {
			return 0;
		}
		return this.catalog.getHashTabel()[current].getValue().heightOfTree();
	}
	
	// free the memory
	public void free() {
		catalog.free();
	}

	// setters and getters
	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
	
	public int size() {
		return catalog.getTabelSize();
	}
	
}



	