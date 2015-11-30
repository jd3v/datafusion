package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;

public class LongEvaluationRule extends EvaluationRule<FusableCity> {

	@Override
	public boolean isEqual(FusableCity record1, FusableCity record2) {
		return false;
	}

}
