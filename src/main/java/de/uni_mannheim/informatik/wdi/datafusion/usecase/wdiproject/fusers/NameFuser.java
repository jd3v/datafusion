package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;

public class NameFuser extends AttributeValueFuser<String, FusableCity> {

	public NameFuser() {
		super(new LongestString<FusableCity>());
	}
	
	@Override
	public void fuse(RecordGroup<FusableCity> group,
			FusableCity fusedRecord) {
		
		// get the fused value
		FusedValue<String, FusableCity> fused = getFusedValue(group);
		
		// set the value for the fused record
		fusedRecord.setName(fused.getValue());
		
		// add provenance info
		fusedRecord.setAttributeProvenance(FusableCity.NAME, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(FusableCity record) {
		return record.hasValue(FusableCity.NAME);
	}
	
	@Override
	protected String getValue(FusableCity record) {
		return record.getName();
	}

}
