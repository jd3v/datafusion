package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.wdi.MatchableFactory;
import de.uni_mannheim.informatik.wdi.datafusion.FusableFactory;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.usecase.wdiproject.Musician;

public class FusableCityFactory extends MatchableFactory<FusableCity> implements FusableFactory<FusableCity> {

	@Override
	public FusableCity createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		
		// create the object with id and provenance information
		FusableCity city = new FusableCity(id, provenanceInfo);
		
		// fill the attributes
		city.setName(getValueFromChildElement(node, "name"));
		city.setCc(getValueFromChildElement(node, "cc"));
		city.setPopulation(Integer.valueOf(getValueFromChildElement(node, "population")));
		city.setLat(Double.valueOf(getValueFromChildElement(node,"lat")));
		city.setLon(Double.valueOf(getValueFromChildElement(node, "long")));
		
		
//		String director = getValueFromChildElement(node, "director");
//		if(director!=null) {
//			city.setDirector(director.replace("\n", " "));
//		}
//
//		// convert the date string into a DateTime object
//		try {
//			String date = getValueFromChildElement(node, "date");
//			if(date!=null && !date.isEmpty()) {
//				DateTime dt = DateTime.parse(date);
//				city.setDate(dt);
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		
		// load the list of actors
		List<Musician> musicians = getObjectListFromChildElement(node, "musicians", "musician", new MusicianFactory(), provenanceInfo);
		city.setMusicians(musicians);
		
		return city;
	}

	@Override
	public FusableCity createInstanceForFusion(
			RecordGroup<FusableCity> cluster) {
		
		List<String> ids = new LinkedList<>();
		
		for(FusableCity m : cluster.getRecords()) {
			ids.add(m.getIdentifier());
		}
		
		Collections.sort(ids);
		
		String mergedId = StringUtils.join(ids, '+');
		
		return new FusableCity(mergedId, "fused");
	}
	
}
