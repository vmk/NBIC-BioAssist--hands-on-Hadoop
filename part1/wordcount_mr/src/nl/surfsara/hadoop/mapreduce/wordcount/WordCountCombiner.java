package nl.surfsara.hadoop.mapreduce.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

class WordCountCombiner extends Reducer<Text, LongWritable, Text, LongWritable> {
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		long sum = 0;
		for (IntWritable i : values) {
			sum += i.get();
		}
		context.write(key, new LongWritable(sum));
	}
}