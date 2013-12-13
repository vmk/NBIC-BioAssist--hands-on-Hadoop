package nl.surfsara.hadoop.cascading.kmercount;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Filter;
import cascading.operation.FilterCall;
import cascading.tuple.TupleEntry;

@SuppressWarnings({ "rawtypes", "serial" })
public class SequenceLineFilter extends BaseOperation implements Filter {

    public SequenceLineFilter() {
        super(2);
    }
    
    @Override
    public boolean isRemove(FlowProcess flowProcess, FilterCall filterCall) {
        TupleEntry arguments = filterCall.getArguments();
        String string = arguments.getString("line");
        boolean result = true;
        if (string.matches("[ACGTN]+")) {
        	result = false;        	
        }
        return result;
    }
    
    

}
