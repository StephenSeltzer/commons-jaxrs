package edu.psu.util.profiler;

import java.util.ArrayList;
import java.util.List;

public class Profile
{
  private List<Long> startTime_ = new ArrayList<Long>();
  private List<Long> stopTime_ = new ArrayList<Long>();

  // ------------------------------------------------------------------------------------------ //

  public void start()
  {
//    start_time_.add( ((Long)System.currentTimeMillis()) );
    startTime_.add( ((Long)System.nanoTime()) );
  }

  // ------------------------------------------------------------------------------------------ //

  public void stop()
  {
//    stop_time_.add( ((Long)System.currentTimeMillis()) );
    stopTime_.add( ((Long)System.nanoTime()) );
  }
  
  // ------------------------------------------------------------------------------------------ //

/*
  public String generateReport()
  {
    long count = 0;
    long delta = 0;
    
    for( int i=0 ; i<stop_time_.size() ; ++i )
    {
      ++count;
      delta += stop_time_.get(i) - start_time_.get(i);
    }
    
    if( count > 0 ) 
      // %[argument_index$][flags][width][.precision]conversion
      return( String.format(" %1$ 8d * %2$ 13d ns = %3$ 13d ns", count, (delta / count), delta ) );

    else
      return( "(no completed profiles taken)" );
  }
*/

	public String generateReport() {
		long count = 0;
		double delta = 0;
		double accumDelta = 0;
		double accumSdev = 0;
		double avg = 0;
		double sdev = 0;
		String avgUnit = "";
		String totalUnit = "";
		String returnMessage = "";
		
		for( int i=0 ; i<stopTime_.size() ; ++i ) {
			if( stopTime_.get(i) != null ) {
				++count;
				delta = (double) stopTime_.get(i) - (double) startTime_.get(i);
				accumDelta += delta;
			}
		}

		if( count > 0 ) {
			avg = accumDelta / (double)count;

			for( int i=0 ; i<stopTime_.size() ; ++i ) {
				if( stopTime_.get(i) != null ) {
					delta = (double) stopTime_.get(i) - (double) startTime_.get(i);
					accumSdev += (delta - avg) * (delta - avg);
				}
			}
			sdev = Math.sqrt( accumSdev / count );

			if( accumDelta < 999 ) {
				totalUnit = "ns";
			} else if( accumDelta < 999999 ) {
				avgUnit = "us";//"µs";
				accumDelta /= 1000;
			} if( accumDelta < 999999999 ) {
				totalUnit = "ms";
				accumDelta /= 1000000;
			} else if( accumDelta > 999 ) {
				totalUnit = " s";
				accumDelta /= 1000000000;
			}

			if( avg < 999 ) {
				avgUnit = "ns";
			} else if( avg < 999999 ) {
				avgUnit = "us";//"µs";
				avg /= 1000;
				sdev /= 1000;
			} else if( avg < 999999999 ) {
				avgUnit = "ms";
				avg /= 1000000;
				sdev /= 1000000;
			} else if( avg > 999 ) {
				avgUnit = " s";
				avg /= 1000000000;
				sdev /= 1000000000;
			}

			// %[argument_index$][flags][width][.precision]conversion
			//return( String.format(" %1$ 8d * %2$ 8.3f "+perUnit+" = %3$ 8.3f "+totalUnit, count, avg, delta ) );
			//return( String.format(" %1$ 8d * %2$ 8.3f "+avgUnit+" = %3$ 8.3f "+totalUnit, count, avg, accumDelta ) );
			returnMessage = String.format(" %1$ 8d * %2$ 8.3f "+avgUnit+" = %3$ 8.3f "+totalUnit+"  σ %4$8.3f "+avgUnit, count, avg, accumDelta, sdev );

			if( sdev > avg ) {
				returnMessage += "\r\n" + this.dumpData();
			}
			
			return returnMessage;
		}
		else {
			return( "(no completed profiles taken)" );
		}
	}

  // ------------------------------------------------------------------------------------------ //

	public String dumpData() {
		long count = 0;
		double total = 0;
		double delta = 0;
		double avg = 0;
		double sdev = 0;
		String returnString = "      ";
    
		for( int i=0 ; i<stopTime_.size() ; ++i ) {
			++count;
			if( ! returnString.equalsIgnoreCase("      ") ) 	{returnString += ", ";}
			if( i > 0 && i % 5 == 0 ) 							{returnString += "\r\n      ";}
			delta = ((double) stopTime_.get(i) - (double) startTime_.get(i)) / 1000000000;
			total += delta;
			// %[argument_index$][flags][width][.precision]conversion
			returnString += (String.format("%1$ 8.5f", delta ));
		}
		returnString += "\r\n";
		
		avg = (double)(total / count);
		total = 0;
		for( int i=0 ; i<stopTime_.size() ; ++i ) {
			delta = ((double) stopTime_.get(i) - (double) startTime_.get(i)) / 1000000000;
			total += (delta - avg) * (delta - avg);
//			returnString += (String.format("      RUNNING: %1$ 8.5f += (%2$ 8.5f - %3$ 8.5f) ^ 2\r\n", total, delta, avg ));
		}
		sdev = Math.sqrt( total / count );
//		returnString += (String.format("      TOTAL: %1$ 8.5f\r\n", total ));
//		returnString += (String.format("      COUNT: %1$ 8d\r\n", count ));
		returnString += (String.format("      AVG: %1$ 8.5f s   SDEV: %2$ 8.5f s", avg, sdev ));
		return( returnString );
	}

  // ------------------------------------------------------------------------------------------ //

}

