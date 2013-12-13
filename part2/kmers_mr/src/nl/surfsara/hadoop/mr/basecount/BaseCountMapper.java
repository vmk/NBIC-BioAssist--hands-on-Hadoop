package nl.surfsara.hadoop.mr.basecount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class BaseCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	@Override
	public void setup(Context context) throws IOException, InterruptedException {
		// Any datastructures you need can be initialized here!
	}

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		if (line.matches("[ACTGN]+")) {
			/*
			 * Implement this function:
			 * You should create values with <base, count> where count is either always 1 (aggregation is left to last) or
			 * already count the bases in the current line
			 */
			
		}
	}

	@Override
	public void cleanup(Context context) throws IOException, InterruptedException {
		Text key = new Text();
		LongWritable value = new LongWritable();
		/*
		 * Implement this function: cleanup is called at the end and can be used to write results to the context
		 * You should return tuples with <base, count> where count is either always 1 (aggregation is left to last) or
		 * already count the bases in the current line
		 */
		
		// Output key values: context.write(key, value);
	}

}