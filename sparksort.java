import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.*;

public class sparksort {

    public static void main(String[] args) {
       
		SparkConf sconf = new SparkConf().setAppName("Sparksort");
		JavaSparkContext sparkC = new JavaSparkContext(sconf);
		String in1 = args[0];
        String out2 = args[1];
        JavaRDD<String> text = sparkC.textFile(in1);
		
		        PairFunction<String, String, String> VP =
               new PairFunction<String, String, String>() {
                    public Vector<String, String > call(String x) throws Exception{
                        return new Vector(x.substring(0,10), x.substring(11,98));                     
                    }
                };
				
								
		        JavaPairRDD<String, String> pairing = text.mapToPair(VP).sortByKey(true);
                pairing.map(x -> x._1 + " " + x._2 + "\r").coalesce(1).saveAsTextFile(out2);        
    }
}