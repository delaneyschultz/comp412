import java.io.BufferedReader;
import java.io.FileReader;

public class Parser extends IR{

    public IR ir;

    public Parser(){
        ir = new IR();
    }

    public void parse(String name, int flag) throws Exception{
        Scanner scanner = new Scanner();
        try{
            
            FileReader file =new FileReader(name);    
            BufferedReader reader = new BufferedReader(file);
            boolean found_error = false;
            String line;  
            int op_num = 0;
            int errors = 0;
            int lines = 0;

            while(reader.ready()){  
                line = reader.readLine();
                line += "\n";
                String op_name;
                boolean error = false;
                Token word = scanner.next_token(line, false);
                if (word.getError() == true){
                    error = true;
                    errors += 1;
                }

                
                boolean opcode = word.getOp();

                while (!(word.getCategory().equals("NEWLINE")) && !(word.getCategory().equals("SKIP")) && error == false){
                    Operation op = new Operation();
                    
                    switch(word.getCategory()){
                        case "MEMOP":
                            op_num += 1;
                            op.setOp(word.getLexeme());
                            word = scanner.next_token(line, opcode);
                            if (word.getError() == true){
                                error = true;
                                errors += 1;
                            }
                            if (!(word.getCategory().equals("REG"))){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        Missing source register in load or store.");
                                error = true;
                                errors += 1;
                                break;
                            }
                            else{
                                op.setSR1("s" + word.getLexeme());
                                word = scanner.next_token(line, opcode);
                                if (word.getError() == true){
                                    error = true;
                                    errors += 1;
                                }
            
                                if (!(word.getCategory().equals("INTO"))){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        Missing '=>' in loadI");
                                error = true;
                                errors += 1;
                                break;
                                }
                                
                                word = scanner.next_token(line, opcode);
                                if (word.getError() == true){
                                    error = true;
                                    errors += 1;
                                }
                                if (!(word.getCategory().equals("REG"))){
                                    System.out.print("ERROR " + scanner.getLine());
                                    System.out.println(":        " + "Missing target register in load or store.");
                                    error = true;
                                    errors += 1;
                                    break;
                                }
                                else{
                                    op.setSR3("s" + word.getLexeme());
                                    word = scanner.next_token(line, opcode);
                                    if (word.getError() == true){
                                        error = true;
                                        errors += 1;
                                    }
        
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
                            if (word.getError() == true){
                                error = true;
                                errors += 1;
                            }
    
                            if (!(word.getCategory().equals("CONST"))){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        " + "Missing constant in loadI.");
                                error = true;
                                errors += 1;
                                break;
                            }
                            else{
                                op.setSR1("val " + word.getLexeme());
                                word = scanner.next_token(line, opcode);
                                if (word.getError() == true){
                                    error = true;
                                    errors += 1;
                                }
    
                                if (!(word.getCategory().equals("INTO"))){
                                    System.out.print("ERROR " + scanner.getLine());
                                    System.out.println(":        Missing '=>' in loadI");
                                    error = true;
                                    errors += 1;
                                    break;
                                }
                                
                                word = scanner.next_token(line, opcode);
                                if (word.getError() == true){
                                    error = true;
                                    errors += 1;
                                }

                                if (!(word.getCategory().equals("REG"))){
                                    System.out.print("ERROR " + scanner.getLine());
                                    System.out.println(":        " + "Missing target register in loadI.");
                                    error = true;
                                    errors += 1;
                                    break;
                                }
                                else{
                                    op.setSR3("s" + word.getLexeme());
                                    word = scanner.next_token(line, opcode);
                                    if (word.getError() == true){
                                        error = true;
                                        errors += 1;
                                    }

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
                            if (word.getError() == true){
                                error = true;
                                errors += 1;
                            }
                            
                            if (!(word.getCategory().equals("REG"))){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        " + "Missing first source register in " + op_name + ".");
                                error = true;
                                errors += 1;
                                break;
                            }
                            else{
                                op.setSR1("s" + word.getLexeme());
                                word = scanner.next_token(line, opcode);
                                if (word.getError() == true){
                                    error = true;
                                    errors += 1;
                                }

                                if (!(word.getCategory().equals("COMMA"))){
                                    System.out.print("ERROR " + scanner.getLine());
                                    System.out.println(":        " + "Missing comma in " + op_name + ".");
                                    error = true;
                                    errors += 1;
                                    break;
                                }
                                word = scanner.next_token(line, opcode);
                                if (word.getError() == true){
                                    error = true;
                                    errors += 1;
                                }

                                if (!(word.getCategory().equals("REG"))){
                                    System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        " + "Missing second source register in " + op_name + ".");
                                error = true;
                                errors += 1;
                                break;
                                }
                                else{
                                    op.setSR2("s" + word.getLexeme());
                                    word = scanner.next_token(line, opcode);
                                    if (word.getError() == true){
                                        error = true;
                                        errors += 1;
                                    }

                                    if (!(word.getCategory().equals("INTO"))){
                                        System.out.print("ERROR" + scanner.getLine());
                                        System.out.println(":\t" + "Missing '=>' in loadI.");
                                        error = true;
                                        errors += 1;
                                        break;
                                    }
                                    word = scanner.next_token(line,opcode);
                                    if (word.getError() == true){
                                        error = true;
                                        errors += 1;
                                    }

                                    if (!(word.getCategory().equals("REG"))){
                                        System.out.print("ERROR " + scanner.getLine());
                                        System.out.println(":        " + "Missing target register in " + op_name);
                                        error = true;
                                        errors += 1;
                                        break;
                                    }
                                    else{
                                        op.setSR3("s" + word.getLexeme());
                                        word = scanner.next_token(line, opcode);
                                        if (word.getError() == true){
                                            error = true;
                                            errors += 1;
                                        }
                                        
                                        if (word.getCategory().equals("NEWLINE") || word.getCategory().equals("SKIP")){
                                            
                                            //add it to the list of ops
                                        }
                                        else{
                                            System.out.print("ERROR " + scanner.getLine());
                                            System.out.println(":        " + "Extra token at end of line " + word.getLexeme() + " (" + word.getCategory() + ").");
                                            error = true;
                                            errors += 1;
                                        }
                                    }
                                }
                            }
                            break;
                        case "OUTPUT":
                            op_num += 1;
                            op.setOp(word.getLexeme());
                            word = scanner.next_token(line, opcode);
                            if (word.getError() == true){
                                error = true;
                                errors += 1;
                            }
    
                        
                            if (!(word.getCategory().equals("CONST"))){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        " + "Missing constant in output.");
                                error = true;
                                errors += 1;
                                break;
                            }
                            else{
                                op.setSR1("val " + word.getLexeme());
                                word = scanner.next_token(line, opcode);
                                if (word.getError() == true){
                                    error = true;
                                    errors += 1;
                                }

                            
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
                            word = scanner.next_token(line, opcode);
                            if (!word.getCategory().equals("NEWLINE") && !word.getCategory().equals("SKIP")){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        " + "Extra token at end of line " + word.getLexeme());
                                error = true;
                                errors += 1;
                            }
                            break;

                        case "COMMA":
                            if (op.opcode.equals("")){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        " + "Operation starts with an invalid opcode: " + word.getLexeme());
                                error = true;
                                errors += 1;
                            }
                            word = scanner.next_token(line, opcode);
                            if (word.getError() == true){
                                error = true;
                                errors += 1;
                            }
    
                            break;
                        case "CONST":
                            if (op.opcode.equals("")){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        " + "Operation starts with an invalid opcode: " + word.getLexeme());
                                error = true;
                                errors += 1;
                            }
                            word = scanner.next_token(line, opcode);
                            if (word.getError() == true){
                                error = true;
                                errors += 1;
                            }
    
                            break;
                            
                        case "REG":
                            if (op.opcode.equals("")){
                                System.out.print("ERROR " + scanner.getLine());
                                System.out.println(":        " + "Operation starts with an invalid opcode: " + word.getLexeme());
                                error = true;
                                errors += 1;
                            }
                            word = scanner.next_token(line, opcode);
                            if (word.getError() == true){
                                error = true;
                                errors += 1;
                            }
    
                            break;
                        default:
                            
                            break;

                    }

                    if (error == false && !(word.getCategory().equals("NEWLINE")) && !(word.getCategory().equals("SKIP"))){
                        word = scanner.next_token(line, opcode);
                        if (word.getError() == true){
                            error = true;
                            errors += 1;
                        }
                    }
                    ir.addNode(op);
                    
                }

                scanner.setLine();
                scanner.setPos();

                if (error == true){
                    found_error = true;
                    lines += 1;
                }

            }
            reader.close();    
            file.close(); 

            if (found_error == false && flag == 1){
                ir.printNodes();
            }
            else if (found_error == false && flag == 0){
                System.out.print("Parse succeeded, finding " + op_num);
                System.out.println(" ILOC operations.");
            }
            else{
                System.out.println("");
                System.out.print(name + " found " + errors);
                System.out.print(" errors on " + lines);
                System.out.println(" lines.");
            }
        
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("ERROR: could not open file");
        }


        

    }


    


}
