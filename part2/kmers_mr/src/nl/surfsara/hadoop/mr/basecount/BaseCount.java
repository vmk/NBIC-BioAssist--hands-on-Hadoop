package nl.surfsara.hadoop.mr.basecount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BaseCount {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// Uncomment to set number of reducers:
		//conf.set("mapred.reduce.tasks", "5");
		
		Job job = new Job(conf);
		job.setJarByClass(BaseCount.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(BaseCountMapper.class);
		job.setReducerClass(BaseCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
