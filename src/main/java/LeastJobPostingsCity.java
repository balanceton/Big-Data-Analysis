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

public class LeastJobPostingsCity {

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

    public static class MinReducer extends Reducer<String, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int min = Integer.MAX_VALUE;
            String keyWithMax = "";
            for (IntWritable value : values) {
                if (value.get() < min) {
                	min = value.get();
                    keyWithMax = key.toString();
                }
            }
            context.write(new Text(keyWithMax), new IntWritable(min));
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

        Job minJob = Job.getInstance(conf, "min city");
        minJob.setJarByClass(MostJobPostingsCity.class);
        minJob.setMapperClass(Mapper.class);
        minJob.setReducerClass(MinReducer.class);
        minJob.setOutputKeyClass(Text.class);
        minJob.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(minJob, new Path(args[1]));
        FileOutputFormat.setOutputPath(minJob, new Path(args[2]));
        System.exit(minJob.waitForCompletion(true) ? 0 : 1);
    }
}