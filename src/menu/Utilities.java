package menu;

import java.util.Scanner;

/**
 * Classe contenente le utilities per l'applicazione.
 */
public class Utilities {
    /**
     * Funzione per aiutare il recupero e la richiesta di un input del cliente.
     *
     * @param type Il tipo di input atteso (Integer, Double, String, Float).
     * @param s    Il messaggio da visualizzare per richiedere l'input.
     * @return L'input dell'utente convertito al tipo specificato.
     * @throws IllegalArgumentException se il tipo specificato non è supportato.
     */
    public static <T> T userInput(Class<T> type, String s) {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println(s);

        if (type == Integer.class) {
            int userInput = sc.nextInt();
            return type.cast(userInput);

        } else if (type == Double.class) {
            double userInput2 = sc.nextDouble();
            return type.cast(userInput2);

        } else if (type == String.class) {
            String userInput3 = sc.next();
            return type.cast(userInput3);

        } else if (type == Float.class) {
            Float userInput4 = sc.nextFloat();
            return type.cast(userInput4);
        } else {
            throw new IllegalArgumentException("Tipo non supportato: " + type.getSimpleName());
        }
    }
}
