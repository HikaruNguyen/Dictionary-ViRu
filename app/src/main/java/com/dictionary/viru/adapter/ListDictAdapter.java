package com.dictionary.viru.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dictionary.viru.NextDictUtils.Utils;
import com.dictionary.viru.R;
import com.dictionary.viru.configuration.Configruation;
import com.dictionary.viru.database.ManagerDictDatabase;
import com.dictionary.viru.event.CheckDictEvent;
import com.dictionary.viru.NextDictUtils.CLog;
import com.dictionary.viru.widget.dragRecyclerView.ItemTouchHelperAdapter;
import com.dictionary.viru.widget.dragRecyclerView.ItemTouchHelperViewHolder;
import com.dictionary.viru.widget.customeControl.OnCustomerListChangedListener;
import com.dictionary.viru.widget.dragRecyclerView.OnStartDragListener;
import com.dictionary.viru.model.resultApi.ListDictResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by Nguyen Duc Manh on 21/7/2015.
 */
public class ListDictAdapter extends RecyclerView.Adapter<ListDictAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {
    private static final String TAG = ListDictAdapter.class.getSimpleName();
    public static Context context;
    public ArrayList<ListDictResult.ListDictInfo> mItems;
    public ArrayList<ListDictResult.ListDictInfo> mItemsBackup;
    private final OnStartDragListener mDragStartListener;
    private OnCustomerListChangedListener mListChangedListener;
    private SharedPreferences sharedPreferences;
    private ManagerDictDatabase managerDictDatabase;
    private static boolean isDelDict;
//    private DictDatabaseOpenHelper dictDatabaseOpenHelper;

    public ListDictAdapter(Context context, OnStartDragListener dragStartListener, ArrayList<ListDictResult.ListDictInfo> arr, OnCustomerListChangedListener listChangedListener) {
        mDragStartListener = dragStartListener;
        mItems = arr;
        mItemsBackup = new ArrayList<>();
        mItemsBackup.addAll(arr);
        ListDictAdapter.context = context;
        this.mListChangedListener = listChangedListener;
        sharedPreferences = context.getSharedPreferences(Configruation.Pref, Context.MODE_PRIVATE);
        managerDictDatabase = new ManagerDictDatabase(context);
        isDelDict = false;
//        dictDatabaseOpenHelper = new DictDatabaseOpenHelper(context);
    }

    public ListDictAdapter(Context context, OnStartDragListener dragStartListener, ArrayList<ListDictResult.ListDictInfo> arr, OnCustomerListChangedListener listChangedListener, boolean isDelDict) {
        mDragStartListener = dragStartListener;
        mItems = arr;
        mItemsBackup = new ArrayList<>();
        mItemsBackup.addAll(arr);
        ListDictAdapter.context = context;
        this.mListChangedListener = listChangedListener;
        sharedPreferences = context.getSharedPreferences(Configruation.Pref, Context.MODE_PRIVATE);
        managerDictDatabase = new ManagerDictDatabase(context);
        this.isDelDict = isDelDict;
    }

    public static boolean getDelDict() {
        return isDelDict;
    }

    public void setDelDict(boolean isDelDict) {
        this.isDelDict = isDelDict;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dict, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    public void setData(int position, ListDictResult.ListDictInfo item) {
        this.mItems.set(position, item);
        this.notifyItemChanged(position);
    }

    public void checkDictbyPosition(int position, boolean isCheck) {
        this.mItems.get(position).isChecked = isCheck;
        this.notifyItemChanged(position);
        EventBus.getDefault().post(new CheckDictEvent(mItems.get(position), isCheck, position));
    }

//    public ListDictResult.ListDictInfo getDictByPosition(int position){
//        return
//    }

    public void setData(ListDictResult.ListDictInfo item) {
        this.notifyDataSetChanged();
    }

    public void addAll(List<ListDictResult.ListDictInfo> arr) {
        mItems.addAll(arr);
        mItemsBackup.addAll(arr);
        notifyDataSetChanged();
    }

    public int countDictDownloaded() {
        int count = 0;
        for (int position = 0; position < mItems.size(); position++) {
//            File file = new File(MyApplication.DICT_ROOT, mItems.get(position).id + ".ndf");
            if (Utils.assetExists(context.getAssets(), mItems.get(position).id + ".ndf")) {
                count++;
            }
        }
        return count;
    }

    public int getToPosition(int position) {
        int toPosition = 0;
        for (int i = 0; i < mItems.size(); i++) {
            if (mItemsBackup.get(i).id.equals(mItems.get(position).id)) {
                toPosition = i;
                break;
            }
        }
        return toPosition;
    }

    public void clearAll() {
        mItems.clear();
        mItemsBackup.clear();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.tvDictName.setText(mItems.get(position).name);
        holder.tvSumWord.setText(mItems.get(position).word_count + " " + context.getString(R.string.words));
        holder.imgDownload.setVisibility(View.INVISIBLE);
        holder.checkDict.setVisibility(View.VISIBLE);
        holder.tvDictName.setTextColor(ContextCompat.getColor(context, R.color.black));
        holder.imgDrag.setVisibility(View.VISIBLE);
        if (mItems.get(position).isChecked) {
            holder.checkDict.setChecked(true);
        } else {
            holder.checkDict.setChecked(false);
        }
        final int po = position;
        // Start a drag whenever the handle view it touched
        holder.imgDel.setVisibility(View.GONE);

//            if (Utils.assetExists(context.getAssets(), mItems.get(position).id + ".ndf")) {
        holder.imgDrag.setVisibility(View.VISIBLE);
//            } else {
//                holder.imgDrag.setVisibility(View.INVISIBLE);
//            }
        holder.imgDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }

        });

        holder.checkDict.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int po1 = 0;
                for (int i = 0; i < mItems.size(); i++) {
                    if (mItemsBackup.get(i).id.equals(mItems.get(po).id)) {
                        po1 = i;
                        break;
                    }
                }
                CLog.d(ItemViewHolder.class.getSimpleName(), "position check: " + po1 + " " + mItemsBackup.get(po1).id);
                EventBus.getDefault().post(new CheckDictEvent(mItemsBackup.get(po1), isChecked, po1));

            }
        });


    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (!isDelDict) {
//            File file = new File(MyApplication.DICT_ROOT, mItems.get(fromPosition).id + ".ndf");
            if (Utils.assetExists(context.getAssets(), mItems.get(fromPosition).id + ".ndf")) {
                if (toPosition > countDictDownloaded() - 1) {
                    toPosition = countDictDownloaded() - 1;
                }
                Collections.swap(mItems, fromPosition, toPosition);
                mListChangedListener.onNoteListChanged(mItems);
                notifyItemMoved(fromPosition, toPosition);
            }
        }


//        notifyDataSetChanged();
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public ArrayList<ListDictResult.ListDictInfo> getAll() {
        return mItems;
    }

    public ListDictResult.ListDictInfo getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mItems.get(position);
        } else return null;
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public TextView tvDictName, tvSumWord;
        public ImageView imgDrag, imgDownload, imgDel;
        public CheckBox checkDict;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvDictName = (TextView) itemView.findViewById(R.id.tvDictName);
            tvSumWord = (TextView) itemView.findViewById(R.id.tvSumWord);
            imgDrag = (ImageView) itemView.findViewById(R.id.imgDrag);
            imgDownload = (ImageView) itemView.findViewById(R.id.imgDownload);
            checkDict = (CheckBox) itemView.findViewById(R.id.checkDict);
            imgDel = (ImageView) itemView.findViewById(R.id.imgDel);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white_alpha));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
