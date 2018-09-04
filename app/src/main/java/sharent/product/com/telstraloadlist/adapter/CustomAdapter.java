package sharent.product.com.telstraloadlist.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import sharent.product.com.telstraloadlist.R;
import sharent.product.com.telstraloadlist.models.Row;
import sharent.product.com.telstraloadlist.utils.constants;

public class CustomAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    List<Row> rows;
    Context context;

    public CustomAdapter(Context context, List<Row> rows) {
        this.context = context;
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        ViewHolder holder;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            vi = inflater.inflate(R.layout.listitem_layout, null);
            holder = new ViewHolder();
            holder.txt_title = (TextView) vi.findViewById(R.id.txt_title);
            holder.txt_desc = (TextView) vi.findViewById(R.id.txt_des);
            holder.mSimpleDraweeView = (SimpleDraweeView) vi.findViewById(R.id.image_view);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        if(constants.isEmptyString(rows.get(position).getTitle())){
            holder.txt_title.setVisibility(View.GONE);
        }

        holder.txt_title.setText(rows.get(position).getTitle());
        holder.txt_desc.setText(rows.get(position).getDescription());

        try{

            Uri mImageUri = Uri.parse(rows.get(position).getImageHref().toString());
            holder.mSimpleDraweeView.setImageURI(mImageUri);
        }catch (Exception e){

        }

        return vi;
    }

    public static class ViewHolder {
        public TextView txt_title, txt_desc;
        public SimpleDraweeView mSimpleDraweeView;
    }
}
