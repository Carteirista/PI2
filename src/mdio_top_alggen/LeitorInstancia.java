package mdio_top_alggen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LeitorInstancia {
	
	File file;
        double[] vertice;
	double[] nodoX;
	double[] nodoY;
	double[] nodoScore;
        double[] vT;
        double[] open;
        double[] close;
	int numNodos;
	double distanciaMax;
	int veiculos;
	
	public LeitorInstancia(File file){
		this.file = file;
	}
	
	public void ler() throws IOException{
		String str = readFileAsString(file);
		String eol = System.getProperty("line.separator"); 
		String[] all = str.split(eol);
		String[] temp;
		// Info
		temp = all[0].split(" ");
		numNodos = Integer.parseInt(temp[1]);
		System.out.println(numNodos);
		temp = all[1].split(" ");
		veiculos = Integer.parseInt(temp[1]);
		System.out.println(veiculos);
		temp = all[2].split(" ");
		distanciaMax = Double.parseDouble(temp[1]);
		System.out.println(distanciaMax);
		
		//Nodos
		nodoX =new double[numNodos];
		nodoY =new double[numNodos];
		nodoScore =new double[numNodos];
                vertice =new double[numNodos];
		vT =new double[numNodos];
		open =new double[numNodos];
		close =new double[numNodos];
		
		for(int i = 0; i<numNodos; i++){
			temp = all[i+3].split(" ");
                        vertice[i] = Double.parseDouble(temp[0]);
			nodoX[i] = Double.parseDouble(temp[1]);
			nodoY[i] = Double.parseDouble(temp[2]);
			vT[i] = Double.parseDouble(temp[3]);
			nodoScore[i] = Double.parseDouble(temp[4]);
			open[i] = Double.parseDouble(temp[5]);
			close[i] = Double.parseDouble(temp[6]);
			System.out.println("["+vertice[i]+"] "+"("+nodoX[i] +", "+ nodoY[i] + ") -> " 
                        +"vT: " + vT[i] + ", " + "[" + open[i] + "," + close[i] + "]" + ", " + nodoScore[i] );
		}
		
	}
	
	private  String readFileAsString(File file) throws java.io.IOException{
			byte[] buffer = new byte[(int)file.length()];
			FileInputStream f = new FileInputStream(file);
			f.read(buffer);
			return new String(buffer);
	}
	
}
