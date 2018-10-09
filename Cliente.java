import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class Cliente{

	static InterfaceGerenciamento obj = null;

	public static void main(String[] args) {

		int opcao = 1;
        boolean senhas = true;
		Scanner scanner = new Scanner(System.in);

		try{
			obj = (InterfaceGerenciamento) Naming.lookup("Serv");

            int escolha = 0;

			do {

                System.out.println("--- JAVA RMI ---\n");
                System.out.println("1 - Login\n");
                System.out.println("2 - Cadastro\n");
                escolha = scanner.nextInt();
                scanner.nextLine();
                System.out.println("\n");

                if (escolha == 1) {

                    String loginRg = "";
                    String loginSenha = "";
                    do {
                        System.out.println("Digite seu RG: ");
                        loginRg = scanner.nextLine();
                        System.out.println("\n ");
                        System.out.println("Digite sua senha: ");
                        loginSenha = scanner.nextLine();
                        System.out.println("\n ");
                        if(obj.login(loginRg, loginSenha) == false){
                            System.out.println("Dados incorretos, tente novamente!\n");
                        }
                    }while(obj.login(loginRg, loginSenha) == false);

                    String rgCorrente = loginRg;
                    opcao = 1;

                    while(opcao != 0){

                        System.out.println("--- MENU PRINCIPAL ---\n");
                        System.out.println("1 - Adicionar usuario\n");
                        System.out.println("2 - Listar usuarios\n");
                        System.out.println("3 - Alterar Cliente\n");
                        System.out.println("4 - Visualizar Cliente\n");
                        System.out.println("5 - Alterar senha\n");
                        System.out.println("6 - Remover usuario\n");
                        System.out.println("0 - Remover usuario\n");
                        opcao = scanner.nextInt();
                        scanner.nextLine();

                        switch(opcao){

                            case 1:

                                String nome, rg, matricula, rua, numero, bairro, senha, confirmaSenha;

                                System.out.println("\nDigite o nome do novo usuario: ");
                                nome = scanner.nextLine();
                                System.out.println("\n ");

                                boolean confereRg = true;

                                do {
                                    System.out.println("Digite o RG do novo usuario: ");
                                    rg = scanner.nextLine();
                                    System.out.println("\n");
                                    confereRg = obj.confereRg(rg);
                                    if(confereRg == true){
                                        System.out.println("O RG cadastrado já existe no sistema, tente novamente.\n");
                                    }
                                }while (confereRg == true);

                                System.out.println("Digite a matrícula do novo usuario: ");
                                matricula = scanner.nextLine();
                                System.out.println("\n");

                                System.out.println("Digite a rua do novo usuario: ");
                                rua = scanner.nextLine();
                                System.out.println("\n");

                                System.out.println("Digite o numero da residencia do novo usuario: ");
                                numero = scanner.nextLine();
                                System.out.println("\n");

                                System.out.println("Digite o bairro do novo usuario: ");
                                bairro = scanner.nextLine();
                                System.out.println("\n");

                                do {
                                    System.out.println("Digite a senha do novo usuario: ");
                                    senha = scanner.nextLine();
                                    System.out.println("\n");
                                    System.out.println("Confirme a senha nome do novo usuario: ");
                                    confirmaSenha = scanner.nextLine();
                                    System.out.println("\n");
                                    senhas = obj.verifica_senha(senha, confirmaSenha);
                                    if (senhas == true){
                                        Usuario user = new Usuario(nome, rg, matricula, rua, numero, bairro, senha);
                                        obj.add_usuario(user);
                                        break;
                                    }else {
                                        System.out.println("As senhas não conferem, tente novamente.\n");
                                    }
                                }while(senhas==false);


                                break;

                            case 2:
                                System.out.println(obj.listagem());
                                break;

                            case 3:

                                String rg1 = "";
                                String senha1 = "";
                                confereRg = true;

                                do {
                                    System.out.println("Digite o RG do usuario a ser alterado: (Digite - para usar seu proprio RG)");
                                    rg1 = scanner.nextLine();
                                    if(rg1.equals("-")){
                                        rg1 = rgCorrente;
                                    }
                                    System.out.println("Digite a senha do usuario a ser alterado: ");
                                    senha1 = scanner.nextLine();
                                    if(obj.login(rg1, senha1) == false){
                                        System.out.println("Dados incorretos, tente novamente!\n");
                                    }
                                }while(obj.login(rg1, senha1) == false);

                                System.out.println("\nPara não alterar dados, digite -");

                                System.out.println("Digite o novo nome do usuario: ");
                                nome = scanner.nextLine();
                                System.out.println("\n ");

                                do {
                                    System.out.println("Digite o novo RG do usuario: ");
                                    rg = scanner.nextLine();
                                    System.out.println("\n");
                                    confereRg = obj.confereRg(rg);
                                    if(confereRg == true){
                                        System.out.println("O RG cadastrado já existe no sistema, tente novamente.\n");
                                    }
                                }while (confereRg == true);

                                System.out.println("Digite a nova matrícula do usuario: ");
                                matricula = scanner.nextLine();
                                System.out.println("\n");

                                System.out.println("Digite a nova rua do usuario: ");
                                rua = scanner.nextLine();
                                System.out.println("\n");

                                System.out.println("Digite o novo numero da residencia do usuario: ");
                                numero = scanner.nextLine();
                                System.out.println("\n");

                                System.out.println("Digite o novo bairro do usuario: ");
                                bairro = scanner.nextLine();
                                System.out.println("\n");

                                Usuario user = new Usuario(nome, rg, matricula, rua, numero, bairro, "-");
                                obj.alterar_usuario(user, rg1);

                                if(!rg.equals("-") && rg1.equals(rgCorrente)){
                                    opcao = 0;
                                    escolha = 3;
                                }

                                break;

                            case 4:

                                String rg2 = "";
                                System.out.println("Digite o RG do usuario a ser visualizado: ");
                                rg2 = scanner.nextLine();

                                try{

                                    System.out.println(obj.view_usuario(rg2));

                                }catch(Exception e){
                                    e.printStackTrace();
                                }

                                break;

                            case 5:
                                String novaSenha = "";
                                senhas = false;

                                do {
                                    System.out.println("Digite o RG do usuario para alterar a senha: (Digite - para usar seu proprio RG)");
                                    rg1 = scanner.nextLine();
                                    if(rg1.equals("-")){
                                        rg1 = rgCorrente;
                                    }
                                    System.out.println("Digite a senha do usuario a ser alterado: ");
                                    senha1 = scanner.nextLine();
                                    if(obj.login(rg1, senha1) == false){
                                        System.out.println("Dados incorretos, tente novamente!\n");
                                    }
                                }while(obj.login(rg1, senha1) == false);

                                do {
                                    System.out.println("Digite a nova senha do usuario: ");
                                    novaSenha = scanner.nextLine();
                                    System.out.println("\n");

                                    System.out.println("Confirme a nova senha nome do usuario: ");
                                    confirmaSenha = scanner.nextLine();
                                    System.out.println("\n");

                                    senhas = obj.verifica_senha(novaSenha, confirmaSenha);
                                    if (senhas == false) {
                                        System.out.println("As senhas não conferem, tente novamente.\n");
                                    }
                                }while(senhas==false);

                                obj.alterar_senha(rg1, novaSenha);
                                System.out.println("Senha alterada com sucesso!\n");

                                if(rg1.equals("-") || rg1.equals(rgCorrente)){
                                    opcao = 0;
                                    escolha = 3;
                                }

                                break;

                            case 6:

                                do {
                                    System.out.println("Digite o RG do usuario que deseja excluir: (Digite - para usar seu proprio RG)");
                                    rg1 = scanner.nextLine();
                                    if(rg1.equals("-")){
                                        rg1 = rgCorrente;
                                    }
                                    System.out.println("Digite a senha do usuario a ser excluido: ");
                                    senha1 = scanner.nextLine();
                                    if(obj.login(rg1, senha1) == false){
                                        System.out.println("Dados incorretos, tente novamente!\n");
                                    }
                                }while(obj.login(rg1, senha1) == false);

                                //obj.remove_usuario(rg1);
                                System.out.println("Usuario removido com sucesso com sucesso!\n");

                                if(rg1.equals("-") || rg1.equals(rgCorrente)){
                                    opcao = 0;
                                    escolha = 3;
                                }

                                break;

                            case 0:
                                escolha = 3;
                                break;


                        }


                    }


                }else if (escolha == 2) {

                    String nome, rg, matricula, rua, numero, bairro, senha, confirmaSenha;

                    System.out.println("\nDigite o nome do novo usuario: ");
                    nome = scanner.nextLine();
                    System.out.println("\n ");

                    boolean confereRg = true;

                    do {
                        System.out.println("Digite o RG do novo usuario: ");
                        rg = scanner.nextLine();
                        System.out.println("\n");
                        confereRg = obj.confereRg(rg);
                        if(confereRg == true){
                            System.out.println("O RG cadastrado já existe no sistema, tente novamente.\n");
                        }
                    }while (confereRg == true);

                    System.out.println("Digite a matrícula do novo usuario: ");
                    matricula = scanner.nextLine();
                    System.out.println("\n");

                    System.out.println("Digite a rua do novo usuario: ");
                    rua = scanner.nextLine();
                    System.out.println("\n");

                    System.out.println("Digite o numero da residencia do novo usuario: ");
                    numero = scanner.nextLine();
                    System.out.println("\n");

                    System.out.println("Digite o bairro do novo usuario: ");
                    bairro = scanner.nextLine();
                    System.out.println("\n");

                    do {
                        System.out.println("Digite a senha do novo usuario: ");
                        senha = scanner.nextLine();
                        System.out.println("\n");
                        System.out.println("Confirme a senha nome do novo usuario: ");
                        confirmaSenha = scanner.nextLine();
                        System.out.println("\n");
                        senhas = obj.verifica_senha(senha, confirmaSenha);
                        if (senhas == true){
                            Usuario user = new Usuario(nome, rg, matricula, rua, numero, bairro, senha);
                            obj.add_usuario(user);
                            break;
                        }else {
                            System.out.println("As senhas não conferem, tente novamente.\n");
                        }
                    }while(senhas==false);

                }else if(escolha == 0){
                    System.out.println("Até logo!\n");
                }else{
                    System.out.println("Tente novamente\n");
                }

            }while(escolha!=1 || escolha != 2 || escolha !=0);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

