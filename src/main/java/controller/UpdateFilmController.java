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
 * Servlet for updating a film.
 * 
 * @author Jake Taylor
 *
 */
@WebServlet("/updateFilm")
public class UpdateFilmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFilmController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmDAO dao = new FilmDAO();
		
		// Get all parameters containing film data.
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String director = request.getParameter("director");
		String year = request.getParameter("year");
		String review = request.getParameter("review");
		String stars = request.getParameter("stars");
		
		System.out.println("[/updateFilm] Request sent to servlet.");
		System.out.println("[/updateFilm] Requesting to update film with ID: " + id);
		System.out.println("[/updateFilm] With data: Name: " + name + ". Director: " + director + ". Year: " + year + ". Review: " + review + ". Stars: " + stars);
		
		// Create a new film object with the data.
		Film f = new Film();
		f.setTitle(name);
		f.setDirector(director);
		f.setYear(Integer.parseInt(year));
		f.setStars(stars);
		f.setReview(review);
		
		// Insert the film and check if it was successful, echo the result to the user.
		if (dao.updateFilm(Integer.parseInt(id), f) == 1) {
			 response.getWriter().print("Success!");
			 System.out.println("[/updateFilm] Film updated successfully.");
		} else {
			 response.getWriter().print("Failure!");
			 System.out.println("[/updateFilm] Could not update film.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
