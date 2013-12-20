package nl.surfsara.hadoop.mr.solution.kmercount;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class KmerCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	private Map<String, Long> kmerMap;
	private int k;

	@Override
	public void setup(Context context) throws IOException, InterruptedException {
		kmerMap = new HashMap<String, Long>();
		Configuration conf = context.getConfiguration();
		k = Integer.parseInt(conf.get("k"));
	}

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		if (line.matches("[ACTGN]+")) {
			for (int i = 0; i <= line.length() - k; i++) {
				String s = line.substring(i, i + k);
				Long n = kmerMap.get(s);
				kmerMap.put(s, (n == null) ? 1 : n + 1);
			}
		}
	}

	@Override
	public void cleanup(Context context) throws IOException, InterruptedException {
		Text key = new Text();
		LongWritable value = new LongWritable();
		for (Entry<String, Long> e : kmerMap.entrySet()) {
			key.set(e.getKey().toString());
			value.set(e.getValue());
			context.write(key, value);
		}
	}
}
