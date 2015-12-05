package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.wdi.datafusion.XMLFormatter;
import de.uni_mannheim.informatik.wdi.usecase.wdiproject.Musician;

public class CityXMLFormatter extends XMLFormatter<FusableCity> {

	MusicianXMLFormatter musicianFormatter = new MusicianXMLFormatter();
	
	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("cities");
	}

	@Override
	public Element createElementFromRecord(FusableCity record, Document doc) {
		Element city = doc.createElement("city");

//		city.appendChild(createTextElement("id", "fused"+record.getId(), doc));
//		city.appendChild(createTextElement("id", record.getIdentifier(), doc));
		city.appendChild(createTextElementWithProvenance("id", "fused"+record.getId(), record.getIdentifier(),doc));
		
		city.appendChild(createTextElementWithProvenance("name", record.getName(), record.getMergedAttributeProvenance(FusableCity.NAME),doc));
		city.appendChild(createTextElement("countrycode", record.getCc(), doc));
		city.appendChild(createTextElement("population", String.valueOf(record.getPopulation()), doc));
		city.appendChild(createTextElement("latitude", String.valueOf(record.getLat()), doc));
		city.appendChild(createTextElement("longitude", String.valueOf(record.getLon()),doc));
	
		

		
//		city.appendChild(createMusiciansElement(record, doc));
		
		return city;
	}

	protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}
	
	protected Element createMusiciansElement(FusableCity record, Document doc) {
		Element musicianRoot = musicianFormatter.createRootElement(doc);
		musicianRoot.setAttribute("provenance", record.getMergedAttributeProvenance(FusableCity.MUSICIANS));
		
		for(Musician a : record.getMusicians()) {
			musicianRoot.appendChild(musicianFormatter.createElementFromRecord(a, doc));
		}
		
		return musicianRoot;
	}
	
}
