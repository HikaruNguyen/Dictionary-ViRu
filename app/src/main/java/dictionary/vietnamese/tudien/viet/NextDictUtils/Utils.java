package dictionary.vietnamese.tudien.viet.NextDictUtils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.regex.Pattern;

import dictionary.vietnamese.tudien.viet.database.DictInfoDBHelper;
import dictionary.vietnamese.tudien.viet.model.resultApi.ListDictResult;

/**
 * Created by manhi on 3/1/2016.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null)
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String EXTRA_MSG = "extra_msg";


    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            return Settings.canDrawOverlays(context);
        }


    }

    public static boolean assetExists(AssetManager assets, String name) {
        try {
            // using File to extract path / filename
            // alternatively use name.lastIndexOf("/") to extract the path
            File f = new File("dict/" + name);
            String parent = f.getParent();
            if (parent == null) parent = "";
            String fileName = f.getName();
            // now use path to list all files
            String[] assetList = assets.list(parent);
            if (assetList != null && assetList.length > 0) {
                for (String item : assetList) {
                    if (fileName.equals(item))
                        return true;
                }
            }
        } catch (IOException e) {
            // Log.w(TAG, e); // enable to log errors
        }
        return false;
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static boolean isSet(int flags, int flag) {
        return (flags & flag) == flag;
    }

    public static String getUrlFacebookUserAvatar(String name_or_idUser) {
        String address = "http://graph.facebook.com/" + name_or_idUser + "/picture?type=large";
        URL url;
        String newLocation = null;
        try {
            url = new URL(address);
            HttpURLConnection.setFollowRedirects(false); //Do _not_ follow redirects!
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            newLocation = connection.getHeaderField("Location");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newLocation;
    }

    public static int countWords(String s) {

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    public static int getFormatTypeDict(ListDictResult.ListDictInfo listDictInfo) {
        String info = "";
        info = DictInfoDBHelper.getInfoDict(listDictInfo.id);
        int format_version = 1;
        for (int ii = 0; ii < info.split("\n").length; ii++) {
            String s = info.split("\n")[ii];
            if (s.trim() != null && !s.isEmpty()) {
                String s1[] = s.split("=");
                if (s1[0].trim().equals("format_version")) {
                    String Sformat_version = s1[1];
                    try {
                        format_version = Integer.parseInt(Sformat_version);
                    } catch (Exception e) {
                        format_version = 1;
                    }
                }
            }
        }
        return format_version;
    }
}
