package nl.surfsara.hadoop.cascading.solution.kmercount;

import java.util.HashMap;

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
		HashMap<String, Long> kmermap = new HashMap<String, Long>(); 
		
		for(int i=0; i <= (line.length() - k); i++) {
			String kmer = line.substring(i, (i + k));
			Long l = kmermap.get(kmer);
			kmermap.put(kmer, (l == null) ? 1 : l + 1);
		}
		
		for(String k : kmermap.keySet()) {
			Tuple result = new Tuple();
			result.add(k.toString());
			result.add(kmermap.get(k));
			functionCall.getOutputCollector().add(result);
		}
	}

}