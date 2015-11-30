package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.wdi.datafusion.XMLFormatter;
import de.uni_mannheim.informatik.wdi.usecase.wdiproject.Musician;

public class MusicianXMLFormatter extends XMLFormatter<Musician> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("musicians");
	}

	@Override
	public Element createElementFromRecord(Musician record, Document doc) {
		Element musician = doc.createElement("musician");
		
		musician.appendChild(createTextElement("firstname", record.getFirstName(), doc));
		musician.appendChild(createTextElement("lastname", record.getLastName(), doc));
		musician.appendChild(createTextElement("instrument", record.getInstrument(), doc));
		musician.appendChild(createTextElement("background", record.getBackground(), doc));
		musician.appendChild(createTextElement("genre", record.getGenre(), doc));
		musician.appendChild(createTextElement("bandassoc", record.getAssociatedBand(), doc));
		musician.appendChild(createTextElement("birthplace", record.getBirthPlace(), doc));

		return musician;
	}

}
