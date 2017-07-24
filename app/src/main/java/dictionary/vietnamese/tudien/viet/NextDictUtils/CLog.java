package dictionary.vietnamese.tudien.viet.NextDictUtils;

import android.util.Log;

/**
 * Created by manhi on 26/12/2015.
 */
public class CLog {
    public static boolean DEBUG = true;

    public static int w(String tag, String msg) {
        if (DEBUG)
            return Log.w(tag, msg);
        return -1;
    }

    public static int d(String tag, String msg) {
        if (DEBUG)
            return Log.d(tag, msg);
        return -1;
    }

    public static int e(String tag, String msg) {
        if (DEBUG)
            return Log.e(tag, msg);
        return -1;
    }

    public static int v(String tag, String msg) {
        if (DEBUG)
            return Log.v(tag, msg);
        return -1;
    }

    public static int i(String tag, String msg) {
        if (DEBUG)
            return Log.i(tag, msg);
        return -1;
    }
}
