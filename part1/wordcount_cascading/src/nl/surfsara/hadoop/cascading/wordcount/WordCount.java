package nl.surfsara.hadoop.cascading.wordcount;

import java.util.Properties;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.aggregator.Count;
import cascading.operation.regex.RegexSplitGenerator;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextLine;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

public class WordCount {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		String docPath = args[0];
		String wcPath = args[1];

		Properties properties = new Properties();
		AppProps.setApplicationJarClass(properties, WordCount.class);
		HadoopFlowConnector flowConnector = new HadoopFlowConnector(properties);

		Tap docTap = new Hfs(new TextLine(), docPath);
		Tap wcTap = new Hfs(new TextLine(), wcPath);

		Fields token = new Fields("token");
		Fields text = new Fields("line");
		RegexSplitGenerator splitter = new RegexSplitGenerator(token, "[ \\[\\]\\(\\),.]");

		Pipe docPipe = new Each("token", text, splitter, Fields.RESULTS);
		Pipe wcPipe = new Pipe("wc", docPipe);
		wcPipe = new GroupBy(wcPipe, token);
		wcPipe = new Every(wcPipe, Fields.ALL, new Count(), Fields.ALL);

		FlowDef flowDef = FlowDef.flowDef().setName("wc").addSource(docPipe, docTap).addTailSink(wcPipe, wcTap);

		Flow wcFlow = flowConnector.connect(flowDef);
		wcFlow.writeDOT("dot/wc.dot");
		wcFlow.complete();
	}
}
