package cs499.a3TopTen;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class TopTenDriver extends Configured implements Tool {
	
	protected static Map<Integer, String> AllMoviesNames = new HashMap<>();
	protected static Map<Integer, Float> TopTenMoviesID;
	protected static Map<Long, Integer> TopTenUserReviews;
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new TopTenDriver(), args);
		System.exit(exitCode);
	}

	public int run(String[] args) throws Exception {
		if (args.length != 4) {
			System.err.printf("Usage: %s needs four arguments, 2 input files (TrainingRatings.txt and movie_titles.txt) and 2 output paths\n", getClass().getSimpleName());
			return -1;
		}

		Job TTMjob = new Job();
		TTMjob.setJarByClass(TopTenDriver.class);
		TTMjob.setJobName("TTMCounter");
		
		Job TTUjob = new Job();
		TTUjob.setJarByClass(TopTenDriver.class);
		TTUjob.setJobName("TTUCounter");
		
		Job getMovieNames = new Job();
		getMovieNames.setJarByClass(TopTenDriver.class);
		getMovieNames.setJobName("getMovieName");

		FileInputFormat.addInputPath(TTMjob, new Path(args[0]));
		FileOutputFormat.setOutputPath(TTMjob, new Path(args[2]));
		
		FileInputFormat.addInputPath(TTUjob, new Path(args[0]));
		FileOutputFormat.setOutputPath(TTUjob, new Path(args[3]));
		
		FileInputFormat.addInputPath(getMovieNames, new Path(args[1]));
		getMovieNames.setMapperClass(MovieNameMapClass.class);
		getMovieNames.setNumReduceTasks(0);
		getMovieNames.setOutputFormatClass(NullOutputFormat.class);

		TTMjob.setOutputKeyClass(IntWritable.class);
		TTMjob.setOutputValueClass(FloatWritable.class);
		TTMjob.setOutputFormatClass(TextOutputFormat.class);
		
		TTUjob.setOutputKeyClass(LongWritable.class);
		TTUjob.setOutputValueClass(IntWritable.class);
		TTUjob.setOutputFormatClass(TextOutputFormat.class);

		TTMjob.setMapperClass(TTMMapClass.class);
		TTMjob.setReducerClass(TTMReduceClass.class);
		
		TTUjob.setMapperClass(TTUMapClass.class);
		TTUjob.setReducerClass(TTUReduceClass.class);

		int TTMreturnValue = TTMjob.waitForCompletion(true) ? 0:1;
		int TTUreturnValue = TTUjob.waitForCompletion(true) ? 0:1;
		int getMovieNamesreturnValue = getMovieNames.waitForCompletion(true) ? 0:1;

		if (TTMjob.isSuccessful() && TTUjob.isSuccessful() && getMovieNames.isSuccessful()) {
			System.out.println("All jobs were successful");
			
			System.out.println("\nTop Ten Movies:\n");
			for (int key : TopTenMoviesID.keySet()){
				System.out.println(AllMoviesNames.get(key) + " " + TopTenMoviesID.get(key));
			}
			System.out.println("\nTop Ten User Reviews:\n");
			for (long key : TopTenUserReviews.keySet()){
				System.out.println(key + " " + TopTenUserReviews.get(key));
			}
		}
			
		if(!TTUjob.isSuccessful()) {
			System.out.println("TTUJob was not successful");
		} 
		if(!TTMjob.isSuccessful()) {
			System.out.println("TTMJob was not successful");
		}
		if(!getMovieNames.isSuccessful()) {
			System.out.println("getMovieNames was not successful");
		}

		return TTMreturnValue & TTUreturnValue & getMovieNamesreturnValue;
	}


}
