import java.io.*;
import java.util.*;

public class Main {
    static int penaltyPerCity = 0;
    static final int GRID_SIZE = 1000; // Grid size
    static Map<String, List<City>> gridMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ArrayList<City> cities = new ArrayList<>();

        File input = new File("input.txt"); 
        Scanner sc = new Scanner(input);
        penaltyPerCity = sc.nextInt();

        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();
            City city = new City(id, x, y);
            cities.add(city);
            String key = getGridKey(x, y);
            gridMap.computeIfAbsent(key, k -> new ArrayList<>()).add(city);
        }
        sc.close();

        City start = selectSmartStartCity(cities);
        ArrayList<City> route = buildGreedyRoute(cities, start);
        apply2Opt(route);

        int totalDistance = computeRouteDistance(route);
        int totalPenalty = computePenalty(cities);
        int totalCost = totalDistance + totalPenalty;

        FileWriter writer = new FileWriter("output.txt");
        writer.write(totalCost + " " + route.size() + "\n");
        for (City c : route) writer.write(c.id + "\n");
        writer.write("\n");
        writer.close();

        System.out.println("Çözüm 'output.txt' dosyasına yazıldı.");
    }

    static String getGridKey(int x, int y) {
        return (x / GRID_SIZE) + "," + (y / GRID_SIZE);
    }

    static List<City> getNearbyCities(City c) {
        int gx = c.x / GRID_SIZE;
        int gy = c.y / GRID_SIZE;
        List<City> result = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                String key = (gx + dx) + "," + (gy + dy);
                if (gridMap.containsKey(key)) {
                    result.addAll(gridMap.get(key));
                }
            }
        }
        return result;
    }

    static City selectSmartStartCity(List<City> cities) {
        double sx = 0, sy = 0;
        for (City c : cities) {
            sx += c.x;
            sy += c.y;
        }
        double avgX = sx / cities.size(), avgY = sy / cities.size();
        City best = null;
        double bestScore = Double.MAX_VALUE;
        for (City c1 : cities) {
            double dist = Math.hypot(c1.x - avgX, c1.y - avgY);
            int neighborCount = 0;
            for (City c2 : getNearbyCities(c1)) {
                if (c1 != c2 && computeDistance(c1, c2) <= penaltyPerCity)
                    neighborCount++;
            }
            double score = dist / (1 + neighborCount);
            if (score < bestScore) {
                bestScore = score;
                best = c1;
            }
        }
        best.visited = true;
        return best;
    }

    static ArrayList<City> buildGreedyRoute(List<City> cities, City start) {
        ArrayList<City> route = new ArrayList<>();
        route.add(start);
        City current = start;
        while (true) {
            City best = null;
            double bestRatio = -1;
            for (City candidate : getNearbyCities(current)) {
                if (!candidate.visited) {
                    int dist = computeDistance(current, candidate);
                    double ratio = (double) penaltyPerCity / dist;
                    if (dist <= penaltyPerCity && ratio > bestRatio) {
                        bestRatio = ratio;
                        best = candidate;
                    }
                }
            }
            if (best == null) break;
            best.visited = true;
            route.add(best);
            current = best;
        }
        return route;
    }

    static int computeDistance(City a, City b) {
        int dx = a.x - b.x, dy = a.y - b.y;
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }

    static int computeRouteDistance(List<City> route) {
        int total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            total += computeDistance(route.get(i), route.get(i + 1));
        }
        total += computeDistance(route.get(route.size() - 1), route.get(0)); // return to start
        return total;
    }

    static int computePenalty(List<City> cities) {
        int unvisited = 0;
        for (City c : cities) if (!c.visited) unvisited++;
        return unvisited * penaltyPerCity;
    }

    static void apply2Opt(ArrayList<City> route) {
        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 1; i < route.size() - 2; i++) {
                for (int j = i + 1; j < route.size() - 1; j++) {
                    City A = route.get(i - 1), B = route.get(i);
                    City C = route.get(j), D = route.get(j + 1);
                    int before = computeDistance(A, B) + computeDistance(C, D);
                    int after = computeDistance(A, C) + computeDistance(B, D);
                    if (after < before) {
                        Collections.reverse(route.subList(i, j + 1));
                        improved = true;
                    }
                }
            }
        }
    }

    static class City {
        int id, x, y;
        boolean visited = false;
        City(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
}
