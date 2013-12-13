package nl.surfsara.hadoop.cascading.basecount;

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
		
		/*
		 * Implement this function:
		 * You should return tuples with <base, count> where count is either always 1 (aggregation is left to last) or
		 * already count the bases in the current line
		 */
		
	}

}