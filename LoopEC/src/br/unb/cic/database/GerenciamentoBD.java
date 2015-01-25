package br.unb.cic.database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//Classe para gerenciamento dos dados sobre as tabelas SQL
public class GerenciamentoBD {

	//M�todo para criar uma tabela SQL que vai conter duas colunas: um valor decimal de 0 at� 255
	//e uma coluna com o respectivo valor do LSB (Zero ou Um) dos 8 bits que compoem esse decimal
	public void createTableDecBin (Connection c) {
		//Cria��o do statement SQL para futura execu��o do c�digo SQL
		Statement stmt = null;
		String status;
		//Bloco try-catch
		try {
			//Carregamento do driver JDBC para execu��o dos tratamentos SQL e abertura de conex�o com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/decbin.db");
			//Mensagem informativa
			System.out.println("Conexao aberta com sucesso!");
			
			//Inicializa��o do statement
			stmt = c.createStatement();
			
			//Cria��o do c�digo SQL a ser executado, cria��o da tabela
			String sqlcode = "CREATE TABLE DECBIN " +
							 "(DECIMAL INT PRIMARY KEY	NOT NULL," +
							 " BINARIO			  TEXT	NOT NULL)";
			//Execu��o do c�digo SQL e encerramento do statement e da conex�o com a tabela criada
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.close();
			//Mensagem informativa
			status = "Tabela criada com sucesso!";
			//Tratamento das poss�veis exce��es
		} catch (SQLException e) {
			status = e.getMessage();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
		} catch (Exception e) {
			status = e.getMessage();
		}
		//Mensagem informando uma poss�vel exce��o
		System.out.println(status);
	}
	
	//M�todo para criar uma tabela SQL que vai conter duas colunas: um valor binario de 8 bits e
	//o seu respetivo caracter ascii
	public void createTableAscii (Connection c) {
		//Cria��o do statement SQL para futura execu��o do c�digo SQL
		Statement stmt = null;
		String status;
		try {
			//Carregamento do driver JDBC para execu��o dos tratamentos SQL e abertura de conex�o com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/ascii.db");
			//Mensagem informativa
			System.out.println("Conexao aberta com sucesso!");
			
			//Inicializa��o do statement
			stmt = c.createStatement();
			
			//Cria��o do c�digo SQL a ser executado, cria��o da tabela
			String sqlcode = "CREATE TABLE ASCII " +
							 "(BINARIO 	TEXT	NOT NULL," +
							 " CARACTER TEXT	NOT NULL)";
			//Execu��o do c�digo SQL e encerramento do statement e da conex�o com a tabela criada
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.close();
			//Mensagem informativa
			status = "Tabela criada com sucesso!";
			//Tratamento das poss�veis exce��es
		} catch (SQLException e) {
			status = e.getMessage();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
		} catch (Exception e) {
			status = e.getMessage();
		}
		//Mensagem informando uma poss�vel exce��o
		System.out.println(status);
	}
	
	//M�todo para captura dos dados a serem inseridos na tabela SQL que vai conter um valor binario de 8 bits
	//e o seu respectivo caracter ascii, os dados s�o obtidos a partir de um arquivo .txt
	public void insertAscii (Connection c) {
		//Bloco try-catch
		try{
			//Inicializacao do arquivo para leitura
			FileReader file = new FileReader("data/tabelaascii.txt");
			Scanner scanner = new Scanner(file)
			.useDelimiter("\\||\\n");

			//Realiza��o da leitura do arquivo .txt e captura dos dados de acordo com os delimitadores
			while(scanner.hasNext()) {
				String binario = scanner.next();
				String trash = scanner.next();
				String caracter = scanner.next();
				//Inser��o dos dados na tabela anteriormente criada
				insertDBAscii (c, binario, caracter);
			}
			//Fechamento  do arquivo
			scanner.close();
		}
		//Tratamento de exce��o
		catch(FileNotFoundException e)
		{
			//Mensagem informativa
			System.out.println("O arquivo nao foi encontrado.");
			e.printStackTrace();
		}
	}
	
	//M�todo para inserir os dados capturados no m�todo anterior na tabela ASCII
	public void insertDBAscii (Connection c, String binario, String caracter) {
		//Cria��o do statement SQL para futura execu��o do c�digo SQL
		Statement stmt = null;
		String status;
		//Bloco try-catch
		try {
			//Carregamento do driver JDBC para execu��o dos tratamentos SQL e abertura de conex�o com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/ascii.db");
			c.setAutoCommit(false);
			
			//Inicializa��o do statement
			stmt = c.createStatement();
			
			//Cria��o do c�digo SQL a ser executado, inser��o dos dados na tabela
			String sqlcode = "INSERT INTO ASCII (BINARIO,CARACTER) " +
							 "VALUES ('" + binario + "', '" + caracter + "');";
			
			//Execu��o do c�digo SQL e encerramento do statement e da conex�o com a tabela
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.commit();
			c.close();
			//Mensagem informativa
			status = "Dados inseridos com sucesso!";
			//Tratamento das poss�veis exce��es
		} catch (SQLException e) {
			status = e.getMessage();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
		} catch (Exception e) {
			status = e.getMessage();
		}
		//Mensagem informando uma das poss�veis exce��es
		System.out.println(status);
	}
	
