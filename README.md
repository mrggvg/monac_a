# monac

**monac** is a small compiler for a C-inspired language that targets a custom
16-bit assembly simulator used in a university hardware systems course.

The project started as a personal exploration—and partly as a joke—but its
serious goal is to help students better understand how high-level constructs
map onto low-level machine operations such as register usage, stack
manipulation, and memory movement.

Rather than aiming for full C compatibility, monac intentionally focuses on a
minimal subset of the language, making the compilation process transparent and
educational.

## Motivation

Modern languages hide too much of the machine. monac is designed to expose it.

With a very limited target architecture (few registers, explicit stack usage,
restricted arithmetic), the compiler demonstrates that surprisingly complex
programs can still be built, understood, and reasoned about.

## Ideas and Goals

Some directions explored or planned for this project include:

- Enabling students to write and observe **recursive functions** on a highly
  constrained architecture
- Demonstrating how function calls, stack frames, and register allocation work
  in practice
- Experimenting with **dynamic memory allocation** on a simple memory model
- Providing a concrete bridge between a C-like language and assembly execution

## Status

Early development.

The compiler currently focuses on expression parsing and incremental language
design. See the language and grammar notes in [`docs/language.md`](docs/language.md).

---

This project prioritizes clarity, learning, and exploration over completeness.

> The name has an intentional double meaning.




