
package combinacionletrassegunnumero;

import java.util.Scanner;

/**
 * @author sebascarag
 */
public class CombinacionLetrasSegunNumero {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Presentar por consola la cantidad de combinaciones válidas según el alfabeto configurado
        System.out.println( String.format("Resultado = %s", Combinaciones(sc.next())));
    }

    private static int Combinaciones(String cadena) {
        //Arreglo con cada uno de los números ingresados
        char [] nros = cadena.toCharArray();
        //Contador de combinaciones
        int cantCombinaciones = 0;
        //Acumulador de las combinaciones halladas
        String combinacion = "";
        //Bandera controlada por condiciones para continuar buscando o no más combinaciones
        boolean siguiente = true;
        //Acumulador de cantidad de grupos que se pueden ir formando (2,4,6,8,...2n)
        int grupos = 2;
        //Indice para controlar posición del siguiente grupo a crear
        int posicionGrupo = 0;
        //Bandera para identificar cada vez que se forma un grupo
        boolean swGrupo = false;
        //Bandera para identificar primera ejecución
        boolean swFirst = true;
        
        while (siguiente){
            for (int i = 0; i < nros.length; i++) {
                if (swFirst) {
                    combinacion+= "(" + nros[i] + ")";
                }else{
                    //Condición para hallar el momento en que se debe formar un grupo de acuerdo al index del for y la coincidencia de la posición inicial de formación del grupo
                    if (posicionGrupo == i && ( i+1 < nros.length ) ) {
                        //Ciclo para controlar los multiples grupos consecutivos a formar
                        for (int g = 0; g < grupos/2; g++) {
                            //Acarreo para controlar el desplazamiento de i
                            i = i + ( g > 1 ? 1 : g );
                            //Acumulación de la combinación hallada
                            combinacion+= "(" + nros[i] + nros[i+1] +")";
                            //Avance adicional de i por el grupo formado que consume dos posiciones
                            i = i + 1;
                        }
                        swGrupo = true;
                    }else{
                        combinacion+= "(" + nros[i] + ")";
                    }
                }
            }
            //Condición para aumentar la posición del próximo grupo
            posicionGrupo = swGrupo ? posicionGrupo + 1 : posicionGrupo;
            //Reset de banderas
            swGrupo = false;
            swFirst = false;
            //Método para hallar y válidar la existencia del número en el alfabeto configurado
            combinacion = HallarLetras(combinacion);
            //Condición para controlar si es válida la combinación según el alfabeto
            if( combinacion != null ){
                //Contador de combinaciones halladas
                cantCombinaciones++;
                //Presentar por consola la combinación de números y su representación en letras
                System.out.println(" - " + cantCombinaciones + " -> " + combinacion);
            }
            //Reset varibale
            combinacion = "";
            //Condición que controla la continuidad de busqueda de combinaciones y los grupos a formar
            if ( ( nros.length / grupos) >= 1 ) {
                //Condición para validar la posibilidad de crear un nuevo grupo partiendo de los index consumidos y el tamaño de los números ingresados
                if ( (posicionGrupo + grupos) <= nros.length) {
                    siguiente = true;
                }else{
                    //Aumento de grupos a formar
                    grupos+= 2;
                    //Validación de la capacidad para crear la nueva formación de grupos
                    if ( ( nros.length / grupos ) >= 1 ) {
                        posicionGrupo = 0;
                        siguiente = true;
                    }else{
                        siguiente = false;
                    }
                }
            }else{
                siguiente = false;
            }
        }
        return cantCombinaciones;
    }

    private static String HallarLetras(String comb) {
        //Alfabeto configurado
        String [] alfabeto = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        //Temporal para limpiar () creados para la presentación en pantalla de las combinaciones halladas
        String temp = comb.replaceAll("(\\)\\()+", ".");
        //Temporal para reemplazar por "" el ( y ) de inicio y cierre
        temp = temp.replace("(", "").replace(")", "");
        //Arreglo que contiene cada una de las combinaciones halladas. Se inicializa con temp para los casos en que se halla ingresado un solo dígito
        String [] numStr = {temp};
        //Arreglo tempora para dividir por punto las combinaciones halladas en caso de ser multiples
        String [] tempNumStr = temp.split("(\\.)+");
        //Condición para identificar cuando se presentan multiples combinaciones
        if ( tempNumStr.length >= 1 ) {
            numStr = tempNumStr;
        }
        //Variable para acumular las letras halladas
        String letras = "";
        //Loop para extrer cada uno de los números de las combinaciones
        for (String num : numStr) {
            //Conversión a número entero
            int numNro = Integer.parseInt(num);
            //Comprobación de que el número no supere el tamaño del alfabeto configurado
            if ( !( numNro > alfabeto.length ) ) {
                //For para la busqueda de las letras por su número
                for (int i = 0; i < alfabeto.length; i++) {
                    if ( numNro == (i+1) ) {
                        letras += alfabeto[i];
                        break;
                    }
                }
            }else{
                //En caso de superar el tamaño del alfabeto, la combinación se considera no válida
                return null;
            }
        }
        //Concatenación de la combinación númerica y alfabética hallada
        return comb + " = " + letras;
    }
}
