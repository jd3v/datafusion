package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.usecase.wdiproject.City;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FusableCity extends City implements Fusable {

	public static final String NAME = "Name";
	public static final String COUNTRYCODE = "Countrycode";
	public static final String POPULATION = "Population";
	public static final String LAT = "Latitude";
	public static final String LONG = "Longitude";
	public static final String MUSICIANS = "Musicians";
	public static int id = 0;
	
	private Map<String, Collection<String>> provenance = new HashMap<>();
	
	public void setRecordProvenance(Collection<String> provenance) {
		this.provenance.put("RECORD", provenance);
	}
	public Collection<String> getRecordProvenance() {
		return provenance.get("RECORD");
	}
	
	public void setAttributeProvenance(String attribute, Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}
	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}
	public String getMergedAttributeProvenance(String attribute) {
		Collection<String> prov = provenance.get(attribute);
		
		if(prov!=null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}
	
	public FusableCity(String identifier, String provenance) {
		super(identifier, provenance);
	}

	@Override
	public Collection<String> getAttributeNames() {
		return Arrays.asList(new String[] { NAME, COUNTRYCODE, POPULATION, LAT, LONG});
	}

	@Override
	public boolean hasValue(String attributeName) {
		switch (attributeName) {
		case NAME:
			return getName()!=null && !getName().isEmpty();
		case COUNTRYCODE:
			return getCc()!=null && !getCc().isEmpty();
		case POPULATION:
			return getPopulation()>0 ;
		case LAT:
			return getLocation()!=null;
		case LONG:
			return getLocation()!=null;
		case MUSICIANS:
			return getMusicians()!=null && getMusicians().size()>0;
		default:
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("[City: %s / %s / %s]", getName(), getPopulation(), getCc());
	}


	public int getId() {
		id++;
		return id;
	}

}
