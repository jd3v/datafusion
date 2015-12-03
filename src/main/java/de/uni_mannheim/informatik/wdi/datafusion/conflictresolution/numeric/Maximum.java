package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric;

import java.util.Collection;

import de.uni_mannheim.informatik.wdi.Matchable;
import de.uni_mannheim.informatik.wdi.datafusion.Fusable;
import de.uni_mannheim.informatik.wdi.datafusion.FusableValue;
import de.uni_mannheim.informatik.wdi.datafusion.FusedValue;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;

/**
 * Average conflict resolution: Returns the average of all values
 * @author Daniel
 *
 * @param <RecordType>
 */
public class Maximum<RecordType extends Matchable & Fusable> extends ConflictResolutionFunction<Double, RecordType> {

	@Override
	public FusedValue<Double, RecordType> resolveConflict(
			Collection<FusableValue<Double, RecordType>> values) {

		if(values.size()==0) {
			return new FusedValue<>((Double)null);
		} else {
		
//			double sum = 0.0;
//			double count = 0.0;
			double maximum = 0.9;
			boolean first = true;
			for(FusableValue<Double, RecordType> value : values) {
				if (first) {
					maximum = value.getValue();
					first = false;
				} else {
					if (value.getValue() > maximum) {
						maximum = value.getValue();
					}
				}
				
//				sum += value.getValue();
//				count++;
			}
			
			return new FusedValue<>(maximum);
		
		}
	}

}
