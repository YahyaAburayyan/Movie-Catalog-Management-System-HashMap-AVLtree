package application;
	
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import javax.lang.model.type.ErrorType;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	
	// base
	private BorderPane root = new BorderPane();
	private MovieCatalog catalog = new MovieCatalog(1);
	//fonts
	private Font f1 = Font.font("Times new roman", FontWeight.BOLD, FontPosture.REGULAR, 16);
	private Font f2 = Font.font("Times new roman", FontWeight.NORMAL, FontPosture.REGULAR, 16);
	private Font f3 = Font.font("Times new roman", FontWeight.BOLD, FontPosture.REGULAR, 20);
	private Font f4 = Font.font("Times new roman", FontWeight.NORMAL, FontPosture.REGULAR, 20);
	private Font f5 = Font.font("Times new roman", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 45);
	//Menu Bar
	private MenuBar featuresBar = new MenuBar();
	private Menu file = new Menu("File");
	private Menu movie = new Menu("Movie");
	
	private MenuItem open = new MenuItem("Open");
	private MenuItem save = new MenuItem("Save");
	private MenuItem exit = new MenuItem("Exit");
	
	private MenuItem addMovie = new MenuItem("Add Movie");
	private MenuItem updateMovie = new MenuItem("Update Movie");
	private MenuItem deleteMovie = new MenuItem("Delete Movie");
	private MenuItem searchMovie = new MenuItem("Search Movie");
	private MenuItem printsorted = new MenuItem("Print Sorted");
	private MenuItem printLeastAndMost = new MenuItem("Print Least and Most Ranked");
	
	//Tables
	private TableView<Movie> MoviesTable = new TableView<>();
	private ObservableList<Movie> MoviesList = FXCollections.observableArrayList();
	private TableColumn movieTitle = new TableColumn("Title");
	private TableColumn movieDescription = new TableColumn("Description");
	private TableColumn movieReleaseYear = new TableColumn("Release Year");
	private TableColumn movieRating = new TableColumn("Rating");
	
	//Welcome Page
	private VBox welcome = new VBox(20);
	private Label lblwelcome = new Label("Welcome to, ");
	private Label lblto = new Label("Movie Catalog Management System.");
	private Label lblstart = new Label("choose what you want to do from the above Menu"); 
	private void welcomePage() {
		welcome.setPadding(new Insets(5));
		welcome.setAlignment(Pos.CENTER);
		welcome.setStyle("-fx-background-color: #1b232a");
		lblwelcome.setStyle("-fx-text-fill: white");
		lblto.setStyle("-fx-text-fill: #f94000");
		lblstart.setStyle("-fx-text-fill: white");
		lblwelcome.setFont(f2);
		lblto.setFont(f5);
		lblstart.setFont(f1);
		welcome.getChildren().addAll(lblwelcome,lblto,lblstart);
		root.setCenter(welcome);
	}
	
	private void initialBar() {
		file.getItems().addAll(open,save,exit);
		movie.getItems().addAll(addMovie,deleteMovie,updateMovie,searchMovie,printsorted,printLeastAndMost);
		featuresBar.getMenus().addAll(file,movie);
		root.setTop(featuresBar);

		
		movieTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
		movieDescription.setCellValueFactory(new PropertyValueFactory<Movie, String>("description"));
		movieReleaseYear.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("releaseYear"));
		movieRating.setCellValueFactory(new PropertyValueFactory<Movie, Double>("rating"));
		MoviesTable.getColumns().addAll(movieTitle,movieDescription,movieReleaseYear,movieRating);
		
		showMovies.getChildren().addAll(btPrivious,lblAVLTreeHeight,lblheight,btNext);
		showMovies.setPadding(new Insets(5));
		showMovies.setAlignment(Pos.CENTER);
		lblAVLTreeHeight.setFont(f1);
		lblheight.setFont(f2);
		lblAVLTreeHeight.setStyle("-fx-text-fill: white");
		lblheight.setStyle("-fx-text-fill: #f94000");
		showMovies.setStyle("-fx-background-color: #1b232a");
		btPrivious.setStyle("-fx-background-color: #f94000");
		btNext.setStyle("-fx-background-color: #f94000");
		nextBt.getChildren().add(btNext);
		prevBt.getChildren().add(btPrivious);
		

	}
	
	// current nex, prev 
	private HBox showMovies = new HBox(7);
	private Button btPrivious = new Button("Prev");
	private Label lblAVLTreeHeight = new Label("AVL Tree Height : ");
	private Label lblheight= new Label();
	private Button btNext = new Button("Next");
	private VBox nextBt = new VBox(50);
	private VBox prevBt = new VBox(50);
	private void firstButtom() {
		showMovies.setPadding(new Insets(8));
		lblheight.setText(catalog.getHeightOfCurrentEntry()+"");
		nextBt.setStyle("-fx-background-color: #1b232a");
		prevBt.setStyle("-fx-background-color: #1b232a");
		nextBt.setPadding(new Insets(7));
		prevBt.setPadding(new Insets(7));
		nextBt.setAlignment(Pos.CENTER);
		prevBt.setAlignment(Pos.CENTER);
		root.setLeft(null);
		root.setRight(null);
		root.setBottom(null);
		root.setLeft(prevBt);
		root.setRight(nextBt);
		root.setBottom(showMovies);
	}
	
	private void showMovies () {
		MoviesList.clear();
		ArrayList<Movie> list = catalog.printASC();
		if(list != null) {
			MoviesList.addAll(list);
		}
		MoviesTable.setItems(MoviesList);
		root.setCenter(MoviesTable);
		firstButtom();
	}
	
	//insert Movie : 
	private FlowPane inMovieBox = new FlowPane();
	private Label lblMovieTitle = new Label("Title : ");
	private Label lblMovieDescription = new Label("Description : ");
	private Label lblMovieReleaseYear = new Label("Release Year : ");
	private Label lblMovieRating = new Label("Rating : ");
	private TextField tfMovieTitle = new TextField();
	private TextField tfMovieDescription = new TextField();
	private DatePicker dpMovieReleaseYear = new DatePicker();
	private TextField tfMovieRating = new TextField();
	private Button btInStu = new Button("Add");
	private void insertMovie() {
	    inMovieBox.getChildren().clear();
	    inMovieBox.setHgap(10);
	    inMovieBox.setVgap(10);
	    inMovieBox.setPadding(new Insets(8));
	    lblMovieTitle.setStyle("-fx-text-fill: white");
	    lblMovieDescription.setStyle("-fx-text-fill: white");
	    lblMovieReleaseYear.setStyle("-fx-text-fill: white");
	    lblMovieRating.setStyle("-fx-text-fill: white");
	    inMovieBox.setStyle("-fx-background-color: #1b232a");
	    btInStu.setStyle("-fx-background-color: #f94000");

	    inMovieBox.getChildren().addAll(
	        lblMovieTitle, tfMovieTitle,
	        lblMovieDescription, tfMovieDescription,
	        lblMovieReleaseYear, dpMovieReleaseYear,
	        lblMovieRating, tfMovieRating,
	        btInStu
	    );

	    btInStu.setOnAction(e -> {
	        // Get the input values
	        String title = tfMovieTitle.getText().trim();
	        String description = tfMovieDescription.getText().trim();
	        String releaseYearText = dpMovieReleaseYear.getValue().getYear()+"" != null? dpMovieReleaseYear.getValue().getYear()+"": "";
	        String ratingText = tfMovieRating.getText().trim();
	        // Validate inputs
	        if (title.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a movie title.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        if (description.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a movie description.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        if (releaseYearText.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a release year.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        if (ratingText.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a movie rating.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        try {
	            int releaseYear = Integer.parseInt(releaseYearText);
	            double rating = Double.parseDouble(ratingText);

	            // Validate rating range
	            if (rating < 0 || rating > 10) {
	                Alert alert = new Alert(Alert.AlertType.WARNING, "Rating must be between 0 and 10.", ButtonType.OK);
	                alert.showAndWait();
	                return;
	            }
	            // Insert the new movie
	            Movie newMovie = new Movie(title, description, releaseYear, rating);
	            if(catalog.insert(newMovie)) {
	            	succesAlert("The movie was added successfully.");
	            }else{
	            	Alert alert = new Alert(Alert.AlertType.ERROR, "A movie with this title already exists. Please use a unique title.", ButtonType.OK);
	                alert.showAndWait();
	                return;
	            }
	            showMovies(); // Refresh movie display
	            // Clear input fields
	            tfMovieTitle.clear();
	            tfMovieDescription.clear();
	            dpMovieReleaseYear.setValue(null);
	            tfMovieRating.clear();
	        } catch (NumberFormatException ex) {
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid number format for release year or rating.", ButtonType.OK);
	            alert.showAndWait();
	        }
	    });
	    showMovies();
	    root.setBottom(null);
	    root.setBottom(inMovieBox);
	}
	
	//update Movie
	private HBox updateMovieBox = new HBox(7);
	private Label lblMovieUp = new Label("Movie Title : ");
	private TextField tfMovieTitleToUp = new TextField();
	private Label lblChooseMovieAtt = new Label("Choose what to update");
	private ComboBox<String> cbMovieUpAtt = new ComboBox<>();
	private TextField tfMovieUpAtt = new TextField();
	private Button btMovieUp = new Button("Update");

	private void updateMovie() {
	    updateMovieBox.getChildren().clear();
	    updateMovieBox.setPadding(new Insets(8));
	    lblMovieUp.setStyle("-fx-text-fill: white");
	    lblChooseMovieAtt.setStyle("-fx-text-fill: white");
	    updateMovieBox.setStyle("-fx-background-color: #1b232a");
	    btMovieUp.setStyle("-fx-background-color: #f94000");
	    cbMovieUpAtt.getItems().clear();
	    cbMovieUpAtt.getItems().addAll("Description", "Release Year", "Rating");
	    updateMovieBox.getChildren().addAll(lblMovieUp, tfMovieTitleToUp,lblChooseMovieAtt, cbMovieUpAtt,tfMovieUpAtt, btMovieUp);
	    btMovieUp.setOnAction(e -> {
	        // Get the input values
	        String movieTitle = tfMovieTitleToUp.getText().trim();
	        String attributeToUpdate = cbMovieUpAtt.getValue();
	        String newValue = tfMovieUpAtt.getText().trim();
	        // Validate inputs
	        if (movieTitle.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a movie title.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        if (attributeToUpdate == null || attributeToUpdate.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an attribute to update.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        if (newValue.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a new value for the selected attribute.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        // Retrieve the movie object
	        Movie movie = catalog.search(movieTitle);
	        if (movie == null) {
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Movie not found. Please check the movie title.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        // Update the movie based on the selected attribute
	        switch (attributeToUpdate.toLowerCase()) {
	            case "description":
	                movie.setDescription(newValue);
	                succesAlert("The movie description has been updated successfully.");
	                break;
	            case "release year":
	                try {
	                    int releaseYear = Integer.parseInt(newValue);
	                    movie.setReleaseYear(releaseYear);
	                    succesAlert("The movie release year has been updated successfully.");
	                } catch (NumberFormatException ex) {
	                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid release year. Please enter a valid number.", ButtonType.OK);
	                    alert.showAndWait();
	                }
	                break;
	            case "rating":
	                try {
	                    double rating = Double.parseDouble(newValue);
	                    if (rating < 0 || rating > 10) {
	                        Alert alert = new Alert(Alert.AlertType.ERROR, "Rating must be between 0 and 10.", ButtonType.OK);
	                        alert.showAndWait();
	                        return;
	                    }
	                    movie.setRating(rating);
	                    succesAlert("The movie rating has been updated successfully.");
	                } catch (NumberFormatException ex) {
	                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid rating. Please enter a number between 0 and 10.", ButtonType.OK);
	                    alert.showAndWait();
	                }
	                break;

	            default:
	                Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid attribute selected.", ButtonType.OK);
	                alert.showAndWait();
	        }
	        // Refresh the movie display
	        showMovies();
	        // Clear input fields
	        tfMovieTitleToUp.clear();
	        cbMovieUpAtt.setValue(null);
	        tfMovieUpAtt.clear();
	    });
	    root.setBottom(null);
	    root.setBottom(updateMovieBox);
	}
	
	//Delete Movie
	private HBox deleMovieBox = new HBox(7);
	private Label lbldeleMovieTitle = new Label("Movie Title to Delete: ");
	private TextField tfdeleMovie = new TextField();
	private Button btdeleMovie = new Button("Delete");

	private void deleteMovie() {
	    deleMovieBox.getChildren().clear();
	    deleMovieBox.setPadding(new Insets(8));
	    lbldeleMovieTitle.setStyle("-fx-text-fill: white");
	    deleMovieBox.setStyle("-fx-background-color: #1b232a");
	    btdeleMovie.setStyle("-fx-background-color: #f94000");
	    deleMovieBox.getChildren().addAll(lbldeleMovieTitle, tfdeleMovie, btdeleMovie);
	    btdeleMovie.setOnAction(e -> {
	        // Get the input value
	        String movieTitle = tfdeleMovie.getText().trim();
	        // Validate input
	        if (movieTitle.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a Movie Title to delete.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        // Retrieve the movie object
	        Movie movie = catalog.search(movieTitle);
	        if (movie == null) {
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Movie not found. Please check the Movie Title.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        // Perform deletion
	        catalog.remove(movie.getTitle());
	        succesAlert("The movie was deleted successfully.");
	        // Refresh the movie display
	        showMovies();
	        // Clear the input field
	        tfdeleMovie.clear();
	    });
	    root.setBottom(null);
	    root.setBottom(deleMovieBox);
	}
	
	//Search For Movie :
	private Label lblMovieSer = new Label("Choose what to search with");
	private ComboBox<String> searchType = new ComboBox<>(); 
	private TextArea movieSearch = new TextArea();
	private HBox movieSearchBox = new HBox(7);
	private TextField tfmovieSer = new TextField();
	private Button btstuser = new Button("Search");

	private void searchMovie() {
	    movieSearchBox.getChildren().clear();
	    movieSearchBox.setPadding(new Insets(8));
	    lblMovieSer.setStyle("-fx-text-fill: white");
	    movieSearchBox.setStyle("-fx-background-color: #1b232a");
	    btstuser.setStyle("-fx-background-color: #f94000");
	    movieSearch.setEditable(false);
	    searchType.getItems().clear();
	    searchType.getItems().addAll("Title", "Release Year");
	    movieSearchBox.getChildren().addAll(lblMovieSer, searchType, tfmovieSer, btstuser);
	    btstuser.setOnAction(e -> {
	        // Get the search type and input value
	        String selectedSearchType = searchType.getValue();
	        String searchInput = tfmovieSer.getText().trim();
	        // Validate inpu
	        if (selectedSearchType == null || selectedSearchType.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a search type.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        if (searchInput.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a search value.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        // Search logic based on selected search type
	        switch (selectedSearchType.toLowerCase()) {
	            case "title":
	                Movie movie = catalog.search(searchInput);
	                if (movie == null) {
	                    movieSearch.setText("No movie found with the provided title.");
	                    root.setCenter(movieSearch);
	                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No movie found with the provided title.", ButtonType.OK);
	                    alert.showAndWait();
	                } else {
	                    movieSearch.setText("Movie found, here is information about:\n" + movie.toString());
	                    root.setCenter(movieSearch);
	                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Movie found:\n" + movie.toString(), ButtonType.OK);
	                    alert.showAndWait();
	                }
	                break;
	            case "release year":
	                try {
	                    int releaseYear = Integer.parseInt(searchInput);
	                    ArrayList<Movie> moviesByYear = catalog.search(releaseYear);

	                    if (moviesByYear == null || moviesByYear.isEmpty()) {
	                        movieSearch.setText("No movies found for the provided release year.");
	                        root.setCenter(movieSearch);
	                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No movies found for the provided release year.", ButtonType.OK);
	                        alert.showAndWait();
	                    } else {
	                        //showMoviesInTableView(moviesByYear); // Assumes method to display movies in a TableView
	                    	MoviesList.clear();
	                    	MoviesList.addAll(moviesByYear);
	                    	MoviesTable.setItems(MoviesList);
	                    	root.setCenter(MoviesTable);
	                        movieSearch.clear(); // Clear the text area when showing the table view
	                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Movies found for the provided release year. Displaying results in the table view.", ButtonType.OK);
	                        alert.showAndWait();
	                    }
	                } catch (NumberFormatException ex) {
	                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid release year. Please enter a numeric value.", ButtonType.OK);
	                    alert.showAndWait();
	                }
	                break;

	            default:
	                Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid search type selected.", ButtonType.OK);
	                alert.showAndWait();
	        }
	    });
	    root.setBottom(null);
	    root.setBottom(movieSearchBox);
	}
	
	// print sorted 
	private Label lblascOrDesc = new Label("Choose sorting order: ");
	private ComboBox<String> cbascOrDesc = new ComboBox<>();
	private Button btprint = new Button("Print Sorted");
	private HBox printBox = new HBox(7);

	private void printSortedMovies() {
	    printBox.getChildren().clear();
	    printBox.setPadding(new Insets(8));
	    lblascOrDesc.setStyle("-fx-text-fill: white");
	    printBox.setStyle("-fx-background-color: #1b232a");
	    btprint.setStyle("-fx-background-color: #f94000");
	    cbascOrDesc.getItems().clear();
	    cbascOrDesc.getItems().addAll("Ascending", "Descending");
	    printBox.getChildren().addAll(lblascOrDesc, cbascOrDesc, btprint);
	    btprint.setOnAction(e -> {
	        // Get the sorting order selected by the user
	        String selectedOrder = cbascOrDesc.getValue();
	        // Validate input
	        if (selectedOrder == null || selectedOrder.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a sorting order.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        ArrayList<Movie> currentMovies = null;
	        // Sort movies based on the selected order
	        if (selectedOrder.equalsIgnoreCase("Ascending")) {
	        	currentMovies = catalog.printASC(); // Sorting by title in ascending order
	        } else if (selectedOrder.equalsIgnoreCase("Descending")) {
	        	currentMovies = catalog.printDESC(); // Sorting by title in descending order
	        }
	        
	        if (currentMovies == null || currentMovies.isEmpty()) {
	            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No movies found in the current hash entry.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	        MoviesList.clear();
        	MoviesList.addAll(currentMovies);
        	MoviesTable.setItems(MoviesList);
        	root.setCenter(MoviesTable);
	        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Movies sorted in " + selectedOrder + " order. Displaying results.", ButtonType.OK);
	        alert.showAndWait();
	    });

	    root.setBottom(null);
	    root.setBottom(printBox);
	}

	
	// print top and least ranked movie in the current hash entry 
	private GridPane leastAndMostBox = new GridPane();
	private Label lblleastTitle = new Label("Least Ranked Title: ");
	private Label lblleastTitleValue = new Label();
	private Label lblleastRating = new Label("Rating: ");
	private Label lblleastRatingValue = new Label();
	private Label lblmostTitle = new Label("Most Ranked Title: ");
	private Label lblmostTitleValue = new Label();
	private Label lblmostRating = new Label("Rating: ");
	private Label lblmostRatingValue = new Label();

	private void findLeastAndMostRankedMovies() {
	    // Clear and configure the GridPane
	    leastAndMostBox.getChildren().clear();
	    leastAndMostBox.setPadding(new Insets(10));
	    leastAndMostBox.setHgap(20);
	    leastAndMostBox.setVgap(10);
	    leastAndMostBox.setStyle("-fx-background-color: #1b232a; -fx-text-fill: white");
	    leastAndMostBox.setAlignment(Pos.CENTER);
	    // Add labels to the GridPane
	    leastAndMostBox.add(lblleastTitle, 0, 0);
	    leastAndMostBox.add(lblleastTitleValue, 1, 0);
	    leastAndMostBox.add(lblleastRating, 0, 1);
	    leastAndMostBox.add(lblleastRatingValue, 1, 1);
	    leastAndMostBox.add(lblmostTitle, 0, 2);
	    leastAndMostBox.add(lblmostTitleValue, 1, 2);
	    leastAndMostBox.add(lblmostRating, 0, 3);
	    leastAndMostBox.add(lblmostRatingValue, 1, 3);
	    lblleastTitle.setStyle("-fx-text-fill: #f94000");
	    lblleastRating.setStyle("-fx-text-fill: #f94000");
	    lblmostTitle.setStyle("-fx-text-fill: #f94000");
	    lblmostRating.setStyle("-fx-text-fill: #f94000");
	    lblleastTitle.setFont(f2);
	    lblleastRating.setFont(f2);
	    lblmostTitle.setFont(f2);
	    lblmostRating.setFont(f2);
	    lblleastTitleValue.setFont(f1);
	    lblleastRatingValue.setFont(f1);
	    lblmostTitleValue.setFont(f1);
	    lblmostRatingValue.setFont(f1);
	    // Find the least and most ranked movies
	    Movie leastRanked = catalog.getLeastRatingMovie();
	    Movie mostRanked = catalog.getMostRatingMovie();
	    // Update the labels with the movie details
	    lblleastTitleValue.setText(leastRanked.getTitle());
	    lblleastRatingValue.setText(String.valueOf(leastRanked.getRating()));
	    lblmostTitleValue.setText(mostRanked.getTitle());
	    lblmostRatingValue.setText(String.valueOf(mostRanked.getRating()));
	    // Set labels' styles
	    lblleastTitleValue.setStyle("-fx-text-fill: white");
	    lblleastRatingValue.setStyle("-fx-text-fill: white");
	    lblmostTitleValue.setStyle("-fx-text-fill: white");
	    lblmostRatingValue.setStyle("-fx-text-fill: white");
	    // Display the GridPane in the UI
	    root.setCenter(null);
	    root.setCenter(leastAndMostBox);
	}

	//success Alert
	private void succesAlert(String s) {
		Alert al = new Alert(AlertType.INFORMATION);
		al.setContentText(s);
		al.show();
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		launch(args);
		
	}
	
	
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			initialBar();
			welcomePage();
			
			btPrivious.setOnAction(e -> {
			    if (catalog.getCurrent() > 0) {
			        catalog.setCurrent(catalog.getCurrent() - 1);
			    }
			    btPrivious.setDisable(catalog.getCurrent() <= 0);
			    btNext.setDisable(catalog.getCurrent() >= catalog.size() - 1);
			    // Show the movie details
			    showMovies();
			});

			btNext.setOnAction(e -> {
			    if (catalog.getCurrent() < catalog.size() - 1) {
			        catalog.setCurrent(catalog.getCurrent() + 1);
			    }
			    btNext.setDisable(catalog.getCurrent() >= catalog.size() - 1);
			    btPrivious.setDisable(catalog.getCurrent() <= 0);
			    // Show the movie details
			    showMovies();
			});
			// File Menu Actions
			open.setOnAction(e -> {
				try {
					catalog.loadMoviesFromFile();
					succesAlert("Movies inserted from file");
					showMovies();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			save.setOnAction(e -> {
				try {
					catalog.saveMoviesToFile();
					succesAlert("Data saved succesfully");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			exit.setOnAction(e -> {
				catalog.free();
				primaryStage.close();
			});
			// Movie Menu Actions
			addMovie.setOnAction(e -> {
				insertMovie();
			});
			updateMovie.setOnAction(e -> {
				updateMovie();
			});
			deleteMovie.setOnAction(e -> {
				deleteMovie();
			});
			searchMovie.setOnAction(e -> {
				searchMovie();
			});
			printsorted.setOnAction(e -> {
				printSortedMovies();
			});
			printLeastAndMost.setOnAction(e -> {
				findLeastAndMostRankedMovies();
			});

			Scene scene = new Scene(root,1000,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
