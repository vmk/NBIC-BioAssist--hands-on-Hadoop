package nl.surfsara.hadoop.mr.solution.kmercount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

class KmerCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	@Override
	public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
		Long sum = 0L;
		for (LongWritable i : values) {
			sum += i.get();
		}
		context.write(key, new LongWritable(sum));
	}

}