package nl.surfsara.hadoop.cascading.kmercount;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;

@SuppressWarnings("serial")
public class KmerSplitter extends BaseOperation<Tuple> implements Function<Tuple> {
	private int k;

	public KmerSplitter(int k) {
		super(2, new Fields("kmer","count"));
		this.k = k;
	}

	public KmerSplitter(int k, Fields fields) {
		super(2, fields);
		this.k = k;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void operate(FlowProcess flowProcess, FunctionCall functionCall) {
		TupleEntry args = functionCall.getArguments();
		String line = args.getString("line");
		
		/*
		 * Implement this function:
		 * You should return tuples with <kmer, count> where count is either always 1 (aggregation is left to last) or
		 * already count the kmers in the current line
		 */
		
		
	}

}