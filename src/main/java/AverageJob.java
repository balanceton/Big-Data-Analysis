import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class AverageJob {
    public static class Map extends Mapper<LongWritable, Text, Text, DoubleWritable> {
        private Text hourKey = new Text();
        private final static DoubleWritable tmp = new DoubleWritable(1);
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //System.out.println("MAP BAŞLADI");
            String[] fields = value.toString().split(",");
            if (fields.length > 1) {
                try {
                    String timestampString = fields[1].replaceAll("\"", "").split("\\+")[0];
                    String hourString = timestampString.substring(11, 13);
                    double hour = Double.parseDouble(hourString);
                    hourKey.set(hourString);
                    context.write(hourKey, tmp);
                    System.out.println("Mapper Output: " + hourString + " - " + hour);
                } catch (Exception e) {
                    System.err.println("Error parsing date or number: " + e.getMessage());
                }
            } else {
                System.err.println("Invalid input line: " + value.toString());
            }
        }
    }

    public static class Reduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    	private DoubleWritable result = new DoubleWritable();
        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
            //System.out.println("REDUCE BAŞLADI");
            double sum = 0;
            for (DoubleWritable val : values) {
                sum += val.get();
                System.out.println("Reducer Value: " + val.get());
            }
            result.set(sum);
            context.write(key, result);
        }
    }
   
    public static void main(String[] args) throws Exception {
        System.out.println("HADOOP BAŞLADI");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "average");
        job.setJarByClass(AverageJob.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
