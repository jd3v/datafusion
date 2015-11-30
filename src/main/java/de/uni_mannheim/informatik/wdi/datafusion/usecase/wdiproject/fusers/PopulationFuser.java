package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric.Median;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;
import org.joda.time.DateTime;

public class PopulationFuser extends AttributeValueFuser<Median, FusableCity> {

	public PopulationFuser() {
		super(new Median<FusableCity>());
	}
	
	@Override
	public boolean hasValue(FusableCity record) {
		return record.hasValue(FusableCity.POPULATION);
	}
	
	@Override
	protected int getValue(FusableCity record) {
		return record.getPopulation();
	}

	@Override
	public void fuse(RecordGroup<FusableCity> group,
			FusableCity fusedRecord) {
		FusedValue<Median, FusableCity> fused = getFusedValue(group);
		fusedRecord.setPopulation(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableCity.POPULATION, fused.getOriginalIds());
	}

}
