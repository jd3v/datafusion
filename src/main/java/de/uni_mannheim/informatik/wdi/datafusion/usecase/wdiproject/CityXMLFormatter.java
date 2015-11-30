package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.wdi.datafusion.XMLFormatter;

public class CityXMLFormatter extends XMLFormatter<FusableCity> {

	MusicianXMLFormatter musicianFormatter = new MusicianXMLFormatter();
	
	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("cities");
	}

	@Override
	public Element createElementFromRecord(FusableCity record, Document doc) {
		Element city = doc.createElement("city");

		city.appendChild(createTextElement("id", record.getIdentifier(), doc));
		
		city.appendChild(createTextElementWithProvenance("name", record.getName(), record.getMergedAttributeProvenance(FusableCity.NAME),doc));
		city.appendChild(createTextElementWithProvenance("countrycode", record.getCc(), record.getMergedAttributeProvenance(FusableCity.CC),doc));
		city.appendChild(createTextElementWithProvenance("population", record.getPopulation().toString(), record.getMergedAttributeProvenance(FusableCity.POPULATION),doc));
		city.appendChild(createTextElementWithProvenance("latitude", record.getLat().toString(), record.getMergedAttributeProvenance(FusableCity.LAT),doc));
		city.appendChild(createTextElementWithProvenance("longitude", record.getLong().toString(), record.getMergedAttributeProvenance(FusableCity.LONG),doc));

		
		city.appendChild(createActorsElement(record, doc));
		
		return city;
	}

	protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}
	
	protected Element createActorsElement(FusableCity record, Document doc) {
		Element actorRoot = actorFormatter.createRootElement(doc);
		actorRoot.setAttribute("provenanec", record.getMergedAttributeProvenance(FusableCity.ACTORS));
		
		for(Actor a : record.getActors()) {
			actorRoot.appendChild(actorFormatter.createElementFromRecord(a, doc));
		}
		
		return actorRoot;
	}
	
}
