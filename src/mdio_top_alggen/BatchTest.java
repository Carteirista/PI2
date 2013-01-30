/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mdio_top_alggen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Artur
 */
public class BatchTest {
	File[] files;
	String filename;
	int limitGeneration, numFiles;
	int pop, elite, subelite, muts;
	LeitorInstancia[] li;
	int limitRuns;
	int[][] runs;
	int[][] bestFitness;
	long[][] start;
	long[][] end;

	public BatchTest(File[] files, int limitGeneration, int limitRuns, int pop, int elite, int subelite, int muts, String filename) throws IOException {
		this.files = files;
		this.limitGeneration = limitGeneration;
		this.limitRuns = limitRuns;
		this.numFiles = files.length;
		this.elite = elite;
		this.subelite = subelite;
		this.pop = pop;
		this.muts = muts;
		this.filename = filename;
		this.li = new LeitorInstancia [this.numFiles];
		this.runs = new int [this.numFiles][this.limitRuns];
		this.bestFitness = new int [this.numFiles][this.limitRuns];
		this.start = new long [this.numFiles][this.limitRuns];
		this.end = new long [this.numFiles][this.limitRuns];
		for(int i=0; i<numFiles;i++){
			li[i] = new LeitorInstancia(files[i]);
			li[i].ler();
		}
	}
	
	public void runTests() throws IOException{
		for(int i=0; i<numFiles;i++){
			for(int j=0; j<limitRuns; j++){
				System.out.println("Run "+j+" of file number "+i+";");
				runInstance(i,j);
			}
		}
		writeResultsCSV(this.filename);
	}
	
	public void runInstance(int inst, int num){
		start[inst][num] = System.currentTimeMillis();
		Universo uni = new Universo(li[inst], pop, elite);
		end[inst][num] = System.currentTimeMillis();
		uni.setSubelite(subelite);
		uni.setNumMuta(muts);
		bestFitness[inst][num] = (int) uni.getMelhorFitActual();
		runs[inst][num] = uni.getGeracaoActual()+1;
		for(int i = 0; i<limitGeneration; i++){
			uni.nextGen();
			if (uni.getMelhorFitActual() > bestFitness[inst][num]) 
			{
				end[inst][num] = System.currentTimeMillis();
				bestFitness[inst][num] = (int) uni.getMelhorFitActual();
				runs[inst][num] = uni.getGeracaoActual()+1;
			}
		}
	}
	
	public void writeResultsCSV(String filename) throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append("Instance").append(";")
						.append("Run").append(";")
						.append("Fitness").append(";")
						.append("Time(ms)").append(";")
						.append("Iterations").append(";")
						.append("max:").append(this.limitGeneration).append(";").append("\n");
		for(int i=0; i<numFiles;i++){
			for(int j=0; j<limitRuns; j++){
				sb.append(i).append(";")
				    .append(j).append(";")
				    .append(this.bestFitness[i][j]).append(";")
				    .append(this.end[i][j]-this.start[i][j]).append(";")
				    .append(this.runs[i][j]).append(";").append("\n");
				    
			}
		}
		 try{
			// Create file 
			FileWriter fstream = new FileWriter(filename+".csv");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(sb.toString());
			//Close the output stream
			out.close();
		}catch (Exception e){System.err.println("Error: " + e.getMessage());}
	}	
}
