package nl.surfsara.hadoop.mr.basecount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

class BaseCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	@Override
	public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
		/*
		 * Implement this function:
		 * You should sum  the values (counts) of the bases
		 */
		
		context.write(key, new LongWritable(result));
	}

}