package mdio_top_alggen;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Artur
 */
public class Universo {
	
	LeitorInstancia li;
	Random rand = new Random();
	private double[] nodoX;
	private double[] nodoY;
	private double[] nodoScore;
	private int numNodos;
	private double distanciaMax;
	private int veiculos;
        private double[] vT;
        private double[] open;
        private double[] close;
        
	private double[][] distancia;
	private int numPopulacao = 520;
	private int geracaoActual = 0;
	private ArrayList<Geracao> geracoes = new ArrayList<Geracao>();
	private Geracao geracao;
	private int elite = 10;
	private int subelite = 10;
	private int numMuta = 1;
	
	public Universo (LeitorInstancia lI) {
		this.li = lI;
		this.numNodos=li.numNodos;
		this.nodoX = li.nodoX;
		this.nodoY = li.nodoY;
		this.nodoScore = li.nodoScore;
		this.numNodos = li.numNodos;
		this.distanciaMax = li.distanciaMax;
		this.veiculos = li.veiculos;
		this.distancia = new double[numNodos][numNodos];
                this.open = li.open;
                this.close = li.close;
                this.vT = li.vT;
		
		for(int j = 0; j<numNodos;j++){
			for(int i = 0; i<numNodos;i++){
				distancia[i][j] = Math.sqrt(Math.pow(nodoX[j]-nodoX[i],2)+Math.pow(nodoY[j]-nodoY[i],2));
			}
		}
		
		ArrayList<Cromossoma> crom = new ArrayList<Cromossoma>(this.numPopulacao);
		for(int i = 0; i<numPopulacao;i++){
			crom.add(criaCromossomaAleatorio());
		}
		Geracao ger = new Geracao(crom, numPopulacao, elite+subelite, this);
		
		this.geracao = ger;
	}
	
	public Universo (LeitorInstancia lI, int pop, int elite) {
		this.li = lI;
		this.numNodos=li.numNodos;
		this.nodoX = li.nodoX;
		this.nodoY = li.nodoY;
		this.nodoScore = li.nodoScore;
		this.numNodos = li.numNodos;
		this.distanciaMax = li.distanciaMax;
		this.veiculos = li.veiculos;
		this.distancia = new double[numNodos][numNodos];
		this.numPopulacao =pop;
		this.elite = elite;
                this.open = li.open;
                this.close = li.close;
                this.vT = li.vT;
		
		for(int j = 0; j<numNodos;j++){
			for(int i = 0; i<numNodos;i++){
				distancia[i][j] = Math.sqrt(Math.pow(nodoX[j]-nodoX[i],2)+Math.pow(nodoY[j]-nodoY[i],2));
			}
		}
		
		ArrayList<Cromossoma> crom = new ArrayList<Cromossoma>(this.numPopulacao);
		for(int i = 0; i<numPopulacao;i++){
			crom.add(criaCromossomaAleatorio());
		}
		Geracao ger = new Geracao(crom, numPopulacao, elite, this);
		this.geracao = ger;
	}

