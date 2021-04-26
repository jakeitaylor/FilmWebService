package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Film;
import model.FilmDAO;

/**
 * Servlet for inserting a new film.
 * 
 * @author Jake Taylor
 *
 */
@WebServlet("/insertFilm")
public class InsertFilmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertFilmController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmDAO dao = new FilmDAO();
		
		// Get all parameters containing film data.
		String name = request.getParameter("name");
		String director = request.getParameter("director");
		String year = request.getParameter("year");
		String review = request.getParameter("review");
		String stars = request.getParameter("stars");
		
		// Create a new film object with the data.
		Film f = new Film();
		f.setTitle(name);
		f.setDirector(director);
		f.setYear(Integer.parseInt(year));
		f.setStars(stars);
		f.setReview(review);
		
		System.out.println("[/insertFilm] Request sent to servlet.");
		System.out.println("[/insertFilm] Film to insert:");
		System.out.println("[/insertFilm] Name: " + name);
		System.out.println("[/insertFilm] Director: " + director);
		System.out.println("[/insertFilm] Year: " + year);
		System.out.println("[/insertFilm] Review: " + review);
		System.out.println("[/insertFilm] Stars: " + stars);
		
		// Insert the film and check if it was successful, echo the result to the user.
		if (dao.insertFilm(f) == 1) {
			 response.getWriter().print("Success!");
			 System.out.println("[/insertFilm] Film was added successfully.");
		} else {
			 response.getWriter().print("Failure!");
			 System.out.println("[/insertFilm] Failed to insert film.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
