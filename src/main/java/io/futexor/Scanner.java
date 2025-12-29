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

    public ArrayList<Word> scan() {
        ArrayList<Word> words = new ArrayList<>();

        while (cursor < code.length()) {
            char c = nextChar();

            // process digits
            if (isDigit(c)) {
                int start = cursor;
                while (cursor < code.length() && isDigit(code.charAt(cursor))) {
                    cursor++;
                }
                String lexeme = code.substring(start, cursor);
                words.add(new Word(TokenType.NUMBER, lexeme, 0, 0));
                continue;
            }

            if (c == '(') words.add(new Word(TokenType.LPAREN, "(", 0, 0));
            if (c == ')') words.add(new Word(TokenType.RPAREN, ")", 0, 0));

            if (c == '*') words.add(new Word(TokenType.MUL, "*", 0, 0));
            if (c == '/') words.add(new Word(TokenType.DIV, "/", 0, 0));
            if (c == '%') words.add(new Word(TokenType.MOD, "%", 0, 0));

            if (c == '+') words.add(new Word(TokenType.ADD, "+", 0, 0));
            if (c == '-') words.add(new Word(TokenType.SUB, "-", 0, 0));



            cursor++;
        }

        words.add(new Word(TokenType.EOF, "", 0, 0));

        return words;
    }

    private char nextChar() {
        return code.charAt(cursor);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

}
