package mdio_top_alggen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Artur
 */
public class Geracao {
	private Random rand = new Random();
	
	private ArrayList<Cromossoma> populacao;
	private ArrayList<Cromossoma> proximaGeracao;
	private ArrayList<Cromossoma>paisProxGer;
	private ArrayList<Cromossoma>maesProxGer;
	
	private Cromossoma melhorFit;
	private int numPopulacao;
	private double fitnessTotal;
	private Universo universo;
	private int elite;
	
	public Geracao(ArrayList<Cromossoma> pop, int numPop, int elite, Universo u){
		this.populacao = pop;
		this.numPopulacao = pop.size();
		this.elite = elite;
		this.fitnessTotal = 0;
		for(Cromossoma c : populacao) this.fitnessTotal += c.getFitness();
		this.universo = u;
		this.sortPop();
		this.calcFitnessTotal();
		this.melhorFit = this.populacao.get(numPopulacao-1);
		this.paisProxGer = new ArrayList<Cromossoma>();
		this.maesProxGer = new ArrayList<Cromossoma>();
		this.proximaGeracao = new ArrayList<Cromossoma>();
	}
	
	public void addElite(){
		for(int i=0; i<elite; i++){
			proximaGeracao.add(proximaGeracao.size(), populacao.get(populacao.size()-i-1));
		}
	}
	public void addSubAndElite(int el){
		
		for(int i=0; i<elite-el; i++){
			proximaGeracao.add(proximaGeracao.size(), populacao.get(populacao.size()-i-1));
		}
		for(int i=0; i<el; i++){
			proximaGeracao.add(proximaGeracao.size(), populacao.get(populacao.size()-i-1));
		}
	}
	public double getFitnessTotal() {
		return fitnessTotal;
	}
	public void setFitnessTotal(double fitnessTotal) {
		this.fitnessTotal = fitnessTotal;
	}
	public ArrayList<Cromossoma> getMaesProxGer() {
		return maesProxGer;
	}
	public void setMaesProxGer(ArrayList<Cromossoma> maesProxGer) {
		this.maesProxGer = maesProxGer;
	}
	public Cromossoma getMelhorFit() {
		return melhorFit;
	}
	public void setMelhorFit(Cromossoma melhorFit) {
		this.melhorFit = melhorFit;
	}
	public int getNumPopulacao() {
		return numPopulacao;
	}
	public void setNumPopulacao(int numPopulacao) {
		this.numPopulacao = numPopulacao;
	}
	public ArrayList<Cromossoma> getPaisProxGer() {
		return paisProxGer;
	}
	public void setPaisProxGer(ArrayList<Cromossoma> paisProxGer) {
		this.paisProxGer = paisProxGer;
	}
	public ArrayList<Cromossoma> getPopulacao() {
		return populacao;
	}
	public void setPopulacao(ArrayList<Cromossoma> populacao) {
		this.populacao = populacao;
	}

	public ArrayList<Cromossoma> getProximaGeracao() {
		return proximaGeracao;
	}
	public void setProximaGeracao(ArrayList<Cromossoma> proximaGeracao) {
		this.proximaGeracao = proximaGeracao;
	}
	public Universo getUniverso() {
		return universo;
	}
	public void setUniverso(Universo universo) {
		this.universo = universo;
	}
	
	public Cromossoma mutarCromossoma(Cromossoma c){
		
		//Configurar vars locais
		Integer numVeiculos = c.getNumGene();
		Integer numNodos = c.getTamGene();
		Integer[][] destino = c.getGene();
		double fit = c.getFitness();
		double[] dist = c.getDist();
		boolean done=false;
		int rand, veiculo,aux;
		int anterior = 0;
		
		//Escolher caminho aleatorio
		veiculo =this.rand.nextInt(numVeiculos);
		
		// duas listas que devem guardar respectivament os nodos utilizados e livres pelo caminho do cromossoma
		ArrayList<Integer> livre = new ArrayList(numNodos) ;
		ArrayList<Integer> ocupado = new ArrayList(numNodos);
		
		//Adiciona todos menos o primeiro e ultimo nodo a livres
		for(int nodo = 1; nodo<numNodos-1; nodo++){
			livre.add(nodo);
		}
		
		//pega nos nodos ocupados e passa de livres para ocupados
		ocupado.add(0);
		for(int nodo = destino[veiculo][0]; nodo<numNodos-1;  nodo=destino[veiculo][nodo]){
			ocupado.add( livre.remove( livre.indexOf(nodo) ) );
		}
                    

			rand = 1+ this.rand.nextInt(ocupado.size());

			//Se houver nodos entre o primeiro e o ultimo tira o rand.
			if(rand != ocupado.size() && rand > 0) {
				rand = ocupado.remove(rand);
				//Remove rand e recalcula a distancia total do caminho
				dist[veiculo] = universo.remove(rand, dist[veiculo], destino, veiculo);
			}
		//}
		//Tenta adicionar nodos aleatorios no caminho ate não haver nenhum nodo por tentar.
		for(;livre.size()>0 /*&& done == false*/;){
			done = false;
			int ind =this.rand.nextInt( livre.size() );
			Integer nodoLivre = livre.remove(ind);
			for(int nodo = 0; nodo<numNodos-1 && !done;  nodo=destino[veiculo][nodo]){
			//for(Integer nodoOcupado: ocupado){
				//Se poder adicionar novo nodo
				if(universo.validoEntre(nodoLivre, nodo, destino[veiculo][nodo], dist[veiculo], destino, veiculo)){
				//if(universo.validoEntre(nodoLivre, nodoOcupado, destino[veiculo][nodoOcupado], dist[veiculo], destino)){	
					//Adiciona nodo, guarda nova distancia do caminho
					dist[veiculo] = universo.addEntre(nodoLivre, nodo, destino[veiculo][nodo], dist[veiculo], destino, veiculo);
					//dist[veiculo] = universo.addEntre(nodoLivre, nodoOcupado, destino[veiculo][nodoOcupado], dist[veiculo], destino, veiculo);
					//Este nodo esta adicionado
					done = true;
					break;
				}
			}
		}
		
		//Actualiza o cromossoma para as novas alteracoes
		c.setDist(dist);
		c.setGene(destino);
		c.setFitness(universo.fitness(destino));
		
		//Devolve o cromossoma original com uma mutacao
		return c;
	} 
	
