package sharent.product.com.telstraloadlist.utils;

public class constants {
    public static final String REST_URL_DOMAIN = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.js";

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim().length() <= 0);
    }
}
