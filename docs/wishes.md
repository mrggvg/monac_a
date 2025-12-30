# Wishes (for now)

> Because of one singer (if we can call it that) probably going to change the name for project from **monac** to **monacc**.

Here I am keeping track of what would I like my language to support, going through examples of
what I will most likely need it to support, so I can lager define the grammar for it.

## Conditional Statements

```
if (x < y) x = x + 1;
```

```
if (x < y) {
    z = y;
} else {
    z = x;
}
```

```
if (x >= 100 && x < 200) x = x / 2;
```

```
if (x < 100 || x >= 200) {  } else {
    x = x / 2;
}
```



## Loops

```
i = 0;
while (i < 10) {
    i = i + 1;
}
```

```
sum = 0;
for (int i = 1; i <= 10; i++) {
    sum = sum + i;
}
```


Calculating factorial in **mona** language.

Iteratively we could do it something like this.

```
u16 fact_iter(u16 n) {
  u16 acc = 1;
  while (n > 1) {
    acc = acc * n;
    n = n - 1;
  }
  return acc;
}

void main() {
  u16 x = fact_iter(5); // 120
}
```

Recursively.

```
u16 fact(u16 n) {
  if (n <= 1) {
    return 1;
  }
  return n * fact(n - 1);
}

u16 main() {
  return fact(5); // 120
}
```






```
void swap(u16* x, u16* y) {
    u16 tmp = *x;
    *x = *y;
    *y = tmp;
}

void main() {
    u16 a = 1;
    u16 b = 2;
    swap(&a, &b);
}
```






