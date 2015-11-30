package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation;

import de.uni_mannheim.informatik.wdi.datafusion.evaluation.EvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.movies.FusableCity;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.wdi.identityresolution.similarity.string.TokenizingJaccardSimilarity;

public class NameEvaluationRule extends EvaluationRule<FusableCity> {

	SimilarityMeasure<String> sim = new TokenizingJaccardSimilarity();

	@Override
	public boolean isEqual(FusableCity record1, FusableCity record2) {
		// the title is correct if all tokens are there, but the order does not matter
		return sim.calculate(record1.getTitle(), record2.getTitle()) == 1.0;
	}

}
