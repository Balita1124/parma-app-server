package com.pharma.app.product.playload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageResponse {
    @Getter
    @Setter
    private boolean first;
    @Getter
    @Setter
    private boolean last;
    @Getter
    @Setter
    private int currentPageNumber;
    @Getter
    @Setter
    private int itemsInPage;
    @Getter
    @Setter
    private int pageSize;
    @Getter
    @Setter
    private int totalPages;
    @Getter
    @Setter
    private long totalItems;
    @Getter
    @Setter
    private Sort sort;
    private List items;

    public void setPageStats(Page pg) {
        this.first = pg.isFirst();
        this.last = pg.isLast();
        this.currentPageNumber = pg.getNumber();
        this.itemsInPage = pg.getNumberOfElements();
        this.pageSize = pg.getSize();
        this.totalPages = pg.getTotalPages();
        this.totalItems = pg.getTotalElements();
        this.sort = pg.getSort();
    }

    public void setPageTotal(int count, boolean setDefaultMessage) {
        this.first = true;
        this.last = true;
        this.itemsInPage = count;
        this.totalItems = count;
        this.totalPages = 1;
        this.pageSize = count;
    }
}
