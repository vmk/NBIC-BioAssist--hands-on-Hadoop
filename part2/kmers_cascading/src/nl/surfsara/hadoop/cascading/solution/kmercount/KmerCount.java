package nl.surfsara.hadoop.cascading.solution.kmercount;

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
import cascading.tap.hadoop.HfsProps;
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
		properties.setProperty("mapred.compress.map.output", "true");
		properties.setProperty("mapred.output.compress", "true");
		properties.setProperty("mapred.reduce.tasks", "125");

		HadoopFlowConnector flowConnector = new HadoopFlowConnector(properties);

		Fields inFields = new Fields("offset", "line");
		TextLine scheme = new TextLine(inFields);
		Tap inTap = new Hfs(scheme, inPath);

		SequenceLineFilter lineFilter = new SequenceLineFilter();
		Pipe filterPipe = new Each("filter", inFields, lineFilter);		

		KmerSplitter bs = new KmerSplitter(Integer.parseInt(k));
		Pipe splitPipe = new Each(filterPipe, inFields, bs);
		
		Pipe groupPipe = new GroupBy(splitPipe, new Fields("kmer"));
		Pipe sumPipe = new Every(groupPipe, new Fields("count"), new Sum(), Fields.ALL);
		
		Tap outTap = new Hfs(new TextLine(), outPath);
		
		FlowDef flowDef = FlowDef.flowDef().setName("kmercount").addSource(filterPipe, inTap).addTailSink(sumPipe, outTap);

		Flow wcFlow = flowConnector.connect(flowDef);
		wcFlow.writeDOT("dot/kmercount.dot");
		wcFlow.complete();
	}
}
