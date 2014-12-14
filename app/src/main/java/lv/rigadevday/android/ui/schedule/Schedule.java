package lv.rigadevday.android.ui.schedule;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import lv.rigadevday.android.domain.Presentation;

public class Schedule {
    private final List<Presentation> presentations;
    private final Map<Presentation, Integer> headerCountMap;
    private final List<Presentation> orderedHeaders;

    public Schedule(List<Presentation> presentations) {
        this.presentations = orderByTime(presentations);
        this.orderedHeaders = createOrderedHeaderList(presentations);
        this.headerCountMap = buildHeaderCountMap();
    }

    private List<Presentation> createOrderedHeaderList(List<Presentation> presentations) {
        return getOrderingById().sortedCopy(
                Iterables.filter(
                        presentations, new Predicate<Presentation>() {
                            @Override
                            public boolean apply(Presentation input) {
                                return false; //input.isHeader();
                            }
                        }));
    }

    private Map<Presentation, Integer> buildHeaderCountMap() {
        Map<Presentation, Integer> headerMap = Maps.newHashMap();

        Presentation currentHeader = null;
        int headerItemCnt = 0;

        for (Presentation presentation : presentations) {
//            if (presentation.isHeader()) {
//                if (currentHeader != null) {
//                    headerMap.put(currentHeader, headerItemCnt);
//                }
//                currentHeader = presentation;
//                headerItemCnt = 0;
//            } else {
//                headerItemCnt++;
//            }
        }

        headerMap.put(currentHeader, headerItemCnt); //put last

        return headerMap;
    }

    private List<Presentation> orderByTime(List<Presentation> presentations) {
        return getOrderingById().sortedCopy(presentations);
    }

    public int getHeaderCount() {
        return orderedHeaders.size();
    }

    public List<Presentation> getPresentations() {
        return Collections.unmodifiableList(presentations);
    }

    public int getCountForHeader(int headerIndex) {
        Integer count = headerCountMap.get(orderedHeaders.get(headerIndex));
        return count == null ? 0 : count;
    }

    public Presentation getItem(int index) {
        return presentations.get(index);
    }

    //TODO: decide how to order presentations. Time
    private static Ordering<Presentation> getOrderingById() {
        return new Ordering<Presentation>() {
            @Override
            public int compare(Presentation left, Presentation right) {
                return left.getId().compareTo(right.getId());
            }
        };
    }
}