	public double[][] getDistancia() {
		return distancia;
	}
	public void setDistancia(double[][] distancia) {
		this.distancia = distancia;
	}
	public double getDistanciaMax() {
		return distanciaMax;
	}
	public void setDistanciaMax(double distanciaMax) {
		this.distanciaMax = distanciaMax;
	}
	public int getGeracaoActual() {
		return geracaoActual;
	}
	public void setGeracaoActual(int geracaoActual) {
		this.geracaoActual = geracaoActual;
	}
	public ArrayList<Geracao> getGeracoes() {
		return geracoes;
	}
	public void setGeracoes(ArrayList<Geracao> geracoes) {
		this.geracoes = geracoes;
	}
	public double[] getNodoScore() {
		return nodoScore;
	}
	public void setNodoScore(double[] nodoScore) {
		this.nodoScore = nodoScore;
	}
	public double[] getNodoX() {
		return nodoX;
	}
	public void setNodoX(double[] nodoX) {
		this.nodoX = nodoX;
	}
	public double[] getNodoY() {
		return nodoY;
	}
	public void setNodoY(double[] nodoY) {
		this.nodoY = nodoY;
	}
	public int getNumNodos() {
		return numNodos;
	}
	public void setNumNodos(int numNodos) {
		this.numNodos = numNodos;
	}
	public int getNumPopulacao() {
		return numPopulacao;
	}
	public void setNumPopulacao(int numPopulacao) {
		this.numPopulacao = numPopulacao;
	}
	public int getVeiculos() {
		return veiculos;
	}
	public void setVeiculos(int veiculos) {
		this.veiculos = veiculos;
	}
	public int getElite() {
		return elite;
	}
	public void setElite(int elite) {
		this.elite = elite;
	}
	public Geracao getGeracao() {
		return geracao;
	}
	public void setGeracao(Geracao geracao) {
		this.geracao = geracao;
	}
	public int getNumMuta() {
		return numMuta;
	}
	public void setNumMuta(int numMuta) {
		this.numMuta = numMuta;
	}
	public int getSubelite() {
		return subelite;
	}
	public void setSubelite(int subelite) {
		this.subelite = subelite;
	}
	public double getMelhorFitActual(){
		return this.geracao.getMelhorFit().getFitness();
	}
	
	
	public void nextGen(){
		//Geracao ger = geracoes.get(geracaoActual);
		Geracao ger = geracao;
		ger.roleta();
		ger.crossOverAll();
		ger.addSubAndElite(elite);
		ArrayList<Cromossoma> nextGen = ger.getProximaGeracao();
		int max = nextGen.size()-elite;
		for(int i = 0; i<max; i++) {
			for(int j = 0; j< this.numMuta;j++){
				ger.mutarCromossoma(nextGen.get(i));
			}
		}
		//Cria nova Gera√ß√£o
		ger = new Geracao(nextGen, numNodos, elite, this);
		//Actualiza Universo com nova Gera√ß√£o
		this.geracaoActual++;
		this.geracao = ger;
	}
	
	
	void filter(int i, Integer[][] n) {
		ArrayList<Integer> nonN = new ArrayList<Integer>();
		
			for(int j = 0; j<numNodos-1; j++){
				nonN.add(n[i][j]);
			}
			System.out.println(nonN);
		
	}
	
	void filter2(Integer[] n) {
		ArrayList<Integer> nonN = new ArrayList<Integer>();
		
			for(int j = 0; j<numNodos-1; j++){
				nonN.add(n[j]);
			}
			System.out.println(nonN);
		
	}
	
	/*
	 * Processo de cria√ß√£o de um cromossoma aleat√≥rio, √∫til na inicializa√ß√£o do problema.
	 */
	public Cromossoma criaCromossomaAleatorio(){
		int i=0;
		double dist[] = new double[this.veiculos] ;
		for(i = 0; i<this.veiculos;i++){dist[i] = this.distancia[0][this.numNodos-1];}
		boolean done = false;
		//Cria base
		ArrayList<Integer> nodosLivres = new ArrayList<Integer>(numNodos-3);
		ArrayList<Integer> nodosTestados = new ArrayList<Integer>(numNodos-3);
		ArrayList<Integer> nodosOcupados = new ArrayList<Integer>(numNodos);
		//Poe o primeiro e ultimo nodos do caminho como ocupados
		nodosOcupados.add(0, 0);
		nodosOcupados.add(1, numNodos-1);
		//Poe todos os outros nodos na lista dos nodos livres
		for(i=1;i<numNodos-1;i++) {nodosLivres.add(i);}
		Integer[][] g = new Integer [veiculos][numNodos];
		//Estabelece que em cada caminho de um veiculo ha uma ligacao entre os nodos entrada e saida
		for(i=0;i<veiculos;i++) { g[i][0] = numNodos-1;} 
		//vai metendo os nodos de Livres em Testados at√© conseguir inserir um ou n√£o existirem mais livres		
		for(; nodosLivres.size()>0; ) {
			i = rand.nextInt(nodosLivres.size());
			Integer nodo = nodosLivres.remove(i);
			done = false;
			//Verifica se encaixa em algum caminho, de algum veiculo, entre alguns 2 nodos.
			for(i=0;i<veiculos && done == false;i++){
				for(Integer p : nodosOcupados){
					if(validoEntre(nodo, p, g[i][p], dist[i], g, i) && !done){
						dist[i] = addEntre(nodo, p, g[i][p], dist[i], g, i);
						done = true;
						break;
					}
				}
			}
			if (done == false) {
				nodosTestados.add(nodo);
			}
		}
		//Cria um cromossoma com os caminhos criados em cima e um fitness calculado coma respectiva fun√ß√£o
		Cromossoma c = new Cromossoma(g, dist, veiculos, numNodos, fitness(g));
		return c;
	}
	
