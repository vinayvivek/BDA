package ramaraju;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FileJoinerMapper extends Mapper<LongWritable, Text, Text, Text> {
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] tokens = value.toString().split(","); 
														
		if (null != tokens && tokens.length == 2) {
			context.write(new Text(tokens[0]), new Text(tokens[1]));
		}
	}
}
