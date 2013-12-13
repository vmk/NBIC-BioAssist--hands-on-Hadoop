package nl.surfsara.hadoop.mapreduce.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	public void map(LongWritable Key, Text value, Context context) throws IOException, InterruptedException {
		String[] tokens = value.toString().split("\\s");
		
		for (String s : tokens) {
			context.write(new Text(s), new LongWritable(1));
		}
	}
}