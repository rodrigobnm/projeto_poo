package br.com.cesarschool.poo.titulos.utils;

public class Ordenador {

    public static void ordenar(Comparavel[] ents, Comparador comp) {
        for (int i = 0; i < ents.length - 1; i++) {
            for (int j = 0; j < ents.length - 1 - i; j++) {
                if (comp.comparar(ents[j], ents[j + 1]) > 0) {
                    Comparavel temp = ents[j];
                    ents[j] = ents[j + 1];
                    ents[j + 1] = temp;
                }
            }
        }
    }

    public static void ordenar(Comparavel[] comps) {
        for (int i = 0; i < comps.length - 1; i++) {
            for (int j = 0; j < comps.length - 1 - i; j++) {
                if (comps[j].comparar(comps[j + 1]) > 0) {
                    Comparavel temp = comps[j];
                    comps[j] = comps[j + 1];
                    comps[j + 1] = temp;
                }
            }
        }
    }
    
    
    
// PARA PASSAR O TESTE 7
//
//	public static void ordenar(Comparavel[] ents, Comparador comp) {
//        for (int i = 0; i < ents.length - 1; i++) {
//            for (int j = 0; j < ents.length - 1 - i; j++) {
//                if (comp.comparar(ents[j], ents[j + 1]) < 0) {
//                    Comparavel temp = ents[j];
//                    ents[j] = ents[j + 1];
//                    ents[j + 1] = temp;
//                }
//            }
//        }
//    }
//
//    public static void ordenar(Comparavel[] comps) {
//        for (int i = 0; i < comps.length - 1; i++) {
//            for (int j = 0; j < comps.length - 1 - i; j++) {
//                if (comps[j].comparar(comps[j + 1]) < 0) {
//                    Comparavel temp = comps[j];
//                    comps[j] = comps[j + 1];
//                    comps[j + 1] = temp;
//                }
//            }
//        }
//    }
    
}