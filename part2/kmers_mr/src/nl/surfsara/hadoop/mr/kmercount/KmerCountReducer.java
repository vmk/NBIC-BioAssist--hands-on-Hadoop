package nl.surfsara.hadoop.mr.kmercount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

class KmerCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	@Override
	public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
		/*
		 * Implement this function:
		 * You should sum  the values (counts) of the kmers
		 */

		context.write(key, new LongWritable(result));

	}

}