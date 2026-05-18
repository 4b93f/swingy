# Swingy — GUI Programming in Java

> Second project in the 42 Java series. Build a text-based RPG with both a console and a Swing GUI, following the MVC pattern.

---

## Table of Contents

- [Introduction](#introduction)
- [Goals](#goals)
- [General Instructions](#general-instructions)
- [Mandatory Part](#mandatory-part)
  - [Gameplay](#gameplay)
  - [Features](#features)
  - [Validation](#validation)
- [Bonus Part](#bonus-part)
- [Turn-in](#turn-in)

---

## Introduction

This project focuses on graphical user interfaces and related software design patterns in Java. You will implement a minimalistic text-based RPG and learn how to properly separate the view from the rest of the application using event-driven, object-oriented programming.

---

## Goals

A client wants a text-based RPG released in two phases:

- **Phase 1** — Console-based (for hardcore hipsters)
- **Phase 2** — Simple Swing GUI for user input

**Technical requirements:**

- Respect the **Model-View-Controller (MVC)** design pattern
- Automated build with **Maven** (no external libraries beyond what Maven strictly needs)
- **Annotation-based** user input validation (Hibernate Validator recommended)
- Clean, readable, maintainable code — use the **Builder pattern**

> The major IDEs have good Maven support. You'll need to learn the Maven plugin system to generate a runnable jar file.

---

## General Instructions

- Java features up to the latest **LTS version** are allowed
- **No external libraries**, build tools, or code generators (except for validation — see below)
- Do **not** use the default package — follow Java package naming conventions
- Do **not** commit `.class` files
- Ensure `javac`, `java`, and `mvn` are available in your terminal
- Build the project from the root with:

```bash
mvn clean package
```

This must produce a runnable `.jar` file.

---

## Mandatory Part

### Gameplay

A player can have **multiple heroes** of different types (you define the types and their starting stats).

**On game start, the player can:**
- Create a new hero
- Select a previously created hero

**Hero stats:**
| Stat | Description |
|------|-------------|
| Name | Hero's name |
| Class | Hero type |
| Level | Current level |
| Experience | XP accumulated |
| Attack | Offensive power |
| Defense | Damage reduction |
| Hit Points | Health |

**Artifacts** (affect hero stats):
| Type | Effect |
|------|--------|
| Weapon | Increases Attack |
| Armor | Increases Defense |
| Helm | Increases Hit Points |

**Map:**
- Size is calculated as: `(level - 1) * 5 + 10 - (level % 2)`
  - Example: level 7 → 39×39 map
- Hero starts at the **center** of the map
- Hero **wins** by reaching any border
- Each turn, the hero moves **one step** in one of 4 directions: North, East, South, West

**Villains:**
- Randomly spread across the map on generation
- When the hero steps on a villain's tile, they have 2 choices:
  - **Fight** — engage in battle
  - **Run** — 50% chance to return to the previous position; on failure, must fight

**Battle:**
- You design the simulation algorithm (based on hero/villain stats, with optional luck factor)
- **Defeat** → hero dies, mission lost
- **Victory** → hero gains:
  - XP based on villain power (may trigger level-up)
  - A possible artifact drop (keep or leave; quality depends on villain strength)

**Level-up formula:** `level * 1000 + (level - 1)² * 450`

| Level | XP Required |
|-------|-------------|
| 1 | 1,000 XP |
| 2 | 2,450 XP |
| 3 | 4,800 XP |
| 4 | 8,050 XP |
| 5 | 12,200 XP |

---

### Features

Launch the game in two modes:

```bash
java -jar swingy.jar console
java -jar swingy.jar gui
```

- Hero data (state included) is **persisted to a text file** on exit
- Heroes are **loaded from the file** on startup

---

### Validation

- Integrate a **javax.validation**-compliant library (Hibernate Validator recommended)
- All abnormal user input must be caught and **highlighted to the user**
- This is the **only exception** to the no-external-libraries rule

---

## Bonus Part

Extra points for:

- **Persisting heroes in a relational database** instead of a text file
- **Switching between console and GUI at runtime** without closing the game

> You may use a library for this section, but its use must be explicitly justified and scoped to this feature only.

---

## Turn-in

Push your work to your **Git repository**. Only what is on the repository will be evaluated during the defense.
