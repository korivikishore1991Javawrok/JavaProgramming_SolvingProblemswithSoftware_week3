/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestSoFar = null;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			// use method to compare two records
			coldestSoFar = getSmallestOfTwo(currentRow, coldestSoFar);
		}
		//The largestSoFar is the answer
		return coldestSoFar;
    }
	public CSVRecord hottestHourInFile(CSVParser parser) {
		//start with largestSoFar as nothing
		CSVRecord largestSoFar = null;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			// use method to compare two records
			largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
		}
		//The largestSoFar is the answer
		return largestSoFar;
	}

	public void testHottestInDay () {
		FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
		CSVRecord largest = hottestHourInFile(fr.getCSVParser());
		System.out.println("hottest temperature was " + largest.get("TemperatureF"));
	}
    
    public void testColdestHourInFile () {
		FileResource fr = new FileResource();
		CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
		System.out.println("coldest temperature was " + coldest.get("TemperatureF"));
	}

	public CSVRecord hottestInManyDays() {
		CSVRecord largestSoFar = null;
		DirectoryResource dr = new DirectoryResource();
		// iterate over files
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			// use method to get largest in file.
			CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
			// use method to compare two records
			largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
		}
		//The largestSoFar is the answer
		return largestSoFar;
	}

	public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
		//If largestSoFar is nothing
		if (largestSoFar == null) {
			largestSoFar = currentRow;
		}
		//Otherwise
		else {
			double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
			double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
			//Check if currentRow’s temperature > largestSoFar’s
			if (currentTemp > largestTemp) {
				//If so update largestSoFar to currentRow
				largestSoFar = currentRow;
			}
		}
		return largestSoFar;
	}
    
    public CSVRecord getSmallestOfTwo (CSVRecord currentRow, CSVRecord coldestSoFar) {
		//If largestSoFar is nothing
		if (coldestSoFar == null) {
			coldestSoFar = currentRow;
		}
		//Otherwise
		else {
			double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
			double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
			//Check if currentRow’s temperature > largestSoFar’s
			if (currentTemp < coldestTemp) {
				//If so update largestSoFar to currentRow
				coldestSoFar = currentRow;
			}
		}
		return coldestSoFar;
	}

	public void testHottestInManyDays () {
		CSVRecord largest = hottestInManyDays();
		System.out.println("hottest temperature was " + largest.get("TemperatureF") +
				   " at " + largest.get("DateUTC"));
	}
    
    
    public String fileWithColdestTemperature(){
        CSVRecord coldestSoFar = null;
		DirectoryResource dr = new DirectoryResource();
        String filename = null;
		
		for (File f : dr.selectedFiles()) {
		   FileResource fr = new FileResource(f);
			
		   CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
			
		   coldestSoFar = getSmallestOfTwo(currentRow, coldestSoFar);
           if (coldestSoFar.get("TemperatureF") == currentRow.get("TemperatureF")){
               filename = f.getAbsolutePath();
            }
		}
            
		
		    return filename;
    }

    public void testFileWithColdestTemperature() {
        
        System.out.println("Coldest day was in file " + fileWithColdestTemperature());
        FileResource fr = new FileResource(fileWithColdestTemperature());
	    CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature on that day was " + coldest.get("TemperatureF")+" at "+ coldest.get("DateUTC"));
        System.out.println("All the Temperatures on the coldest day were:");
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
             System.out.println(record.get("DateUTC")+" "+record.get("TemperatureF"));
            }

    }

    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord resultRecord = null;

        for (CSVRecord record : parser) {

            if (record.get("Humidity").equals("N/A")) {
                continue;
            }

            if (resultRecord == null) {
                resultRecord = record;
            } else {

                double temperatureF = Double.parseDouble(record.get("Humidity"));
                double coldestTemp = Double.parseDouble(resultRecord.get("Humidity"));
                resultRecord = (temperatureF < coldestTemp) ? record : resultRecord;

            }

        }

        return resultRecord;
    }
    
    
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was "+csv.get("Humidity")+" at "+csv.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
		CSVRecord lowestSoFar = null;
		DirectoryResource dr = new DirectoryResource();
		// iterate over files
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			// use method to get largest in file.
			CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
			// use method to compare two records
			lowestSoFar = getlowestOfTwo(currentRow, lowestSoFar);
		}
		//The largestSoFar is the answer
		return lowestSoFar;
	}
    
     public CSVRecord  getlowestOfTwo(CSVRecord currentRow, CSVRecord lowestSoFar) {
		CSVRecord return_value = null;
        //If largestSoFar is nothing
         if (currentRow.get("Humidity").equals("N/A")) {
                return_value = lowestSoFar;
            }
		else if (lowestSoFar == null) {
			return_value = currentRow;
		}
		//Otherwise
		else {
			double currentTemp = Double.parseDouble(currentRow.get("Humidity"));
			double coldestTemp = Double.parseDouble(lowestSoFar.get("Humidity"));
			//Check if currentRow’s temperature > largestSoFar’s
			if (currentTemp < coldestTemp) {
				//If so update largestSoFar to currentRow
				return_value = currentRow;
			}
		}
        return return_value;
	}
    
    public void testLowestHumidityInManyFiles(){
        CSVRecord lowest = lowestHumidityInManyFiles();
		System.out.println("hottest Humidity was " + lowest.get("Humidity") +" at " + lowest.get("DateUTC"));
    }
    
    
    public double averageTemperatureInFile(CSVParser parser) {
        double total = 0.0;
        double count = 0.0;
        for (CSVRecord record : parser) {
            total = total+Double.parseDouble(record.get("TemperatureF"));
            count = count + 1;
        }
        double Average = total/count;
        return Average;
    }
    
    
    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println("Average temperature in file is "+averageTemperatureInFile(parser));
    }  


    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        double total = 0.0;
        double count = 0.0;
        for (CSVRecord record : parser) {
            if ((record.get("Humidity") != "N/A")&&(Integer.parseInt(record.get("Humidity")) >= value)){
                total = total+Double.parseDouble(record.get("TemperatureF"));
                count = count + 1;
            } 
        }
        double Average = total/count;
        return Average;
    }
    
     public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println("Average Temp when high Humidity is "+averageTemperatureWithHighHumidityInFile(parser, 80));
    }  

}
