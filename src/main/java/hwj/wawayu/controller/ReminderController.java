/**
 * @author 何伟健
 * @version 1.0
 * @date 2024/6/1 10:28
 */


package hwj.wawayu.controller;


import hwj.wawayu.service.ReminderService;
import hwj.wawayu.sys.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;



    /**
     * 增加新的提醒。
     */

    @PostMapping("/add")
    public Reminder addReminder(@RequestParam Long creatorId, @RequestBody Reminder reminder) {
        return reminderService.addReminder(creatorId, reminder);
    }

    /**
     * 获取指定创建者的提醒列表。
     */
    @GetMapping("/{creatorId}")
    public List<Reminder> getRemindersByCreatorId(@PathVariable Long creatorId) {

        return reminderService.getRemindersByCreatorId(creatorId);
    }

    /**
     * 删除指定提醒。
     */
    @DeleteMapping("/{id}/{creatorId}")
    public void deleteReminder(@PathVariable Long id, @PathVariable Long creatorId) {

        reminderService.deleteReminder(id, creatorId);
    }

    /**
     * 更新提醒内容或时间。
     */
    @PutMapping("/{id}")
    public Reminder updateReminder(@PathVariable Long id,
                                   @RequestParam Long creatorId,
                                   @RequestParam String content,
                                   @RequestParam LocalDateTime reminderTime
                                  ) {

        return reminderService.updateReminder(id, creatorId, content, reminderTime);
    }
}
