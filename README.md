# TSPwP (Traveling Salesman Problem with Penalty)

This Java program solves a variation of the **Traveling Salesman Problem (TSP)** where **some cities may be skipped at a cost (penalty)**. The objective is to **minimize the total cost**, which is defined as:

```
Total Cost = Total Tour Distance + (Penalty per Skipped City × Number of Skipped Cities)
```

---

## 📁 Input Format

The input file must be named `input.txt` and placed in the project source directory. Format:

```
<PENALTY_PER_CITY>
<ID1> <X1> <Y1>
<ID2> <X2> <Y2>
...
```

- `PENALTY_PER_CITY`: Integer penalty cost for each unvisited city.
- `ID, X, Y`: City ID and coordinates.

---

## 📤 Output Format

Output is written to `output.txt`:

```
<TOTAL_COST> <NUMBER_OF_VISITED_CITIES>
<ID1>
<ID2>
...
```

---

## ⚙️ Algorithm Overview

This implementation combines **grid-based spatial partitioning**, **greedy heuristics**, **lookahead scoring**, **multiple reinsertion strategies**, and **2-opt local optimization** to construct a cost-efficient tour under penalty constraints.

### 1. Grid Partitioning (Spatial Indexing)

- The map is partitioned into square grids (`GRID_SIZE` is dynamically determined).
- Cities are indexed into grid cells for **fast neighbor lookups**, significantly reducing pairwise comparisons.

### 2. Smart Start City Selection

A start city is selected based on:
- Proximity to the geometric center (centroid) of all cities.
- Number of neighbors within `penaltyPerCity` distance.
- Score = Distance to center / (1 + Neighbor Count)

### 3. Greedy Tour Construction with Lookahead

- At each step, chooses the best unvisited city using a **lookahead score**:
  - `Score = distance(current, candidate) + 0.3 × distance(candidate, next closest)`
- This anticipates future path quality.

### 4. 2-Opt Optimization

- Local search that reverses city segments if the route distance improves.
- Iteratively applied until no further improvement is found.

### 5. Reinsertion Strategies for Skipped Cities

Skipped cities are reconsidered with increasing levels of aggressiveness:

- **insertCheapSkippedCities**: Insert if cost increase < `1.2 × penalty`
- **attemptGlobalReinsertion**: Try globally if cost increase < `1.4 × penalty`
- **penaltyDrivenGreedyReinsertion**: Maximize penalty-to-cost ratio
- **forceInsertRemainingCities**: Insert any remaining city with the lowest cost

---

## 🧮 Cost Calculations

- **Distance**: Euclidean distance (rounded to nearest integer)
- **Penalty**: `penaltyPerCity × number of unvisited cities`
- **Total Cost**: `route distance + penalty`

---

## 🚀 How to Run

1. Place `input.txt` in the source directory (e.g., `src/`).
2. Compile and run the `Main.java` file.
3. Output is generated as `output.txt` in the same directory.

---

## 📌 Example

**input.txt**
```
100
1 10 20
2 30 35
3 45 10
4 15 60
```

**output.txt**
```
350 3
1
3
2
```
This indicates:
- Total cost = 350
- 3 cities visited (1 city skipped with 100 penalty)

---

## ✅ Features Summary

- Grid-based city indexing for fast access
- Smart initial city selection
- Lookahead-based greedy path building
- 2-opt route optimization
- Multi-phase reinsertion for unvisited cities
- Balances distance and penalty in decision-making

---


