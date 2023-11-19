package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;

    @FXML
    private TextField txtYear, txtTitle, txtID;


    private MovieModel movieModel;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public Movie addMovie(ActionEvent actionEvent){
        String title = txtTitle.getText();
        int year = Integer.parseInt(txtYear.getText());
        Movie newMovie = new Movie(-1, year, title);

        try {
            newMovie = movieModel.createNewMovie(newMovie);

        } catch (Exception e){
            displayError(e);
            e.printStackTrace();
        }
        return newMovie;
    }

    public void deleteID(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtID.getText());
            movieModel.deleteMovie(id);
        } catch (NumberFormatException e) {
            displayError(new Exception("ID doesn't exist!"));
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void updateId(ActionEvent actionEvent) {
    }
}
