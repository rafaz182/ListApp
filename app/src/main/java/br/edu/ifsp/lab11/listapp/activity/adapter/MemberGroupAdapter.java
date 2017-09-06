package br.edu.ifsp.lab11.listapp.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.lab11.listapp.R;
import br.edu.ifsp.lab11.listapp.domain.User_TO;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by r0xxFFFF-PC on 29/05/2017.
 */
public class MemberGroupAdapter extends BaseAdapter {

    private Context mContext = null;

    private List<User_TO> mMembersList = null;

    public MemberGroupAdapter(Context mContext, ArrayList<User_TO> mMembersList) {
        this.mContext = mContext;
        this.mMembersList = mMembersList;
    }

    @Override
    public int getCount() {
        return this.mMembersList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mMembersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getViewTypeCount() {
        int total = 1;

        return total;
    }

    /**
     * Verify the type of the view
     *
     * @param position - The position of a view
     * @return - An integer representing the type of the view
     */
    public int getItemViewType(int position) {
        int type = 1;

        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Local Variables
        View view = null;
        MemberGroupHolder holder = null;

        if (convertView == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.member_layout, parent, false);
            holder = new MemberGroupHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (MemberGroupHolder) view.getTag();
        }

        User_TO member = (User_TO) getItem(position);
        holder._promptMemberName.setText(member.getUserName());
        holder._promptMemberEmail.setText(member.getEmail());
        holder.objectID = member.getObjectId();

        return view;
    }

    public void addItem(User_TO item){

        if (item != null)
            this.mMembersList.add(item);
        else {
            Log.e("NullPointerException", "Cannot add an empty or null User_TO to list.");
            throw new NullPointerException("Cannot add an empty or null User_TO to list.");
        }
    }

    /**
     * Viewholder pattern class for this personalized list item
     */
    public class MemberGroupHolder{

        @BindView(R.id.prompt_member_name)
        TextView _promptMemberName = null;

        @BindView(R.id.prompt_memeber_email)
        TextView _promptMemberEmail = null;

        @BindView(R.id.action_delete_member)
        ImageView _actionDeleteMember = null;

        private String objectID = null;

        public MemberGroupHolder(View view) {
            ButterKnife.bind(this, view);

            this.setEvents();
        }

        private void setEvents(){

        }
    }
}
