package GrafoMatriz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//definição de uma estrutura Matriz de Adjacência para armezanar um grafo
public class TGrafoND {
	// Atributos Privados
	private int tipoGrafo;
	private	int n; // quantidade de vértices
	private	int m; // quantidade de arestas
	private	float adj[][]; //matriz de adjacência
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

	public void removeVertice(int v){
		float[][] ad = new float[n-1][n-1]; //Criar auxiliares
		String[] localadj = new String[n-1];
	    String[] tipoadj = new String[n-1];
		int linhaNova = 0, colunaNova = 0;
		for(int i = 0; i<n; i++){
		    
			if(i==v){ //Caso o vértice encontrado for o vértice a ser removido
			    
				for(int a = 0; a<n; a++)
					removeA(i, a);
				continue;
			}else{
			    localadj[i] = local[i];
			    tipoadj[i] = tipo[i];
			}
			colunaNova = 0;
			for(int j= 0; j<n; j++){
				if(j==v){ //Caso o vértice encontrado for o vértice a ser removido
					removeA(i, j);
					continue;
				}
				ad[linhaNova][colunaNova] = adj[i][j];
				colunaNova++;
			}
			linhaNova++;
		}
		local = localadj;
		tipo = tipoadj;
		adj = ad;
		n = n-1;
			
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