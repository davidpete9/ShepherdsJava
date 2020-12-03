package commons;

import animal.Animal;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class AnimalRepository extends BaseRepository {

    public static String FILEPATH = "animals.json";

    @Override
    public void readFromJSONFile() {
        List<Animal> animals = Storage.getInstance().getAnimals();
        String content = this.readFileToString(FILEPATH);
        JSONArray json_arr = new JSONArray(content);
        for (int i = 0; i < json_arr.length(); i++) {
            JSONObject actual = json_arr.getJSONObject(i);
            animals.add(new Animal(
                    actual.getString("id"),
                    actual.getString("type"),
                    actual.getInt("avg_cost")
            ));
        }
    }

    @Override
    public void writeToJSONFile() {
        JSONArray items = new JSONArray();
        for (Animal a : Storage.getInstance().getAnimals()) {
            JSONObject item = new JSONObject();
            item.put("id", a.getId());
            item.put("type", a.getType());
            item.put("avg_cost", a.getAvg_cost());
            items.put(item);
        }
        this.saveStringToFile(FILEPATH, items.toString());
    }
}
