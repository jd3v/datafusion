package de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject;

import de.uni_mannheim.informatik.wdi.DataSet;
import de.uni_mannheim.informatik.wdi.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.wdi.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.wdi.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.wdi.datafusion.FusableDataSet;
import de.uni_mannheim.informatik.wdi.datafusion.evaluation.DataFusionEvaluator;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.evaluation.*;
import de.uni_mannheim.informatik.wdi.datafusion.usecase.wdiproject.fusers.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;

public class Cities_Main {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException,
			IOException, TransformerException {
		// load the data sets
		FusableDataSet<FusableCity> geonames = new FusableDataSet<>();
		FusableDataSet<FusableCity> maxmind = new FusableDataSet<>();
		FusableDataSet<FusableCity> dbpedia = new FusableDataSet<>();

		geonames.loadFromXML(new File("usecase/wdiproject/input/geonames.xml"), new FusableCityFactory(), "/cities/city");
		maxmind.loadFromXML(new File("usecase/wdiproject/input/maxmind.xml"), new FusableCityFactory(), "/cities/city");
		dbpedia.loadFromXML(new File("usecase/wdiproject/input/cities_v5.xml"), new FusableCityFactory(), "/cities/city");

		// set dataset metadata
		geonames.setScore(3.0);
		maxmind.setScore(2.0);
		dbpedia.setScore(1.0);
//		geonames.setDate(DateTime.parse("2012-01-01"));
//		maxmind.setDate(DateTime.parse("2010-01-01"));
//		dbpedia.setDate(DateTime.parse("2008-01-01"));

		// print dataset density
		System.out.println("geonames.xml"); geonames.printDataSetDensityReport();
		System.out.println("maxmind.xml");
		maxmind.printDataSetDensityReport();
		System.out.println("cities_v5.xml");
		dbpedia.printDataSetDensityReport();

		// load the correspondences
		CorrespondenceSet<FusableCity> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(
				new File("usecase/wdiproject/correspondences/geonames2dbpedia_correspondences_v5.csv"), geonames, dbpedia);
		correspondences.loadCorrespondences(
				new File("usecase/wdiproject/correspondences/geonames2maxmind_correspondences_v5.csv"), geonames, maxmind);
		correspondences.loadCorrespondences(
				new File("usecase/wdiproject/correspondences/maxmind2dbpedia_correspondences_v5.csv"), maxmind, dbpedia);

		// write group size distribution
		correspondences.writeGroupSizeDistribution(new File("usecase/wdiproject/output/group_size_distribution.csv"));

		// define the fusion strategy
		DataFusionStrategy<FusableCity> strategy = new DataFusionStrategy<>(new FusableCityFactory());
		// add attribute fusers
		// Note: The attribute name is only used for printing the reports
		strategy.addAttributeFuser("Name", new NameFuser(), new NameEvaluationRule());
		strategy.addAttributeFuser("CountryCode", new CountryCodeFuser(), new CountryCodeEvaluationRule());
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
		fusedDataSet.writeXML(new File("usecase/wdiproject/output/fused_v5.xml"), new CityXMLFormatter());

		 // load the gold standard
		 DataSet<FusableCity> gs = new FusableDataSet<>();
		 gs.loadFromXML(
		 new File("usecase/wdiproject/goldstandard/goldstandard_v5.xml"),
		 new FusableCityFactory(), "/cities/city");
		
		 // evaluate
		 DataFusionEvaluator<FusableCity> evaluator = new
		 DataFusionEvaluator<>(strategy);
		 evaluator.setVerbose(true);
		 double accuracy = evaluator.evaluate(fusedDataSet, gs);
		
		 System.out.println(String.format("Accuracy: %.2f", accuracy));

	}

}
