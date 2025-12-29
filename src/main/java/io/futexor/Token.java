package io.futexor;

public class Token {

    public final TokenType type;
    private final String lexeme;
    private final int line;
    private final int col;

    public Token(TokenType type, String lexeme, int line, int col) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.col = col;
    }

    @Override
    public String toString() {
        return type + "";
    }

}
