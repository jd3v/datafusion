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

public class FusableCityFactory extends MatchableFactory<FusableCity> implements FusableFactory<FusableCity> {

	@Override
	public FusableCity createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");
		
		// create the object with id and provenance information
		FusableCity city = new FusableCity(id, provenanceInfo);
		
		// fill the attributes
		city.setTitle(getValueFromChildElement(node, "title"));
		
		String director = getValueFromChildElement(node, "director");
		if(director!=null) {
			movie.setDirector(director.replace("\n", " "));
		}

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "date");
			if(date!=null && !date.isEmpty()) {
				DateTime dt = DateTime.parse(date);
				movie.setDate(dt);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// load the list of actors
		List<Actor> actors = getObjectListFromChildElement(node, "actors", "actor", new ActorFactory(), provenanceInfo);
		movie.setActors(actors);
		
		return movie;
	}

	@Override
	public FusableMovie createInstanceForFusion(
			RecordGroup<FusableMovie> cluster) {
		
		List<String> ids = new LinkedList<>();
		
		for(FusableMovie m : cluster.getRecords()) {
			ids.add(m.getIdentifier());
		}
		
		Collections.sort(ids);
		
		String mergedId = StringUtils.join(ids, '+');
		
		return new FusableMovie(mergedId, "fused");
	}
	
}