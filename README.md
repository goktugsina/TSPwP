# TSP with Penalty (TSPwP) Solver

This project implements an efficient heuristic algorithm to solve the **Traveling Salesman Problem with Penalty (TSPwP)**. In TSPwP, each city can either be visited or skipped. Skipping a city incurs a fixed penalty cost. The goal is to minimize the total cost, which is the sum of the travel distance and the penalties of skipped cities.

---

## 📌 Key Features
- Adaptive **grid-based spatial filtering** to reduce search space.
- Smart **start city selection** using centrality and neighbor density.
- **Greedy lookahead routing** for foresight in decision-making.
- Multiple **reinsertion strategies** to minimize penalty:
  - Cheap skipped city insertion
  - Global reinsertion
  - Penalty-driven greedy reinsertion
- **2-opt local optimization** to improve route quality.
- Automatically adjusts `GRID_SIZE` based on input distribution.

---

## 🧠 Algorithm Explanation

### Step 1: Grid-based Spatial Filtering
- Cities are partitioned into spatial grid cells to only consider nearby cities during routing.
- This improves performance from O(n²) to near-linear for local decisions.
- `GRID_SIZE` is computed dynamically based on city density.

### Step 2: Smart Start City Selection
- Calculate the geometric center (avgX, avgY).
- For each city, score = distance to center / (1 + nearby city count).
- The city with the lowest score becomes the starting city.

### Step 3: Greedy Route with Lookahead
- At each step, pick the unvisited nearby city minimizing:
  
  	`score = d1 + 0.3 * d2`

  where:
  - `d1` = distance to candidate city
  - `d2` = candidate's distance to its nearest neighbor

- This lookahead prevents short-sighted greedy paths.

### Step 4: Skipped City Reinsertion
Three strategies are applied:

1. `insertCheapSkippedCities`: Insert if added cost < 1.2 * penalty
2. `attemptGlobalReinsertion`: Globally insert if gain < 1.4 * penalty
3. `penaltyDrivenGreedyReinsertion`: Maximize penalty/cost ratio

### Step 5: 2-opt Optimization
- Iteratively checks and swaps edges to shorten the tour.
- Runs until no more improvements are found.

---

## 📁 Input Format
```
penaltyPerCity
id1 x1 y1
id2 x2 y2
...
```

## 📄 Output Format (output.txt)
```
totalCost visitedCityCount
cityId1
cityId2
...
```

---

## 📈 Performance
- Tested on inputs up to **50,000 cities**.
- Achieves high city coverage with low total cost.
- Runtime optimized via grid filtering and localized decision-making.

---

## 🚀 How to Run
```bash
javac Main.java
java Main
```
Ensure `input.txt` is present in the working directory.

---
