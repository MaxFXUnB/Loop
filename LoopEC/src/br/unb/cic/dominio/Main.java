package br.unb.cic.dominio;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.unb.cic.database.GerenciamentoBD;
import br.unb.cic.view.Imagem;
import br.unb.cic.view.Window;

//Classe Main
public class Main {

	//Declaração dos objetos que vão operar o programa
	//Objeto Window para operar os métodos de visualização do frame com a imagem
	protected static Window janela;
	//Objeto Imagem para conter a imagem de entrada e seus atributos
	protected static Imagem imagem;
	//Objeto Controller para operar os métodos sobre a matriz tridimensional que vai representar a imagem
	protected static Controller control;
	
	//Método main
	public static void main(String[] args) throws IOException {
		
		//Construção do objeto Window
		janela = new Window();
		//Mensagem informativa
		System.out.println("Abrindo imagem...");
		//Construção do objeto Imagem
		imagem = new Imagem ();
		//Abertura da imagem de entrada
		imagem.AbreImagem ();
		//Ajuste do frame e exibição da imagem de entrada
		janela.InitializeImagem(imagem);
		//Construção do objeto Controller
		control = new Controller(imagem);
		//Elaboração da matriz tridimensional de acordo com a imagem de entrada
		control.MontaMatriz(imagem);
		//Elaboração da frase oculta na imagem de acordo com a matriz previamente inicializada e exibição da frase em um
		//painel de mensagem
		janela.ExibeMensagem(control.ElaboraFrase());
		//Encerramento do programa
		System.exit(0);
	}
}
