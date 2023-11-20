package easv.mrs.DAL.db;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.IMovieDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;

    public MovieDAO_DB() throws IOException {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {
        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Movie;";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Movie object
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                int year = rs.getInt("year");

                Movie movie = new Movie(id, year, title);
                allMovies.add(movie);
            }
            return allMovies;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get movies from database", ex);
        }
    }

    public Movie createMovie(Movie movie) throws Exception {
        String sql = "INSERT INTO dbo.Movie (Title,Year) VALUES (?,?);";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Bind parameters
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getYear());

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create movie object and send up the layers
            Movie createdMovie = new Movie(id, movie.getYear(), movie.getTitle());

            return createdMovie;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create movie", ex);
        }
    }

    public void updateMovie(Movie movie) throws Exception {
        String sql = "UPDATE FROM dbo.Movie WHERE Id = ?;";

        try (Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, movie.getId());



        }
    }

    public void deleteMovie(Movie movie) throws Exception {
        String sql = "DELETE FROM dbo.Movie WHERE ID = ?;";

        try (Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, movie.getId());

            int deletetMovies = statement.executeUpdate();
            if (deletetMovies == 0){
                throw new Exception("No movie found with ID: " + movie.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Couldn't delete movie");
        }
    }

    public List<Movie> searchMovies(String query) throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

}
