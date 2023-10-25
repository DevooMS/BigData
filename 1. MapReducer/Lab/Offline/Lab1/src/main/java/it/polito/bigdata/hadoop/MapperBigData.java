package it.polito.bigdata.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


/**
 * Basic MapReduce Project - Mapper
 */
class MapperBigData extends Mapper<
                    LongWritable, // Input key type
                    Text,         // Input value type
                    Text,         // Output key type
                    IntWritable> {// Output value type
    
    protected void map(
            LongWritable key,   // Input key type
            Text value,         // Input value type
            Context context) throws IOException, InterruptedException {

            // Split each sentence in words. Use whitespace(s) as delimiter 
    		// (=a space, a tab, a line break, or a form feed)
    		// The split method returns an array of strings
            String[] words = value.toString().split("\\s+");
            
            // Iterate over the set of words
            /*for(String word : words) {
            	// Transform word case
                String cleanedWord = word.toLowerCase();
                
                // emit the pair (word, 1)
                context.write(new Text(cleanedWord),
                		      new IntWritable(1));
            }*/
          
    		for (int i=0; i<words.length-1; i++) {
    			String twograms = words[i] + " " + words[i+1];
    			twograms = twograms.toLowerCase();
    		
                context.write(new Text(twograms),
                		      new IntWritable(1));

                /**
    			 lo uso perche nel file grosso ci sono symboli che vengono anche contate 
                 quindi per evitare faccio questo filtro nel quale se non e alphanumerico
                 lo IGNORO
    			 */
    			if (twograms.matches("[a-z0-9]+ [a-z0-9]+"))
                context.write(new Text(twograms), new IntWritable(1));

    		}

        
        
             
    }
}
