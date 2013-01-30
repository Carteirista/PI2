package mdio_top_alggen;

import java.util.ArrayList;

/**
 *
 * @author Artur
 */
public class Cromossoma{
	/*
	 * gene contém cada caminho, sendo este um array origem->destino 
	 * em que a origem e o indice no array e o valor na posicao o destino.
	 */
	private Integer[][] gene;
	private double[] dist;
	private ArrayList<Integer> livres;
	private ArrayList<Integer> ocupados;
	
	/*
	 * numGene é o numero de caminhos.
	 */
	private int numGene;
	
	private int tamGene;
	/*
	 * fitness é a medida de aptidão do cromossoma para resolver o problema(quanto mais melhor)
	 */
	private double fitness;

	public Cromossoma(Integer[][] gene, double[] dist,  int numGene, int tamGene, double fitness) {
		this.gene = gene;
		this.dist = dist;
		this.numGene = numGene;
		this.fitness = fitness;
		this.tamGene = tamGene;
	}

	public double getFitness() {
		return fitness;
	}

	public Integer[][] getGene() {
		return gene;
	}

	public int getNumGene() {
		return numGene;
	}
	
	public int getTamGene(){
		return tamGene;
	}

	public double[] getDist() {
		return dist;
	}
	

	public void setDist(double[] dist) {
		this.dist = dist;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void setGene(Integer[][] gene) {
		this.gene = gene;
	}

	public void setNumGene(int numGene) {
		this.numGene = numGene;
	}

	public void setTamGene(int tamGene) {
		this.tamGene = tamGene;
	}
	
	@Override
	public String toString(){
		StringBuilder sb= new StringBuilder();
		sb.append("Fitness:");
		sb.append(fitness);
		sb.append("\n");
		for(int i=0; i<numGene;i++){
			sb.append("[");
			sb.append(dist[i]);			
			sb.append("]");
			sb.append(": ");
			for(int j=0; j<tamGene-1; j=gene[i][j]){
				if (gene[i][j]  != 0){
					sb.append("[");
					sb.append(j+1);
					sb.append("->");
					sb.append(gene[i][j]+1);
					sb.append("]");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
