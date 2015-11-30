package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.RecordGroup;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.Voting;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableMovie;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;

public class LatFuser extends AttributeValueFuser<DateTime, FusableCity> {

	public LatFuser() {
		super(new Voting<DateTime, FusableMovie>());
	}
	
	@Override
	public boolean hasValue(FusableMovie record) {
		return record.hasValue(FusableMovie.DATE);
	}
	
	@Override
	protected DateTime getValue(FusableMovie record) {
		return record.getDate();
	}

	@Override
	public void fuse(RecordGroup<FusableMovie> group,
			FusableMovie fusedRecord) {
		FusedValue<DateTime, FusableMovie> fused = getFusedValue(group);
		fusedRecord.setDate(fused.getValue());
		fusedRecord.setAttributeProvenance(FusableMovie.DATE, fused.getOriginalIds());
	}

}
