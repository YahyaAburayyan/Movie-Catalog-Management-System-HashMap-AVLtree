package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;

public class HashMap {

	private int tabelSize;  // actual size of the hash (array)
	private int entrysSize = 0; // number of stored elements 
	private HashEntry [] hashTabel ;
	private double loadFactor = 0;
	
	
	public HashMap (int size) {   // creates a new hash with initialization and proper selection of the size that reduce collisions
		this.tabelSize = findNextPrime(size);
		this.hashTabel = new HashEntry[tabelSize];
		for(int i=0 ;i< tabelSize;i++) {
			hashTabel [i] = new HashEntry();
		}
		System.out.println("The choosed array size for the hash = " + tabelSize);
	}
	
	private int hashFunction(String key) { 
	    String keyInLower = key.toLowerCase();  // i adopted the lowered case latters for the hash function 
	    BigInteger hashCode = BigInteger.ZERO; // Start with zero for BigInteger , used big integer cuse the result dont fit in int or long  
	    int len = keyInLower.length();
	    for (int i = 0; i < len; i++) {
	        BigInteger charASCValue = BigInteger.valueOf((long) keyInLower.charAt(i)); 
	        BigInteger powerValue = BigInteger.valueOf(32).pow(i);
	        hashCode = hashCode.add(charASCValue.multiply(powerValue)); // Update hashCode using BigInteger operations
	    }
	    // Convert hashCode to an index within the range of the table size using (%)
	    BigInteger tableSize = BigInteger.valueOf(tabelSize);
	    int index = hashCode.mod(tableSize).intValue(); // Use mod to get index and convert to int
	    return Math.abs(index); // duo to big values that is out of rang of a int , results sometimes result become negative 
	}
	
	public int getIndexOfTree(String key) { // just getting the index a key will be mapped to 
		int index = hashFunction(key);
		if(hashTabel[index].getStatus()==1 ) { 
			System.out.println(index);
			return index;
		}
		return -1;
	}

	
	// this is the method for linear probing to solve collisions using it , but we are using separate chaening 
//	private int h(int i, String key) {
//	    key = key.toLowerCase(); // Ensure key is converted to lowercase
//	    int h = hashFunction(key);
//	    while (i < tabelSize) {
//	        int index = (h + i) % tabelSize;
//	        if (hashTabel[index].getStatus() == 0) {
//	            return index;
//	        }
//	        i++;
//	    }
//	    return h;
//	}

	public void insert(String key, Movie value) {
		if(getLoadFactor() >= 3.0) { // rehashing if the avg high of AVL trees exceeds 2 
			System.out.println("Rehashing");
			rehash();
		}
	    int index = hashFunction(key);
	    if (hashTabel[index].getStatus() == 1) { //means there is a movie already mapped to this index  
	    	hashTabel[index].getValue().insert(value);// now we need to solve the collision using the avl tree 
	    } else{ 
	    	// this means the hash entry is empty 
	        hashTabel[index] = new HashEntry(key.toLowerCase()); // create a hash entry with empty avl tree 
	        hashTabel[index].getValue().insert(value);// adding to the avl tree 
	    }
	    entrysSize++;// adding to the number of actual movies in the hash 
	}

	
	// delete method 
	public boolean delete (String key ) {
		int index = hashFunction(key);
		if(hashTabel[index].getStatus()==1 ) { // none empty 
			hashTabel[index].getValue().remove(key); // removing  from the avl tree 
			System.out.println("Deletion happened ");
			if(hashTabel[index].getValue().isEmpty()) { // checking if the avl tree is empty 
				hashTabel[index].setStatus(2);
				hashTabel[index].setKey(null);
				hashTabel[index].setValue(null);
			}
			return true;
		}
		return false;
	}
	
	
	// search method 
	
	public Movie search (String key) {
		int index = hashFunction(key); // index key will be mapped to
		if(hashTabel[index].getStatus()== 1 ) {  
			if(hashTabel[index].getValue().find(key) == null) { // searching in the AVL tree 
				return null;
			}
			System.out.println("founded ");
			return hashTabel[index].getValue().find(key).getElement();
		}
		return null;
	}
	
