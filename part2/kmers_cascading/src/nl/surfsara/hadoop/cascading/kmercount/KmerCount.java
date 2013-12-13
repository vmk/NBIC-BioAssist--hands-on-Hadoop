package nl.surfsara.hadoop.cascading.kmercount;

import java.util.Properties;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.aggregator.Sum;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextLine;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

public class KmerCount {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		String inPath = args[0];
		String outPath = args[1];
		String k = args[2];

		Properties properties = new Properties();
		AppProps.setApplicationJarClass(properties, KmerCount.class);
		properties.setProperty("mapreduce.job.complete.cancel.delegation.tokens", "false");
		properties.setProperty("mapred.output.compress", "true");
		// Uncomment to set the number of reducers
		// properties.setProperty("mapred.reduce.tasks", "5");
		HadoopFlowConnector flowConnector = new HadoopFlowConnector(properties);
		
		Fields inFields = new Fields("offset", "line");
		TextLine scheme = new TextLine(inFields);
		Tap inTap = new Hfs(scheme, inPath);

		/* Implement this! */
		
		Tap outTap = new Hfs(new TextLine(), outPath);
		
		// Uncomment the flowdef and add your pipes:
		// FlowDef flowDef = FlowDef.flowDef().setName("kmercount").addSource(my_first_pipe, inTap).addTailSink(my_last_pipe, outTap);

		Flow wcFlow = flowConnector.connect(flowDef);
		wcFlow.writeDOT("dot/kmercount.dot");
		wcFlow.complete();
	}
}
