package io.futexor;

/*
* Reads a stream of characters and produces a stream of words.
*/

import java.util.ArrayList;

public class Scanner {

    private final String code;
    private int cursor = 0;

    public Scanner(String code) {
        this.code = code;
    }

    public ArrayList<Token> scan() {
        ArrayList<Token> tokens = new ArrayList<>();

        while (cursor < code.length()) {
            char c = nextChar();

            // process digits
            if (isDigit(c)) {
                int start = cursor;
                while (cursor < code.length() && isDigit(code.charAt(cursor))) {
                    cursor++;
                }
                String lexeme = code.substring(start, cursor);
                tokens.add(new Token(TokenType.NUMBER, lexeme, 0, 0));
                continue;
            }

            if (c == '(') tokens.add(new Token(TokenType.LPAREN, "(", 0, 0));
            if (c == ')') tokens.add(new Token(TokenType.RPAREN, ")", 0, 0));

            if (c == '*') tokens.add(new Token(TokenType.MUL, "*", 0, 0));
            if (c == '/') tokens.add(new Token(TokenType.DIV, "/", 0, 0));
            if (c == '%') tokens.add(new Token(TokenType.MOD, "%", 0, 0));

            if (c == '+') tokens.add(new Token(TokenType.ADD, "+", 0, 0));
            if (c == '-') tokens.add(new Token(TokenType.SUB, "-", 0, 0));



            cursor++;
        }
        return tokens;
    }

    private char nextChar() {
        return code.charAt(cursor);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

}
