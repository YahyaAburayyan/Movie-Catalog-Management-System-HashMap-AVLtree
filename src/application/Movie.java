package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Movie {
	// Attributes
	private SimpleStringProperty title;
	private SimpleStringProperty description ;
	private SimpleIntegerProperty releaseYear;
	private SimpleDoubleProperty rating;
	
	// Constructors 
	public Movie () {
		
	}

	public Movie(String title, String description, int releaseYear,double rating) {
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.releaseYear = new SimpleIntegerProperty(releaseYear);
		this.rating = new SimpleDoubleProperty(rating);
	}

	// Setters and Getters
	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title = new SimpleStringProperty(title);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	}

	public int getReleaseYear() {
		return releaseYear.get();
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = new SimpleIntegerProperty(releaseYear);
	}

	public double  getRating() {
		return rating.get();
	}

	public void setRating(double rating) {
		this.rating = new SimpleDoubleProperty(rating);
	}


	// override 
	@Override
	public String toString() {
		return "Title:" + title.get() + "\nDescription:" + description.get() + "\nRelease Year:" + releaseYear.get() + "\nRating:"
				+ rating.get() +  "\n";
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Movie) {
			return ((Movie) obj).getTitle().equalsIgnoreCase(this.getTitle()) ;
 		}
		return false;
	}
	
	
	
	
	
}
