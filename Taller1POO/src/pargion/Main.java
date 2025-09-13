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
			System.out.println("Apagando sistema...");
		}
			
		 case 1 -> {
		        // Mostrar matriz de métricas de forma ordenada
		        System.out.println("==================================================================");
		        System.out.println("                    MATRIZ DE MÉTRICAS POR EXPERIMENTO");
		        System.out.println("==================================================================");
		        System.out.printf("%-8s | %-10s | %-10s | %-10s | %-10s%n", 
		                         "ID", "Accuracy", "Precision", "Recall", "F1-Score");
		        System.out.println("------------------------------------------------------------------");
		        
		        for (int i = 0; i < expsTotal; i++) {
		            double accuracy = Math.round(getAccuracy(i, mtr) * 100.0) / 100.0;
		            double precision = Math.round(getPrecission(i, mtr) * 100.0) / 100.0;
		            double recall = Math.round(getRecall(i, mtr) * 100.0) / 100.0;
		            double f1Score = Math.round(getF1Score(precision, recall) * 100.0) / 100.0;
		            
		            System.out.printf("Exp%-6d | %-10.2f | %-10.2f | %-10.2f | %-10.2f%n", 
		                             i + 1, accuracy, precision, recall, f1Score);
		        }
		        System.out.println("==================================================================");
		    }
			
		case 2 -> {
			
			double bestF1 = -1;		// Variable del mejor f1 inicializada
			int bestF1Exp = -1;
			
			for (int i = 0; i < expsTotal; i++)		//Iterador para encontrar f1 con mejor ptje
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
			
			System.out.println("El mejor F1-Score es del Exp" + bestF1Exp + " con " + Math.round((bestF1)*100.0)/100.0);
		
			}
		
		case 3 -> 
		{
			File metrics = new File("metricas.txt");	//Abrir archivos y hacer scanner
			Scanner scaMet = new Scanner(metrics);
			
			double promAcc = 0;				//Inicializar variables de promedios
			double promPrec = 0;
			double promRec = 0;
			double promF1 = 0;
			
			for (int i = 0; i<expsTotal; i++)		// Suma de cada metodo yendo de experimento a otro
			{
				promAcc += getAccuracy(i, mtr);
				promPrec += getPrecission(i,mtr);
				promRec += getRecall(i,mtr);
				promF1 += getF1Score(getPrecission(i,mtr),getRecall(i,mtr));
			}
			
			System.out.println(scaMet.nextLine() + ": " + (Math.round((promAcc / expsTotal) * 100.0) / 100.0));
			System.out.println(scaMet.nextLine() + ": " + (Math.round((promPrec / expsTotal) * 100.0) / 100.0));
			System.out.println(scaMet.nextLine() + ": " + (Math.round((promRec / expsTotal) *100.0) / 100.0));
			System.out.println(scaMet.nextLine() + ": " + (Math.round((promF1 / expsTotal) *100.0) / 100.0));
			scaMet.close();
			
		}
		
		case 4 -> 
		{
		    System.out.println("Ingrese primer experimento a comparar:");	
		    int expComp1 = adminS.nextInt();
		    													//Ingreso de valores con control de errores
		    if (expComp1 > expsTotal || expComp1 < 1) {
		        System.out.println("Error: Valor no válido. Debe ser entre 1 y " + expsTotal);
		        break;
		    }
		    System.out.println("Ingrese segundo experimento a comparar:");
		    int expComp2 = adminS.nextInt();
		    
		    if (expComp2 > expsTotal || expComp2 < 1) {
		        System.out.println("Error: Valor no válido. Debe ser entre 1 y " + expsTotal);
		        break;
		    }
		    
		    // Ajuste matriz (para que no de error out of bounds)
		    int idx1 = expComp1 - 1;
		    int idx2 = expComp2 - 1;
		    
		    System.out.println("METRICA    |    EXP" + expComp1 + "  vs  EXP" + expComp2 + "  |");
		    System.out.println("--------------------------------------");
		    
		    File metrics = new File("metricas.txt");
		    Scanner scaMet = new Scanner(metrics);
		    
		    System.out.println(scaMet.nextLine() + ":  " + Math.round((getAccuracy(idx1, mtr)*100.0))/100.0 +
		            "  |  " + Math.round((getAccuracy(idx2, mtr)*100.0))/100.0);
		    
		    System.out.println(scaMet.nextLine() + ":" + Math.round((getPrecission(idx1, mtr)*100.0))/100.0 +
		            "  |  " + Math.round((getPrecission(idx2, mtr)*100.0))/100.0);
		    
		    System.out.println(scaMet.nextLine() + ":" + Math.round((getRecall(idx1, mtr)*100.0))/100.0 +
		            "  |  " + Math.round((getRecall(idx2, mtr)*100.0))/100.0);
		    
		    System.out.println(scaMet.nextLine() + ":    " + 
		    Math.round(getF1Score(getPrecission(idx1, mtr), getRecall(idx1, mtr)*100.0))/100.0 + "  |  "
		    + Math.round(getF1Score(getPrecission(idx2, mtr), getRecall(idx2, mtr)*100.0))/100.0);
		    
		    scaMet.close();
		}
		
		case 5 -> {
		    
		    // Leer el archivo CSV
		    File csvFile = new File("verificacion_docente_confusiones.csv");
		    Scanner csvScanner = new Scanner(csvFile);
		    
		    // Saltar la primera línea (cabecera)
		    if (csvScanner.hasNextLine()) {
		        csvScanner.nextLine();
		    }
		    
		    // Variables para almacenar las diferencias
		    int diferencias = 0;
		    int totalComparaciones = 0;
		    
		    System.out.println("Experimento | TP (gen vs CSV) | FP (gen vs CSV) | TN (gen vs CSV) | FN (gen vs CSV)");
		    System.out.println("------------------------------------------------------------------------------");
		    
		    // Comparar cada experimento
		    while (csvScanner.hasNextLine()) {
		        String[] datosCSV = csvScanner.nextLine().split(",");
		        
		        // Obtener datos del CSV
		        String expCSV = datosCSV[0];
		        int tpCSV = Integer.parseInt(datosCSV[1]);
		        int fpCSV = Integer.parseInt(datosCSV[2]);
		        int tnCSV = Integer.parseInt(datosCSV[3]);
		        int fnCSV = Integer.parseInt(datosCSV[4]);
		        
		        // Obtener índice del experimento (restar un numero)
		        int expIndex = Integer.parseInt(expCSV.replace("Exp", "")) - 1;
		        
		        if (expIndex >= 0 && expIndex < expsTotal) {
		            // Obtener datos de nuestra matriz
		            int tpNuestro = mtr[expIndex][0];
		            int fpNuestro = mtr[expIndex][1];
		            int tnNuestro = mtr[expIndex][2];
		            int fnNuestro = mtr[expIndex][3];
		            
		            // Mostrar comparación
		            System.out.printf("%-10s | %-15s | %-15s | %-15s | %-15s%n",
		                expCSV,
		                tpNuestro + " vs " + tpCSV + (tpNuestro != tpCSV ? " *" : ""),
		                fpNuestro + " vs " + fpCSV + (fpNuestro != fpCSV ? " *" : ""),
		                tnNuestro + " vs " + tnCSV + (tnNuestro != tnCSV ? " *" : ""),
		                fnNuestro + " vs " + fnCSV + (fnNuestro != fnCSV ? " *" : ""));
		            
		            // Contar diferencias
		            if (tpNuestro != tpCSV) diferencias++;
		            if (fpNuestro != fpCSV) diferencias++;
		            if (tnNuestro != tnCSV) diferencias++;
		            if (fnNuestro != fnCSV) diferencias++;
		            
		            totalComparaciones += 4;
		        }
		    }
		    
		    // Mostrar resumen
		    System.out.println("------------------------------------------------------------------------------");
		    System.out.println("Coincidencias: " + (totalComparaciones - diferencias) + "/" + totalComparaciones);
		    System.out.println("Diferencias: " + diferencias + "/" + totalComparaciones);
		    
		    if (diferencias == 0) {
		        System.out.println("Todos los valores coinciden con el CSV del docente.");
		    } else {
		        System.out.println("Se encontraron diferencias. Los valores marcados con * no coinciden.");
		    }
		    
		    csvScanner.close();
		}
		
		}
		
		
		} while (adminOpt!=0);
		
		adminS.close();
		
		
	}

	public static void mostrarMenuUsuario(int expsTotal, int[][] matriz, File experiments) {
	    Scanner Uintrosc = new Scanner(System.in);
	    int Uopt;

	    do {
	        System.out.println("--- Menu Usuario ---");
	        System.out.println("1.- Ver lista de experimentos");
	        System.out.println("2.- Mostrar matriz de confusión de un experimento");
	        System.out.println("3.- Ver métricas de un experimento");
	        System.out.println("4.- Ver promedio de Accuracy de todos los modelos");
	        System.out.println("0.- Salir");

	        do {
	            Uopt = Uintrosc.nextInt();
	            if (Uopt != 1 && Uopt != 2 && Uopt != 3 && Uopt != 4 && Uopt != 0) {
	                System.out.println("Ingrese una opción valida");						// control de errores
	            }
	        } while (Uopt != 1 && Uopt != 2 && Uopt != 3 && Uopt != 4 && Uopt != 0);

	        switch (Uopt) {
	            case 1 -> {																	// Ver lista de experimentos
	                try (Scanner expscan = new Scanner(experiments)) {
	                    System.out.println("ID  |  Descripción ");
	                    while (expscan.hasNextLine()) {
	                        String[] partes = expscan.nextLine().split(";");
	                        System.out.println("Experimento " + partes[0].replace("Exp", "") + " : " + partes[1]); // uso de .replace() para mejorar ui
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error leyendo archivo de experimentos.");
	                }
	                System.out.println("-------------------------------------------");
	            }

	            case 2 -> {																	// Mostrar matriz de confusión
	                System.out.println("Matriz de confusión (TP | FP | TN | FN):");
	                for (int i = 0; i < expsTotal; i++) {
	                    System.out.println("Experimento " + (i + 1) + " : "					// uso de ciclo for para recorrer matriz
	                            + matriz[i][0] + "| "
	                            + matriz[i][1] + "| "
	                            + matriz[i][2] + "| "
	                            + matriz[i][3]);
	                }
	                System.out.println("-------------------------------------------");
	            }

	            case 3 -> {																	// Ver metricas de un experimento
	                System.out.println("Hay " + expsTotal + " Experimentos");
	                System.out.print("Ingrese el N° identificador del experimento: ");
	                int u3exp = Uintrosc.nextInt() - 1;										// Ajustar eleccion a the matrix 
	                if (u3exp < 0 || u3exp >= expsTotal) {									//+ reciclado del scanner de la intro porque antes se cerraba el system.in y no funcionaba
	                    System.out.println("Error: El experimento ingresado no existe.");	// Control de error
	                } else {
	                    double acc = getAccuracy(u3exp, matriz);
	                    double prec = getPrecission(u3exp, matriz);							// double cast
	                    double rec = getRecall(u3exp, matriz);								// invocacion de metodos
	                    double f1 = getF1Score(rec, prec);

	                    System.out.println("Accuracy: " + Math.round(acc * 100.0) / 100.0);
	                    System.out.println("Precision: " + Math.round(prec * 100.0) / 100.0);	//redondeo masivo
	                    System.out.println("Recall: " + Math.round(rec * 100.0) / 100.0);
	                    System.out.println("F1-Score: " + Math.round(f1 * 100.0) / 100.0);
	                }
	                
	                
	                System.out.println("-------------------------------------------");
	            }

	            case 4 -> {																	// Ver promedio de accuracy overall
	                double accuracySum = 0;
	                for (int j = 0; j < expsTotal; j++) {
	                    accuracySum += getAccuracy(j, matriz);
	                }
	                double promAcc = accuracySum / expsTotal;
	                System.out.println("El promedio de Accuracy de todos los modelos es de "
	                        + Math.round(promAcc * 100.0) / 100.0);
	                System.out.println("-------------------------------------------");
	            }

	            case 0 -> {
	                System.out.println("Apagando sistema...");
	            } 
	        }
	    
	    } while (Uopt != 0);
	    
	    Uintrosc.close();
	  
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
			mostrarMenuUsuario(expsTotal, matriz, experiments);
			break;
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
