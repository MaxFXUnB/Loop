package br.unb.cic.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//Classe para conter a imagem de entrada e seus atributos
public class Imagem {

	//Declara��o do objeto de imagem
	protected BufferedImage imagem;
	
	//M�todo para abrir a imagem de entrada
	public BufferedImage AbreImagem () {
		//Bloco try-catch
		try {
			//Abertura da imagem existente do diretorio
			imagem = ImageIO.read(new File("src/br/unb/cic/view/testImage.bmp"));
			//Mensagem informativa
			System.out.println("Imagem aberta com sucesso!\n");
			//Tratamento de exce��o
		} catch (Exception e) {
			//Mensagem informativa
			System.out.println("Falha na abertura da imagem.");
		}
		//Retorno da imagem aberta
		return imagem;
	}
	
	//M�todo para capturar a largura da imagem
	public int GetWidthImagem () {
		return imagem.getWidth();
	}
	
	//M�todo para capturar a altura da imagem
	public int GetHeightImagem () {
		return imagem.getHeight();
	}
	
	//M�todo para retornar o objeto de imagem
	public BufferedImage GetImagem () {
		return imagem;
	}
	
	//M�todo para capturar os valores RGB de cada pixel que compoem a imagem de entrada
	public int[] GetRGBImagem (int x, int y, int width, int height, int [] rgbarray, int offset) {
		return imagem.getRGB(x, y, width, height, rgbarray, offset, width);
	}
}
