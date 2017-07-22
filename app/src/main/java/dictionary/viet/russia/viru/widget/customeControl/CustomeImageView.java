package dictionary.viet.russia.viru.widget.customeControl;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by manhi on 5/3/2016.
 */
public class CustomeImageView extends ImageView {

    public CustomeImageView(Context context) {
        super(context);
        init();
    }

    public CustomeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomeImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }




    public interface OnTouchImageView {
        void OnUp();
    }
}
