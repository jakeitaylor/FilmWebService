package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Film;
import model.FilmDAO;

/**
 * Servlet for getting specific films based on a search query.
 * 
 * @author Jake Taylor
 *
 */
@WebServlet("/getFilm")
public class GetFilmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFilmController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmDAO dao = new FilmDAO();
		ArrayList<Film> films = new ArrayList<Film>();
		
		// Get the search query and the format (JSON/XML/Text).
		String filmname = request.getParameter("filmname");
		String format = request.getParameter("format");
		
		System.out.println("[/getFilm] Request sent to servlet. Request format is: " + format);
		System.out.println("[/getFilm] Search parameter is: " + filmname);
		
		films = dao.getFilm(filmname);
		
		// If there is no format supplied, set to JSON.
		if (format == null) { 
			format = "json"; 
		}
				
		request.setAttribute("films", films);
		String outputPage;
		
		// Check which format was given and set the correct content type of the request.
		// Also set the output page which contains functions to translate data into requested format.
		if (format.contains("xml")) {
			response.setContentType("text/xml");
		    outputPage = "/WEB-INF/results/films-xml.jsp";
		} else if (format.contains("json")) {
			response.setContentType("application/json");
			outputPage = "/WEB-INF/results/films-json.jsp";
		} else {
			response.setContentType("text/plain");
			outputPage = "/WEB-INF/results/films-string.jsp";
		}
		
		// Send a request to the output page.
		RequestDispatcher dispatcher = request.getRequestDispatcher(outputPage);
	    dispatcher.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
