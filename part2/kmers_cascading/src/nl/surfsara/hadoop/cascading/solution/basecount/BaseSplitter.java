package nl.surfsara.hadoop.cascading.solution.basecount;

import java.util.HashMap;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;

@SuppressWarnings("serial")
public class BaseSplitter extends BaseOperation<Tuple> implements Function<Tuple> {

	public BaseSplitter() {
		super(2, new Fields("base","count"));
	}

	public BaseSplitter(Fields fields) {
		super(2, fields);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void operate(FlowProcess flowProcess, FunctionCall functionCall) {
		TupleEntry args = functionCall.getArguments();
		String line = args.getString("line");
		
		HashMap<Character, Long> basemap = new HashMap<Character, Long>(); 
		
		for(char c : line.toCharArray()) {
			Long i = basemap.get(c);
            basemap.put(c, (i == null) ? 1 : i + 1);
		}
		
		for(Character k : basemap.keySet()) {
			Tuple result = new Tuple();
			result.add(k.toString());
			result.add(basemap.get(k));
			functionCall.getOutputCollector().add(result);
		}

		
	}

}