# Requirement Specification

For the mvp it is focus on making it useful as well as fast dev.

## Hard Requirements

- There is going to be only single data type, due to the fact that target
architecture is 16-bit asm simulator, which supports 16-bit addressing mode 
as well as 8-bit one, thus for simplicity's sake only one type, and it is going
to be implicit in source code.


## Supported Code Snippets



```
swap(x, y) {        // swap expects x and y to be addresses (u16 pointers)
    t = *x;         // load value at address x
    *x = *y;        // store value at address y into address x
    *y = t;         // store original x value into address y
}

main() {            // program entry point
    a = 1;          // allocate a, store 1
    b = 2;          // allocate b, store 2
    swap(&a, &b);   // pass addresses of a and b so swap mutates them
}
```

> Notice: `swap` expects addresses but nothing enforces that. If someone calls `swap(123, 456)`
, your program will happily scribble into memory cells 123 and 456, that is simply the cost of
not having types.

---

This is how one might write recursive function for calculating factorial.

```
factorial(n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

Example of TAC that I might use (example is for factorial recursive)

| Operation | Address 1   | Address 2 | Destination |
| --------- | ----------- | --------- | ----------- |
| `if_le`   | `n`         | `1`       | `L_base`    |
| `jmp`     |             |           | `L_rec`     |
| `label`   | `L_base`    |           |             |
| `ret`     | `1`         |           |             |
| `label`   | `L_rec`     |           |             |
| `sub`     | `n`         | `1`       | `t0`        |
| `param`   | `t0`        |           |             |
| `call`    | `factorial` | `1`       | `t1`        |
| `mul`     | `n`         | `t1`      | `t2`        |
| `ret`     | `t2`        |           |             |






Again factorial function but using iterative approach.

```
factorial(n) {
    acc = 1;
    while (n > 1) { acc = acc * n; n = n - 1; }
    return acc;
}
```

What if we would like to check if number is divisible by some other number?

```
divisible(x, n) {
    return x % n == 0;
}
```

What if we would like to count set bits?

```
csb(x) {
    result = 0;
    i = 16;
    while (i > 0) {
        result = result + (x & 0x0001);     // add low bit
        x = x >> 1;                         // shift one bit
        i = i - 1;                          // decrement counter
    }
    return result;
}
```

```
csb(x) {
    result = 0;
    while (x != 0) {
        x = x & (x - 1);        // drop lowest 1-bit
        result = result + 1;
    }
    return result;
}
```

---

Write a function that determines how much memory is free?

```

```

---

