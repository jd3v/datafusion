package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;

public class LatFuser extends AttributeValueFuser<Double, FusableCity> {

	public LatFuser() {
		super(new FavourSources<Double, FusableCity>());
	}
	
	@Override
	public boolean hasValue(FusableCity record) {
		return record.hasValue(FusableCity.LAT);
	}
	
	@Override
	protected Double getValue(FusableCity record) {
		return record.getLat();
	}

	@Override
	public void fuse(RecordGroup<FusableCity> group,
			FusableCity fusedRecord) {
		FusedValue<Double, FusableCity> fused = getFusedValue(group);
		fusedRecord.setLat(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableCity.LAT, fused.getOriginalIds());
	}

}
