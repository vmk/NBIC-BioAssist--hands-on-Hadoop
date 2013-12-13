package nl.surfsara.hadoop.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class WordCount {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// Uncomment to set number of reducers:
		// conf.set("mapred.reduce.tasks", "1");
		
		Job job = new Job(conf);
		job.setJarByClass(WordCount.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		job.setCombinerClass(WordCountCombiner.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		job.waitForCompletion(true);
	}
}
