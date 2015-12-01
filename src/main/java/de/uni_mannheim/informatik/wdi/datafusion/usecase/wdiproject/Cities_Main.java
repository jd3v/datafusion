package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.xml.sax.SAXException;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.wdi.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.wdi.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.wdi.datafusion.FusableDataSet;
import de.uni_mannheim.informatik.wdi.datafusion.evaluation.DataFusionEvaluator;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation.CountryCodeEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation.LatEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation.LongEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation.NameEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation.PopulationEvaluationRule;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers.CountryCodeFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers.LatFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers.LongFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers.NameFuser;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers.PopulationFuser;

public class Cities_Main {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException,
			IOException, TransformerException {
		// load the data sets
		FusableDataSet<FusableCity> ds1 = new FusableDataSet<>();
		FusableDataSet<FusableCity> ds2 = new FusableDataSet<>();
		FusableDataSet<FusableCity> ds3 = new FusableDataSet<>();
		
		ds1.loadFromXML(new File("usecase/wdiproject/input/geonames.xml"), new FusableCityFactory(), "/movies/movie");
		ds2.loadFromXML(new File("usecase/wdiproject/input/geonames.xml"), new FusableCityFactory(), "/movies/movie");
		ds3.loadFromXML(new File("usecase/wdiproject/input/geonames.xml"), new FusableCityFactory(), "/movies/movie");

		// set dataset metadata
		ds1.setScore(1.0);
		ds2.setScore(2.0);
		ds3.setScore(3.0);
		ds1.setDate(DateTime.parse("2012-01-01"));
		ds2.setDate(DateTime.parse("2010-01-01"));
		ds3.setDate(DateTime.parse("2008-01-01"));

		// print dataset density
		System.out.println("academy_awards.xml");
		ds1.printDataSetDensityReport();
		System.out.println("actors.xml");
		ds2.printDataSetDensityReport();
		System.out.println("golden_globes.xml");
		ds3.printDataSetDensityReport();

		// load the correspondences
		CorrespondenceSet<FusableCity> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(
				new File("usecase/movie/correspondences/academy_awards_2_actors_correspondences.csv"), ds1, ds2);
		correspondences.loadCorrespondences(
				new File("usecase/movie/correspondences/actors_2_golden_globes_correspondences.csv"), ds2, ds3);

		// write group size distribution
		correspondences.writeGroupSizeDistribution(new File("usecase/wdiproject/output/group_size_distribution.csv"));

		// define the fusion strategy
		DataFusionStrategy<FusableCity> strategy = new DataFusionStrategy<>(new FusableCityFactory());
		// add attribute fusers
		// Note: The attribute name is only used for printing the reports
		strategy.addAttributeFuser("Name", new NameFuser(), new NameEvaluationRule());
		strategy.addAttributeFuser("ContryCode", new CountryCodeFuser(), new CountryCodeEvaluationRule());
		strategy.addAttributeFuser("Population", new PopulationFuser(), new PopulationEvaluationRule());
		strategy.addAttributeFuser("Lat", new LatFuser(), new LatEvaluationRule());
		strategy.addAttributeFuser("Long", new LongFuser(), new LongEvaluationRule());


		// create the fusion engine
		DataFusionEngine<FusableCity> engine = new DataFusionEngine<>(strategy);

		// calculate cluster consistency
		engine.printClusterConsistencyReport(correspondences);

		// run the fusion
		FusableDataSet<FusableCity> fusedDataSet = engine.run(correspondences);

		// write the result
		fusedDataSet.writeXML(new File("usecase/wdiproject/output/fused.xml"), new CityXMLFormatter());

		 // load the gold standard
		 DataSet<FusableCity> gs = new FusableDataSet<>();
		 gs.loadFromXML(
		 new File("usecase/wdiproject/goldstandard/fused.xml"),
		 new FusableCityFactory(), "/cities/city");
		
		 // evaluate
		 DataFusionEvaluator<FusableCity> evaluator = new
		 DataFusionEvaluator<>(strategy);
		 evaluator.setVerbose(true);
		 double accuracy = evaluator.evaluate(fusedDataSet, gs);
		
		 System.out.println(String.format("Accuracy: %.2f", accuracy));

	}

}
