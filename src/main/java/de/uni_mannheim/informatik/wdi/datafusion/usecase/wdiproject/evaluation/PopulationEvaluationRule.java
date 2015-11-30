package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;

public class PopulationEvaluationRule extends EvaluationRule<FusableCity> {

	@Override
	public boolean isEqual(FusableCity record1, FusableCity record2) {
		return record1.getPopulation() == record2.getPopulation();
	}

}
