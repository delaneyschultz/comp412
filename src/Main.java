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
                        if (i != (args.length-1)){
                            names = args[i+1];
                        }
                        else{
                            System.out.println("ERROR: bad file name");
                        }
                        
                        break;
                    case "-p":
                        pflag = true;
                        if (i != (args.length-1)){
                            namep = args[i+1];
                        }
                        else{
                            System.out.println("ERROR: bad file name");
                        }
                        break;
                    case "-r":
                        rflag = true;
                        if (i != (args.length-1)){
                            namer = args[i+1];
                        }
                        else{
                            System.out.println("ERROR: bad file name");
                        }
                        break;
                    default:
                        break;
                }

            }

            if (names.equals("") && namep.equals("") && namer.equals("") && hflag == false){
                //default behavior
                if (args.length != 0){

                    namep = namep = args[0];
                    Parser parser= new Parser();
                            
                    try{
                        int flagp = 0;
                        parser.parse(namep, flagp);
    
                    }
                    catch (Exception e){
                       System.out.println("ERROR: cannot open file");
                    }

                }
            }
            else if (hflag == true){
                    System.out.println("Required arguments:");
                    System.out.println("\tfilename is the pathname (abosulte or relative) to the input file");
                    System.out.println("Optional flags:");
                    System.out.println("\t-h\tprints this message");
                    System.out.println("\t-l\tOpens log file \"./Log\" and starts logging.");
                    System.out.println("\t-v\tprints version number");
                    System.out.println("At most one of the following three flags:");
                    System.out.println("\t-s\tprints tokens in token stream");
                    System.out.println("\t-p\tinvokes parser and reports on success or failure");
                    System.out.println("\t-r\tprints human readable version of parser's IR");
            }
            else{
                if (hflag == true){
                    System.out.println("Required arguments:");
                    System.out.println("\tfilename is the pathname (abosulte or relative) to the input file");
                    System.out.println("Optional flags:");
                    System.out.println("\t-h\tprints this message");
                    System.out.println("\t-l\tOpens log file \"./Log\" and starts logging.");
                    System.out.println("\t-v\tprints version number");
                    System.out.println("At most one of the following three flags:");
                    System.out.println("\t-s\tprints tokens in token stream");
                    System.out.println("\t-p\tinvokes parser and reports on success or failure");
                    System.out.println("\t-r\tprints human readable version of parser's IR");
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
                               System.out.println("ERROR: cannot open file");
                            }
                            
                        }
                        else if(rflag == true){
                            Parser parser= new Parser();
                            try{
                                int flagr = 1;
                                parser.parse(namer, flagr);
                            }
                            catch (Exception e){
                                System.out.println("ERROR: cannot open file");
                            }
                        }
                    }
                }
            } 
            


            


            
        }
  

        
    }
}
