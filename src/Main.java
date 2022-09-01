import java.io.BufferedReader;
import java.io.FileReader;


public class Main {


    public static void scan_file(String filename) throws Exception{

        FileReader file =new FileReader(filename);    
        BufferedReader reader = new BufferedReader(file);

        Scanner scanner = new Scanner();
        String line;  

        Token token = new Token(0);
        while(reader.ready()){  
            line = reader.readLine();
            line += "\n";
            token = scanner.next_token(line, false);
            while (!(token.getCategory().equals("NEWLINE")) && !(token.getCategory().equals("SKIP"))){
                System.out.println(token.getLine() + ": < " + token.getCategory() + ", " + "\"" + token.getLexeme() + "\"" + " >");

                token = scanner.next_token(line, scanner.getOp());
            }

            System.out.println(token.getLine() + ": < " + "NEWLINE" + ", " + "\"\\n\"" + " >");

            scanner.setLine();
            scanner.setPos();
            scanner.setOp(false);

        }


        System.out.println(scanner.getLine() + ": < " + "ENDFILE" + ", " + "\"\"" + " >");

        reader.close();    
        file.close();  
    }


    public static void main (String[] args){

        boolean hflag = false;
        boolean sflag = false;
        boolean pflag = false;
        boolean rflag = false;

        String names = "";
        String namep = "";
        String namer = "";

        if (args.length == 0){
            System.out.println("Command line is empty");
        }
        else{
            for (int i=0; i < args.length; i++){
                switch (args[i]){
                    case "-h":
                        hflag = true;
                        break;
                    case "-s":
                        sflag = true;
                        names = args[i+1];
                        break;
                    case "-p":
                        pflag = true;
                        namep = args[i+1];
                        break;
                    case "-r":
                        rflag = true;
                        namer = args[i+1];
                        break;
                    default:
                        break;
                }

            }

            if (hflag == true){
                System.out.println("-h: help");
                System.out.println("-s: read the file specified by <name> and print, to the standard output stream, a list of the tokens that the scanner found");
                System.out.println("-p: read the file specified by <name>, scan it and parse it, build the intermediate representation");
                System.out.println("-r: read the file specified by <name>, scan it, parse it, build the intermediate representation, and print out the information in the intermediate representation");
            }
            else{
                if (sflag == true){
                    if (names == ""){
                        System.out.println("No file name found.");
                    }
                    else{
                        try{
                            scan_file(names);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        
                    }
                    
                }
                else{
                    if (pflag == true){
                        Parser parser= new Parser();
                        try{
                            int flagp = 0;
                            parser.parse(namep, flagp);
        
                        }
                        catch (Exception e){
                           e.printStackTrace();
                        }
                        
                    }
                    else if(rflag == true){
                        Parser parser= new Parser();
                        try{
                            int flagr = 1;
                            parser.parse(namer, flagr);
                        }
                        catch (Exception e){
                           e.printStackTrace();
                        }
                    }
                }
            }
            
        }
        
    }
}