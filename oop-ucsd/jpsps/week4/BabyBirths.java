package jpsps.week4;
import edu.duke.*;

import java.io.File;

import org.apache.commons.csv.*;

public class BabyBirths {
	public void printNames () {
		FileResource fr = new FileResource();
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (numBorn <= 100) {
				System.out.println("Name " + rec.get(0) +
						   " Gender " + rec.get(1) +
						   " Num Born " + rec.get(2));
			}
		}
	}
	public void totalBirths (FileResource fr) {
		int totalBirths = 0;
		int totalBoys = 0;
		int totalGirls = 0;
		int totalBoysNames = 0;
		int totalGirlsNames = 0;
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				totalBoysNames += 1;
			}
			else {
				totalGirls += numBorn;
				totalGirlsNames += 1;
			}
		}
		System.out.println("total births = " + totalBirths);
		System.out.println("female girls = " + totalGirls);
		System.out.println("male boys = " + totalBoys);
		System.out.println("total male names = " + totalBoysNames);
		System.out.println("total female names = " + totalGirlsNames);
	}
	public int getRank(int year, String name, String gender) {
		int rank = 0;
		boolean foundName = false;
		FileResource fr = new FileResource(); 
		//FileResource fr = new FileResource("us_babynames_by_year/yob" + year + ".csv");
		for (CSVRecord rec : fr.getCSVParser(false)) {
			boolean sameName = rec.get(0).equals(name);
			boolean sameGender = rec.get(1).equals(gender);
			if (sameGender) {
				rank += 1;
			}
			if (sameName && sameGender) {
				foundName = true;
				break;
			}
		}
		if (foundName) {
			return rank;
		}
		return -1;
	}
	public String getName(int year, int rank, String gender) {
		int currRank = 0;
		String nameInRank = "NO NAME";
		FileResource fr = new FileResource();
		//FileResource fr = new FileResource("us_babynames_by_year/yob" + year + ".csv");
		for (CSVRecord rec : fr.getCSVParser(false)) {
			boolean sameGender = rec.get(1).equals(gender);
			if (sameGender) {
				currRank += 1;
			}
			if (currRank == rank && sameGender) {
				nameInRank = rec.get(0);
				break;
			}
		}
		return nameInRank;
	}
	public int yearOfHighestRank(String name, String gender) {
		int highestRank = -1;
		int highestRankYear = -1;
		DirectoryResource dr = new DirectoryResource();
		for (File f : dr.selectedFiles()) {
			String fileName = f.getName();
			int currYear = fileNameYear(fileName);
			int currRank = getRank(currYear, name, gender);

			if (highestRank < currRank) {
				highestRank = currRank;
				highestRankYear = currYear;
			}
		}
		return highestRankYear;
	}
	public int fileNameYear(String filename) {
		int startIndex = filename.indexOf("yob") + 3;
		return Integer.parseInt(filename.substring(startIndex,startIndex+4));
	}
	public void whatIsNameInYear(String name, int year, int newYear, String gender) {
		int rankInYear = getRank(year, name, gender);
		String newNameInNewYear = getName(newYear,rankInYear,gender);
		System.out.println( name + " born in " + year + " would be " + 
							newNameInNewYear + " if she was born in " + newYear + ".");
	}
	public void testTotalBirths () {
		//FileResource fr = new FileResource();
		FileResource fr = new FileResource();
		totalBirths(fr);
	}
	public void testGetRank() {
		int rank = getRank(2012, "Mason", "F");
		if (rank == -1) {
			System.out.println("not found");
		}
		else {
			System.out.println("Rank: " + rank);
		}
	}
	public void testGetName() {
		String name = getName(2014, 5, "M");
		System.out.println(name);
	}
    public static void main(String[] args) {
        BabyBirths b = new BabyBirths();
        //b.testTotalBirths();
		//b.testGetRank();
		b.testGetName();
    }
}
