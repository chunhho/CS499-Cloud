package cs499.a3TopTen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class TTUReduceClass extends Reducer<LongWritable, IntWritable, LongWritable, IntWritable> {
	
	private Map<Long, Integer> TopTen = new HashMap<>();

	@Override
	protected void reduce(LongWritable key, Iterable<IntWritable> values,
			Context context)
			throws IOException, InterruptedException {
	
		int sum = 0;
		Iterator<IntWritable> valuesIt = values.iterator();
		
		while(valuesIt.hasNext()){
			sum = sum + valuesIt.next().get();
		}
		
		TopTen.put(key.get(), sum);

		if (TopTen.size() > 10){
			long minKey = getKeyWithSmallestValue();
			TopTen.remove(minKey);
		}
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException{
		
		TopTenDriver.TopTenUserReviews = TopTen;
		for (long key : TopTen.keySet()){
			context.write(new LongWritable(key), new IntWritable(TopTen.get(key)));
		}
	}
	
	public long getKeyWithSmallestValue(){
		
		Map.Entry<Long, Integer> minEntry = null;
		
		for(Map.Entry<Long, Integer> entry : TopTen.entrySet()){
		   if(minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0)
		      minEntry = entry;
		}
		return minEntry.getKey();
		
	}
}
