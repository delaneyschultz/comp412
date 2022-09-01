import java.io.BufferedReader;
import java.io.FileReader;

public class Parser extends IR{

    public IR ir;

    public Parser(){
        ir = new IR();
    }

    public void parse(String name, int flag) throws Exception{
        Scanner scanner = new Scanner();
        FileReader file =new FileReader(name);    
        BufferedReader reader = new BufferedReader(file);

        boolean found_error = false;
        String line;  
        int op_num = 0;

        while(reader.ready()){  
            line = reader.readLine();
            line += "\n";
            String op_name;
            Token word = scanner.next_token(line, false);
            boolean error = false;
            boolean opcode = word.getOp();

            while (!(word.getCategory().equals("NEWLINE")) && !(word.getCategory().equals("SKIP")) && error == false){
                Operation op = new Operation();
                
                switch(word.getCategory()){
                    case "MEMOP":
                        op_num += 1;
                        op.setOp(word.getLexeme());
                        word = scanner.next_token(line, opcode);

                        if (!(word.getCategory().equals("REG"))){
                            System.out.println("ERROR " + scanner.getLine() + " Missing source register in load or store.");
                            error = true;
                            break;
                        }
                        else{
                            op.setSR1(word.getLexeme());
                            word = scanner.next_token(line, opcode);
                            
                         
                            if (!(word.getCategory().equals("INTO"))){
                               System.out.println("ERROR " + scanner.getLine() + " Missing '=>' in loadI");
                               error = true;
                               break;
                            }
                            
                            word = scanner.next_token(line, opcode);

                            if (!(word.getCategory().equals("REG"))){
                                System.out.println("ERROR " + scanner.getLine() + " Missing target register in load or store.");
                                error = true;
                                break;
                            }
                            else{
                                 op.setSR3(word.getLexeme());
                                 word = scanner.next_token(line, opcode);
              
    
                                 if (word.getCategory().equals("NEWLINE") || word.getCategory().equals("SKIP")){
                                     break;
                                 }
                                 else{
                                    
                                     System.out.println("Error");
                                 }
                            }
                        }
                        break;
                    case "LOADI":
                        op_num += 1;
                        op.setOp(word.getLexeme());
                        word = scanner.next_token(line, opcode);
  
                        if (!(word.getCategory().equals("CONST"))){
                            System.out.println("ERROR " + scanner.getLine() + " Missing constant in loadI.");
                            error = true;
                            break;
                        }
                        else{
                            op.setSR1(word.getLexeme());
                            word = scanner.next_token(line, opcode);
 
                            if (!(word.getCategory().equals("INTO"))){
                                System.out.println("ERROR " + scanner.getLine() + " Missing '=>' in loadI");
                                error = true;
                                break;
                            }
                            
                            word = scanner.next_token(line, opcode);

                            if (!(word.getCategory().equals("REG"))){
                                System.out.println("ERROR " + scanner.getLine() + " Missing target register in loadI.");
                                error = true;
                                break;
                            }
                            else{
                                 op.setSR3(word.getLexeme());
                                 word = scanner.next_token(line, opcode);

                                 if (word.getCategory().equals("NEWLINE") || (word.getCategory().equals("SKIP"))){
                                    
                                     //do nothing yet
                                 }
                                 else{
                                     System.out.println("Error");
                                 }
                            }
                        }
                        break;
                    case "ARITHOP":
                        op_num += 1;
                        op.setOp(word.getLexeme());
                        op_name = word.getLexeme();
                        word = scanner.next_token(line, opcode);
                        
                        if (!(word.getCategory().equals("REG"))){
                            System.out.println("ERROR " + scanner.getLine() + " Missing first source register in " + op_name + ".");
                            error = true;
                            break;
                        }
                        else{
                            op.setSR1(word.getLexeme());
                            word = scanner.next_token(line, opcode);

                            if (!(word.getCategory().equals("COMMA"))){
                                System.out.println("ERROR " + scanner.getLine() + " Missing comma in " + op_name + ".");
                                error = true;
                                break;
                            }
                            word = scanner.next_token(line, opcode);

                            if (!(word.getCategory().equals("REG"))){
                               System.out.println("ERROR " + scanner.getLine() + " Missing second source register in " + op_name + ".");
                               error = true;
                               break;
                            }
                            else{
                                op.setSR2(word.getLexeme());
                                word = scanner.next_token(line, opcode);

                                if (!(word.getCategory().equals("INTO"))){
                                    System.out.println("ERROR " + scanner.getLine() + " Missing '=>' in loadI");
                                    error = true;
                                    break;
                                }
                                word = scanner.next_token(line,opcode);

                                if (!(word.getCategory().equals("REG"))){
                                    System.out.println("ERROR " + scanner.getLine() + " Missing target register in " + op_name);
                                    error = true;
                                    break;
                                }
                                else{
                                    op.setSR3(word.getLexeme());
                                    word = scanner.next_token(line, opcode);
                                    
                                    if (word.getCategory().equals("NEWLINE") || word.getCategory().equals("SKIP")){
                                        
                                        //add it to the list of ops
                                    }
                                    else{
                                        System.out.println("ERROR " + scanner.getLine() + ": Extra token at end of line " + word.getLexeme() + " (" + word.getCategory() + ").");
                                    }
                                }
                            }
                        }
                        break;
                    case "OUTPUT":
                        op_num += 1;
                        op.setOp(word.getLexeme());
                        word = scanner.next_token(line, opcode);
 
                       
                        if (!(word.getCategory().equals("CONST"))){
                            System.out.println("ERROR " + scanner.getLine() + " Missing constant in output.");
                            error = true;
                            break;
                        }
                        else{
                            op.setSR1(word.getLexeme());
                            word = scanner.next_token(line, opcode);

                        
                            if (word.getCategory().equals("NEWLINE") || word.getCategory().equals("SKIP")){
                                
                                //add it to the list of ops
                            }
                            else{
                                System.out.println("Error");
                            }
                        }
                        break;
                    case "NOP":
                        op_num += 1;
                        op.setOp(word.getLexeme());
                        

                    default:
                        
                        break;

                }

                if (error == false && !(word.getCategory().equals("NEWLINE")) && !(word.getCategory().equals("SKIP"))){
                    word = scanner.next_token(line, opcode);
                }
                ir.addNode(op);
                
            }

            scanner.setLine();
            scanner.setPos();

            if (error == true){
                found_error = true;
            }

        }
        reader.close();    
        file.close(); 

        if (found_error == false && flag == 1){
            ir.printNodes();
        }
        else if (found_error == false && flag == 0){
            System.out.println("Parse succeeded, finding " + op_num + " ILOC operations.");
        }
        else{
            System.out.println("");
            System.out.println("Due to syntax errors, run terminates.");
        }
        

    }

    


}