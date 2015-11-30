package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import org.joda.time.DateTime;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;
import de.uni_mannheim.informatik.wdi.usecase.wdiproject.Musician;

public class MusicianFactory extends MatchableFactory<Musician> {

	@Override
	public Musician createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		
		// create the object with id and provenance information
		Musician musician = new Musician(id, provenanceInfo);
		
		// fill the attributes
		musician.setFirstName(getValueFromChildElement(node, "firstname"));
		musician.setLastName(getValueFromChildElement(node, "lastname"));
		musician.setInstrument(getValueFromChildElement(node, "instrument"));
		musician.setBackground(getValueFromChildElement(node, "background"));
		musician.setGenre(getValueFromChildElement(node, "genre"));
		musician.setAssociatedBand(getValueFromChildElement(node, "bandassoc"));
		musician.setBirthPlace(getValueFromChildElement(node, "birthplace"));

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "birthdate");
			if(date!=null) {
				DateTime dt = DateTime.parse(date);
				musician.setBirthDate(dt);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return musician;
	}

}
