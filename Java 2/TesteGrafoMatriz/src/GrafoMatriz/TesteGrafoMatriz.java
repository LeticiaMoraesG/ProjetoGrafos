/*Giovana Ribeiro de Francisco - 10297494
Leila Akina Ino - 10402951
Letícia Moraes Gutierrez de Oliveira - 10400969*/ 

package GrafoMatriz;

import java.util.Scanner;

public class TesteGrafoMatriz {
	public static String converteVetorParaString(String[] vetor) {
        // Cria um StringBuilder
        StringBuilder sb = new StringBuilder();

        // Itera sobre o vetor de Strings
        for (String str : vetor) {
            sb.append(str); // Adiciona cada String ao StringBuilder
            sb.append(" "); // Adiciona um espaço entre as Strings (ou outro delimitador se quiser)
        }

        // Retorna a String final, removendo o último espaço extra (opcional)
        return sb.toString().trim(); // trim() remove espaços em branco no início e no fim
    }


	public static void main(String args[]) {
		//  chama o construtor para criar um grafo 4x4
		TGrafoND g = null;
		int opcao;
		do{
		//MENU
		System.out.print("\nEscolha uma das opções abaixo:\n1 - Ler dados do arquivo grafo.txt;\n" + //
						"2 - Gravar dados no arquivo grafo.txt\n" + //
						"3 - Inserir vértice\n" + //
						"4 - Inserir aresta\n" + //
						"5 - Remove vértice\n" + //
						"6 - Remove aresta\n" + //
						"7 - Mostrar conteúdo do arquivo\n" + //
						"8 - Mostrar grafo\n" + //
						"9 - Apresentar a conexidade do grafo e o reduzido\n" + //
						"10 - Encerrar a aplicação. \nOpção: ");
		
		Scanner s = new Scanner(System.in);
		opcao = s.nextInt();

		switch (opcao) {
			case 1:
				//OPÇÃO 1
				g = new TGrafoND("grafo.txt");
				System.out.println("Arquivo gravado com sucesso!");
				break;
			case 2:
				//OPÇÃO 2
				g.gravarGrafo("grafo.txt");
				break;
			case 3:
				//OPÇÃO 3
				String local;
				System.out.println("Digite o lugar:");
				s.nextLine();
				local = s.nextLine().trim();
				System.out.println("Digite a quantidade de tipos: ");
				int tiposnum = s.nextInt();
				s.nextLine();
				String[] tipos = new String[tiposnum];
				for(int i = 0; i< tiposnum ; i++){
					System.out.println("Tipo "+(i+1)+": ");
					tipos[i] = s.nextLine().trim();
				}
				String tipo = converteVetorParaString(tipos);
				g.insereV(local, tipo);
				break;
			case 4:
				//OPÇÃO 4
				System.out.println("Digite o vértice origem: ");
				int origem = s.nextInt();
				System.out.println("Digite o vértice destino: ");
				int destino = s.nextInt();
				System.out.println("Digite a distância: ");
				s.nextLine();
				float rot = Float.parseFloat(s.nextLine());
				g.insereA(origem, destino, rot);
				break;
			case 5:
				//OPÇÃO 5
				System.out.println("Digite o vértice que quer remover: ");
				int vertice = s.nextInt();
				g.removeVertice(vertice);
				break;
			case 6:
				//OPÇÃO 6
				System.out.println("Digite o vértice origem: ");
				int or = s.nextInt();
				System.out.println("Digite o vértice destino: ");
				int des = s.nextInt();
				g.removeA(or, des);
				break;
			case 7:
				//OPÇÃO 7
				g.mostrarConteudoArquivo("grafo.txt");
				break;
			case 8:
				//OPÇÃO 8
				g.show();
				break;
			case 9:
				//OPÇÃO 9
				if(g.ehConexo()==0){
					System.out.println("É conexo");
				}else{
					System.out.println("É desconexo");
				}
				break;
			case 10:
				//OPÇÃO 10
				System.out.println("Aplcação encerrando...");
				break;
		}
	}while(opcao != 10);

	}
	
	
}
