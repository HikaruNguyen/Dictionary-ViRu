package dictionary.vietnamese.tudien.viet.NextDictUtils;

/**
 * Created by manhi on 6/7/2016.
 */
public class WordHash {
    public static final char DEFAULT_CHAR = '_';

    public static char hash(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return ch;
        }
        if (ch >= 'A' && ch <= 'Z') {
            return (char) (ch + 32); // 'a' - 'A'
        }
        if (ch <= 255) {
            return DEFAULT_CHAR;
        }
        return (char) ('a' + (char) (charToInt(ch) % 26));
    }

    public static int charToInt(char ch) {
        String str = ch + "";
        try {
            byte[] bytes = str.getBytes("UTF-8");
            if (bytes.length < 2)
                return 0;
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
        } catch (Exception e) {
            return '*';
        }
    }
}
