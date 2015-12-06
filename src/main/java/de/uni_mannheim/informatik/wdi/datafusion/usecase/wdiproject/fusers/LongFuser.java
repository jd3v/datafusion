package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric.Median;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;

public class LongFuser extends AttributeValueFuser<Double, FusableCity> {

	public LongFuser() {
		super(new FavourSources<Double, FusableCity>());
	}
	
	@Override
	public boolean hasValue(FusableCity record) {
		return record.hasValue(FusableCity.LONG);
	}
	
	@Override
	protected Double getValue(FusableCity record) {
		return record.getLon();
	}

	@Override
	public void fuse(RecordGroup<FusableCity> group,
			FusableCity fusedRecord) {
		FusedValue<Double, FusableCity> fused = getFusedValue(group);
		fusedRecord.setLon(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableCity.LONG, fused.getOriginalIds());
	}

}
