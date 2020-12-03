package commons;

import org.json.JSONArray;
import org.json.JSONObject;
import shepherd.Shepherd;
import shepherdsAnimal.ShepherdAnimal;

import java.util.ArrayList;
import java.util.List;

public class ShepherdRepository extends BaseRepository {

    public static String FILEPATH =  "shepherds.json";

    @Override
    public void readFromJSONFile() {
        List<Shepherd> shepherds = Storage.getInstance().getShepherds();
        String content = this.readFileToString(FILEPATH);
        JSONArray json_arr = new JSONArray(content);
        for (int i = 0; i < json_arr.length(); i++) {
            JSONObject actual = json_arr.getJSONObject(i);
            shepherds.add(new Shepherd(
                    actual.getString("id"),
                    actual.getString("name"),
                    actual.getString("area"),
                    this.getShepherdAnimals(actual.getJSONArray("animals")),
                    Storage.parseDate(actual.getString("born")),
                    actual.getString("address"),
                    Storage.getCompanyById(actual.getString("employer")),
                    actual.getInt("salary")
            ));
        }
    }


    /**
     * Az fájlból beolvasott, id-val referált állatot lecseréli magának az állat-objektumnak a pointerével.
     * @param animals:JSONArray
     * @return List
     */
    private List<ShepherdAnimal> getShepherdAnimals(JSONArray animals) {
        List<ShepherdAnimal> result = new ArrayList<>();

        for (int i = 0; i < animals.length(); i++) {
            JSONObject actual = animals.getJSONObject(i);
            result.add(new ShepherdAnimal(Storage.getAnimalById(actual.getString("animal")), actual.getInt("quantity")));
        }

        return result;
    }

    @Override
    public void writeToJSONFile() {
        JSONArray items = new JSONArray();
        for (Shepherd s : Storage.getInstance().getShepherds()) {
            JSONObject item = new JSONObject();
            item.put("id", s.getId());
            item.put("name", s.getName());
            item.put("area", s.getArea());
            item.put("animals", this.getShepherdAnimalListJSON(s));
            item.put("born", Storage.dateToString(s.getBorn()));
            item.put("address", s.getAddress());
            item.put("employer", s.getEmployer() == null ? "" : s.getEmployer().getId());
            item.put("salary", s.getSalary());
            items.put(item);
        }
        this.saveStringToFile(FILEPATH, items.toString());
    }

    /**
     * Az allatlistat, a fajlbairashoz elkesziti, az allatokat lecsereli azok id-jéra.
     * @param s: Shepherd
     * @return JSONArray
     */
    private JSONArray getShepherdAnimalListJSON(Shepherd s) {
        JSONArray animals = new JSONArray();
        if (s.getAnimals() == null) {
            return animals;
        }
        for (ShepherdAnimal an : s.getAnimals()) {
            JSONObject animal = new JSONObject();
            animal.put("animal", an.getAnimal().getId());
            animal.put("quantity", an.getQuantity());
            animals.put(animal);
        }
        return animals;
    }
}
