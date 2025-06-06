# TSPwP – Traveling Salesman Problem with Penalty

This project solves the **Traveling Salesman Problem with Penalty (TSPwP)**. In this variant, you can skip cities by paying a fixed penalty. The objective is to minimize:

```
Total Cost = Total Distance + (Penalty × Number of Skipped Cities)
```

---

## Features

- 🧠 **Smart Initial City Selection** – based on central location and connectivity
- 🚀 **Penalty/Distance Ratio Heuristic** – prioritize cities with high penalty and low cost
- 🧭 **Grid-Based Spatial Filtering** – reduces search space for nearest cities
- 🔁 **Lookahead-Based Greedy Selection** – considers next-next steps for smarter paths
- ✂️ **2-opt Optimization** – locally improves the route by edge swaps
- ⚡ **Fast for Large Inputs** – tested up to 50,000 cities with efficient runtime

---

## 📊 Algorithm Explained

This algorithm combines multiple powerful techniques to efficiently solve the TSP with penalties:

### 1. Smart Initial City Selection
The algorithm calculates the geometric center (average x and y). Then, it selects a city:
- Close to the center
- With the highest number of nearby cities within penalty range  
  This ensures a central and well-connected starting point.

### 2. Grid-Based Spatial Filtering
To reduce computation time:
- The map is divided into square grids (e.g., 1000x1000 units)
- Each city is placed in a grid cell
- When searching for the next city, only nearby grids (current + 8 neighbors) are considered  
  This optimization scales well for 50,000+ cities.

### 3. Penalty/Distance Ratio Heuristic
For each unvisited city, the algorithm calculates:
```
score = distance / penalty
```
Cities with lower scores are prioritized – cheap to visit and costly to skip.

### 4. Lookahead Strategy
Instead of making short-sighted choices, the algorithm:
- Simulates the next two steps (current → A → B)
- Chooses A that minimizes total projected cost  
  This prevents getting trapped in low-connectivity areas.

### 5. 2-opt Local Optimization
After building the route:
- It iteratively swaps two edges if the total distance can be shortened
- This improves the route without re-building it from scratch  
  Result: significantly lower total cost.

---

## Input Format (`input.txt`)

```
<penalty>
<city_id> <x> <y>
<city_id> <x> <y>
...
```

Example:
```
2601
0 200 800
1 3600 2300
...
```

---

## Output Format (`output.txt`)

```
<total_cost> <number_of_visited_cities>
<city_id_1>
<city_id_2>
...
```

Example:
```
445999 53
73
68
11
...
```

---

## How to Run

1. Place your input in `input.txt`
2. Compile and run `Main.java`
3. Output is written to `output.txt`

---

## File Structure

```
TSPwP/
├── Main.java        # Main algorithm
├── City.java        # City representation
├── input.txt        # Input file
├── output.txt       # Output file
└── README.md        # Documentation
```

---

## Developer

Göktuğ Sina Bekçioğulları  
Computer Engineering – Marmara University  
June 2025
