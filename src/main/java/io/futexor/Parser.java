package io.futexor;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    public class Node {
        Word word;
        ArrayList<Node> children;

        public Node(Word word, Node... children) {
            this.word = word;
            this.children = new ArrayList<>();

            // todo: figure out better solution
            this.children.addAll(Arrays.asList(children));
        }

        public Node(Word word) {
            this.word = word;
            this.children = null;
        }
    }

    ArrayList<Word> words;
    int cursor = 0;


    public Parser(ArrayList<Word> words) {
        this.words = words;
    }

    // helpers

    private Word peek() {
        return words.get(cursor);
    }

    private Word previous() {
        return words.get(cursor - 1);
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private boolean check(TokenType type) {
        return peek().type == type;
    }

    private Word advance() {
        if (!isAtEnd()) cursor++;
        return previous();
    }

    // If next token is one of the types, consume it and return true.
    private boolean match(TokenType... types) {
        for (TokenType t : types) {
            if (check(t)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Word expect(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(message + " (found " + peek() + ")");
    }

    private RuntimeException error(String message) {
        Word t = peek();
        return new RuntimeException("Parse error at token " + t + " @ " + "" + ": " + message);
    }

    Node parse() {
        Node n = expression();
        expect(TokenType.EOF, "Expected end of input");
        return n;
    }


    // from grammar

    private Node expression() {
        return additiveExpression();
    }

    private Node additiveExpression() {
        Node left = multiplicativeExpression();
        while (peek().type == TokenType.ADD || peek().type == TokenType.SUB) {
            Word op = advance();
            Node right = multiplicativeExpression();
            left = new Node(op, left, right);
        }
        return left;
    }

    private Node multiplicativeExpression() {
        Node left = primaryExpression();
        while (peek().type == TokenType.MUL || peek().type == TokenType.DIV || peek().type == TokenType.MOD) {
            Word op = advance();
            Node right = primaryExpression();
            left = new Node(op, left, right);
        }
        return left;
    }

    private Node primaryExpression() {

        if (match(TokenType.NUMBER)) return new Node(previous());
        if (match(TokenType.LPAREN)) {
            Node inside = expression();
            expect(TokenType.RPAREN, ") missing");
            return inside;
        }
        error("Expected NUMBER or '('");
        return null; // this should not be reachable


    }

}
