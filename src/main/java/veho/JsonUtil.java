package veho;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonUtil {

    public static String toJson (Object o) {
        return new Gson().toJson(o);
    }

    public static ResponseTransformer json () {
        return JsonUtil::toJson;
    }

}
