package cs499.a3TopTen;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TTMMapClass extends Mapper<LongWritable, Text, IntWritable, FloatWritable> {
    
	private IntWritable movieID = new IntWritable();
	
	@Override
    protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		StringTokenizer st = new StringTokenizer(line,",");
		
		while(st.hasMoreTokens()){
			movieID.set(Integer.parseInt(st.nextToken()));
			st.nextToken();
			context.write(movieID,new FloatWritable(Float.parseFloat(st.nextToken())));
		}
		
	}

}
