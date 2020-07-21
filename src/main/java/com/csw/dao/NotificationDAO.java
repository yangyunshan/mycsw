package com.csw.dao;

import com.csw.model.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationDAO {
    int insertData(@Param("notification") Notification notification,
                    @Param("notificationId") String notificationId);

    List<Notification> selectNotificationById(@Param("id") String id);

    int deleteNotificationById(@Param("id") String id);
}
