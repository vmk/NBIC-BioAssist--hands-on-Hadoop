package nl.surfsara.hadoop.mr.solution.kmercount;

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
		conf.set("mapred.max.split.size", "26843550");
		conf.set("mapred.compress.map.output", "true");
		conf.set("mapred.output.compress", "true");
		conf.set("mapred.reduce.tasks", "125");
		conf.set("k", "5");
		
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
