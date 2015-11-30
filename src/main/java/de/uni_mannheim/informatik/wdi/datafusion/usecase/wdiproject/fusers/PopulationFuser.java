package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric.Median;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;

public class PopulationFuser extends AttributeValueFuser<Integer, FusableCity> {

	public PopulationFuser() {
		super(new Median<FusableCity>());
	}
	
	@Override
	public boolean hasValue(FusableCity record) {
		return record.hasValue(FusableCity.POPULATION);
	}
	
	@Override
	protected Integer getValue(FusableCity record) {
		return record.getPopulation();
	}

	@Override
	public void fuse(RecordGroup<FusableCity> group,
			FusableCity fusedRecord) {
		FusedValue<Integer, FusableCity> fused = getFusedValue(group);
		fusedRecord.setPopulation(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableCity.POPULATION, fused.getOriginalIds());
	}

}
