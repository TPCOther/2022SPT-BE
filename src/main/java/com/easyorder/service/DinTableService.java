package com.easyorder.service;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.DinTable;
import com.easyorder.util.BaseExecuteException;

public interface DinTableService {
    BaseExecution<DinTable> selectDinTableList(DinTable selectDinTable) throws BaseExecuteException;
    BaseExecution<DinTable> insertDinTable(DinTable insertDinTable) throws BaseExecuteException;
    BaseExecution<DinTable> updateDinTable(DinTable updateDinTable) throws BaseExecuteException;
    BaseExecution<DinTable> deleteDinTable(DinTable deleteDinTable) throws BaseExecuteException;
    BaseExecution<DinTable> selectDinTableById(Long selectId)throws BaseExecuteException;
}