	 // search using the release year 
	public ArrayList<Movie> search (int releaseYear){
		ArrayList<Movie> result = new ArrayList<Movie>();
		for(int i=0 ; i<hashTabel.length ;i++) {
			if(hashTabel[i].getStatus() == 1) {
				ArrayList<Movie> inCurrentEntry = hashTabel[i].getValue().getMoviesWithReleaseYear(releaseYear);
				result.addAll(inCurrentEntry);
			}
		}
		return result;
	}
	
	
	// calculating load factor 
	public double getLoadFactor() {   // calculating the load factor for the hash
	    int sumOfHeights = 0;
	    int numberOfTrees = 0; 
	    for (int i = 0; i < hashTabel.length; i++) {
	        if (hashTabel[i].getStatus() == 1) {  // Status 1 means the entry have tree
	            sumOfHeights += hashTabel[i].getValue().heightOfTree();
	            numberOfTrees++;  // Increment only for non-empty entries
	        }
	    }
	    if (numberOfTrees == 0) {
	        return 0;  // to not divide by 0 and get the exception 
	    }
	    loadFactor = (double) sumOfHeights / numberOfTrees; 
	    return loadFactor;
	}
	
	

	private void rehash() {
		int newTabelSize = findNextPrime(2 * tabelSize); // finding the next prime larger than twice the current size of hash
		HashEntry [] oldHashTabel = this.hashTabel; // refrence to old hash tabel (array)
		System.out.println("new re size = " + newTabelSize);
		HashEntry [] newHashTabel = new HashEntry[newTabelSize];// create an hashtabel array with new size 
		this.hashTabel = newHashTabel; 
		// initlization for new hashtabel
		for(int i=0 ; i<newHashTabel.length ;i++) {
			newHashTabel[i] = new HashEntry();
		}
		// loop over old hash to move map it to the new one 
		for(int j=0 ;j<oldHashTabel.length ;j++) {
			if(oldHashTabel[j].getStatus() == 1) {  // if there is an element we need to move it to new hash 
				int index = hashFunction(oldHashTabel[j].getKey()); // caling the hash function
				if(newHashTabel[index].getStatus() == 0) { 
					newHashTabel[index].setKey(oldHashTabel[j].getKey());
					newHashTabel[index].setValue(oldHashTabel[j].getValue());
					newHashTabel[index].setStatus(1);

				}
			}
		}
		this.tabelSize = newHashTabel.length;
		oldHashTabel = null; // by removing the refrence to it it became garbge 
	}
	
	
	public void printHashMap () { // traverse the hash and print it entry by entry 
		for(int i=0 ; i< tabelSize ;i++) {
			if(hashTabel[i].getValue() != null) {
				hashTabel[i].getValue().printTreeIn();
				
			}
			System.out.println("-----");
		}
	}
	
	//save to file 
	public void saveMoviesToFile(PrintWriter out) throws FileNotFoundException {
		for(int i=0 ; i<tabelSize ;i++) {
			if(hashTabel[i].getValue() != null) {
				hashTabel[i].getValue().saveToFile(out);
			}
		}
	}
	
	public void free() {
	    // Free the hash table array itself
	    hashTabel = null;
	    tabelSize = 0;
	    entrysSize = 0;
	    loadFactor = 0.0;
	}
	
	// checks if the given number is a prime 
	private boolean isPrime(int n) {   
        if (n <= 1) {
            return false; 
        }
        for (int i = 2; i <= n / 2; i++) { 
            if (n % i == 0) {
                return false; // Not a prime number
            }
        }
        return true; // Prime number
    }

    //find the next prime number to be the size of the hash tabel 
    public int findNextPrime(int num) {
        num++; // Start checking from the next number
        while (!isPrime(num)) {
            num++;
        }
        return num;
    }
	
	// Setters and Getters
	public int getTabelSize() {
		return tabelSize;
	}

	public void setTabelSize(int tabelSize) {
		this.tabelSize = tabelSize;
	}

	public int getEntrysSize() {
		return entrysSize;
	}

	public void setEntrysSize(int entrysSize) {
		this.entrysSize = entrysSize;
	}

	public HashEntry[] getHashTabel() {
		return hashTabel;
	}

	public void setHashTabel(HashEntry[] hashTabel) {
		this.hashTabel = hashTabel;
	}

	public void setLoadFactor(double loadFactor) {
		this.loadFactor = loadFactor;
	}

	
	
}
