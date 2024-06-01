/**
 * @author 何伟健
 * @version 1.0
 * @date 2024/6/1 9:50
 */


package hwj.wawayu.sys;

import lombok.Data;

import java.time.LocalDateTime;





/*
 * 提醒实体类
 */

@Data
public class Reminder {
    private Long id;  // 提醒ID
    private Long creatorId;  // 创建者ID
    private String content;  // 提醒内容
    private LocalDateTime reminderTime;  // 提醒时间



}
