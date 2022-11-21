import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HadoopSort {

  private static final Logger Mylogger = Logger.getLogger(HadoopSort.class);
  private static final int Charcount = 128;
  

public static class HadoopReducer extends Reducer<Text,Text,Text,Text> {
    public void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
      for (Text value : values) {
        context.write(key,value);
      }
    }
  }


  public static class HadoopMapper
       extends Mapper<Object, Text, Text, Text>{

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
      String eachline  = value.toString();
      String firstcolumn = eachline.substring(0,10);
      String othercolumns = eachline.substring(11,98) + "\r";
      context.write(new Text(firstcolumn),new Text(othercolumns));
    }
  }

  public static class MyPartitioner extends Partitioner<Text,Text>{
                public int getPartition(Text key, Text value, int numReduceTasks){
      int numChars = Charcount/numReduceTasks;
      int firstChar = (int)key.toString().charAt(0);
      int j=0;
      for(j=0;j < numReduceTasks;j++){
        int start = j * numChars;
        int end = (j+1) * numChars;
        if(firstChar >= start && firstChar < end){
          return j;
        }
      }
      return j-1;
    }


    public static void main(String[] args) throws Exception {

    Mylogger.info("Started ...");
    long starttime = System.currentTimeMillis();
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "hadoop sort");
    job.setJarByClass(HadoopSort.class);
    job.setMapperClass(HadoopMapper.class);
    job.setCombinerClass(HadoopReducer.class);
    job.setReducerClass(HadoopReducer.class);
    job.setPartitionerClass(MyPartitioner.class);
    job.setNumReduceTasks(1);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    if(job.waitForCompletion(true) == true){
            long timeElapsed = System.currentTimeMillis() - starttime;
            Mylogger.info("Execution time(ms) - " + timeElapsed);
        System.exit(0);
    }
    else{
         Mylogger.info("job failed");
        System.exit(1);
    }
  }
}
}





