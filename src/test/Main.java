package test;

import dao.*;

public class Main {

	public static void main(String[] args) {
		
		NegozioDao n = new NegozioDao();
		System.out.println(n.mostraProdotti());
	}

}
