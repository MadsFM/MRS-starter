package easv.mrs.GUI.Model;

import easv.mrs.BE.Movie;
import easv.mrs.BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }



    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public Movie createNewMovie(Movie newMovie) throws Exception {
        Movie createdMovie = movieManager.createNewMovie(newMovie);
        moviesToBeViewed.add(createdMovie);
        return createdMovie;
    }

    public void deleteMovie(int id) throws Exception{
        Movie moviesToDelete = null;
        for (Movie movie : moviesToBeViewed) {
            if (movie.getId() == id){
                moviesToDelete = movie;
                break;
            }
        }
        if (moviesToDelete != null) {
            moviesToBeViewed.remove(moviesToDelete);
        } else {
            throw new Exception("Movie with ID: " + id + "not found");
        }
    }

}
