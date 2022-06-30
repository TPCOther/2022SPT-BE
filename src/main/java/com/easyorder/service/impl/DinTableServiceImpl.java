package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.DinTable;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.DinTableMapper;
import com.easyorder.service.BookingService;
import com.easyorder.service.DinTableService;
import com.easyorder.util.BaseExecuteException;

@Service
public class DinTableServiceImpl implements DinTableService {
    
    @Resource
    private DinTableMapper dinTableMapper;
    @Autowired
    private BookingService bookingService;

    @Override
    public BaseExecution<DinTable> selectDinTableList(DinTable selectDinTable)throws BaseExecuteException {
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        QueryWrapper<DinTable> wapper = new QueryWrapper<>();
        Long dinTableId=selectDinTable.getDinTableId();
        Long areaId=selectDinTable.getAreaId();
        Integer dinTableState=selectDinTable.getDinTableState();
        Integer dinTableCapacity=selectDinTable.getDinTableCapacity();
        
        wapper.eq("1",1);
        wapper.eq(dinTableId!=null,"din_table_id",dinTableId);
        wapper.eq(areaId!=null,"area_id",areaId);
        wapper.eq(dinTableState!=null,"din_table_state",dinTableState);
        wapper.eq(dinTableCapacity!=null,"din_table_capacity",dinTableCapacity);
        
        try{
            // TODO 检查预定桌台预定是否过期
            bookingService.checkBooking();
            
            List<DinTable> dinTableList = dinTableMapper.getDinTableArea(wapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(dinTableList);
            baseExecution.setCount(Long.valueOf(dinTableList.size()));
            return baseExecution;
        }catch (Exception e) {
            throw new BaseExecuteException("查询桌台(DinTable)失败: "+e.getMessage());
        }

    }

    @Override
    @Transactional
    public BaseExecution<DinTable> insertDinTable(DinTable insertDinTable) throws BaseExecuteException {
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        //判断输入是否完整
        if(insertDinTable.getAreaId()==null){
            throw new BaseExecuteException("输入信息不完整");
        }
        try{
            int effctedNum = dinTableMapper.insert(insertDinTable);
            if(effctedNum <= 0){
                throw new BaseExecuteException("插入0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(insertDinTable);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("创建桌台(DinTable)失败: "+e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseExecution<DinTable> updateDinTable(DinTable updateDinTable) throws BaseExecuteException {
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        try{
            int effctedNum = dinTableMapper.updateById(updateDinTable);
            if(effctedNum <= 0){
                throw new BaseExecuteException("更新0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(updateDinTable);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("更新桌台(DinTable)失败: "+e.getMessage());
        }
        
    }

    @Override
    @Transactional
    public BaseExecution<DinTable> deleteDinTable(DinTable deleteDinTable) throws BaseExecuteException {
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        try{
            int effctedNum = dinTableMapper.deleteById(deleteDinTable);
            if(effctedNum <= 0){
                throw new BaseExecuteException("删除0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(deleteDinTable);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("删除桌台(DinTable)失败: "+e.getMessage());
        }
        
    }
    
    @Override
    public BaseExecution<DinTable> selectDinTableById(Long selectId)throws BaseExecuteException {
        // TODO 检查预定桌台是否过期
        bookingService.checkBooking();

        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        QueryWrapper<DinTable> wapper = new QueryWrapper<>();

        wapper.eq("1",1);
        wapper.eq(selectId!=null,"din_table_id",selectId);

        try{
            List<DinTable> dinTableList = dinTableMapper.getDinTableArea(wapper);
            // if(dinTableList.size()!=1){
            //     throw new BaseExecuteException("查询桌台(DinTable)失败");
            // }
            DinTable selectTable = dinTableList.get(0);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(selectTable);
            baseExecution.setCount(Long.valueOf(dinTableList.size()));
            return baseExecution;
        }catch (Exception e) {
            throw new BaseExecuteException("查询桌台(DinTable)失败: "+e.getMessage());
        }
    }
    
}
