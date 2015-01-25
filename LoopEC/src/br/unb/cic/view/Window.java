package br.unb.cic.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

//Classe para exibi��o da imagem de entrada e da frase oculta
public class Window {

	//Declara��o do frame para a imagem e do label para exibi��o da imagem
	protected JFrame frame;
	protected JLabel label;
	
	//Construtor da classe
	public Window () {
		frame = new JFrame();
	}
	
	//M�todo para setar a dimens�o do frame e adicionar o label que vai conter a imagem ao frame
	public void InitializeImagem (Imagem imagem) {
		//Set da dimens�o do frame
		frame.setBounds(400, 200, imagem.GetWidthImagem(), imagem.GetHeightImagem());
		//Adi��o da imagem ao label
		label = new JLabel(new ImageIcon(imagem.GetImagem()));
		//Adi��o do label ao frame
		frame.getContentPane().add(label);
		//Ativa��o da visualiza��o do frame
		frame.setVisible(true);
	}
	
	//M�todo para exibir a mensagem oculta na imagem
	public void ExibeMensagem (String frase) {
		//Desativa��o do frame que cont�m a imagem de entrada
		frame.setVisible(false);
		//Painel para exibir a frase oculta na imagem
		JOptionPane.showMessageDialog(null, frase);
		//Mensagem informativa
		System.out.println("Programa encerrado com sucesso!");
	}
}
