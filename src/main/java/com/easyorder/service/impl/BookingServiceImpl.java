package com.easyorder.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Booking;
import com.easyorder.entity.DinTable;
import com.easyorder.enums.BookingStateEum;
import com.easyorder.enums.DinTableStateEum;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.BookingMapper;
import com.easyorder.mapper.DinTableMapper;
import com.easyorder.service.BookingService;
import com.easyorder.util.BaseExecuteException;

@Service
public class BookingServiceImpl implements BookingService {
    @Resource
    private BookingMapper bookingMapper;
    @Resource
    private DinTableMapper dinTableMapper;

    @Override
    @Transactional
    public BaseExecution<Booking> selectBookingList(Booking selectBooking) throws BaseExecuteException {
        BaseExecution<Booking> baseExecution = new BaseExecution<Booking>();
        QueryWrapper<Booking> wapper = new QueryWrapper<Booking>();

        Long bookingId=selectBooking.getBookingId();
        Long tableId=selectBooking.getDinTableId();
        String areaName=selectBooking.getAreaName();
        String bookingName=selectBooking.getBookingName();
        Integer bookingPhone=selectBooking.getBookingPhone();
        Integer bookingState=selectBooking.getBookingState();

        wapper.eq("1",1);
        wapper.eq(bookingId!=null,"booking_id",bookingId);
        wapper.eq(tableId!=null,"booking.din_table_id",tableId);
        wapper.eq(areaName!=null,"area_name",areaName);
        wapper.eq(bookingName!=null,"booking_name",bookingName);
        wapper.eq(bookingPhone!=null,"booking_phone",bookingPhone);
        wapper.eq(bookingState!=null,"booking_state",bookingState);

        try{
            checkBooking();
            List<Booking> bookingList = bookingMapper.getBookingDetail(wapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(bookingList);
            baseExecution.setCount(Long.valueOf(bookingList.size()));
            return baseExecution;
        }catch (Exception e) {
            throw new BaseExecuteException("查询预约信息(Booking)失败: "+e.getMessage());
    }
    }

    @Override
    @Transactional
    public BaseExecution<Booking> insertBooking(Booking insertBooking) throws BaseExecuteException {
        BaseExecution<Booking> baseExecution = new BaseExecution<Booking>();
        try{
            //判断该桌台是否可以被预定
            if(dinTableMapper.selectById(insertBooking.getDinTableId()).getDinTableState()!=DinTableStateEum.IDLE.getState() ){
                throw new BaseExecuteException("该桌台现在不可预定");
            }
            //设置时间
            Date now = new Date();
            int duration = 1000*60*60*2;
            insertBooking.setCreateTime(now);
            insertBooking.setStartTime(insertBooking.getStartTime());
            insertBooking.setEndTime(new Date(insertBooking.getStartTime().getTime()+duration));
            //插入数据
            int effctedNum = bookingMapper.insert(insertBooking);
            if(effctedNum <= 0){
                throw new BaseExecuteException("插入0条数据");
            }
            //修改对应餐桌的状态
            DinTable bookingTable = new DinTable();
            bookingTable.setDinTableId(insertBooking.getDinTableId());
            bookingTable.setDinTableState(DinTableStateEum.BOOKING.getState());
            dinTableMapper.updateById(bookingTable);
            //返回baseExecution
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(insertBooking);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("创建预约信息(Booking)失败: "+e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseExecution<Booking> updateBooking(Booking updateBooking) throws BaseExecuteException {
        BaseExecution<Booking> baseExecution = new BaseExecution<Booking>();
        try{
            //完成失效与完成预约的操作，需要修改对应桌台的状态变化
            Booking oriBooking = bookingMapper.selectById(updateBooking.getBookingId());
            
            int effctedNum = bookingMapper.updateById(updateBooking);
            if(effctedNum <= 0){
                throw new BaseExecuteException("更新0条数据");
            }
            Booking aftBooking = bookingMapper.selectById(updateBooking.getBookingId());
            //判断桌台是否改变
            if(aftBooking.getDinTableId()!=null && aftBooking.getDinTableId()!=oriBooking.getDinTableId()){
                DinTable oriTable = new DinTable();
                DinTable updTable = new DinTable();

                oriTable.setDinTableId(oriBooking.getDinTableId());
                oriTable.setDinTableState(DinTableStateEum.IDLE.getState());
                updTable.setDinTableId(aftBooking.getDinTableId());
                updTable.setDinTableState(DinTableStateEum.BOOKING.getState());

                dinTableMapper.updateById(oriTable);
                dinTableMapper.updateById(updTable);
            }
            //判断预订状态是否仍为预约中，否则改变桌台状态
            if(aftBooking.getBookingState()==BookingStateEum.BOOKING.getState()){
                DinTable updTable = new DinTable();
                updTable.setDinTableId(aftBooking.getDinTableId());
                updTable.setDinTableState(DinTableStateEum.BOOKING.getState());
                dinTableMapper.updateById(updTable);
            }else{
                DinTable updTable = new DinTable();
                updTable.setDinTableId(aftBooking.getDinTableId());
                updTable.setDinTableState(DinTableStateEum.IDLE.getState());
                dinTableMapper.updateById(updTable);
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(aftBooking);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("更新预约信息(Booking)失败: "+e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseExecution<Booking> deleteBooking(Booking deleteBooking) throws BaseExecuteException {
        BaseExecution<Booking> baseExecution = new BaseExecution<Booking>();
        try{
            int effctedNum = bookingMapper.deleteById(deleteBooking);
            if(effctedNum <= 0){
                throw new BaseExecuteException("删除0条数据");
            }
            //更新桌台状态
            DinTable updTable = new DinTable();
            updTable.setDinTableId(deleteBooking.getDinTableId());
            updTable.setDinTableState(DinTableStateEum.IDLE.getState());
            dinTableMapper.updateById(updTable);

            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(deleteBooking);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("删除预约信息(Booking)失败: "+e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseExecution<Booking> selectBookingById(Long selectId)throws BaseExecuteException {
        BaseExecution<Booking> baseExecution = new BaseExecution<Booking>();
        QueryWrapper<Booking> wapper = new QueryWrapper<>();

        wapper.eq("1",1);
        wapper.eq(selectId!=null,"booking_id",selectId);

        try{
            List<Booking> bookingList = bookingMapper.getBookingDetail(wapper);
            // if(dinTableList.size()!=1){
            //     throw new BaseExecuteException("查询桌台(DinTable)失败");
            // }
            Booking selectBooking = bookingList.get(0);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(selectBooking);
            baseExecution.setCount(Long.valueOf(bookingList.size()));
            return baseExecution;
        }catch (Exception e) {
            throw new BaseExecuteException("查询桌台(DinTable)失败: "+e.getMessage());
        }
    }

    @Override
    @Transactional
    public BaseExecution<Booking> checkBooking() throws BaseExecuteException {
        BaseExecution<Booking> baseExecution = new BaseExecution<Booking>();

        QueryWrapper<Booking> wapper = new QueryWrapper<Booking>();
        wapper.eq("1",1);
        wapper.eq("booking_state",BookingStateEum.BOOKING.getState());
        try{
            List<Booking> checkBookingList=bookingMapper.getBookingDetail(wapper);
            Date now = new Date();
            for(Booking checkBooking : checkBookingList){
                if(checkBooking.getEndTime().getTime() <= now.getTime()){
                    checkBooking.setBookingState(BookingStateEum.INVALID.getState());
                    updateBooking(checkBooking);
                }
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            return baseExecution;
        }catch(Exception e){
            throw new BaseExecuteException("检查预约信息(Booking)失败: "+e.getMessage());
        }

    }



}
