package easv.mrs.DAL;

import easv.mrs.BE.Movie;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private static Path filePath = Path.of(MOVIES_FILE);


    @Override
    public List<Movie> getAllMovies() throws Exception {
        List<String> lines = Files.readAllLines(filePath);
        List<Movie> movies = new ArrayList<>();

        for (String line: lines) {
            String[] separatedLine = line.split(",");

            int id = Integer.parseInt(separatedLine[0]);
            int year = Integer.parseInt(separatedLine[1]);
            String title = separatedLine[2];
            if(separatedLine.length > 3)
            {
                for(int i = 3; i < separatedLine.length; i++)
                {
                    title += "," + separatedLine[i];
                }
            }
            Movie movie = new Movie(id, year, title);
            movies.add(movie);
        }
        return movies;
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        List<String> movies = Files.readAllLines(filePath);

        if (movies.size() > 0) {

            int year = movie.getYear();
            String title = movie.getTitle();
            // get next id
            String[] separatedLine = movies.get(movies.size() - 1).split(",");
            int nextId = Integer.parseInt(separatedLine[0]) + 1;
            String newMovieLine = nextId + "," + year + "," + title;
            Files.write(filePath, (newMovieLine + "\r\n").getBytes(), APPEND);
            return new Movie(nextId, year, title);
        }
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        List<String> movies = Files.readAllLines(filePath);
        List<String> updatedMovies = new ArrayList<>();

        for (String line : movies) {
            String[] seperatedID = line.split(",");
            int id = Integer.parseInt(seperatedID[0]);
            if (id != movie.getId()) {
                updatedMovies.add(line);
            }
        }

        Files.write(filePath, updatedMovies);

    }
}