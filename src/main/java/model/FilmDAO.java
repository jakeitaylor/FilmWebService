package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Data Access Object to access and manipulate the 
 * MySQL database containing film data.
 * 
 * @author Jake Taylor
 *
 */
public class FilmDAO {
		
	Film film = null;
	Connection conn = null;
    Statement stmt = null;
    
    // Set login details for remote Google Cloud database.
	String user = "";
    String password = "";
    String url = "" + user;
    
	public FilmDAO() {}

	/**
	 * Open a connection to the database using the JDBC MySQL library.
	 */
	private void openConnection() {
		try {
		    //Class.forName("com.mysql.jdbc.GoogleDriver").newInstance();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e) { 
			System.out.println(e); 
		}

		try {
			// Get the connection using the MySQL credentials.
 			conn = DriverManager.getConnection(url, user, password);
		    stmt = conn.createStatement();
		} catch(SQLException e) { 
			e.printStackTrace(); 
		}	   
    }
	
	/**
	 * Close the connection to the database.
	 */
	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the next film in the ResultSet.
	 * 
	 * @param rs The ResultSet returned from the database query.
	 * @return f The next film in the ResultSet.
	 */
	private Film getNextFilm(ResultSet rs) {
    	Film f = null;
		try {
			// Create a new film object using the resultset to get the values.
			f = new Film(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
					rs.getString("director"), rs.getString("stars"), rs.getString("review"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    	return f;		
	}
	
	/**
	 * Get all films from the film table.
	 * 
	 * @return films An arraylist containing all films.
	 */
    public ArrayList<Film> getAllFilms() {
    	ArrayList<Film> films = new ArrayList<Film>();
		openConnection();
	
		try {
		    String query = "SELECT * FROM films";
		    ResultSet rs = stmt.executeQuery(query);
		    
		    // Loop through the resultset and get the next film.
		    // Add the film to the ArrayList.
		    while(rs.next()) {
		    	film = getNextFilm(rs);
		    	films.add(film);
		    }
		    System.out.println("[/getAllFilms] " + films.size() + " films returned to the user.");
		    System.out.println("[/getAllFilms] No errors present.");
		    stmt.close();
		    closeConnection();
		} catch(SQLException e) { 
			System.out.println("[/getAllFilms] Errors present.");
			e.printStackTrace(); 
		}

	   return films;
    }
    
    /**
     * Get a subset of films based on a query.
     * 
     * @param searchQuery The film name query to search.
     * @return ArrayList of the films containing the query in the title.
     */
    public ArrayList<Film> getFilm(String searchQuery) {
    	ArrayList<Film> films = new ArrayList<Film>();
		openConnection();
	
		try {
		    String query = "SELECT * FROM films WHERE title LIKE '%" + searchQuery + "%'";
		    ResultSet rs = stmt.executeQuery(query);
		    
		    // Loop through the resultset and get the next film.
		    // Add the film to the ArrayList.
		    while(rs.next()) {
		    	film = getNextFilm(rs);
		    	films.add(film);
		    }
		    
		    System.out.println("[/getFilm] " + films.size() + " films returned to the user.");
		    System.out.println("[/getFilm] No errors present.");
		    
		    stmt.close();
		    closeConnection();
		} catch(SQLException e) { 
			System.out.println("[/getFilm] Errors present.");
			e.printStackTrace(); 
		}

	   return films;
    }
    
    /**
     * Get a film object based on a given ID.
     * 
     * @param id ID of the film to retrieve.
     * @return film Film object of the film corresponding to the given ID.
     */
    public Film getFilmByID(int id) {
		openConnection();
		film = null;
		
		try {
		    String query = "SELECT * FROM films WHERE id=" + id;
		    ResultSet rs = stmt.executeQuery(query);
		    
		    // Check if there is a next value in the ResultSet.
		    // Get the film from the ResultSet.
		    while(rs.next()){
		    	film = getNextFilm(rs);
		    }

		    stmt.close();
		    closeConnection();
		} catch (SQLException e) { 
			e.printStackTrace();
		}

	   return film;
   }
   
    /**
     * Inserts a film into the database.
     * 
     * @param f The film to add.
     * @return Integer value indicating if the query was successful.
     */
   public int insertFilm(Film f) {
	   // Assign an ID to the new film.
	   // Get the current length of the film database and add 10001 (the starting ID number).
	   // This guarantees a unique ID.
	   int id = getAllFilms().size() + 10001;
	   
	   openConnection();
	   
	   try {
		   String query = "INSERT INTO films (id, title, year, director, stars, review) VALUES (?, ?, ?, ?, ?, ?)";
		   
		   // Use a prepared statement to plug in the values.
		   // Helps sanitise the data and protects against SQL Injection attacks.
		   PreparedStatement ps = conn.prepareStatement(query);
		   ps.setInt(1, id);
		   ps.setString(2, f.getTitle());
		   ps.setInt(3, f.getYear());
		   ps.setString(4, f.getDirector());
		   ps.setString(5, f.getStars());
		   ps.setString(6, f.getReview());
		   
		   // Returns 1 if successful and 0 if not.
		   if (ps.executeUpdate() == 1) {
			   closeConnection();
			   return 1;
		   } else {
			   return 0;
		   }
		   
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return 0;
   }
   
   /**
    * Updates an existing film in the database.
    * 
    * @param id ID of the film to update.
    * @param f Film object containing the new film data.
    * @return Integer value indicating if the query was successful.
    */
   public int updateFilm(int id, Film f) {
	   openConnection();
	   
	   try {
		   String query = "UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ? WHERE id = ?";
		   PreparedStatement ps = conn.prepareStatement(query);
		   ps.setString(1, f.getTitle());
		   ps.setInt(2, f.getYear());
		   ps.setString(3, f.getDirector());
		   ps.setString(4, f.getStars());
		   ps.setString(5, f.getReview());
		   ps.setInt(6, id);
		   
		   // Returns 1 if successful and 0 if not.
		   if (ps.executeUpdate() == 1) {
			   closeConnection();
			   return 1;
		   } else {
			   return 0;
		   }
		   
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return 0;
   }
   
   /**
    * Removes a film from the database.
    * 
    * @param id ID of the film to remove
    * @return Integer value indicating if the query was successful.
    */
   public int deleteFilm(int id) {
	   openConnection();
	   
	   try {
		   String query = "DELETE FROM films WHERE id = ?";
		   PreparedStatement ps = conn.prepareStatement(query);
		   ps.setInt(1, id);
		   
		   // Returns 1 if successful and 0 if not.
		   if (ps.executeUpdate() == 1) {
			   closeConnection();
			   return 1;
		   } else {
			   return 0;
		   }
		   
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return 0;
   }
   
   
   
   
}
