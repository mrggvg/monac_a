package io.futexor.lexer;

public enum TokenType {

    // Keywords
    RETURN, IF, ELSE, WHILE, BREAK,
    // Types
    VOID, BYTE, WORD,
    // Operators
    EQ, NEQ, LE, GE, ASSIGN, LT, GT,
    ADD, SUB, MUL, DIV, MOD,
    // Others
    SEMICOLON, LPAREN, RPAREN, LBRACE, RBRACE, COMMA,
    // Literals/Identifiers
    CONSTANT, IDENTIFIER,
    EOF

}
