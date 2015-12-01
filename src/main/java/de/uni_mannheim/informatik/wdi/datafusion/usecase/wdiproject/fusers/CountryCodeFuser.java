package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.string.ShortestString;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;

public class CountryCodeFuser extends AttributeValueFuser<String, FusableCity> {
	
	public CountryCodeFuser() {
		super(new ShortestString<FusableCity>());
	}
	
	@Override
	public boolean hasValue(FusableCity record) {
		return record.hasValue(FusableCity.COUNTRYCODE);
	}
	
	@Override
	protected String getValue(FusableCity record) {
		return record.getCc();
	}

	@Override
	public void fuse(RecordGroup<FusableCity> group,
			FusableCity fusedRecord) {
		FusedValue<String, FusableCity> fused = getFusedValue(group);
		fusedRecord.setCc(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableCity.COUNTRYCODE, fused.getOriginalIds());
	}

}
