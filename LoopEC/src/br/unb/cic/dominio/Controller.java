package br.unb.cic.dominio;

import java.awt.Color;

import br.unb.cic.database.GerenciamentoBD;
import br.unb.cic.view.Imagem;

//Classe para processar a imagem de entrada e elaborar a frase oculta
public class Controller {

	//Declaração das variáveis e objetos para execução dos métodos
	//PixelPlane é um vetor que vai conter os pixels da imagem de entrada para posterior captura
	//dos valores RGB de cada pixel
	protected int[] pixelplane;
	//Matriz para representar a imagem em seus 3 planos
	protected int matriz[][][];
	//Variaveis de contagem e auxilio
	protected int i, j, k, decimal, up, contador, flag;
	//String para compor o valor binario de 8 bits
	protected String binario;
	//String para representar um caracter ascii
	protected String caracter;
	//String para representar a frase oculta na imagem
	protected String frase;
	//Objeto Color para armazenar as informações de cada pixel para posterior captura
	//dos valores RGB de cada pixel
	protected Color corRGB;
	//Objeto GerenciamentoBD para buscar nas tabelas SQL os dados previamente estipulados
	protected GerenciamentoBD gc;
	
	//Construtor da classe
	public Controller (Imagem imagem) {
		gc = new GerenciamentoBD();
		//PixelPlane é inicializado com os pixels da imagem de entrada com os respectivos valores RGB
		this.pixelplane = imagem.GetRGBImagem(0, 0, imagem.GetWidthImagem(), imagem.GetHeightImagem(), null, 0);
		//Inicialização da matriz
		this.matriz = new int [imagem.GetHeightImagem()][imagem.GetWidthImagem()][3];
		//Variavel contador para realizar a iteração do vetor PixelPlane
		this.contador = 0;
		//Variavel flag para indicar a presença do caracter NULO
		this.flag = 1;
		//Variavel up para verificar se foram obtidos 8 bits consecutivos
		this.up = 0;
		//Inicialização das strings
		this.binario = "";
		this.caracter = "";
		this.frase = "";
	}
	
	//Método para montar a matriz tridimensional de acordo com a imagem de entrada
	public void MontaMatriz (Imagem imagem) {
		//Mensagem informativa
		System.out.println("Criando matriz RGB a partir da imagem de entrada...");
		//Estruturas de repetição contada para varrer a matriz
		for (i = 0; i < imagem.GetHeightImagem(); i++) {
			for (j = 0; j < imagem.GetWidthImagem(); j++) {
				//Inicialização do objeto Color de acordo com cada pixel existente no array PixelPlane
				//previamente inicializado
				this.corRGB = new Color (pixelplane[contador]);
				//Envio dos valores R,G e B para cada plano da matriz de acordo com os valores existentes
				//no objeto Color que contém um determinado pixel em determinado instante
				this.matriz[i][j][0] = corRGB.getRed();
				this.matriz[i][j][1] = corRGB.getGreen();
				this.matriz[i][j][2] = corRGB.getBlue();
				//Incremento da variavel contador para iterar o array PixelPlane
				this.contador++;
			}
		}
		//Mensagem informativa
		System.out.println("Matriz RGB criada com sucesso!");
	}	
	
	//Método para processar a matriz previamente inicializada e elaborar a frase oculta na imagem
	public String ElaboraFrase () {
		//Mensagem informativa
		System.out.println("Elaborando a frase oculta na imagem, aguarde um momento por favor...");
		//Estruturas de repetição contada para varrer a matriz com indicação da flag de presença do NULO
		for (i = 333; i > 0 && flag != 0; i--) {
			for (j = 0; j < 500 && flag != 0; j++) {
				for (k = 2; k >= 0 && flag != 0; k--) {
					//Captura do valor decimal na ordem Blue, Green, Red, a partir do canto inferior esquerdo da matriz
					decimal = this.matriz[i][j][k];
					//Busca-se o valor do LSB do decimal lido na matriz de acordo com a tabela SQL
					//e concatena-se o LSB lido com o dado anteriormente armazenado na string binario
					binario = gc.BuscaBit (decimal) + binario;
					//Incremento da variavel up
					up++;
					//Caso a variavel up alcance o valor 8, indicará a presença de formação de um caracter ascii
					if (up == 8) {
						//Busca-se o caracter ascii na tabela SQL de acordo com o valor binario de 8 bits
						//previamente armazenado
						caracter = gc.BuscaAscii (binario);
						//Caso o caracter retornado seja "nulo" altera a flag para 0 indicando o final da frase
						if (caracter.charAt(0) == 'n' && caracter.charAt(1) == 'u' && caracter.charAt(2) == 'l' && caracter.charAt(3) == 'o') {
							flag = 0;
						} else {
							//Caso o caracter retornado seja um salto de linha ("\n"), adiciona-se o salto de linha à frase
							if (caracter.charAt(0) == '\\' && caracter.charAt(1) == 'n') {
								frase = frase + "\n";
							} 
							//Caso o caracter retornado seja "espaco", adiciona-se um " " (espaço) à frase
							else if (caracter.charAt(0) == 'e' && caracter.charAt(1) == 's' && caracter.charAt(2) == 'p' && caracter.charAt(3) == 'a' && 
									caracter.charAt(4) == 'c' && caracter.charAt(5) == 'o') { 	
								frase = frase + " ";
							}
							//Caso nenhuma das condições anteriores seja obedecida apenas adiciona o caracter retornado à frase
							else {
								frase = frase + caracter;
							}
						}
						//Reinicialização da variavel up e da string binario
						up = 0;
						binario = "";
					}
				}
			}
		}
		//Retorno da frase oculta na imagem
		return frase;
	}
	
}