	/*
	 *Verifica a validade de adicionar o nodo "novo" entre os nodos "pontoA" e "pontoB"
	 */
	public boolean validoEntre(Integer novo, Integer pontoA, Integer pontoB, double distActual, Integer[][]gene, Integer veiculo){
//System.out.println("***ENTROU***");
//System.out.println("PontoA:"+pontoA+" PontoB:"+pontoB+" Novo:"+novo +" Veiculo:"+veiculo);
		
		if (pontoA == this.numNodos-1) { return false; }
		for(int i = 0; i<this.veiculos;i++){
			for(int j = 0; j<this.numNodos-1 ;  j=gene[i][j]){ 
				if(novo == j) {return false;}
			}
		}
		
		//Calcula a rota ordenada de vértices percorridos até agora pelo veiculo
		Integer[] aux = new Integer [numNodos];
		for(int i= 0, j = 0; i < numNodos - 1; i = gene[veiculo][i], j++){
			aux[j] = i;
			if(gene[veiculo][i]==(numNodos-1)){
				aux[j+1] = numNodos-1;

			}
		}
//filter2(aux);
		double caminho = 0;
		
//System.out.println("PontoA:"+pontoA+" PontoB:"+pontoB);
		
		//Calcula o tempo decorrido até ao pontoA
		for(int i = 0; aux[i+1] != pontoB; i++){
			caminho += this.distancia[aux[i]][aux[i+1]];
			if(caminho<this.open[aux[i+1]])
				caminho = open[aux[i+1]] + this.vT[aux[i+1]];
			else
				caminho += this.vT[aux[i+1]];			
//System.out.println("Nodo:"+ aux[i]+" caminho: "+caminho +"vT:"+this.vT[aux[i]]+"dist:"+aux[i]+"->"+aux[i+1]+": "+this.distancia[aux[i]][aux[i+1]]);
		}
//System.out.println("*"+caminho);
		
		//Tenta adicionar distância do pontoA -> novo + duração no novo
//System.out.println(">>>Nodo:"+ novo+" caminho: "+caminho +"vT:"+this.vT[novo]+"dist:"+pontoA+"->"+novo+": "+this.distancia[pontoA][novo]);
		caminho += this.distancia[pontoA][novo];
		if(caminho > this.close[novo]){
			return false;
		}
		if(caminho < this.open[novo]){
			caminho = this.open[novo];
		}
		caminho += vT[novo];
//System.out.println(">>>"+caminho);
		
//System.out.println("+++Nodo:"+ novo+" caminho: "+caminho +"vT:"+this.vT[pontoB]+"dist:"+novo+"->"+pontoB+": "+this.distancia[novo][pontoB]);
		//Tenta adicionar distância do novo -> PontoB + duração no pontoB
		caminho += this.distancia[novo][pontoB];
		if(caminho > close[pontoB]){
			return false;
			}
        if(caminho < open[pontoB]){
        	caminho = open[pontoB];
        }
        caminho += vT[pontoB];
//System.out.println("+++"+caminho);

		int p;
		for(p=0; aux[p] != pontoB; p++)
			;

      //Calcula o tempo decorrido do pontoB até ao fim do caminho
		for(int i = p; aux[i] != numNodos-1; i++){
//System.out.println("entrou");
			caminho += this.distancia[aux[i]][aux[i+1]];
			if(caminho<this.open[aux[i+1]])
				caminho = open[aux[i+1]] + this.vT[aux[i+1]];
			if(caminho > this.close[aux[i+1]])
				return false;
			else
				caminho += this.vT[aux[i+1]];
//System.out.println("xxxNodo:"+ aux[i]+" caminho: "+caminho +"vT:"+this.vT[aux[i]]+"dist:"+aux[i]+"->"+aux[i+1]+": "+this.distancia[aux[i]][aux[i+1]]);
		}
//System.out.println("^^^"+caminho);

        distActual = caminho;
        
//System.out.println(distActual <= this.distanciaMax);
//System.out.println("***Saiu***");
		return (distActual <= this.distanciaMax);
	}
	
