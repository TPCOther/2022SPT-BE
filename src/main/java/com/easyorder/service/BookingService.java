package com.easyorder.service;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Booking;
import com.easyorder.util.BaseExecuteException;

public interface BookingService {
    BaseExecution<Booking> selectBookingList(Booking selectBooking) throws BaseExecuteException;
    BaseExecution<Booking> insertBooking(Booking insertBooking) throws BaseExecuteException;
    BaseExecution<Booking> updateBooking(Booking updateBooking) throws BaseExecuteException;
    BaseExecution<Booking> deleteBooking(Booking deleteBooking) throws BaseExecuteException;
    BaseExecution<Booking> selectBookingById(Long selectId)throws BaseExecuteException;
    BaseExecution<Booking> checkBooking()throws BaseExecuteException;
}
