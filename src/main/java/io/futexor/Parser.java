package io.futexor;

import io.futexor.ir.ast.Node;

import java.util.ArrayList;

public class Parser {

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


    private Node.Operator toOperator(Word token) {
        return switch (token.type) {
            case ADD -> Node.Operator.ADD;
            case SUB -> Node.Operator.SUB;
            case MUL -> Node.Operator.MUL;
            case DIV -> Node.Operator.DIV;
            case MOD -> Node.Operator.MOD;
            default -> null;
        };
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
            var op = toOperator(advance());
            Node right = multiplicativeExpression();
            left = new Node.Binary(op, left, right);
        }
        return left;
    }

    private Node multiplicativeExpression() {
        Node left = primaryExpression();
        while (peek().type == TokenType.MUL || peek().type == TokenType.DIV || peek().type == TokenType.MOD) {
            var op = toOperator(advance());
            Node right = primaryExpression();
            left = new Node.Binary(op, left, right);
        }
        return left;
    }

    private Node primaryExpression() {

        if (match(TokenType.NUMBER)) {
            return new Node.Literal(Integer.parseInt(previous().lexeme));
        }
        if (match(TokenType.LPAREN)) {
            Node inside = expression();
            expect(TokenType.RPAREN, ") missing");
            return inside;
        }
        error("Expected NUMBER or '('");
        return null; // this should not be reachable

    }

}
