package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FilmDAO;

/**
 * Servlet for deleting a film.
 * 
 * @author Jake Taylor
 *
 */
@WebServlet("/deleteFilm")
public class DeleteFilmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFilmController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmDAO dao = new FilmDAO();
		
		String id = request.getParameter("id");
		
		System.out.println("[/deleteFilm] Request sent to servlet.");
		System.out.println("[/deleteFilm] Requesting to delete film with ID: " + id);
		
		// Check if 1 is returned (deletion successful) and show this to the user.
		if (dao.deleteFilm(Integer.parseInt(id)) == 1) {
			 response.getWriter().print("Success!");
			 System.out.println("[/deleteFilm] Film deleted with ID: " + id);
		} else {
			 response.getWriter().print("Failure!");
			 System.out.println("[/deleteFilm] Failed to delete film with ID: " + id);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
