package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Planete;

public class ReadCSV {
	
	public static ReadCSV INSTANCE = new ReadCSV();

	public ReadCSV() {
		// TODO Auto-generated constructor stub
	}


	public ArrayList<Planete> run() {

		String csvFile = "src/main/resources/data/data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		ArrayList<Planete> planetes = new ArrayList<Planete>();

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] planete = line.split(cvsSplitBy,-1);

				planetes.add(new Planete(planete[0],planete[1],planete[2],planete[3],planete[4],planete[5],planete[6],planete[7]));

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

		return planetes;
	}



}