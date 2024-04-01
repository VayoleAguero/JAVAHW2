import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.csv"));
            String line;
            List<List<Integer>> votes = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                List<Integer> vote = new ArrayList<>();
                for (String part : parts) {
                    vote.add(Integer.parseInt(part));
                }
                votes.add(vote);
            }
            reader.close();

            Map<Integer, Integer> scores = new HashMap<>();
            for (List<Integer> vote : votes) {
                for (int i = 0; i < vote.size(); i++) {
                    scores.put(vote.get(i), scores.getOrDefault(vote.get(i), 0) + (vote.size() - i));
                }
            }

            List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(scores.entrySet());
            entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

            BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
            for (int i = 0; i < entries.size(); i++) {
                if (i > 0) {
                    writer.write(",");
                }
                int j = i;
                while (j < entries.size() - 1 && entries.get(j).getValue().equals(entries.get(j + 1).getValue())) {
                    j++;
                }
                if (j == i) {
                    writer.write(String.valueOf(entries.get(i).getKey()));
                } else {
                    writer.write("[");
                    for (int k = i; k <= j; k++) {
                        if (k > i) {
                            writer.write(",");
                        }
                        writer.write(String.valueOf(entries.get(k).getKey()));
                    }
                    writer.write("]");
                    i = j;
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