	//M�todo para captura dos dados a serem inseridos na tabela SQL que vai conter um valor deciaml de 0 at� 255
	//e o LSB dos 8 bits que compoem esse decimal (Zero ou Um), os dados s�o obtidos a partir de um arquivo .txt
	public void insertDecBin (Connection c) {
		//Bloco try-catch
		try{
			//Inicializacao do arquivo para leitura
			FileReader file = new FileReader("data/tabelabinaria.txt");
			Scanner scanner = new Scanner(file)
			.useDelimiter("\\||\\n");

			//Variaveis para conter os dados capturados
			int dec;
			String lsbx;
			//Realiza��o da leitura do arquivo .txt e captura dos dados
			while(scanner.hasNext()) {
				dec = scanner.nextInt();
				String trash = scanner.next();
				lsbx = scanner.next();
				//Inser��o dos dados na tabela SQL
				insertDBDecBin (c, dec, lsbx);
			}
			//Fechamento  do arquivo
			scanner.close();
		}
		//Tratamento de exce��o
		catch(FileNotFoundException e)
		{
			//Mensagem informativa
			System.out.println("O arquivo nao foi encontrado.");
			e.printStackTrace();
		}
	}
	
	//M�todo para inser��o dos dados capturados no m�todo anterior na tabela Decimal-Binario
	public void insertDBDecBin (Connection c, int decimal, String lsb) {
		//Cria��o do statement SQL para futura execu��o do c�digo SQL
		Statement stmt = null;
		String status;
		//Bloco try-catch
		try {
			//Carregamento do driver JDBC para execu��o dos tratamentos SQL e abertura de conex�o com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/decbin.db");
			c.setAutoCommit(false);
			
			//Inicializa��o do statement
			stmt = c.createStatement();
			
			//Cria��o do c�digo SQL a ser executado, inser��o dos dados na tabela
			String sqlcode = "INSERT INTO DECBIN (DECIMAL,BINARIO) " +
							 "VALUES ('" + decimal + "', '" + lsb + "');";
			
			//Execu��o do c�digo SQL e encerramento do statement e da conex�o com a tabela
			stmt.executeUpdate(sqlcode);
			stmt.close();
			c.commit();
			c.close();
			//Mensagem informativa
			status = "Dados inseridos com sucesso!";
			//Tratamento das poss�veis exce��es
		} catch (SQLException e) {
			status = e.getMessage();
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
		} catch (Exception e) {
			status = e.getMessage();
		}
		//Mensagem informando uma das poss�veis exce��es
		System.out.println(status);
	}
	
	//M�todo para buscar na tabela SQL o valor binario de 8 bits de um determinado valor decimal de 0 at� 255
	public String BuscaBit (int decimal) {
		//Cria��o da conex�o, statement e das variaveis auxiliares
		Connection c = null;
		Statement stmt = null;
		String bit = null;
		int dec;
		String status;
		//Bloco try-catch
		try {
			//Carregamento do driver JDBC para execu��o dos tratamentos SQL e abertura de conex�o com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/decbin.db");
			c.setAutoCommit(false);
			
			//Inicializa��o do statement
			stmt = c.createStatement();
			//Execu��o do comando SELECT sobre a tabela SQL e armazenagem do resultado em um ResulSet
			ResultSet rs = stmt.executeQuery("SELECT * FROM DECBIN;");
			//Leitura do ResultSet
			while (rs.next()) {
				//Captura do valor inteiro presente na coluna decimal
				dec = rs.getInt("decimal");
				//Caso o valor lido seja igual ao enviado para a fun��o, realiza a leitura do valor
				//da coluna binario
				if (dec == decimal) {
					//Leitura do campo da coluna binario
					String bitx = rs.getString("binario");
					//Caso a string existente no campo binario seja "Um" faz com que a variavel bit contenha "1"
					//caso contr�rio torna-a "0"
					if (bitx.compareTo("Um") == 1) {
						bit = "1";
					} else {
						bit = "0";
					}
				}
			}
			//Encerramento do statement e da conex�o e retorn do valor do bit (LSB, Zero ou Um)
			stmt.close();
			c.close();
			return bit;
			//Tratamento das poss�veis exce��es
		} catch (SQLException e) {
			status = e.getMessage();
			return (String) null;
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			return (String) null;
		} catch (Exception e) {
			status = e.getMessage();
			return (String) null;
		}
	}
	
	//M�todo para buscar o caracter ASCII de acordo um valor binario de 8 bits existente na tabela SQL
	public String BuscaAscii (String binariosource) {
		//Cria��o da conexao, statement  e variaveis auxiliares
		Connection c = null;
		Statement stmt = null;
		String caracter = "";
		String binx;
		String status;
		//Bloco try-catch
		try {
			//Carregamento do driver JDBC para execu��o dos tratamentos SQL e abertura de conex�o com a tabela
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bd/ascii.db");
			c.setAutoCommit(false);
			
			//Inicializa��o do statement
			stmt = c.createStatement();
			//Execu��o do comando SELECT sobre a tabela SQL e armazenagem do resultado em um ResulSet
			ResultSet rs = stmt.executeQuery("SELECT * FROM ASCII;");
			//Leitura do ResultSet
			while (rs.next()) {
				//Captura do valor na coluna binario
				binx = rs.getString("binario");
				//Caso o valor binario de 8 bits capturado na tabela seja igual ao enviado para o m�todo
				//ent�o retorna o caracter de acordo com a captura na coluna caracter
				if (binx.equals(binariosource)) {
					caracter = rs.getString("caracter");
				}
			}
			//Encerramento do statement e da conexao, e retorno do caracter
			stmt.close();
			c.close();
			return caracter;
			//Tratamento das possiveis exce��es
		} catch (SQLException e) {
			status = e.getMessage();
			return (String) null;
		} catch (ClassNotFoundException e) {
			status = e.getMessage();
			return (String) null;
		} catch (Exception e) {
			status = e.getMessage();
			return (String) null;
		}
	}
}
