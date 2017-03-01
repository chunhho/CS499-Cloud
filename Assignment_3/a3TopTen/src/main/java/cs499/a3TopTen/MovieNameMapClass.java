package cs499.a3TopTen;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MovieNameMapClass extends Mapper<LongWritable, Text, IntWritable, Text> {
    
	
	@Override
    protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		StringTokenizer st = new StringTokenizer(line,",");
		int movieID;
		String movieName;
		
		while(st.hasMoreTokens()){
			int size = st.countTokens();
			movieID = Integer.parseInt(st.nextToken());
			st.nextToken();
			movieName = st.nextToken();
			if(size > 3){
				while(st.hasMoreTokens()){
					movieName += "," + st.nextToken();
				}
			}
			TopTenDriver.AllMoviesNames.put(movieID, movieName);	
			
		}
		
	}

}
