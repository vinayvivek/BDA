package vvstemp;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class HighestReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>
{
      
      public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException
      {
    	  int min_temp = 100; 
    	  ; 
          while (values.hasNext())
                      {
        	  int current=values.next().get();
                         if ( min_temp >  current)  
                        	 min_temp =  current;
                      }
          output.collect(key, new IntWritable(min_temp/10));

      }

	
		
	
      
}