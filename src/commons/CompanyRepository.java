package commons;

import company.Company;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CompanyRepository extends BaseRepository {

    public static String FILEPATH =  "companies.json";

    @Override
    public void readFromJSONFile() {
        List<Company> companies = Storage.getInstance().getCompanies();
        String content = this.readFileToString(FILEPATH);
        JSONArray json_arr = new JSONArray(content);
        for (int i = 0; i < json_arr.length(); i++) {
            JSONObject actual = json_arr.getJSONObject(i);
            companies.add(new Company(
                    actual.getString("id"),
                    actual.getString("name"),
                    actual.getString("leader"),
                    actual.getString("address"),
                    actual.getString("account")
            ));
        }
    }

    @Override
    public void writeToJSONFile() {
        JSONArray items = new JSONArray();
        for (Company c : Storage.getInstance().getCompanies()) {
            JSONObject item = new JSONObject();
            item.put("id", c.getId());
            item.put("name", c.getName());
            item.put("leader", c.getLeader());
            item.put("address", c.getAddress());
            item.put("account", c.getAccount());
            items.put(item);
        }
        this.saveStringToFile(FILEPATH, items.toString());
    }
}
