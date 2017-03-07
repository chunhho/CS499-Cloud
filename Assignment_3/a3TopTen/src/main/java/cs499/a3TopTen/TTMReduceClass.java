package cs499.a3TopTen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class TTMReduceClass extends Reducer<IntWritable, FloatWritable, IntWritable, FloatWritable>{

	protected Map<Integer, Float> TopTen = new HashMap<>();
	
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
		
			TopTen.put(key.get(), sum/count);

			if (TopTen.size() > 10){
				int minKey = getKeyWithSmallestValue();
				TopTen.remove(minKey);
			}

	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException{
		
		TopTenDriver.TopTenMoviesID = TopTen;
		for (int key : TopTen.keySet()){
			context.write(new IntWritable(key), new FloatWritable(TopTen.get(key)));
		}
	}
	
	public int getKeyWithSmallestValue(){
		
		Map.Entry<Integer, Float> minEntry = null;
		
		for(Map.Entry<Integer, Float> entry : TopTen.entrySet()){
		   if(minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0)
		      minEntry = entry;
		}
		return minEntry.getKey();
		
	}
	
}
