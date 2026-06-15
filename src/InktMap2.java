
 /*

This is currently a project focused solely on syntax.
 */



import java.util.Scanner;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;



public class InktMap2 {

    public static void main(String[] args) {

        HashMap<String, String> inkten = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        ladenUitBestand(inkten);

        while(true) {

            keuzeMenu();
            String keuze1 = scanner.nextLine();

            if (keuze1.equals("1")) {
                inktZoeken(inkten,scanner);

            }
            else if (keuze1.equals("2")) {
                 inktInvoeren(inkten,scanner);
            }
            else if (keuze1.equals("3")) {
                opslaanInBestand(inkten);
                System.out.println("Het programma afgesloten.");
                break;
            }
            else {
                System.out.println("Ongeldige keuze");
            }


        }



    }
                        //KEUZE MENU

    public static void keuzeMenu(){
        System.out.println("------SCHUT INKTMAP------");
        System.out.println("1. Inkt zoeken");
        System.out.println("2. Inkt toevoegen");
        System.out.println("3.Afsluiten");
        System.out.print("Keuze?: ");

    }

    //INKTZOEKEN

    public static void inktZoeken(HashMap<String, String> inkten, Scanner scanner){


        System.out.print("Voer inkt nummer of naam in: ");

        String inktnummer = scanner.nextLine().trim();


        if (inkten.containsKey(inktnummer)) {

            String locatie = inkten.get(inktnummer);
            System.out.println(locatie);

        } else {
            System.out.println("Inkt niet gevonden");

        }

    }


    //INKTINVOEREN

    public static void inktInvoeren(HashMap<String, String> inkten, Scanner scanner){
        System.out.print("Voer inkt nummer of naam in: ");

        String newInkt = scanner.nextLine().trim();

        if (inkten.containsKey(newInkt)) {
            System.out.println("Let op!. Deze inkt bestaat al op " + inkten.get(newInkt));
            System.out.print("Wilt u de locatie wijzigen? (J/N) : ");

            String keuze2 = scanner.nextLine();

            if (keuze2.equalsIgnoreCase("j")) {

                System.out.print("Voer de NIEUW locatie in: ");
                String newLocatie = scanner.nextLine().toUpperCase();

                System.out.println(newInkt + " is toegevoegd aan ---------> " + newLocatie);

            }
            else {
                System.out.println("Wijziging geannuleerd.");
            }
        }
        else {

            System.out.print("Voer locatie for " + newInkt + " in: ");
            String newLocatie = scanner.nextLine().toUpperCase();

            System.out.println(newInkt + " is toegevoegd aan ---------> " + newLocatie);

        }

    }


    public static void ladenUitBestand(HashMap<String, String> inkten){

        try {

            File bestand = new File("inkten.txt");

            Scanner bestandScanner = new Scanner(bestand);

            while (bestandScanner.hasNextLine()) {

                String lijn = bestandScanner.nextLine();
                if (lijn.trim().isEmpty()) continue;

                String[] apart = lijn.split(",");

                if (apart.length >= 2) {
                    inkten.put(apart[0].trim(), apart[1].trim());
                } else {
                    System.out.println("verkeerde lijn overgeslagen " + lijn);
                }
            }


            bestandScanner.close();
            System.out.println("Het bestand is succesvol geupload.");
        }

            catch(FileNotFoundException e) {
                System.out.println("Het bestand in not gevonden.");


            }



    }

    public static void opslaanInBestand(HashMap<String, String> inkten){

        try{
            PrintWriter writer = new PrintWriter(new FileWriter("inkten.txt", false));

            for (String key : inkten.keySet()){

                String value = inkten.get(key);
                writer.println(key + "," + value);
            }

            writer.close();
            System.out.println("De wijzigingen zijn opgeslagen. ");
        }

        catch (IOException e){
            System.out.println("Fout bij het bestand: " + e.getMessage());
        }


    }

    }



