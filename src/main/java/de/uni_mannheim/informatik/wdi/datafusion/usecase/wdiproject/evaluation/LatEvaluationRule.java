package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.FusableCity;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.numeric.AbsoluteDifferenceSimilarity;

public class LatEvaluationRule extends EvaluationRule<FusableCity> {

	AbsoluteDifferenceSimilarity sim = new AbsoluteDifferenceSimilarity(0.5);

	@Override
	public boolean isEqual(FusableCity record1, FusableCity record2) {
		return sim.calculate(record1.getLat(), record2.getLat())>0.9;
	}

}
