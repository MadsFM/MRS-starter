package easv.mrs.BLL;

import easv.mrs.BE.Movie;
import easv.mrs.BLL.util.MovieSearcher;
import easv.mrs.DAL.IMovieDataAccess;
import easv.mrs.DAL.MovieDAO_File;
import easv.mrs.DAL.db.MovieDAO_DB;

import java.io.IOException;
import java.util.List;

public class MovieManager {

    private MovieSearcher movieSearcher = new MovieSearcher();

    private IMovieDataAccess movieDAO;

    public MovieManager() throws IOException {
        movieDAO = new MovieDAO_DB();
        //movieDAO = new MovieDAO_Mock();

    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }

    public Movie createNewMovie(Movie newMovie) throws Exception {
        return movieDAO.createMovie(newMovie);
    }

    public void deleteMovie(Movie movie) throws Exception{
        movieDAO.deleteMovie(movie);
    }

    public void updateMovie(Movie updatedMovie) throws Exception {
        movieDAO.updateMovie(updatedMovie);
    }
}
