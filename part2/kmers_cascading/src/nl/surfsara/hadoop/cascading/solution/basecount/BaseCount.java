package nl.surfsara.hadoop.cascading.solution.basecount;

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

public class BaseCount {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		String inPath = args[0];
		String outPath = args[1];

		Properties properties = new Properties();
		AppProps.setApplicationJarClass(properties, BaseCount.class);
		properties.setProperty("mapreduce.job.complete.cancel.delegation.tokens", "false");
		// Uncomment to set the number of reducers
		//properties.setProperty("mapred.reduce.tasks", "5");
		HadoopFlowConnector flowConnector = new HadoopFlowConnector(properties);
		
		Fields inFields = new Fields("offset", "line");
		TextLine scheme = new TextLine(inFields);
		Tap inTap = new Hfs(scheme, inPath);

		SequenceLineFilter lineFilter = new SequenceLineFilter();
		Pipe filterPipe = new Each("filter", inFields, lineFilter);		

		// Implement the split bases function!
		BaseSplitter bs = new BaseSplitter();
		Pipe splitPipe = new Each(filterPipe, inFields, bs);
		
		Pipe groupPipe = new GroupBy(splitPipe, new Fields("base"));
		Pipe sumPipe = new Every(groupPipe, new Fields("count"), new Sum(), Fields.ALL);
		
		Tap outTap = new Hfs(new TextLine(), outPath);
		
		FlowDef flowDef = FlowDef.flowDef().setName("basecount").addSource(filterPipe, inTap).addTailSink(sumPipe, outTap);

		Flow wcFlow = flowConnector.connect(flowDef);
		wcFlow.writeDOT("dot/basecount.dot");
		wcFlow.complete();
	}
}
