# Decisio

A lightweight Java decision DSL that replaces nested if-else with fluent rule composition, supporting AND/OR/NOT predicate logic and functional execution flows.

> DSL = A specialized way of writing code that makes it feel like you are describing the problem, not programming it.

### 🧩 Concept

Instead of writing:

```java
if (a) {
    if (b) {
        action1();
    }
} else {
    action2();
}
```

We write:

```java
Decisio.of(value)
    .when(a).then(action1)
    .otherwise(action2);
```

### 🚀 Why Decisio?

Deeply nested `if-else` logic quickly becomes:

- hard to read
- difficult to maintain
- error-prone
- tightly coupled

Decisio simplifies this by providing a **fluent, readable decision flow API** using Java lambdas.

### ✨ Features

- Fluent decision API
- Replace nested if-else logic
- Predicate composition (AND / OR / NOT)
- Functional execution flow
- Lightweight (no external dependencies)
- Easy to embed in any Java project

### 📦 Installation

#### Maven

```xml
<dependency>
    <groupId>io.github.mashuk-bd</groupId>
    <artifactId>decisio</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 🧠 Quick Example

```java
Decisio.of(user)

    .when(Objects::isNull)
        .then(u -> guest())

    .when(User::isBlocked)
        .then(u -> blocked())

    .when(User::isAdmin)
        .and(User::isActive)
        .then(u -> admin())

    .when(User::isPremium)
        .or(User::isVip)
        .then(u -> premium())

    .otherwise(u -> normal());
```

### 🔗 Predicate Composition

#### AND
```java
.when(User::isAdmin)
.and(User::isActive)
```
#### OR
```java
.when(User::isPremium)
.or(User::isVip)
```
#### NOT
```java
.when(User::isAdmin)
.andNot(User::isBlocked)
```

### ⚙️ How It Works

Decisio evaluates rules in order:

1. Match condition
2. Execute associated action
3. Stop (default behavior: first match wins)
4. Fallback to otherwise

### 🎯 Use Cases

* Business rule evaluation
* Validation flows
* Access control logic
* Decision trees
* Request routing logic
* Reducing nested conditional complexity

### 📌 Design Philosophy

* Keep it simple
* Stay close to Java functional style
* Avoid enterprise complexity
* Focus on readability over abstraction

### ⚠️ Note

Decisio is not a full rule engine or BPM system.

It is a **lightweight decision composition library** for everyday Java development.

### 📄 License

MIT License

### 👤 Author

[Mashukur Rahman](https://github.com/mashuk-bd)
