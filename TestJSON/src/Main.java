import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

public class Main {
    public static void main(String[] args) {
        String str = "{\"key\":\"value\"}";
        JSON json = JSONSerializer.toJSON(str);
        System.out.println(json.toString());
    }
}