	public void crossOverAll(){
		int num = paisProxGer.size();
		for(int i = 0; i< num; i++){
			crossOver(paisProxGer.get(i), maesProxGer.get(i));
		}
	}
	
	public void crossOver(Cromossoma a, Cromossoma b){
		//Poe referencias aos valores em vars locais
		int genes = a.getNumGene();
		int tamGenes = a.getTamGene();
		Integer ga[][] = a.getGene();
		Integer gb[][] = b.getGene();
		double da[] = a.getDist();
		double db[] = b.getDist();
		//Copia vars para onde possam ser alteradas.
		double dx[] = new double[genes];
		double dy[] = new double[genes];
		double dz[] = new double[genes];
		Integer x[][] = new Integer[genes][tamGenes];
		Integer y[][] = new Integer[genes][tamGenes];
		Integer z[][] = new Integer[genes][tamGenes];
		// Preenche os genes novos com a informaçao dos cromossomas a e b
		System.arraycopy(da, 0,dx, 0, genes);
		System.arraycopy(db, 0,dy, 0, genes);
		for(int i = 0; i<genes;i++){
			System.arraycopy(ga[i], 0, x[i], 0, tamGenes);
			System.arraycopy(gb[i], 0, y[i], 0, tamGenes);
			/*for(int j = 0; j<tamGenes; j++){
				x[i][j] = ga[i][j];
				y[i][j] = gb[i][j];
			}*/
		}
		//Decide por onde cortar os cromossomas
		int split = rand.nextInt(genes+1);
		//troca os veiculos de acordo com o valor decidido em cima
		for(int i = 0; i<split; i++){
			System.arraycopy(x[i], 0, z[i], 0, tamGenes);
			dz[i] = dx[i];
			System.arraycopy(y[i], 0, x[i], 0, tamGenes);
			dx[i] = dy[i];
			System.arraycopy(z[i], 0, y[i], 0, tamGenes);
			dy[i] = dz[i];
		}
		//Adiciona novos cromossomas com os genes gerados em cima a proxima geracao
		this.proximaGeracao.add(new Cromossoma(x, dx, genes, tamGenes, universo.fitness(x)));
		this.proximaGeracao.add(new Cromossoma(y, dy, genes, tamGenes, universo.fitness(y)));
	}
	
	public void roleta(){
		int pop = (numPopulacao-elite)/2;
		for(int i = 0; i < pop; i++){
			paisProxGer.add(cromoRoleta());
		}
		for(int i = 0; i < pop; i++){
			maesProxGer.add(cromoRoleta());
		}
	}
	
	private Cromossoma cromoRoleta(){
		Cromossoma cr = null;
		double cromoRand = this.rand.nextInt((int)fitnessTotal);/*Math.random() * (fitnessTotal);*/
		double soma = 0;
		for(int i = 0; i<this.numPopulacao; i++){
			cr =  this.populacao.get(i);
			soma +=cr.getFitness();
			if (soma >= cromoRand) {break;}
		}
		return cr;
	}
	
	private void calcFitnessTotal(){
		this.fitnessTotal = 0;
		for(Cromossoma cr : populacao){
			this.fitnessTotal += cr.getFitness();
		}
	}
	
	private void sortPop(){
		Collections.sort(populacao, new CromossomaComparator());
	}
	public void sortPop(ArrayList<Cromossoma> pop){
		Collections.sort(pop, new CromossomaComparator());
	}
	
}
