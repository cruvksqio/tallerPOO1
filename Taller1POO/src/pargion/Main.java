/*
Constantino Bekios 21761616-6
Luis Molina 21564225-9
*/
package pargion;

import java.util.Scanner;





public class Main {

	public static void main(String[] args) {
		
		
		Scanner intro = new Scanner(System.in);
		
		System.out.println("Ingresa el menu a usar:");
		System.out.println("1.- Usuario");
		System.out.println("2.- Admin");
		System.out.println("3.- Salir");
		
		int opc;
		
		do {
			
			opc = intro.nextInt();
			
			if (opc!= 1 || opc!= 2 || opc!= 3) {
				System.out.println("pone la wea bien sipo");
				opc = intro.nextInt();
			}
			
		} while (opc!=1 || opc!=2 || opc!=3);
		
		intro.close();
		
		switch(opc) {
		
		case 1:
			//mostrarMenuUsuario();
			break;
		case 2:
			//mostrarMenuAdmin();
			break;
		case 3:
			System.out.println("Apagando sistema...");
			break;
		
		}
		

	}

}
