/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class WhichCountriesExport {
	/*public void listExporters(CSVParser parser, String exportOfInterest) {
		//for each row in the CSV File
		for (CSVRecord record : parser) {
			//Look at the "Exports" column
			String export = record.get("Exports");
			//Check if it contains exportOfInterest
			if (export.contains(exportOfInterest)) {
				//If so, write down the "Country" from that row
				String country = record.get("Country");
				System.out.println(country);
			}
		}
	}

	public void whoExportsCoffee() {
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		listExporters(parser, "coffee");
	}
    */
    
    public String countryInfo(CSVParser parser, String countryOfInterest){
        String return_value = "NOT FOUND";
        for (CSVRecord record : parser) {
            String country = record.get("Country");
            if (country.equals(countryOfInterest)) {
                return_value = record.get("Country")+": "+record.get("Exports")+": "+record.get("Value (dollars)");
            }
         }
         return(return_value);
    }
    
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2 ) {
		//for each row in the CSV File
		for (CSVRecord record : parser) {
			//Look at the "Exports" column
			String export = record.get("Exports");
			//Check if it contains exportOfInterest
			if (export.contains(exportItem1) && export.contains(exportItem2)) {
				//If so, write down the "Country" from that row
				String country = record.get("Country");
				System.out.println(country);
			}
  		}
	}
    
    
    public int numberOfExporters(CSVParser parser, String exportItem){
        int count = 0;
        for (CSVRecord record : parser) {
			String export = record.get("Exports");
			if (export.contains(exportItem)) {
                count = count + 1;
			}
		}
        return(count);
    }
    
    
    public void bigExporters(CSVParser parser, String amount){
        for (CSVRecord record : parser) {
			String export = record.get("Value (dollars)");
			if (export.length() > amount.length()) {
                System.out.println(record.get("Country")+" "+record.get("Value (dollars)"));
			}
		}
        
    }
    
    
    public void tester(){
        FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		//String return_value = countryInfo(parser, "Nauru");
        //System.out.println(return_value);
        //listExportersTwoProducts(parser, "cotton", "flowers");
        //int numberOfExporterscount = numberOfExporters(parser, "cocoa");
        //System.out.println(numberOfExporterscount);
        bigExporters(parser, "$999,999,999,999.");
    }
}
