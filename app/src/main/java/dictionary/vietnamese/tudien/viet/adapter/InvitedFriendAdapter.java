package dictionary.vietnamese.tudien.viet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import dictionary.vietnamese.tudien.viet.R;
import dictionary.vietnamese.tudien.viet.model.resultApi.FriendFacebookResult;

/**
 * Created by manhi on 29/2/2016.
 */
public class InvitedFriendAdapter extends BaseAdapter {
    private List<FriendFacebookResult.FriendFacebookInfo> lstUsers;
    private Context context;

    public InvitedFriendAdapter(Context context, List<FriendFacebookResult.FriendFacebookInfo> list) {
        super();
        this.lstUsers = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lstUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return lstUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend, null);
            viewHolder.imgAvatar = (CircularImageView) convertView.findViewById(R.id.imgAvatar);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FriendFacebookResult.FriendFacebookInfo userFriend = lstUsers.get(position);
        if (userFriend != null) {
            viewHolder.tvName.setText(userFriend.name);
            Picasso.with(context).load(userFriend.picture.data.url).error(R.mipmap.avt_default).placeholder(R.mipmap.avt_default).into(viewHolder.imgAvatar);
        }

        return convertView;
    }

    public class ViewHolder {
        private TextView tvName;
        private CircularImageView imgAvatar;

    }
}
