package dictionary.vietnamese.tudien.viet.widget.customeControl;

import java.util.ArrayList;

import dictionary.vietnamese.tudien.viet.model.resultApi.ListDictResult;


/**
 * Created by manhi on 15/1/2016.
 */
public interface OnCustomerListChangedListener {
    void onNoteListChanged(ArrayList<ListDictResult.ListDictInfo> customers);
}
