# Language Design Notes

This document describes the initial design and grammar of a small language.
The language is intentionally defined as a **subset of C**, chosen for
convenience and for its natural mapping to an assembler-oriented backend.

The goal at this stage is **expression parsing only**. The grammar will
expand incrementally as the compiler/interpreter pipeline becomes functional.

[ANSI C grammar reference](https://www.quut.com/c/ANSI-C-grammar-y.html)

---

## Current Capabilities

The following constructs are supported by the current grammar.

### Supported

```
1) Single number
42

2) Simple addition / subtraction
1 + 2
1 + 2 - 3

3) Operator precedence
1 + 2 * 3

4) Parentheses override precedence
(1 + 2) * 3

5) Chained multiplicative operators
20 / 5 / 2

6) Mixed operators
10 - 3 * 2 + 4

7) Deep nesting
((1 + 2) * (3 + 4)) % 5
```

---

### Not Supported Yet

The following features are intentionally excluded at this stage:

```

- Unary operators:
-3

- Identifiers / variables:
x + 1

- Assignment:
x = 3

- Function calls:
f(1, 2)

- Floating-point numbers:
3.14

- Postfix operators:
i++

- And many more...
```

---

## Grammar (Conceptual, Left-Recursive)

This is the initial, **conceptual grammar**, written in a left-recursive form
that is easier to reason about semantically. It encodes precedence and
left-associativity directly.

This grammar is **not suitable for recursive-descent parsing**.

```antlr
expression 
    : additive-expression
    ;

additive-expression 
    : multiplicative-expression
    | additive-expression '+' multiplicative-expression 
    | additive-expression '-' multiplicative-expression
    ;

multiplicative-expression
    : primary-expression
    | multiplicative-expression '*' primary-expression
    | multiplicative-expression '/' primary-expression
    | multiplicative-expression '%' primary-expression
    ;

primary-expression
    : NUMBER
    | '(' expression ')'
    ;

NUMBER : [0-9]+ ;
```

---

## Parser-ready (left recursion eliminated)

The following grammar is semantically equivalent to the conceptual grammar
above, but rewritten to eliminate left recursion. This form is suitable for a
recursive-descent (LL-style) parser.

```antlr
expression 
    : additive-expression
    ;


additive-expression
    : multiplicative-expression additive-expression'
    ;

additive-expression'
    : '+' multiplicative-expression additive-expression'
    | '-' multiplicative-expression additive-expression'
    | ε
    ;

    
multiplicative-expression
    : primary-expression multiplicative-expression'
    ;

multiplicative-expression'
    : '*' primary-expression multiplicative-expression'
    | '/' primary-expression multiplicative-expression'
    | '%' primary-expression multiplicative-expression'
    | ε
    ;
    

primary-expression
    : NUMBER
    | '(' expression ')'
    ;


NUMBER : [0-9]+ ;
```

---

## Even better

Above was the important step but here, is just further simplification, so that the
implementation part is straight forward.

The problem with above, although it is parser ready, it makes you kinda think to
implement two functions, one normal and prime, valid approach but cleaner is with
wile loops, and bellow definitions help to reason easier about it.


```antlr
expression 
    : additive-expression
    ;

additive-expression
    : multiplicative-expression (('+' | '-') multiplicative-expression)*
    ;
    
multiplicative-expression
    : primary-expression (('*' | '/' | '%') primary-expression)*
    ;
    
primary-expression
    : NUMBER
    | '(' expression ')'
    ;

NUMBER : [0-9]+ ;
```

---

## Notes

- Operator precedence is enforced structurally:
  - multiplicative operators bind tighter than additive operators
- All binary operators in this grammar are left-associative
- The grammar is intentionally minimal to support early AST construction,
evaluation, and testing
- Identifiers, unary operators, and assignment will be added after expression
parsing works end-to-end
