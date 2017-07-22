package dictionary.viet.russia.viru.widget.customeControl;

import java.util.ArrayList;

import dictionary.viet.russia.viru.model.resultApi.ListDictResult;


/**
 * Created by manhi on 15/1/2016.
 */
public interface OnCustomerListChangedListener {
    void onNoteListChanged(ArrayList<ListDictResult.ListDictInfo> customers);
}
