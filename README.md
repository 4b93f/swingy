# Swingy

A turn-based RPG with both a console and a Swing GUI mode.

> Built on a MacBook M3 Pro running macOS Tahoe 26.4.1.

## Build

```bash
mvn package -DskipTests
```

## Run

```bash
# Console mode
java -jar target/swingy-1.0-SNAPSHOT.jar console

# GUI mode
java -jar target/swingy-1.0-SNAPSHOT.jar gui
```

## Gameplay

### Hero classes

| Class   | ATK multiplier | DEF multiplier | Crit chance |
|---------|---------------|----------------|-------------|
| Warrior | ×1.3          | ×1.1           | 15%         |
| Mage    | ×1.4          | ×0.8           | 20%         |
| Rogue   | ×1.5          | ×0.7           | 25%         |
| Tank    | ×0.8          | ×1.5           | 5%          |

### Base stats at creation (level N)

```
HP  = 80 + 20×N   (Tank ×1.2, Rogue ×0.9)
ATK = 8  +  3×N
DEF = 6  +  2×N
```

Class multipliers from the table above are applied in combat, not at creation.

### Map

- Move with **W A S D** (console) or the arrow buttons (GUI).
- Reach the **border** of the map to win and progress to the next level.
- Enemies are scattered across the inner cells — stepping on one triggers a battle.

#### Map size formula

```
size = (level - 1) × 5 + 10 - (level % 2)
```

| Level | Map size |
|-------|----------|
| 1     | 9×9      |
| 2     | 15×15    |
| 3     | 19×19    |
| 4     | 25×25    |
| 5     | 29×29    |

### Battle

- **Fight** — simulate combat turn by turn. Hero attacks first each turn.
- **Run** — 50% chance to escape back to your previous position. On failure, you are forced to fight.
- Winning a fight grants XP. Enough XP triggers an automatic level-up.
- A 30% chance exists that a defeated enemy drops an artifact (Weapon / Armor / Helmet). You choose to keep or leave it.

#### Damage formula

```
base   = max(ATK × class_multiplier − DEF, 1)
damage = max(floor(base × variance), 1)   where variance ∈ [0.9, 1.1]
crit   = base × 2  (triggered at class crit chance)
```

### Persistence

Heroes are saved to `heroes.txt` after each run. A hero with the same name is upserted (replaces the old entry). HP is always restored to full before saving.

## Stats

Stats persist between fights within a run, so damage carries over.

### Level-up gains

| Stat | Per level |
|------|-----------|
| HP   | +25       |
| ATK  | +3        |
| DEF  | +2        |

#### XP threshold to level up

```
XP needed = level × 1000 + (level − 1)² × 450
```

| Current level | XP needed |
|---------------|-----------|
| 1             | 1 000     |
| 2             | 2 450     |
| 3             | 4 800     |
| 4             | 8 050     |

### Enemy stats at level N

```
HP  ∈ [40×N, 60×N)
ATK ∈ [8+4×N, 8+7×N)
DEF ∈ [2×N,   4×N)
```

Enemy level is weighted around hero level: 60% same level, 20% one below, 20% one above (minimum 1).

### Artifacts

| Type   | Bonus  |
|--------|--------|
| Weapon | +ATK   |
| Armor  | +DEF   |
| Helmet | +HP    |

Artifact bonus scales with enemy strength (`S` = enemy strength):
```
base        = S × 2
Weapon ATK  = base + random(base/2 + 1) + S
Armor  DEF  = base + random(base/2 + 1) + S
Helmet HP   = base × 5 + random(base × 3 + 1)
```

## Tests

```bash
mvn test
```

140 tests covering model logic, combat, persistence, and map generation.
