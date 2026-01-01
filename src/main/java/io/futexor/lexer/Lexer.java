package io.futexor.lexer;

import java.util.*;
import java.util.regex.*;

public class Lexer {

    enum TokenType {
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

    static class Token {
        final TokenType type;
        final String lexeme;
        final int line, column;

        Token(TokenType type, String lexeme, int line, int column) {
            this.type = type;
            this.lexeme = lexeme;
            this.line = line;
            this.column = column;
        }

        @Override public String toString() {
            return type + "('" + lexeme + "')@" + line + ":" + column;
        }
    }

    private record Rule(TokenType type, Pattern pattern, boolean skip) {}

    private static final List<Rule> RULES = List.of(
            // Skip first (comments/whitespace)
            new Rule(null, Pattern.compile("//[^\\r\\n]*"), true),                 // LINE_COMMENT
            new Rule(null, Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL), true),  // BLOCK_COMMENT
            new Rule(null, Pattern.compile("[ \\t\\r\\n\\f]+"), true),             // WHITE_SPACE

            // Multi-char operators first
            new Rule(TokenType.EQ,     Pattern.compile("=="), false),
            new Rule(TokenType.NEQ,    Pattern.compile("!="), false),
            new Rule(TokenType.LE,     Pattern.compile("<="), false),
            new Rule(TokenType.GE,     Pattern.compile(">="), false),

            // --- Single-char operators ---
            new Rule(TokenType.ASSIGN, Pattern.compile("="), false),
            new Rule(TokenType.LT,     Pattern.compile("<"), false),
            new Rule(TokenType.GT,     Pattern.compile(">"), false),
            new Rule(TokenType.ADD,    Pattern.compile("\\+"), false),
            new Rule(TokenType.SUB,    Pattern.compile("-"), false),
            new Rule(TokenType.MUL,    Pattern.compile("\\*"), false),
            new Rule(TokenType.DIV,    Pattern.compile("/"), false),
            new Rule(TokenType.MOD,    Pattern.compile("%"), false),

            // --- Punctuation ---
            new Rule(TokenType.SEMICOLON, Pattern.compile(";"), false),
            new Rule(TokenType.LPAREN,    Pattern.compile("\\("), false),
            new Rule(TokenType.RPAREN,    Pattern.compile("\\)"), false),
            new Rule(TokenType.LBRACE,    Pattern.compile("\\{"), false),
            new Rule(TokenType.RBRACE,    Pattern.compile("}"), false),
            new Rule(TokenType.COMMA,     Pattern.compile(","), false),

            // --- Literals ---
            new Rule(TokenType.CONSTANT, Pattern.compile("0|[1-9][0-9]*"), false),

            // --- Identifiers/Keywords ---
            // Match identifier first, then re-type as keyword/type.
            new Rule(TokenType.IDENTIFIER, Pattern.compile("[a-z][a-z0-9_]*"), false)
    );

    private static final Map<String, TokenType> KEYWORDS = Map.ofEntries(
            Map.entry("return", TokenType.RETURN),
            Map.entry("if", TokenType.IF),
            Map.entry("else", TokenType.ELSE),
            Map.entry("while", TokenType.WHILE),
            Map.entry("break", TokenType.BREAK),
            Map.entry("void", TokenType.VOID),
            Map.entry("byte", TokenType.BYTE),
            Map.entry("word", TokenType.WORD)
    );

    public List<Token> tokenize(String source) {
        List<Token> tokens = new ArrayList<>();

        int cursor = 0;
        int line = 1;
        int col = 1;

        while (cursor < source.length()) {
            boolean matched = false;

            for (Rule rule : RULES) {
                Matcher m = rule.pattern.matcher(source);
                m.region(cursor, source.length());

                if (!m.lookingAt()) continue;

                matched = true;
                String lexeme = m.group();

                // Update line/col BEFORE cursor moves
                int startLine = line;
                int startCol  = col;

                // Advance cursor and update line/column counters
                for (int i = 0; i < lexeme.length(); i++) {
                    char ch = lexeme.charAt(i);
                    if (ch == '\n') { line++; col = 1; }
                    else { col++; }
                }
                cursor = m.end();

                if (!rule.skip) {
                    TokenType type = rule.type;

                    // keyword re-typing
                    if (type == TokenType.IDENTIFIER) {
                        TokenType kw = KEYWORDS.get(lexeme);
                        if (kw != null) type = kw;
                    }

                    tokens.add(new Token(type, lexeme, startLine, startCol));
                }

                break; // important: first matching rule wins (because order encodes precedence)
            }

            if (!matched) {
                char bad = source.charAt(cursor);
                throw new RuntimeException("Unexpected character '" + bad + "' at " + line + ":" + col);
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line, col));
        return tokens;
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        String src = """
            void main() {
              // comment
              word x = 10;
              if (x >= 1) return;
            }
            """;
        lexer.tokenize(src).forEach(System.out::println);
    }
}
