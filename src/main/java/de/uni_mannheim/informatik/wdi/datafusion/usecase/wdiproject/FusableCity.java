package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.usecase.wdiproject.City;
import de.uni_mannheim.informatik.wdi.usecase.wdiproject.Musician;

public class FusableCity extends City implements Fusable {

	public static final String NAME = "Name";
	public static final String CC = "Countrycode";
	public static final int POPULATION = 0;
	public static final double LAT = 0;
	public static final double LONG = 0;
	public static final List<Musician> MUSICIANS = null;
	
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
		return Arrays.asList(new String[] { NAME, CC, POPULATION, LAT, LONG});
	}

	@Override
	public boolean hasValue(String attributeName) {
		switch (attributeName) {
		case NAME:
			return getName()!=null && !getName().isEmpty();
		case CC:
			return getCc()!=null && !getCc().isEmpty();
		case POPULATION:
			return getPopulation()!=null;
		case LAT:
			return getLat()!=null;
		case LONG:
			return getLong()!=null;
		case MUSICIANS:
			return getActors()!=null && getActors().size()>0;
		default:
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("[Movie: %s / %s / %s]", getTitle(), getDirector(), getDate().toString());
	}

}
