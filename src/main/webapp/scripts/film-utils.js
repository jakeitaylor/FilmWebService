// Builds a URL to the getAllFilms endpoint using the format (json/xml/text).
function showAllFilms(resultRegion, format) {
    let url = "getAllFilms?format=" + getValue(format);
    ajaxResult(url, resultRegion);
}

// Builds a URL to the getFilm endpoint using the format and the film name.
function showSubsetFilms(searchField, resultRegion, format) {
    let url = "getFilm" + "?filmname=" + getValue(searchField)
                        + "&format=" + getValue(format);
    ajaxResult(url, resultRegion);
}

// Calls the endpoint to insert a film.
function insertFilm(title, director, year, stars, review, resultRegion) {

    // Check if the form passes validation checks.
    if (validateInsertForm() == true) {
        let url = "insertFilm" + "?name=" + getValue(title)
                               + "&director=" + getValue(director)
                               + "&year=" + getValue(year)
                               + "&stars=" + getValue(stars)
                               + "&review=" + getValue(review);

        // Create a bootstrap alert confirming insertion.
        ajaxResult(url, resultRegion);
        htmlInsert("insert-film-result",
                   "<br><div class='alert alert-success'>Success!</div>");
    }
}

// Validates the insert film form, returning true if passed.
function validateInsertForm() {
    // Get parameters from the DOM tree.
    let title = document.forms.insertForm.title.value;
    let director = document.forms.insertForm.director.value;
    let year = document.forms.insertForm.year.value;
    let stars = document.forms.insertForm.stars.value;
    let review = document.forms.insertForm.review.value;

    // Validation checks: empty values.
    if (title == "" || director == "" ||
        year == "" || stars == "" || review == "") {
        htmlInsert("insert-film-result",
                   ("<br><div class='alert alert-danger'>" +
                    "Error! No inputs can be empty." +
                    "</div>"));
        return false;

     // Validation checks: year value must be 4 digits.
     } else if (year.length > 4 || year.length < 4) {
         htmlInsert("insert-film-result",
                    ("<br><div class='alert alert-danger'>" +
                     "Error! Year must be 4 digits." +
                     "</div>"));
         return false;

      // Validation checks: year must be an integer.
      } else if (!(Number.isInteger(parseInt(year)))) {
          htmlInsert("insert-film-result",
                    ("<br><div class='alert alert-danger'>" +
                     "Error! Year must be an integer." +
                     "</div>"));
          return false;
      } else {
          return true;
      }
}

// Calls the remove film endpoint to remove film with given ID.
function removeFilm(url, request, id, resultRegion) {
    let del_url = "deleteFilm" + "?id=" + id;
    ajaxResult(del_url, resultRegion);
    ajaxResult(url, resultRegion);
}

// Gets the request / determine which object to use based on the browser.
function getRequestObject() {
  if (window.XMLHttpRequest) {
      return (new XMLHttpRequest());
  } else if (window.ActiveXObject) {
      return (new ActiveXObject("Microsoft.XMLHTTP"));
  } else {
      return (null);
  }
}

// Make a HTTP request to the URL.
function ajaxResult(url, resultRegion) {
  let request = getRequestObject();
  request.onreadystatechange =
    function() {
        showResponseText(url, request, resultRegion);
    };
  request.open("GET", url, true);
  request.send(null);
}

