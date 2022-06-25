package com.easyorder.service;

import java.util.List;

import com.easyorder.entity.DinTable;

public interface DinTableService {
    List<DinTable> getDinTableList();
    void insertDinTable(DinTable insertTable);
}
