import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class Usuario implements Serializable{

private String nome, rg, matricula, rua, numero, bairro, senha;

public Usuario (String _nome, String _rg, String _matricula, String _rua, String _numero, String _bairro, String _senha){
		this.nome = _nome;
		this.rg = _rg;
		this.matricula = _matricula;
		this.rua = _rua;
		this.bairro = _bairro;
		this.numero = _numero;
		this.senha = _senha;
}
	
    public String getNome() {

        return nome;
    }

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEndereco() {
		return (rua + ", " + numero + ", " + bairro);
	}

	public String getRua(){
		return rua;
	}

	public String getNumero(){
		return numero;
	}

	public String getBairro(){
		return bairro;
	}

	public void setEndereco(String rua, String numero, String bairro) {
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
	}

    public String getSenha() {
        return senha;
    }
}