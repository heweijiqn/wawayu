/**
 * @author 何伟健
 * @version 1.0
 * @date 2024/6/1 10:13
 */


package hwj.wawayu.service;


import hwj.wawayu.sys.Reminder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReminderService {

    private final Map<Long, Reminder> reminders = new HashMap<>();
    // 用于生成唯一ID的AtomicLong
    private static final AtomicLong idGenerator  = new AtomicLong();


    /**
     * 添加新的提醒。
     */
    public Reminder addReminder(Reminder reminder) {
        long id = idGenerator.incrementAndGet();  // 生成唯一ID
        reminder.setId(id);
        reminders.put(id, reminder);
        return reminder;
    }


    /**
     * 根据创建者ID获取提醒列表。
     */
    public List<Reminder> getRemindersByCreatorId(Long creatorId) {
        List<Reminder> result = new ArrayList<>();
        for (Reminder reminder : reminders.values()) {
            if (reminder.getCreatorId().equals(creatorId)) {
                result.add(reminder);
            }
        }
        return result;

    }

    /**
     * 删除提醒
     */
    public void deleteReminder(Long reminderId, Long creatorId) {
        Reminder reminder = reminders.get(reminderId);
        if (reminder == null) {
            throw new IllegalArgumentException("提醒不存在");
        }
        if (!reminder.getCreatorId().equals(creatorId)) {
            throw new IllegalStateException("只能对自己的信息进行删除");
        }
        reminders.remove(reminderId);
    }

    /**
     * 更新提醒内容或时间。
     */
    public Reminder updateReminder(Long id, Long creatorId, String content, LocalDateTime reminderTime) {
        Reminder reminder = reminders.get(id);
        if (reminder != null && reminder.getCreatorId().equals(creatorId)) {
            reminder.setContent(content);
            reminder.setReminderTime(reminderTime);
            reminders.put(id, reminder);
            return reminder;
        } else {
            throw new IllegalStateException("只能更新自己的提醒信息");
        }
    }

}
