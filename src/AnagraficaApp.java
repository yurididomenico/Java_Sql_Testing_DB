import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AnagraficaApp {

    private static Connection connection;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int scelta = 6;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/anagrafica_schema",
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

                try {
                    scelta = scanner.nextInt();
                    scanner.nextLine();  // Consuma la nuova linea
                    System.out.println();
                } catch (InputMismatchException e) {
                    System.out.println("Scelta non valida!");
                    scelta = 6;
                }

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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        scanner.close();
    }

    private static void inserisciAnagrafica(Scanner scanner) {
        System.out.print("Inserisci codice fiscale: ");
        String codiceFiscale = scanner.nextLine();
        System.out.print("Inserisci nome: ");
        String nome = scanner.nextLine();

        try {
            String query = "INSERT INTO utenti (codice_fiscale, username) VALUES (?, ?)";
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

    private static void cercaNome(Scanner scanner) {
        System.out.print("Inserisci codice fiscale da cercare: ");
        String codiceFiscaleDaCercare = scanner.nextLine();

        try {
            String query = "SELECT * FROM utenti WHERE codice_fiscale = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, codiceFiscaleDaCercare);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Nome associato: " + resultSet.getString("username"));
            } else {
                System.out.println("Nessuna persona trovata con il codice fiscale: " + codiceFiscaleDaCercare);
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la ricerca dell'anagrafica:");
            e.printStackTrace();
        }
    }


    private static void cancellaAnagrafica(Scanner scanner) {
        System.out.print("Inserisci codice fiscale dell'anagrafica da cancellare: ");
        String codiceFiscaleDaCancellare = scanner.nextLine();

        try {
            String query = "DELETE FROM utenti WHERE codice_fiscale = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, codiceFiscaleDaCancellare);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Anagrafica cancellata.");
            } else {
                System.out.println("Nessuna persona trovata con il codice fiscale " + codiceFiscaleDaCancellare);
            }

        } catch (SQLException e) {
            System.out.println("Errore durante la cancellazione dell'anagrafica:");
            e.printStackTrace();
        }
    }


    private static void stampaNumeroPersone() {
        try {
            String query = "SELECT COUNT(*) AS \"Numero Persone\" FROM utenti";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Numero di persone inserite: " + resultSet.getInt("Numero Persone"));
            }

        } catch (SQLException e) {
            System.out.println("Errore durante il conteggio delle persone:");
            e.printStackTrace();
        }
    }

    private static void stampaElencoAnagrafico() {
        try {
            String query = "SELECT * FROM utenti";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Nessun utente inserito.");
                return;
            }

            while (resultSet.next()) {
                System.out.println("Codice Fiscale: " + resultSet.getString("codice_fiscale") + " - " + "Nome: " + resultSet.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la stampa dell'elenco:");
            e.printStackTrace();
        }
    }
}
//
//class Persona {
//    private String codiceFiscale;
//    private String nome;
//
//    public Persona(String codiceFiscale, String nome) {
//        this.codiceFiscale = codiceFiscale;
//        this.nome = nome;
//    }
//
//    public String getCodiceFiscale() {
//        return codiceFiscale;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    @Override
//    public String toString() {
//        return "Codice Fiscale: " + codiceFiscale + " - " + "Nome: " + nome;
//    }
//}
