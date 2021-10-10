
package combinacionletrassegunnumero;

import java.util.Scanner;

/**
 * @author sebascarag
 */
public class CombinacionLetrasSegunNumero {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println( String.format("Resultado = %s", Combinaciones(sc.next())));
    }

    private static int Combinaciones(String cadena) {
        char [] nros = cadena.toCharArray();
        int cantCombinaciones = 0;
        String combinacion = "";
        
        boolean siguiente = true;
        int grupos = 2;
        int posicionGrupo = 0;        
        boolean swGrupo = false;
        boolean swFirst = true;
        
        while (siguiente){
            for (int i = 0; i < nros.length; i++) {
                if (swFirst) {
                    combinacion+= "(" + nros[i] + ")";
                }else{
                    if (posicionGrupo == i && ( i+1 < nros.length ) ) {
                        for (int g = 0; g < grupos/2; g++) {
                            i = i + ( g > 1 ? 1 : g );
                            combinacion+= "(" + nros[i] + nros[i+1] +")";
                            i = i + 1;
                        }
                        swGrupo = true;
                    }else{
                        combinacion+= "(" + nros[i] + ")";
                    }
                }
            }
            posicionGrupo = swGrupo ? posicionGrupo + 1 : posicionGrupo;
            swGrupo = false;
            swFirst = false;
            combinacion = HallarLetras(combinacion);
            if( combinacion != null ){
                cantCombinaciones++;
                System.out.println(" - " + cantCombinaciones + " -> " + combinacion);
            }
            combinacion = "";
            if ( ( nros.length / grupos) >= 1 ) {
                if ( (posicionGrupo + grupos) <= nros.length) {
                    siguiente = true;
                }else{
                    grupos+= 2;
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
        String [] alfabeto = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        String temp = comb.replaceAll("(\\)\\()+", ".");
        temp = temp.replace("(", "").replace(")", "");
        
        String [] numStr = {temp};
        String [] tempNumStr = temp.split("(\\.)+");
        if ( tempNumStr.length >= 1 ) {
            numStr = tempNumStr;
        }
        String letras = "";
        for (String num : numStr) {
            int numNro = Integer.parseInt(num);
            if ( !( numNro > alfabeto.length ) ) {
                for (int i = 0; i < alfabeto.length; i++) {
                    if ( numNro == (i+1) ) {
                        letras += alfabeto[i];
                        break;
                    }
                }
            }else{
                return null;
            }
        }
        return comb + " = " + letras;
    }
}
