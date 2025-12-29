package io.futexor;

public class Word {

    public final TokenType type;
    public final String lexeme;
    public final int line;
    public final int col;

    public Word(TokenType type, String lexeme, int line, int col) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.col = col;
    }

    @Override
    public String toString() {
        return type + "{ " + lexeme + " }";
    }

}
