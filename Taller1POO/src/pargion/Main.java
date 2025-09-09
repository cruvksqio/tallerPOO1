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
		
		//Inicio de programa definiendo matriz y llenandola con los archivos :PPPP
		
		File experiments = new File("experimentos.txt");	//definir filas matriz
		Scanner countExp = new Scanner(experiments);
		int expsTotal=0;
		
		while (countExp.hasNextLine()) {
			expsTotal++;
			countExp.nextLine();
		}
		

        // Inicializar matriz con ceros
        int[][] matriz = new int[expsTotal][4];
        for (int i = 0; i < expsTotal; i++) {
            for (int j = 0; j < 4; j++) {
                matriz[i][j] = 0;
            }
        }
		
        
        //leer los predicts
		File predicts = new File("predicciones.txt");
		Scanner scanPred = new Scanner(predicts);
		
		String[] parts = scanPred.nextLine().split(";");
		String expActual = parts[0];
		int realV = Integer.parseInt(parts[1]);
		int predV = Integer.parseInt(parts[2]);		
											
											
		for (int i = 0; i < expsTotal; i++) {
            for (int j = 0; j < 4; j++) {
                matriz[i][j] = 0;
            }
        } 
		
		int expI = -1;
		String expSetter = "";
		
		while (scanPred.hasNextLine()) {
			
            if (!expActual.equals(expSetter)) {
                expI++;
                expSetter= expActual;
            }
			
			//FILL THE MATRIXXXXKSKSKS
		
			if (realV == 1 && predV == 1) {			// TP
				matriz[expI][0]++;
			}
			else if (realV == 0 && predV == 1) {	// FP
				matriz[expI][1]++;
			}
			else if (realV == 0 && predV == 0) {	// TN
				matriz[expI][2]++;
			}
			else if (realV == 1 && predV == 0) {	// FN
				matriz[expI][3]++;
			}
			
			parts = scanPred.nextLine().split(";");
			expActual = parts[0];
			realV = Integer.parseInt(parts[1]);
			predV = Integer.parseInt(parts[2]);
			
			
		}
		
		
		
		Scanner intro = new Scanner(System.in);
		
		System.out.println("Ingresa el menu a usar:");
		System.out.println("1.- Usuario");
		System.out.println("2.- Admin");
		System.out.println("3.- Salir");
		
		int opc;
		
		do {
			opc = intro.nextInt();
			
			if (opc != 1 && opc != 2 && opc != 3) {
				System.out.println("Error: Ingrese de nuevo opcion");
			}
			
		} while (opc != 1 && opc != 2 && opc != 3);
		
		
		
		switch(opc) {
		
		case 1:
			// mostrar Menu Usuario
			
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
	
		intro.close();
		countExp.close();
		scanPred.close();
		
	}
	
}
