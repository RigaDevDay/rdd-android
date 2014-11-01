/*
 Copyright 2013 Tonic Artos

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package lv.rigadevday.android.ui.schedule;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapter;

import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.ui.ViewHolder;

public class ScheduleGridAdapter extends BaseAdapter implements StickyGridHeadersBaseAdapter {
    private final Typeface robotoLight;
    private final Typeface robotoRegular;
    private final Context context;

    private int headerResId;
    private LayoutInflater inflater;
    private int itemResId;
    private Schedule schedule;


    public ScheduleGridAdapter(Context context, Schedule schedule, int headerResId, int itemResId) {
        this.context = context;
        this.schedule = schedule;
        this.headerResId = headerResId;
        this.itemResId = itemResId;
        this.inflater = LayoutInflater.from(context);
        //
        this.robotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        this.robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getCount() {
        return schedule.getPresentations().size();
    }

    @Override
    public Presentation getItem(int position) {
        return schedule.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCountForHeader(int index) {
        return schedule.getCountForHeader(index);
    }

    @Override
    public int getNumHeaders() {
        return schedule.getHeaderCount();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(headerResId, parent, false);
        }
        Presentation item = getItem(position);
        pupulateView(convertView, item, ViewIds.HEADER);

        return convertView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(itemResId, parent, false);
        }
        Presentation item = getItem(position);
        pupulateView(convertView, item, ViewIds.ITEM);

        return convertView;
    }

    private void pupulateView(View convertView, Presentation item, ViewIds ids) {
        TextView title = ViewHolder.get(convertView, ids.titleId);
        title.setTypeface(robotoRegular);
        title.setText(item.getTitle());

        TextView time = ViewHolder.get(convertView, ids.timePlaceId);
        time.setTypeface(robotoLight);
        // TODO fix
        String info = assembleEventInfo("Start", "End", "Room");
        time.setText(info);

        ImageView bookmark = ViewHolder.get(convertView, ids.bookmarkId);
        bookmark.setVisibility(item.isBookmarked() ? View.VISIBLE : View.INVISIBLE);

        // TODO: implement placeholder arawable assigning to presentations
        ImageView image = ViewHolder.get(convertView, ids.imageId);
        int drawableId = item.isHeader() ? R.drawable.pic_1_edited : R.drawable.pic_2_edited;
        image.setImageDrawable(context.getResources().getDrawable(drawableId));
    }

    private String assembleEventInfo(String start, String end, String room) {
        return String.format("%s - %s, %s", start, end, room);
    }

    private static enum ViewIds {
        HEADER(R.id.header_title, R.id.header_time_place, R.id.header_bookmark, R.id.header_image),
        ITEM(R.id.item_title, R.id.item_time_place, R.id.item_bookmark, R.id.item_image);

        final int titleId;
        final int timePlaceId;
        final int bookmarkId;
        final int imageId;

        private ViewIds(int titleId, int timePlaceId, int bookmarkId, int imageId) {
            this.titleId = titleId;
            this.timePlaceId = timePlaceId;
            this.bookmarkId = bookmarkId;
            this.imageId = imageId;
        }
    }
}
