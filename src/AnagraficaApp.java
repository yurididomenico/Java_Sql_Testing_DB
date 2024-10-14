import java.sql.*;
import java.util.Scanner;

public class AnagraficaApp {
    private static Connection connection;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scelta;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql//127.0.0.1:3306/anagrafica_schema",
                    "root",
                    "A6ced1d6!!"
            );

            do {
                System.out.println("\nMenu:");
                System.out.println("1 – Inserisci anagrafica");
                System.out.println("2 – Cerca nome (tramite codice fiscale)");
                System.out.println("3 – Cancella anagrafica");
                System.out.println("4 – Stampa numero di persone inserite");
                System.out.println("5 – Stampa elenco anagrafico");
                System.out.println("6 – Esci");
                System.out.print("Scegli un'opzione: ");
                scelta = scanner.nextInt();
                scanner.nextLine();  // Consuma la nuova linea
                System.out.println();

                switch (scelta) {
                    case 1:
                        inserisciAnagrafica(scanner);
                        break;
                    case 2:
                        cercaNome(scanner);
                        break;
                    case 3:
                        cancellaAnagrafica(scanner);
                        break;
                    case 4:
                        stampaNumeroPersone();
                        break;
                    case 5:
                        stampaElencoAnagrafico();
                        break;
                    case 6:
                        System.out.println("Uscita...");
                        break;
                    default:
                        System.out.println("Scelta non valida!");
                }
            } while (scelta != 6);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Qualcosa è andato storto :/");
            e.printStackTrace();
        }

        scanner.close();
    }

    private static void inserisciAnagrafica(Scanner scanner) {
        System.out.print("Inserisci codice fiscale: ");
        String codiceFiscale = scanner.nextLine();
        System.out.print("Inserisci nome: ");
        String nome = scanner.nextLine();

        try {
            String query = "INSERT INTO utenti (codice_fiscale, nome) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, codiceFiscale);
            preparedStatement.setString(2, nome);
            preparedStatement.execute();
            System.out.println("Anagrafica inserita correttamente!");
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento dell'anagrafica:");
            e.printStackTrace();
        }
    }


}
