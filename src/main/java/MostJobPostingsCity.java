import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MostJobPostingsCity {

    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text city = new Text();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split(",");
            if (fields.length > 8) {
                String jobCity = fields[8];
                city.set(jobCity);
                context.write(city, one);
            }
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static class MaxReducer extends Reducer<String, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int max = 0;
            String keyWithMax = "";
            for (IntWritable value : values) {
                if (value.get() > max) {
                    max = value.get();
                    keyWithMax = key.toString();
                }
            }
            context.write(new Text(keyWithMax), new IntWritable(max));
        }
    }


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        
        Job job = Job.getInstance(conf, "city job count");
        job.setJarByClass(MostJobPostingsCity.class);
        job.setMapperClass(Map.class);
        job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        if (!job.waitForCompletion(true)) {
            System.exit(1);
        }

        Job maxJob = Job.getInstance(conf, "max city");
        maxJob.setJarByClass(MostJobPostingsCity.class);
        maxJob.setMapperClass(Mapper.class);
        maxJob.setReducerClass(MaxReducer.class);
        maxJob.setOutputKeyClass(Text.class);
        maxJob.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(maxJob, new Path(args[1]));
        FileOutputFormat.setOutputPath(maxJob, new Path(args[2]));
        System.exit(maxJob.waitForCompletion(true) ? 0 : 1);
    }
}