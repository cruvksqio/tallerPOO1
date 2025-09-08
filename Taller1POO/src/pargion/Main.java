/*
Constantino Bekios 21761616-6
Luis Molina 21564225-9
*/
package pargion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner intro = new Scanner(System.in);
		
		System.out.println("Ingresa el menu a usar:");
		System.out.println("1.- Usuario");
		System.out.println("2.- Admin");
		System.out.println("3.- Salir");
		
		int opc;
		
		do {
			opc = intro.nextInt();
			
			if (opc != 1 && opc != 2 && opc != 3) {
				System.out.println("pone la wea bien sipo");
			}
			
		} while (opc != 1 && opc != 2 && opc != 3);
		
		
		
		switch(opc) {
		
		case 1:
			// mostrarMenuUsuario();
			
			Scanner Uintrosc = new Scanner(System.in);
			
			int Uopt;
			
			do {
			
			System.out.println("* Menu Usuario *");
			System.out.println("1.- Ver lista de experimentos");
			System.out.println("2.- Mostrar matriz de confusión de un experimento");
			System.out.println("3.- Ver métricas de un experimento");
			System.out.println("4.- Ver promedio de Accuracy de todos los modelos");
			
			
			
			do {
				Uopt = Uintrosc.nextInt();
				
				if (Uopt != 1 && Uopt != 2 && Uopt != 3 && Uopt != 4) {
					System.out.println("Ingrese una opción valida");
				}
				
			} while (Uopt != 1 && Uopt != 2 && Uopt != 3 && Uopt != 4);
			
			
			
				
			
			
			
			switch(Uopt) {
			
			case 1: // Ver lista de experimentos
				File experiments = new File("experimentos.txt");
				Scanner expscan = new Scanner(experiments);
				
				System.out.println("ID  |  Descripción ");
				
				while (expscan.hasNextLine()) {
					System.out.println(expscan.nextLine());
				}

				System.out.println("");
				System.out.println("#######################################################");
				System.out.println("");
				
				break;
				
			case 2:
				
				break;
				
			case 3: // Ver metricas de un experimento (Accuracy, Precision, Recall, F1)
				File experimentos = new File("experimentos.txt");
				Scanner expscan1 = new Scanner(experimentos);
				
				int iterador = 1;
				
				while (expscan1.hasNextLine()) {
					System.out.println("Experimento N° "+ iterador);
					expscan1.nextLine();
					iterador += 1;	
				}
				
				System.out.println("");
				System.out.println("#######################################################");
				System.out.println("");
				
				
				
				
				
				break;
			
			case 4:
				
				break;
			}
			} while (Uopt != 4);
			
			
			Uintrosc.close();
			
		case 2:
			// mostrarMenuAdmin();
			break;
			
		case 3:
			System.out.println("Apagando sistema...");
			break;
		}
	
		
		
	}
	
}
