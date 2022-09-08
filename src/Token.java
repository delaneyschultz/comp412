


public class Token {

    public String lexeme;
    public String category;
    public int line_num;
    public boolean opcode;
    public boolean error;

    public Token(int line){
        lexeme = "";
        category = "";
        line_num = line;
        error = false;
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

    public boolean getError(){
        return error;
    }

    public void setError(boolean e){
        error = e;
    }


    public void setCategory(String c){
        category = c;
    }

    public void setLexeme(String l){
        lexeme = l;
    }
}