package GrafoMatriz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.*;

//definição de uma estrutura Matriz de Adjacência para armezanar um grafo
public class TGrafoND {
	// Atributos Privados
	private int tipoGrafo;
	private	int n; // quantidade de vértices
	private	int m; // quantidade de arestas
	private	float adj[][]; //matriz de adjacência
	private float d[][];
	private float infinito = Float.POSITIVE_INFINITY;
	private String[] local;
	private String[] tipo;
	private int contador;
	// Métodos Públicos
	// CONTRUIR GRAFO A PARTIR DE ARQUIVO
	public TGrafoND(String nomeArquivo){
		
        
		try {
            // Abrir o arquivo e criar um scanner para leitura
            File arquivo = new File(nomeArquivo);
            Scanner scanner = new Scanner(arquivo);

            // Lê o tipo de grafo e o número de vértices
            tipoGrafo = scanner.nextInt();
            n = scanner.nextInt();

            // Inicializa a matriz de adjacência e os arrays de vértices e tipos
            this.adj = new float[n][n];
            this.local = new String[n];
            this.tipo = new String[n];

            // Preenche a matriz com "infinito"
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    this.adj[i][j] = infinito;
                }
            }

            // Lê os vértices e seus tipos
            scanner.nextLine(); // Avança para a próxima linha
            for (int i = 0; i < n; i++) {
                // Lê a linha inteira do arquivo
                String linha = scanner.nextLine();

                // Divide a linha em partes: índice, identidade (que pode ser composta), número de tipos e os tipos
                Scanner linhaScanner = new Scanner(linha);

                // Lê o índice do vértice
                int a = linhaScanner.nextInt();

                // Lê a identidade do vértice, até encontrar o número de tipos
                StringBuilder identidadeBuilder = new StringBuilder();
                while (!linhaScanner.hasNextInt()) {
                    identidadeBuilder.append(linhaScanner.next()).append(" ");
                }
                String identidade = identidadeBuilder.toString().trim(); // Remove espaços extras no final

                this.local[a] = identidade; // Armazena o nome composto

                // Lê o número de tipos
                int qtd = linhaScanner.nextInt();

                // Lê os tipos associados ao vértice
                StringBuilder tiposSB = new StringBuilder();
                for (int j = 0; j < qtd; j++) {
                    tiposSB.append(linhaScanner.next()).append(" ");
                }

                this.tipo[a] = tiposSB.toString().trim(); // Armazena os tipos no array
                linhaScanner.close();
            }

            // Lê o número de arestas
            int A = scanner.nextInt();
            
            for (int i = 0; i < A; i++) {
				//Lê a origem e destino do vértice
                int origem = scanner.nextInt();
                int destino = scanner.nextInt();
                float peso = Float.parseFloat(scanner.nextLine());
                this.insereA(origem, destino, peso);
            }

