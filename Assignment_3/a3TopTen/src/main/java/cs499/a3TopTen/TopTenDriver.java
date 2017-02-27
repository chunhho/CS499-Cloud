package cs499.a3TopTen;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class TopTenDriver extends Configured implements Tool {
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new TopTenDriver(), args);
		System.exit(exitCode);
	}

	public int run(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.printf("Usage: %s needs three arguments, input and output files\n", getClass().getSimpleName());
			return -1;
		}

		Job TTMjob = new Job();
		TTMjob.setJarByClass(TopTenDriver.class);
		TTMjob.setJobName("TTMCounter");
		
		Job TTUjob = new Job();
		TTUjob.setJarByClass(TopTenDriver.class);
		TTUjob.setJobName("TTUCounter");

		FileInputFormat.addInputPath(TTMjob, new Path(args[0]));
		FileOutputFormat.setOutputPath(TTMjob, new Path(args[1]));
		
		FileInputFormat.addInputPath(TTUjob, new Path(args[0]));
		FileOutputFormat.setOutputPath(TTUjob, new Path(args[2]));

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

		if (TTMjob.isSuccessful() && TTUjob.isSuccessful()) {
			System.out.println("Both jobs were successful");
		} else if(!TTMjob.isSuccessful() && !TTUjob.isSuccessful()) {
			System.out.println("Both jobs were unsuccessful");
		} else if(!TTUjob.isSuccessful()) {
			System.out.println("TTUJob was not successful");
		} else {
			System.out.println("TTMJob was not successful");
		}

		return TTMreturnValue & TTUreturnValue;
	}


}
