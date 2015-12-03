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

		city.appendChild(createTextElement("id", "fused"+record.getId(), doc));
		
		city.appendChild(createTextElementWithProvenance("name", record.getName(), record.getMergedAttributeProvenance(FusableCity.NAME),doc));
		city.appendChild(createTextElementWithProvenance("countrycode", record.getCc(), record.getMergedAttributeProvenance(FusableCity.COUNTRYCODE),doc));
		city.appendChild(createTextElementWithProvenance("population", String.valueOf(record.getPopulation()), record.getMergedAttributeProvenance(FusableCity.POPULATION),doc));
		city.appendChild(createTextElementWithProvenance("latitude", String.valueOf(record.getLat()), record.getMergedAttributeProvenance(FusableCity.LAT),doc));
		city.appendChild(createTextElementWithProvenance("longitude", String.valueOf(record.getLon()), record.getMergedAttributeProvenance(FusableCity.LONG),doc));

		
		city.appendChild(createMusiciansElement(record, doc));
		
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
