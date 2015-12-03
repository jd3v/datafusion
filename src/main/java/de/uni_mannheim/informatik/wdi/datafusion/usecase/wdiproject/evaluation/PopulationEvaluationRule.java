package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric.PercentageSimilarity;

public class PopulationEvaluationRule extends EvaluationRule<FusableCity> {
	
	PercentageSimilarity per = new PercentageSimilarity(0.2);
	
	@Override
	public boolean isEqual(FusableCity record1, FusableCity record2) {
		double sim = per.calculate((double)(record1.getPopulation()), (double)(record2.getPopulation()));
	
		return sim>0;
//		return record1.getPopulation() == record2.getPopulation();
	}

}