// Format and show the film data using AJAX.
function showResponseText(url, request, resultRegion) {

    // Determine if request was successful.
    if ((request.readyState == 4) && (request.status == 200)) {

        // Get the data and create an empty array to hold the film data.
        let data = request.responseText;

        // Gets the content type of the request (json/xml/text).
        let format = request.getResponseHeader("Content-Type");

        if (format == "application/json") {
            let films = eval("(" + data + ")");
            var rows = new Array();
		
	        // Loop through all films retrieved and add to array, separating id, title, director, stars and review data.
	        for (var i=0; i<films.length; i++) {
	            var film = films[i];
	            var id = film.id;
	            
	            // Generate buttons for removing and editing films.
	            removeButtonStr = `<input type="button" class="btn btn-danger" value="Remove" onclick='removeFilm("${url}", "${request}", "${id}", "subset-films-result")'/>`;
	            editButtonStr = `<input type="button" class="btn btn-primary" value="Edit" onclick='editFilm("${id}", "subset-film-result")'/>`;
	            
	            // Add parsed data to array.
	            rows[i] = [id, film.title + " (" + film.year + ")", film.director, film.stars, film.review, editButtonStr, removeButtonStr];
	        }
	    } else if (format == "text/xml") {
	    	// Declare DOM parser object and parse the XML data to a string object.
	    	parser = new DOMParser();
			xmlDoc = parser.parseFromString(data, "text/xml");
	    	xmlString = (new XMLSerializer()).serializeToString(xmlDoc);
    
			var rows = new Array();
			removeButtonStr = `<input type="button" class="btn btn-danger" value="Remove" onclick='removeFilm("${id}", "subset-films-result")'/>`;
	        editButtonStr = `<input type="button" class="btn btn-primary" value="Edit" onclick='editFilm("${id}", "subset-films-result")'/>`;
	            
	        rows[i] = ["test", ("test" + " (" + "2021" + ")"), "test", "test", "test", editButtonStr, (removeButtonStr+"<br>")];
	    
	    } else if (format == "text/plain") {
	    	var rows = new Array();
	    	var res = new Array();
	    	res = data.split("[");
	    	
	    	for (var i=0; i<res.length; i++) {
	            
	            // Search for each parameter in the string and get their positions.
	            var id_pos = res[i].search("id=");
	            var title_pos = res[i].search("title=");
	            var director_pos = res[i].search("director=");
	            var year_pos = res[i].search("year=");
	            var stars_pos = res[i].search("stars=");
	            var review_pos = res[i].search("review=");
	            
	            // Work out the value of each parameter with substrings.
	            var id =  res[i].substring(id_pos+3,title_pos-1);
	            var title =  res[i].substring(title_pos+6,director_pos-1);
	            var director =  res[i].substring(director_pos+9,year_pos-1);
	            var year =  res[i].substring(year_pos+5,stars_pos-1);
	            var stars =  res[i].substring(stars_pos+6,review_pos-1);
	            var review =  res[i].substring(review_pos+7);
	            
	            // Replace trailing commas and brackets to improve formatting.
	            id = id.replace(",","");
	            title = title.replace(",","");
	            year = year.replace(",","");
	            director = director.replace(",","");
	            review = review.replace("]]","");
	            review = review.replace("],","");
	            
	            removeButtonStr = `<input type="button" class="btn btn-danger" value="Remove" onclick='removeFilm("${id}", "subset-films-result")'/>`;
	            editButtonStr = `<input type="button" class="btn btn-primary" value="Edit" onclick='edit("${id}", "insert-film-result")'/>`;
	            
	            rows[i] = [id, (title + " (" + year + ")"), director, stars, review, editButtonStr, (removeButtonStr+"<br>")];

	    	}
	    	
	    	// Remove the first two elements of the array (to fix formatting issues).
	    	rows.splice(0,2);
	    	
	    }
        
        
        // Create the table using the rows and add a button to download data in correct format.
        var table = getFilmTable(rows);
        var fullHtml = table + "<br><b>Raw Data:</b><br>" + data;
        htmlInsert(resultRegion, fullHtml);
  }
}

// Build the table using the headings and rows and return the built table.
function getFilmTable(rows) {
    var headings = ["ID", "Title", "Director", "Stars", "Review", "Edit", "Remove"];
    var table = '<table class="table table-striped table-bordered table-lg" cellspacing="0" width="100%">\n' + 
              getTableHeadings(headings) +
              getTableBody(rows) +
              "</table>";
    return(table);
}

// Format the table headings using the array of headings supplied.
function getTableHeadings(headings) {
    var firstRow = '<br><tr>';
    for (var i=0; i<headings.length; i++) {
        firstRow += "<th>" + headings[i] + "</th>";
    }
    firstRow += "</tr>\n";
    return(firstRow);
}

// Format the table body using the rows passed in.
function getTableBody(rows) {
    var body = "";
    for (var i=0; i<rows.length; i++) {
        body += "  <tr>";
        var row = rows[i];
        for (var j=0; j<row.length; j++) {
            body += "<td>" + row[j] + "</td>";
        }
        body += "</tr>\n";
    }

    return(body);
}

// Insert the HTML data into the element that has the specified id.
function htmlInsert(id, htmlData) {
    document.getElementById(id).innerHTML = htmlData;
}

// Return escaped value of textfield that has given id.
// The builtin "escape" function url-encodes characters.
function getValue(id) {
    return(escape(document.getElementById(id).value));
}