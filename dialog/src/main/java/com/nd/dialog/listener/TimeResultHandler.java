package com.nd.dialog.listener;

import java.util.Date;

/**
 * 定义结果回调接口
 *
 * @author Administrator
 */
public interface TimeResultHandler {
    /**
     * 时间选择回调
     *
     * @param date
     */
    void onTimeHandle(Date date);
}