            // Fechar o scanner
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
        }

	}
	
	

	public void insereV(String l, String t){
	    float adjac[][] = new float[n+1][n+1]; //Criar auxiliares
	    String[] localadj = new String[n+1];
	    String[] tipoadj = new String[n+1];
	    for(int i = 0; i<n; i++){
	        localadj[i] = local[i];
	        tipoadj[i] = tipo[i];
	        for(int j = 0; j< n; j++ ){
	            adjac[i][j] = adj[i][j];
	        }
	    }
	    localadj[n] = l;// Adiciona o local e tipos do novo vértice aos auxiliares
	    tipoadj[n] = t;
	    for(int i = 0; i<n+1; i++){
	        adjac[i][n] = infinito;
	        adjac[n][i] = infinito;
	 
	    }
	    tipo = tipoadj;// Auxiliares viram os globais
	    local = localadj;
	    n++;
	    adj = adjac;
	    
	}

	// Insere uma aresta no Grafo tal que
	// v é adjacente a w
	public void insereA(int v, int w, float rot) {
	    // testa se nao temos a aresta
	    if(adj[v][w] == infinito && adj[w][v] == infinito ){
	        adj[v][w] = rot;
            adj[w][v] = rot;
	        m++; // atualiza qtd arestas
	    }
	}
	
	// remove uma aresta v->w do Grafo	
	public void removeA(int v, int w) {
	    // testa se temos a aresta
	    if(adj[v][w] != infinito && adj[w][v] != infinito ){
	        adj[v][w] = infinito;
            adj[w][v] = infinito;
	        m--; // atualiza qtd arestas
	    }
	}

	public void removeVertice(int v) {
    // Verifica se o vértice está dentro dos limites
    if (v < 0 || v >= n) {
        System.out.println("Vértice inválido!");
        return;
    }

    // Reduz o número total de vértices
    int novoN = n - 1;

    // Criar novas matrizes e arrays para o grafo reduzido
    float[][] novaAdj = new float[novoN][novoN];
    String[] novoLocal = new String[novoN];
    String[] novoTipo = new String[novoN];

    // Índices para preencher as novas matrizes
    int novaLinha = 0;
    int novaColuna;

    // Percorre todas as linhas e colunas do grafo original
    for (int i = 0; i < n; i++) {
        // Pula o vértice a ser removido
        if (i == v) continue;

        novaColuna = 0;
        for (int j = 0; j < n; j++) {
            // Pula o vértice a ser removido
            if (j == v) continue;

            // Copia os valores da matriz de adjacência
            novaAdj[novaLinha][novaColuna] = adj[i][j];
            novaColuna++;
        }

        // Copia as informações de local e tipo
        novoLocal[novaLinha] = local[i];
        novoTipo[novaLinha] = tipo[i];
        novaLinha++;
    }

    // Atualiza os atributos do grafo
    adj = novaAdj;
    local = novoLocal;
    tipo = novoTipo;
    n = novoN;

    // Recalcula o número de arestas
    m = 0;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (adj[i][j] != infinito) {
                m++;
            }
        }
    }
}

	private boolean naLista(int a, int[] lista) {
        for (int i = 0; i < contador; i++) {
            if (lista[i] == a) {
                return true; // Vértice já visitado
            }
        }
        return false; // Vértice não visitado
    }

	public int[] profundidade(int a, int[] lista, float[][] mat) {
        if (!naLista(a, lista)) {
            lista[contador] = a; // Marca o vértice como visitado
            contador++; // Incrementa o contador de visitados

            // Visita todos os vértices adjacentes não visitados
            for (int i = 0; i < n; i++) {
                if (mat[a][i] != infinito) {
                    profundidade(i, lista, mat);
                }
            }
        }
        return lista;
    }

    // Método para verificar se o grafo é conexo
    public int ehConexo() {
        contador = 0; // Reinicia o contador de vértices visitados
		int[] lista1 = new int[n];
        profundidade(0,lista1,adj); // Inicia a busca em profundidade a partir do vértice 0
        if(contador == n){
			return 0;
		}
		return 1; // Verifica se todos os vértices foram visitados
    }
    
    private int[][] Floyd(){//O código de floyd retorna a matriz rotas e altera a matriz global distâncias
		d = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            d[i][j] = adj[i][j];
            }
        }
		int[][] rot = new int[n][n];
		for (int i =0; i< n; i++){
			for(int j = 0; j<n; j++){
				if (adj[i][j]<infinito){
					rot[i][j] = j;
				}else{
					rot[i][j] = 0;
				}
			}
		}

		for(int k = 0; k<n; k++){
			for(int i= 0; i<n; i++){
				for(int j=0; j<n; j++){
					if(i!=j && (d[i][k]+d[k][j]<d[i][j])){
						d[i][j] = d[i][k]+d[k][j];
						rot[i][j] = rot[i][k];
					}
				}
			}
		}
		return rot;

	}

	public void achaRotaFloyd(int i, int j){//Chama a função floyd e mostra a distância e a rota entre dois pontos
		int[][] r = Floyd();
		float u = d[i][j]; // printar se o professor pedir
		System.out.print("Rota do ponto "+ i+ " ao ponto "+j+" : ");
		while (i!=j){
			System.out.print(i+" ");
			i = r[i][j];
		}
		System.out.println(i);
		System.out.println("Distância entre os dois pontos: "+ u);
	}
	
	public boolean isHamiltoniano() {
        boolean[] visitado = new boolean[n];
        int[] caminho = new int[n];
        
        // Começa do vértice 0
        visitado[0] = true;
        caminho[0] = 0;
        
        if (encontrarCicloHamiltoniano(caminho, visitado, 1)) {
            System.out.println("Ciclo Hamiltoniano encontrado:");
            for (int i = 0; i < n; i++) {
                System.out.print(caminho[i] + " -> ");
            }
            System.out.println(caminho[0]); // Volta ao início
            return true;
        }
        
        System.out.println("Não é um grafo hamiltoniano");
        return false;
    }
    
    private boolean encontrarCicloHamiltoniano(int[] caminho, boolean[] visitado, int pos) {
        // Se todos os vértices estão incluídos no caminho
        if (pos == n) {
            // Verifica se existe aresta do último vértice para o primeiro
            return adj[caminho[pos-1]][caminho[0]] != infinito;
        }
        
        // Tenta diferentes vértices como próximo candidato
        for (int v = 0; v < n; v++) {
            // Verifica se o vértice pode ser adicionado ao ciclo
            if (isCandidatoValido(v, caminho[pos-1], visitado)) {
                caminho[pos] = v;
                visitado[v] = true;
                
                if (encontrarCicloHamiltoniano(caminho, visitado, pos + 1))
                    return true;
                
                // Se não levar à solução, remove do caminho
                visitado[v] = false;
            }
        }
        return false;
    }
    
    private boolean isCandidatoValido(int v, int ultimoVertice, boolean[] visitado) {
        // Verifica se o vértice é adjacente ao último vértice no caminho
        // e não foi visitado
        return adj[ultimoVertice][v] != infinito && !visitado[v];
    }
    
    // Verifica se o grafo é euleriano
    public boolean isEuleriano() {
        // Para ser euleriano, todos os vértices devem ter grau par
        // e o grafo deve ser conexo
        
        if (ehConexo()==1) {
            System.out.println("O grafo não é euleriano: não é conexo");
            return false;
        }
        
        // Verifica se todos os vértices têm grau par
        for (int i = 0; i < n; i++) {
            int grau = 0;
            for (int j = 0; j < n; j++) {
                if (adj[i][j] != infinito) {
                    grau++;
                }
            }
            if (grau % 2 != 0) {
                System.out.println("O grafo não é euleriano: o vértice " + i + 
                                 " tem grau ímpar (" + grau + ")");
                return false;
            }
        }
        
        System.out.println("O grafo é euleriano");
        return true;
    }
    
    private void bfs(int start, boolean[] visitado) {
        Queue<Integer> fila = new LinkedList<>();
        fila.add(start);
        visitado[start] = true;
        
        while (!fila.isEmpty()) {
            int v = fila.poll();
            for (int i = 0; i < n; i++) {
                if (adj[v][i] != infinito && !visitado[i]) {
                    fila.add(i);
                    visitado[i] = true;
                }
            }
        }
    }
    
   public void filtrarGrafoPorTipo(String tipoFiltro) {
    System.out.println("\nGrafo filtrado por tipo: " + tipoFiltro);
    
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            // Se o vértice de origem ou destino não tiver o tipo, 
            // imprime infinito, senão imprime o valor original da adjacência
            if (!tipo[i].toLowerCase().contains(tipoFiltro.toLowerCase()) || 
                !tipo[j].toLowerCase().contains(tipoFiltro.toLowerCase())) {
                System.out.print(infinito + " ");
            } else {
                System.out.print((adj[i][j] != infinito ? adj[i][j] : infinito) + " ");
            }
        }
        System.out.println(); // Pula linha após cada linha da matriz
    }
}
	// Apresenta o Grafo contendo
	// número de vértices, arestas
	// e a matriz de adjacência obtida	
	public void show() {
	    System.out.println("n: " + n );
	    System.out.println("m: " + m );
	    for( int i=0; i < n; i++){
	    	System.out.print("\n");
	        for( int w=0; w < n; w++)
	            if(adj[i][w] != infinito)
	            	System.out.print("Adj[" + i + "," + w + "]= " + adj[i][w]+ " ");
	            else System.out.print("Adj[" + i + "," + w + "]=" + infinito +" ");
	    }
		
	    System.out.println("\n\nfim da impressao do grafo." );
	}

	 public void gravarGrafo(String nomeArquivo) {
		try {
			// Cria um BufferedWriter para gravar no arquivo, no modo de sobrescrita
			BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo));
	
			// Escreve o tipo de grafo
			escritor.write(tipoGrafo + "\n");
	
			// Escreve o número de vértices
			escritor.write(n + "\n");
	
			// Escreve a lista de vértices com suas identidades e tipos
			for (int i = 0; i < n; i++) {
				// Monta a linha do vértice no formato: índice "identidade" qtdTipos "tipo1" "tipo2" ...
				StringBuilder linha = new StringBuilder(i + " " + local[i] + " " + tipo[i].split(" ").length);
				
				// Para cada tipo de vértice, adicionar entre aspas
				for (String tipoVertice : tipo[i].split(" ")) {
					linha.append(" ").append(tipoVertice);
				}
	
				escritor.write(linha.toString() + "\n");
			}
	
			// Escreve o número de arestas
			escritor.write(m + "\n");
	
			// Escreve cada aresta no formato: origem destino peso
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) { // Para evitar duplicação de arestas, comece de `i + 1`
					if (adj[i][j] != infinito) {
						escritor.write(i + " " + j + " " + adj[i][j] + "\n");
					}
				}
			}
	
			// Fecha o BufferedWriter
			escritor.close();
	
			System.out.println("Grafo gravado com sucesso no arquivo: " + nomeArquivo);
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao gravar o arquivo.");
			e.printStackTrace();
		}
    }
	public void mostrarConteudoArquivo(String nomeArquivo) {
    try {
        // Cria um BufferedReader para ler o arquivo
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));

        String linha;
        // Lê o arquivo linha por linha
        while ((linha = leitor.readLine()) != null) {
            // Exibe a linha lida
            System.out.println(linha);
        }

        // Fecha o BufferedReader
        leitor.close();

    } catch (IOException e) {
        System.out.println("Ocorreu um erro ao ler o arquivo.");
        e.printStackTrace();
    }
}
}
