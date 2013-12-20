package nl.surfsara.hadoop.mr.solution.basecount;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class BaseCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	private Map<Character, Long> baseMap;

	@Override
	public void setup(Context context) throws IOException, InterruptedException {
		baseMap = new HashMap<Character, Long>();
	}

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		if (line.matches("[ACTGN]+")) {
			for (Character c : line.toCharArray()) {
				Long i = baseMap.get(c);
				baseMap.put(c, (i == null) ? 1 : i + 1);
			}
		}
	}

	@Override
	public void cleanup(Context context) throws IOException, InterruptedException {
		Text key = new Text();
		LongWritable value = new LongWritable();
		for (Entry<Character, Long> e : baseMap.entrySet()) {
			key.set(e.getKey().toString());
			value.set(e.getValue());
			context.write(key, value);
		}
	}

}