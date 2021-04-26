package model;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model of all films, used in XML generation via JAXB.
 * 
 * @author jakeitaylor
 *
 */
@XmlRootElement(namespace = "xml.jaxb.model")
@XmlAccessorType(XmlAccessType.FIELD)
public class Films {

	// Set the XML parent tags for when JAXB generates XML data.
    @XmlElementWrapper(name = "filmList")
    @XmlElement(name = "film")
    private ArrayList<Film> filmList;

    public void setFilmList(ArrayList<Film> filmList) {
        this.filmList = filmList;
    }

    public ArrayList<Film> getFilmList() {
        return filmList;
    }

}