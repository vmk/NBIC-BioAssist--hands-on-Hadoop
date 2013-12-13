package nl.surfsara.hadoop.mr.kmercount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KmerCount {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// Hardcoded k = 2; use -D k=X on the command line to set own k 
		// conf.set("k", "2");
		// Uncomment to set number of reducers:
		//conf.set("mapred.reduce.tasks", "5");
		
		Job job = new Job(conf);
		job.setJarByClass(KmerCount.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(KmerCountMapper.class);
		job.setReducerClass(KmerCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
