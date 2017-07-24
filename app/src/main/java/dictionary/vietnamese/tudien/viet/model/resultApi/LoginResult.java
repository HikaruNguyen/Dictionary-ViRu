package dictionary.vietnamese.tudien.viet.model.resultApi;

/**
 * Created by manhi on 1/3/2016.
 */
public class LoginResult extends BaseResult {
    public LoginData data;
    public static class LoginData {
        public String session;
        public UserInfo subscriber;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
