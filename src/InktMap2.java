
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;





public class InktMap2 {

    private static ResourceBundle messages;
    private static Locale huidigeLocale;


    public static void main(String[] args) {

        HashMap<String, String> inkten = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        setLanguage("nl");

        ladenUitBestand(inkten);

        while(true) {

            keuzeMenu();
            String keuze1 = scanner.nextLine();

            if (keuze1.equals("1")) {
                inktZoeken(inkten,scanner);

            } else if (keuze1.equals("2")) {

                inktInvoeren(inkten,scanner);

            } else if (keuze1.equals("3")) {

                taalWisselen();

            } else if (keuze1.equals("4")) {

                opslaanInBestand(inkten);
                System.out.println(messages.getString("program.closed"));
                break;

            } else {
                System.out.println(messages.getString("invalid.choice"));
            }


        }



    }
                        //KEUZE MENU

    public static void keuzeMenu(){
        System.out.println(messages.getString("menu.title"));
        System.out.println(messages.getString("menu.search"));
        System.out.println(messages.getString("menu.add"));
        System.out.println(messages.getString("menu.language"));
        System.out.println(messages.getString("menu.exit"));
        System.out.print(messages.getString("menu.choice"));

    }

    public static void taalWisselen(){

        if (huidigeLocale.getLanguage().equals("nl"))
            setLanguage("en");
        else {
            setLanguage("nl");
        }

    }

    public static void setLanguage(String langCode){

        huidigeLocale = new Locale(langCode);
        messages = ResourceBundle.getBundle("messages", huidigeLocale);

    }

    //INKTZOEKEN

    public static void inktZoeken(HashMap<String, String> inkten, Scanner scanner){


        System.out.print(messages.getString("search.prompt"));

        String inktnummer = scanner.nextLine().trim();


        if (inkten.containsKey(inktnummer)) {

            String locatie = inkten.get(inktnummer);
            System.out.println(locatie);

        } else {
            System.out.println(messages.getString("search.notfound"));

        }

    }


    //INKTINVOEREN

    public static void inktInvoeren(HashMap<String, String> inkten, Scanner scanner){
        System.out.print(messages.getString("add.prompt"));

        String newInkt = scanner.nextLine().trim();

        if (inkten.containsKey(newInkt)) {
            String alertMsg = MessageFormat.format(messages.getString("add.exists"),inkten.get(newInkt));

            System.out.println(alertMsg);
            System.out.print(messages.getString("add.change"));

            String keuze2 = scanner.nextLine();

            if (keuze2.equalsIgnoreCase("j") || keuze2.equalsIgnoreCase("y")) {

                System.out.print(messages.getString("add.newlocation"));
                String newLocatie = scanner.nextLine().toUpperCase();

                String SuccessMsg = MessageFormat.format(messages.getString("add.success"), newInkt, newLocatie);

                System.out.println(SuccessMsg);

                inkten.put(newInkt,newLocatie);

            }
            else {
                System.out.println(messages.getString("add.cancelled"));
            }
        }
        else {

            String locationMsg = MessageFormat.format(messages.getString("add.locationprompt"), newInkt);
            System.out.print(locationMsg);
            String newLocatie = scanner.nextLine().toUpperCase();

            String SuccessMsg = MessageFormat.format(messages.getString("add.success"), newInkt, newLocatie);
            System.out.println(SuccessMsg);
            inkten.put(newInkt, newLocatie);

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
                    String errorMsg1 = MessageFormat.format(messages.getString("file.skip"), lijn);
                    System.out.println(errorMsg1);
                }
            }


            bestandScanner.close();
            System.out.println(messages.getString("file.loaded"));
        }

            catch(FileNotFoundException e) {
                System.out.println(messages.getString("file.notfound"));


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
            System.out.println(messages.getString("file.saved"));
        }

        catch (IOException e){
            String errorMsg2 = MessageFormat.format(messages.getString("file.error"), e.getMessage());
            System.out.println(errorMsg2);
        }


    }

    }



