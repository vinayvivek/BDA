package citation;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
public class PatentCitation {
public static class PatentCitationMapper extends Mapper<Text, Text, Text, Text> {
public void map(Text key, Text value, Context context) throws IOException,
InterruptedException {
String[] citation = key.toString().split( "," );
Text cited = new Text(citation[1]);
Text citing = new Text(citation[0]);
context.write(cited, citing);
} }
public static class PatentCitationReducer extends Reducer<Text, Text, Text, Text> {
@Override
protected void reduce(Text key, Iterable<Text> values, Context context) throws
IOException, InterruptedException {
String csv = "";
for (Text value : values) {
if (csv.length() > 0) {
csv += ",";
} csv += value.toString();
} context.write(key, new Text(csv));
} }
public static void main(String[] args) throws Exception {
Configuration conf = new Configuration();
Job job = Job.getInstance(conf, "Hadoop Patent Citation" );
job.setJarByClass(PatentCitation.class );
job.setMapperClass(PatentCitationMapper.class);
job.setCombinerClass(PatentCitationReducer.class);
job.setReducerClass(PatentCitationReducer.class);
job.setInputFormatClass(KeyValueTextInputFormat.class);
job.setOutputFormatClass(TextOutputFormat.class);
job.setMapOutputKeyClass(Text.class);
job.setMapOutputValueClass(Text.class);
job.setOutputKeyClass(Text.class);
job.setOutputValueClass(Text.class);
FileInputFormat.addInputPath(job, new Path(args[0]));
FileOutputFormat.setOutputPath(job, new Path(args[1]));
System.exit(job.waitForCompletion(true) ? 0 : 1 );
} }
