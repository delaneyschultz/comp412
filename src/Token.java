import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;


public class Token {

    public String lexeme;
    public String category;
    public int line_num;
    public boolean opcode;

    public Token(int line){
        lexeme = "";
        category = "";
        line_num = line;
    }

    public String getCategory(){
        return category;
    }

    public String getLexeme(){
        return lexeme;
    }
    
    public boolean getOp(){
        return opcode;
    }


    public int getLine(){
        return line_num;
    }

    public void setCategory(String c){
        category = c;
    }

    public void setLexeme(String l){
        lexeme = l;
    }
}