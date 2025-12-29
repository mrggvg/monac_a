package io.futexor;

public class Word {

    public final TokenType type;
    private final String lexeme;
    private final int line;
    private final int col;

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
