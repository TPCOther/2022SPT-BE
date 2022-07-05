/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 11:35:07
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-29 16:45:36
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\DinTableServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.DinTable;
import com.easyorder.enums.DinTableStateEum;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.DinTableMapper;
import com.easyorder.mapper.OrderMapper;
import com.easyorder.service.BookingService;
import com.easyorder.service.DinTableService;
import com.easyorder.util.BaseExecuteException;

@Service
public class DinTableServiceImpl implements DinTableService {
    
    @Resource
    private DinTableMapper dinTableMapper;
    @Resource
    private OrderMapper orderMapper;
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
            // 检查预定桌台预定是否过期
            bookingService.checkBooking();
            //获取订单金额        
            List<DinTable> dinTableList = dinTableMapper.getDinTableDetail(wapper);
            for(DinTable tableTemp : dinTableList){
                try{
                    tableTemp.setOrderAmount(orderMapper.getOrderAmountByTableId(tableTemp.getDinTableId()));
                }catch(Exception e){}
                if(tableTemp.getOrderAmount()==null){
                    tableTemp.setOrderAmount(0); 
                }
            }
            System.out.println(dinTableList);
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
        if(insertDinTable.getDinTableState()==null){
            insertDinTable.setDinTableState(DinTableStateEum.IDLE.getState());
        }
        try{
            int effctedNum = dinTableMapper.insert(insertDinTable);
            if(effctedNum <= 0){
                throw new BaseExecuteException("插入0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(selectDinTableById(insertDinTable.getDinTableId()).getTemp());
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
                throw new BaseExecuteException("更新0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(deleteDinTable);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("更新桌台(DinTable)失败: "+e.getMessage());
        }
        
    }
    
    @Override
    public BaseExecution<DinTable> selectDinTableById(Long selectId)throws BaseExecuteException {
        // 检查预定桌台是否过期
        bookingService.checkBooking();

        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        QueryWrapper<DinTable> wapper = new QueryWrapper<>();

        wapper.eq("1",1);
        wapper.eq(selectId!=null,"din_table_id",selectId);

        try{
            List<DinTable> dinTableList = dinTableMapper.getDinTableDetail(wapper);
            if(dinTableList.size()!=1){
                throw new BaseExecuteException("查询桌台(DinTable)失败");
            }
            DinTable selectTable = dinTableList.get(0);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(selectTable);
            baseExecution.setCount(Long.valueOf(dinTableList.size()));
            return baseExecution;
        }catch (Exception e) {
            throw new BaseExecuteException("查询桌台(DinTable)失败");
        }
    }
    
}
