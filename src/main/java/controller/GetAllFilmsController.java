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
 * Servlet for getting all films.
 * 
 * @author Jake Taylor
 *
 */
@WebServlet("/getAllFilms")
public class GetAllFilmsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllFilmsController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmDAO dao = new FilmDAO();
		ArrayList<Film> films = new ArrayList<Film>();
		
		// Get the requested format (JSON/XML/Text).
		String format = request.getParameter("format");
		
		// If there is no format supplied, set to JSON.
		if (format == null) { 
			format = "json"; 
		}
		
		System.out.println("[/getAllFilms] Request sent to servlet. Request format is: " + format);
		
		films = dao.getAllFilms();
		
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
