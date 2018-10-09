import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.lang.*;


public class Servidor extends UnicastRemoteObject implements InterfaceGerenciamento{

	public Servidor()throws RemoteException{
		super();
	}

	public void add_usuario(Usuario user) throws RemoteException{

        System.out.println("ADICIONAR USUARIO\n");

	    try {
            BufferedWriter banco = new BufferedWriter(new FileWriter("banco.txt", true));

            banco.append(user.getNome() + ", " + user.getRg() + ", " + user.getMatricula() + ", " + user.getEndereco() + ", " + user.getSenha() + "\n");

            banco.close();

            return;
        }catch(Exception e){
	        e.printStackTrace();
        }
	}

	public String listagem() throws RemoteException{

        System.out.println("LISTAR USUARIOS\n");

		String retorno = "--- USUARIOS ATIVOS ---\n\n";

		try{

			BufferedReader banco = new BufferedReader(new FileReader("banco.txt"));

			while(banco.ready()){
				String[] user = banco.readLine().split(", ");
				retorno += "Nome: " + user[0] + "\nRG: " + user[1] + "\nMatricula: " +  user[2] + "\nEndereço: Rua/Av" + user[3] + ", Numero " + user[4] + ", Bairro " + user[5] + "\n\n";
			}
			banco.close();

		}catch(Exception e){
			e.printStackTrace();
		}

		retorno+="---------------------------------------------------------\n\n";

		return retorno;

	}

	public boolean login(String rg, String senha){

        System.out.println("FAZENDO LOGIN\n");

        try{

            BufferedReader banco = new BufferedReader(new FileReader("banco.txt"));
            while(banco.ready()){
                String[] user = banco.readLine().split(", ");

                if(user[1].equals(rg) && user[6].equals(senha)){
                    banco.close();
                    return true;
                }
            }

            banco.close();
            return false;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;

    }

    public boolean confereRg(String rg){

        System.out.println("CONFERINDO RG\n");

        try{

            BufferedReader banco = new BufferedReader(new FileReader("banco.txt"));
            while(banco.ready()){
                String[] user = banco.readLine().split(", ");

                if(user[1].equals(rg)){
                    banco.close();
                    return true;
                }
            }

            banco.close();
            return false;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;

    }

	public void alterar_usuario(Usuario usuario, String rg1) throws RemoteException{

        System.out.println("ALTERAR USUARIO\n");

	    try {
	        File banco1 = new File("banco1.txt");

	        FileWriter writer =  new FileWriter(banco1);

            BufferedReader banco = new BufferedReader(new FileReader("banco.txt"));

            BufferedWriter buffer = new BufferedWriter(writer);

            while (banco.ready()) {

                String[] user = banco.readLine().split(", ");

                if (user[1].equals(rg1)) {

                    if (!usuario.getNome().equals("-")) {
                        user[0] = usuario.getNome();
                    }
                    if (!usuario.getRg().equals("-")) {
                        user[1] = usuario.getRg();
                    }
                    if (!usuario.getMatricula().equals("-")) {
                        user[2] = usuario.getMatricula();
                    }
                    if (!usuario.getRua().equals("-")) {
                        user[3] = usuario.getRua();
                    }
                    if (!usuario.getNumero().equals("-")) {
                        user[4] = usuario.getNumero();
                    }
                    if (!usuario.getBairro().equals("-")) {
                        user[5] = usuario.getBairro();
                    }
                }

                buffer.append(user[0] + ", " + user[1] + ", " + user[2] + ", " + user[3] + ", " + user[4] + ", " + user[5] + ", " + user[6] + "\n");

            }

            System.out.println("USUARIO ALTERADO\n");

            banco.close();
            buffer.close();
            File banco2 = new File("banco.txt");
            banco1.renameTo(banco2);
        }catch (Exception e){

        }

		return;
	}
	public String view_usuario(String rg) throws RemoteException{

        System.out.println("VER USUARIO\n");

		try{

			String retorno = "--- USUARIO ---\n\n";

			BufferedReader banco = new BufferedReader(new FileReader("banco.txt"));
			while(banco.ready()){
				String[] user = banco.readLine().split(", ");
				if(user[1].equals(rg)){
					retorno += "Nome: " + user[0] + "\nRG: " + user[1] + "\nMatricula: " +  user[2] + "\nEndereço: " + user[3] + ", " + user[4] + ", " + user[5] + "\n\n";
				}
			}

            banco.close();

			if(retorno.equals("--- USUARIO ---\n\n")){
				return "Não foram encontrados usuarios na base de dados com o RG especificado!\n";
			}else{
			    retorno += "----------------------------------------------------";
				return retorno;
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return "Não foram encontrados usuarios na base de dados com o RG especificado!\n";
	}
	public void alterar_senha(String rg, String senha) throws RemoteException{

        System.out.println("ALTERARA SENHA\n");

        try {
            File banco1 = new File("banco1.txt");

            FileWriter writer =  new FileWriter(banco1);

            BufferedReader banco = new BufferedReader(new FileReader("banco.txt"));

            BufferedWriter buffer = new BufferedWriter(writer);

            while (banco.ready()) {

                String[] user = banco.readLine().split(", ");

                if (user[1].equals(rg)) {
                    user[6] = senha;
                }

                buffer.append(user[0] + ", " + user[1] + ", " + user[2] + ", " + user[3] + ", " + user[4] + ", " + user[5] + ", " + user[6] + "\n");

            }

            System.out.println("SENHA ALTERADA\n");

            banco.close();
            buffer.close();
            File banco2 = new File("banco.txt");
            banco1.renameTo(banco2);

        }catch (Exception e){

        }

        return;
	}
	public boolean verifica_senha(String senha, String confirmaSenha) throws RemoteException{

        System.out.println("VERIFICAR SENHA\n");

        boolean senhas = senha.equals(confirmaSenha);
        return senhas;

	}

	public static void main(String args[]){

		try{
			Servidor obj = new Servidor();
			Naming.rebind("Serv", obj);
			System.out.println("Server ON !");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}