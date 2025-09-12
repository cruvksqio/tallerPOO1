/*
Constantino Bekios 21761616-6
Luis Molina 21564225-9
*/
package pargion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
	
	public static void mostrarMenuAdmin(int expsTotal, int mtr[][]) throws FileNotFoundException {

		Scanner adminS =  new Scanner(System.in);
		int adminOpt = 0;

		do
		{
		System.out.println("--- Menu Admin ---");
		System.out.println("1.- Ver matriz de metricas");
		System.out.println("2.- Identificar mejor F-1 Score");
		System.out.println("3.- Calcular promedio global de metricas");
		System.out.println("4.- Comparar experimento lado a lado");
		System.out.println("5.- Comparar CSV");
		System.out.println("0.- Salir");
		
		try {
			adminOpt = adminS.nextInt();
			adminS.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Error: Numero no fue ingresado, ingrese un numero correcto");
			adminS.nextLine();
		}
		
		switch(adminOpt) {
		case 0 -> {
			System.out.println("Volviendo al menu login...");
		}
			
		case 1 -> {
			System.out.println("  ID  | Accuraccy  | Precission | Recall  |  F1-Score ");
			
			for (int i = 0; i < expsTotal; i++) {
				System.out.println("  EXP  " + i + "  ");
				System.out.print(getAccuracy(i,mtr) + "  ");
				
				double pre = getPrecission(i,mtr);
				System.out.print(pre + "  ");
				
				double rec = getRecall(i,mtr);
				System.out.print(rec + "  ");
				System.out.print(getF1Score(pre,rec) + "  ");
			}
		}
			
		case 2 -> {
			
			double bestF1 = -1;
			int bestF1Exp = -1;
			
			for (int i = 0; i < expsTotal; i++)
			{
				double pre = getPrecission(i,mtr);
				double rec = getRecall(i,mtr);
				double currentF1 = getF1Score(pre,rec);
				
				if (currentF1>bestF1)
				{
					bestF1 = currentF1;
					bestF1Exp = i;
				}
			}
			
			System.out.println("El mejor F1-Score es del Exp" + bestF1Exp + "con " + bestF1);
		
			}
		
		case 3 -> 
		{
			File metrics = new File("metricas.txt");
			Scanner scaMet = new Scanner(metrics);
			
			double promAcc = 0;
			double promPrec = 0;
			double promRec = 0;
			double promF1 = 0;
			
			for (int i = 0; i<expsTotal; i++)
			{
				promAcc += getAccuracy(i, mtr);
				promPrec += getPrecission(i,mtr);
				promRec += getRecall(i,mtr);
				promF1 += getF1Score(getPrecission(i,mtr),getRecall(i,mtr));
			}
			
			System.out.println(scaMet.nextLine() + ": " + Math.round(promAcc/expsTotal)/100);
			System.out.println(scaMet.nextLine() + ": " + Math.round(promPrec/expsTotal)/100);
			System.out.println(scaMet.nextLine() + ": " + Math.round(promRec/expsTotal)/100);
			System.out.println(scaMet.nextLine() + ": " + Math.round(promF1/expsTotal)/100);
			scaMet.close();
			
		}
		
		case 4 -> 
		{
			System.out.println("Ingrese primer experimento a comparar:");
			int expComp1 = adminS.nextInt();
			if (expComp1>expsTotal || expComp1<0) {
				throw new IllegalArgumentException("Valor no valido");
			}
			System.out.println("Ingrese primer experimento a comparar:");
			int expComp2 = adminS.nextInt();
			if (expComp2>expsTotal || expComp2<0) {
				throw new IllegalArgumentException("Valor no valido");
			}
			
			System.out.println("METRICA    |    EXP" + expComp1 + "  vs  EXP" + expComp2 + "  |");
			System.out.println("--------------------------------------");
			
			File metrics = new File("metricas.txt");
			Scanner scaMet = new Scanner(metrics);
			
			System.out.println(scaMet.nextLine() + ":    " + getAccuracy(expComp1, mtr) +
					"  |  " + getAccuracy(expComp2, mtr));
			
			System.out.println(scaMet.nextLine() + ":    " + getPrecission(expComp1, mtr) +
					"  |  " + getPrecission(expComp2, mtr));
			
			System.out.println(scaMet.nextLine() + ":    " + getRecall(expComp1, mtr) +
					"  |  " + getRecall(expComp2, mtr));
			
			System.out.println(scaMet.nextLine() + ":    " + 
			getF1Score(getPrecission(expComp1, mtr), getRecall(expComp1, mtr)) + "  |  "
			+ getF1Score(getPrecission(expComp2, mtr), getRecall(expComp2, mtr)));
			
			scaMet.close();
		}
		
		
		}
		
		
		} while (adminOpt!=0);
		
		adminS.close();
		
		
	}
	

	

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
		
		//Importar variables para uso en menu admin
		
		//Inicio de sesion
		
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
			
			System.out.println("--- Menu Usuario ---");
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
					
					String[] partes = expscan.nextLine().split(";");
					partes[0].split("Exp");
					
					
					System.out.println(partes[0] + " " + partes[1]);
				}

				System.out.println("");
				System.out.println("-------------------------------------------");
				System.out.println("");
				
				break;
				
			case 2:
				
				break;
				
			case 3: // Ver metricas de un experimento (Accuracy, Precision, Recall, F1)
				
				Scanner u3opt = new Scanner(System.in);
				int u3exp;
				
				System.out.println("Hay " + expsTotal + " Experimentos");
				System.out.println("Ingrese el N° identificador de el experimento que desea ver :");
				
				u3exp = u3opt.nextInt();
				
				u3exp -= 1;															   // Ajustar eleccion a the matrix
																						
				if (u3exp < 0 || u3exp >= expsTotal) {								   
			        System.out.println("Error: El experimento ingresado no existe.");  // Control de error
			    } else {
					
					double acc = getAccuracy(u3exp, matriz);
					double prec = getPrecission(u3exp, matriz);						   // double cast
					double rec = getRecall(u3exp, matriz);
					double f1 = getF1Score(rec, prec);
	
					System.out.println("Accuracy: " + acc);
					System.out.println("Precision: " + prec);
					System.out.println("Recall: " + rec);
					System.out.println("F1-Score: " + f1);
					
					
					System.out.println("");
					System.out.println("-------------------------------------------");
					System.out.println("");
			    }
					
				break;
																							
			case 4:
				                        // FIX FIX FIX FIX FIX FIX FIX :(
				break;
			}
			} while (Uopt != 4);
			
			
			Uintrosc.close();
			
		case 2:
			mostrarMenuAdmin(expsTotal, matriz);
			break;
			
		case 3:
			System.out.println("Apagando sistema...");
			break;
		}

	
		intro.close();
		countExp.close();
		scanPred.close();
		
		
		
		
	}
	
	

	
	
	
	public static double getAccuracy (int i, int[][] mtr) {
	    return (double)(mtr[i][0]+mtr[i][3]) / (mtr[i][0] + mtr[i][1] + mtr[i][2] + mtr[i][3]);
	}

	public static double getPrecission (int i, int[][] mtr) {
	    return (double)mtr[i][0] / (mtr[i][0] + mtr[i][1]);
	}

	public static double getRecall(int i, int[][] mtr) {
	    return (double)mtr[i][0] / (mtr[i][0] + mtr[i][3]);
	}

    public static double getF1Score(double reca, double pres) {

        double f1 = 2* (reca * pres)/(reca+pres);

        return f1;
    } 
	
}