	/*
	 * Adiciona o nodo "novo" entre os nodos "pontoA" e "pontoB"
	 */
	public double addEntre(Integer novo, Integer pontoA, Integer pontoB, double distActual, Integer[][]gene, Integer veiculo){
//System.out.println("PontoA:"+pontoA+" PontoB:"+pontoB+" Novo:"+novo +" Veiculo:"+veiculo);
		//Calcula a rota ordenada de vértices percorridos até agora pelo veiculo
		Integer[] aux = new Integer [numNodos];
		for(int i= 0, j = 0; i < numNodos - 1; i = gene[veiculo][i], j++){
			aux[j] = i;
			if(gene[veiculo][i]==(numNodos-1)){
				aux[j+1] = numNodos-1;

			}
		}
//filter2(aux);
		double caminho = 0;

		//Calcula o tempo decorrido até ao pontoA
		for(int i = 0; aux[i+1] != pontoB; i++){
			caminho += this.distancia[aux[i]][aux[i+1]];
			if(caminho<this.open[aux[i+1]])
				caminho = open[aux[i+1]] + this.vT[aux[i+1]];
			else
				caminho += this.vT[aux[i+1]];			
//System.out.println("Nodo:"+ aux[i]+" caminho: "+caminho +"vT:"+this.vT[aux[i]]+"dist:"+aux[i]+"->"+aux[i+1]+": "+this.distancia[aux[i]][aux[i+1]]);
		}
		
//System.out.println("*"+caminho);
		
		gene[veiculo][pontoA] = novo;
		gene[veiculo][novo] = pontoB;
		
		//Tenta adicionar distância do pontoA -> novo + duração no novo
		caminho += this.distancia[pontoA][novo];
		
		if(caminho < this.open[novo]){
			caminho = this.open[novo];
		}
		caminho += vT[novo];
		
		//Tenta adicionar distância do novo -> PontoB + duração no pontoB
		caminho += this.distancia[novo][pontoB];

        if(caminho < open[pontoB]){
        	caminho = open[pontoB];
        }
        caminho += vT[pontoB];
		
        /*
		distActual -= this.distancia[pontoA][pontoB];
		distActual += this.distancia[pontoA][novo];
		distActual += this.distancia[novo][pontoB];
        distActual += vT[novo];
        */
        distActual = caminho;
        
		return distActual;
	}
	
	/*
	 * Remove o nodo "rem" entre os nodos "pontoA" e "pontoB"
	 */
	public double remove(Integer rem, double distActual, Integer[][]gene, Integer veiculo){
		int pontoA = 0;
		int pontoB = gene[veiculo][rem];
		//Calcula nodo anterior ao rem
		for(int nodo = 0; nodo != rem; nodo = gene[veiculo][nodo]) {
					pontoA = nodo;
		}
		//poe o destino do pontoA como pontoB
		gene[veiculo][pontoA] = pontoB;
		//poe o destino de rem como null para sinalizar que j√° nao esta no caminho
		gene[veiculo][rem] = null;
		//Subtrai √† distancia os valores das liga√ßoes com rem
		distActual -= this.distancia[pontoA][rem];
		distActual -= this.distancia[rem][pontoB];
                distActual -= this.vT[rem];
		//adiciona √† distancia os valores da liga√ß√£o pontoA para pontoB
		distActual += this.distancia[pontoA][pontoB];
		return distActual;
	}
	
	/*
	 * A fun√ß√£o de fitness atribui a um cromossoma, a sua aptid√£o no universo existente.
	 * Decisoes: fitness est√° dependente apenas do score.
	 */
	public double fitness(Integer[][] gene){
		double score = 0;
		ArrayList<Integer> livre = new ArrayList<Integer>(numNodos) ;		
		for(int j = 0; j<numNodos-1; j++){
			livre.add(j);
		}
		//Para cada veiculo
		for(int i = 0, k = 0; i<this.veiculos; i++){
			//Cada nodo do seu caminho
			for(int j=0; j<this.numNodos-1; j=gene[i][j]){
				//indice do nodo na lista dos livres
				k = livre.indexOf(j);
				//Se j n√£o for repetido ent√£o adiciona o seu score e tira-o dos livres.
				if(k != -1) {
					livre.remove(k);
					score += nodoScore[j];
				}
			}
		}
		return score;
	}
	
	public void reler(){
		this.numNodos=li.numNodos;
		this.nodoX = li.nodoX;
		this.nodoY = li.nodoY;
		this.nodoScore = li.nodoScore;
		this.numNodos = li.numNodos;
		this.distanciaMax = li.distanciaMax;
		this.veiculos = li.veiculos;
		this.vT = li.vT;
		this.close = li.close;
		this.open = li.open;
	}
}
