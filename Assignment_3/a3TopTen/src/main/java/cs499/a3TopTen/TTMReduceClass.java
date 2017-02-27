package cs499.a3TopTen;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class TTMReduceClass extends Reducer<IntWritable, FloatWritable, IntWritable, FloatWritable>{

	@Override
	protected void reduce(IntWritable key, Iterable<FloatWritable> values,
			Context context)
			throws IOException, InterruptedException {
	
		float sum = 0;
		int count = 0;
		Iterator<FloatWritable> valuesIt = values.iterator();
		
		while(valuesIt.hasNext()){
			sum = sum + valuesIt.next().get();
			count++;
		}
		
		context.write(key, new FloatWritable(sum/count));
	}	
	
}
