import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;


public interface InterfaceGerenciamento extends Remote{
    void add_usuario(Usuario usuario) throws RemoteException; //ok
    String listagem() throws RemoteException; //ok
    void alterar_usuario(Usuario usuario, String rg1) throws RemoteException; //ok
    boolean login(String rg, String senha) throws RemoteException; //ok
    boolean confereRg(String rg) throws RemoteException; //ok
    String view_usuario(String rg) throws RemoteException; //ok
    void alterar_senha(String rg, String senha) throws RemoteException;
    boolean verifica_senha(String senha, String confirmaSenha) throws RemoteException; //ok
}