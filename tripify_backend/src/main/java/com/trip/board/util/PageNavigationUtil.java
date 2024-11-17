package com.trip.board.util;

import com.trip.board.vo.PaginationVO;

public class PageNavigationUtil {
    public static PaginationVO createPagination(int currentPage, int totalRecords, int pageSize) {
        PaginationVO pagination = new PaginationVO();
        pagination.setCurrentPage(currentPage);
        pagination.setTotalRecords(totalRecords);
        pagination.setPageSize(pageSize);
        pagination.setTotalPages((int) Math.ceil((double) totalRecords / pageSize));
        return pagination;
    }
}
