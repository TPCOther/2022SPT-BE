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

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.DinTable;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.DinTableMapper;
import com.easyorder.service.DinTableService;
import com.easyorder.util.BaseExecuteException;

@Service
public class DinTableServiceImpl implements DinTableService {
    
    @Resource
    private DinTableMapper dinTableMapper;

    @Override
    public BaseExecution<DinTable> selectDinTableList(Long dinTableId, Long areaId, Integer dinTableState, Integer dinTableCapacity)
            throws BaseExecuteException {
                BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
                QueryWrapper<DinTable> wapper = new QueryWrapper<>();
                wapper.eq(dinTableId!=null,"dinTable_id",dinTableId);
                wapper.eq(areaId!=null,"area_id",areaId);
                wapper.eq(dinTableState!=null,"dinTable_state",dinTableState);
                wapper.eq(dinTableCapacity!=null,"dinTable_capacity",dinTableCapacity);
                try{
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
    public BaseExecution<DinTable> insertDinTable(DinTable insertDinTable) throws BaseExecuteException {
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
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
    
    
    
}
