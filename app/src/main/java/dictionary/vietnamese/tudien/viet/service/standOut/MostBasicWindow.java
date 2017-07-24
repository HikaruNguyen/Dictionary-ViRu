package dictionary.vietnamese.tudien.viet.service.standOut;

import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by manhi on 30/1/2016.
 */
public class MostBasicWindow extends StandOutWindow {

    @Override
    public String getAppName() {
        return "MostBasicWindow";
    }

    @Override
    public int getAppIcon() {
        return android.R.drawable.btn_star;
    }

    @Override
    public void createAndAttachView(int id, FrameLayout frame) {
        TextView view = new TextView(this);
        view.setText("MostBasicWindow");
        view.setBackgroundColor(Color.CYAN);

        frame.addView(view);
    }

    @Override
    public StandOutLayoutParams getParams(int id, Window window) {
        return new StandOutLayoutParams(id, 200, 150, 100, 100);
    }
}
