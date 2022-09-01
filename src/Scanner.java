
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Scanner{

    public int line_num;
    public int pos;
    public boolean opcode;

    public Scanner(){
        line_num = 1;
        pos = 0;
    }

    public int getLine(){
        return line_num;
    }

    public void setLine(){
        line_num += 1;
    }

    public void setPos(){
        pos = 0;
    }

    public boolean getOp(){
        return opcode;
    }


    public void setOp(boolean op){
       opcode = op;
    }



    public Token next_token(String line, boolean opcode){
        Token token = new Token(line_num);
        scan(token, line, opcode);

        return token;
    }

    public void scan(Token token, String line, boolean opcode){
        
        char c = line.charAt(pos);
        String lexeme = "";

        lexeme += c;
        find(token, line, opcode, lexeme);

    }

    public void find(Token token, String line, boolean opcode, String lexeme){
        List<String> categories = Arrays.asList("MEMOP", "LOADI", "ARITHOP", "OUTPUT", "NOP", "CONST", "REG", "COMMA", "INTO", "ENDFILE", "NEWLINE", "SKIP");
        List<String> numbers = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

        char c;

        if (pos == line.length() || line.charAt(pos) == '\n'){
            token.setCategory(categories.get(10));
            token.setLexeme("");
        }
        else {

                while (lexeme.equals(" ") || lexeme.equals("")){
                    pos += 1;
                    c = line.charAt(pos);
                    lexeme = "";
                    lexeme += c;    
                }


                switch (lexeme){
                    case "/":
                        if (line.charAt(pos+1) == '/'){
                           
                            if (opcode == true){
                                while(pos < line.length()){
                                    pos += 1;
                                }
                                token.setCategory(categories.get(10)); 
                            }
                            else{
                                token.setCategory(categories.get(11)); 
                            }
                               
                        }
                        else{
                            System.out.println("ERROR " + token.getLine() + ": " + lexeme + " is not a valid word.");
                            token.setCategory(categories.get(10));
                            token.setLexeme("");
                        }
                        pos += 2;
                        break;
                    case "s":
                        if (line.substring(pos+1,pos+5).equals("tore")){
                            token.setCategory(categories.get(0));
                            token.setLexeme(lexeme += line.substring(pos+1,pos+5));
                            pos += 5;
                            opcode = true;
                            token.opcode = true;
                            break;
                        }
                        else if (line.substring(pos+1,pos+3).equals("ub")){
                            token.setCategory(categories.get(2));
                            token.setLexeme(lexeme += line.substring(pos+1,pos+3)); 
                            pos += 3;
                            opcode = true;
                            
                        }
                        break;
                    case "l":
                        if (line.substring(pos+1,pos+4).equals("oad")){
                            if (line.charAt(pos+4) == 'I'){
                                token.setCategory(categories.get(1));
                                token.setLexeme(lexeme = lexeme + line.substring(pos+1,pos+5));
                                pos += 5;
                                opcode = true;
                        
                            }
                            else{
                                token.setCategory(categories.get(0));
                                token.setLexeme(lexeme += line.substring(pos+1,pos+4));
                                pos += 4;
                                opcode = true;
                                
                            }
                        }
                        else if (line.substring(pos+1,pos+6).equals("shift")){ 
                            token.setCategory(categories.get(2));
                            token.setLexeme(lexeme += line.substring(pos+1,pos+6));
                            pos += 6;
                            opcode = true;
                            
                        }
                        break;
                    case "r":
                        
                        if ((pos+6) < line.length() && line.substring(pos+1,pos+6).equals("shift")){
                            token.setCategory(categories.get(2));
                            token.setLexeme(lexeme += line.substring(pos+1,pos+6));
                            pos += 6;
                    
                        }
                        else if (numbers.contains(Character.toString(line.charAt(pos+1)))){
                            
                            int curr = Integer.parseInt(Character.toString(line.charAt(pos+1)));
                            String next = Character.toString(line.charAt(pos+2));
                            pos += 1;
                            while (numbers.contains(next)){
                                curr = curr * 10 +  Integer.parseInt(next);
                                pos += 1;
                                next = Character.toString(line.charAt(pos+1));
                            }
                            token.setCategory(categories.get(6));
                            token.setLexeme("r" + String.valueOf(curr));
                            pos += 1;
                            opcode = false;

                        }
                        else{
                            System.out.println("ERROR " + token.getLine() + ": " + lexeme + " is not a valid word.");
                            token.setCategory(categories.get(10));
                            token.setLexeme("");
                        }
                        
                        break;
                    case "m":
                        if (line.substring(pos+1,pos+4).equals("ult")){
                            token.setCategory(categories.get(2));
                            token.setLexeme(lexeme += line.substring(pos+1,pos+4));
                            pos += 4;
                            opcode = true;
                        }
                        else{
                            System.out.println("ERROR " + token.getLine() + ": " + lexeme + " is not a valid word.");
                            token.setCategory(categories.get(10));
                            token.setLexeme("");
                        }
                        break;
                    case "n":
                        if (line.substring(pos+1,pos+3).equals("op")){
                            token.setCategory(categories.get(4));
                            token.setLexeme(lexeme += line.substring(pos+1,pos+3));
                            pos += 3;
                            opcode = true;
                            

                        }
                        else{
                            System.out.println("ERROR " + token.getLine() + ": " + lexeme + " is not a valid word.");
                            token.setCategory(categories.get(10));
                            token.setLexeme("");
                        }
                        break;
                    case "a":
                        if (line.substring(pos+1,pos+3).equals("dd")){
                            token.setCategory(categories.get(2));
                            token.setLexeme(lexeme += line.substring(pos+1,pos+3));
                            opcode = true;
                        }
                        else{
                            lexeme += line.charAt(pos+1);
                            System.out.println("ERROR " + token.getLine() + ": " + lexeme + " is not a valid word.");
                            token.setCategory(categories.get(10));
                            token.setLexeme("");
                        }
                        pos += 3;
            
                        break;
                    case "o":
                        if (line.substring(pos+1,pos+6).equals("utput")){
                            token.setCategory(categories.get(3));
                            token.setLexeme(lexeme += line.substring(pos+1,pos+6));
                            opcode = true;
                        }
                        else{
                            System.out.println("ERROR " + token.getLine() + ": " + lexeme + " is not a valid word.");
                            token.setCategory(categories.get(10));
                            token.setLexeme("");
                        }
                        pos += 6;
                
                        break;
                    case "=":
                        if (line.charAt(pos+1) == '>'){
                            token.setCategory(categories.get(8));
                            token.setLexeme(lexeme += line.charAt(pos+1));
                        }
                        pos += 2;
                        break;  
                    case ",":
                        token.setCategory(categories.get(7));
                        token.setLexeme(lexeme);
                        pos += 1;
                        break;
                    default:
                        if (numbers.contains(lexeme)){
                            int curr = Integer.parseInt(lexeme);
                            String next = line.substring(pos+1,pos+2);
                            while (numbers.contains(next)){
                                curr = curr * 10 +  Integer.parseInt(next);
                                pos += 1;
                                next = line.substring(pos+1,pos+2);
                            }
                            token.setCategory(categories.get(5));
                            token.setLexeme(String.valueOf(curr));
                            pos += 1;
                            opcode = false;
                            break;
                        }
                        else{
                            if (lexeme.isBlank()){
                                while (lexeme.isBlank() && (pos != line.length()-1)){
                                    pos += 1;
                                    c = line.charAt(pos);
                                    lexeme = "";
                                    lexeme += c;
                                }

                                if (pos == (line.length()-1)){
                                    token.setCategory(categories.get(10));
                                    token.setLexeme("");
                                }

                                if (!(token.getCategory().equals("NEWLINE"))){
                                    find(token, line, opcode, lexeme);
                                }
                                else{
                                    break;
                                }
                                
                            }
                            else{
                                System.out.println("ERROR " + token.getLine() + ": " + lexeme + " is not a valid word.");
                                token.setCategory(categories.get(10));
                                token.setLexeme("");
                            }

                        }

                }
    
        
        }

        

        setOp(opcode);
    }

}
