package test;

import dao.*;
import menu.*;

public class Main {

	public static void main(String[] args) {
		
		NegozioDao n = new NegozioDao();

		
		Menu m = new Menu();
		m.interagisci();
	}

}
