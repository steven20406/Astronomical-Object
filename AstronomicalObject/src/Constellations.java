import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Constellations {
    public static final HashMap<String, Integer> allConstellations = new HashMap<>();

    public static void addConstellations(String ConstellationsName) {
        Integer number = allConstellations.get(ConstellationsName);
        if (number == null) {
            allConstellations.put(ConstellationsName, 1);
        } else {
            allConstellations.put(ConstellationsName, number + 1);
        }
    }

    public static Map.Entry<String, Integer> largestConstellations() {
        Set<Map.Entry<String, Integer>> temp = allConstellations.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = temp.iterator();
        Map.Entry<String, Integer> result = null;

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> current = iterator.next();
            if (result == null) {
                result = current;
            }
            result = result.getValue() < current.getValue() ? current : result;
        }
        return result;
    }
}
