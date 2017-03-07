package cs499.a3TopTen;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TTUMapClass extends Mapper<LongWritable, Text, LongWritable, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private LongWritable userID = new LongWritable();
	
	@Override
    protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		StringTokenizer st = new StringTokenizer(line,",");
		
		while(st.hasMoreTokens()){
			st.nextToken();
			userID.set(Long.parseLong(st.nextToken()));
			st.nextToken();
			context.write(userID,one);
		}
		
	}
 

}
