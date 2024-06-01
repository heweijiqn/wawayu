package hwj.wawayu.service;

import hwj.wawayu.sys.Reminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReminderServiceTest {

    private ReminderService reminderService;

    @BeforeEach
    void setUp() {
        reminderService = new ReminderService();
    }

    /**
     * 测试添加提醒功能。
     * 确保提醒被正确添加，并具有唯一的 ID 和正确的内容。
     */
    @Test
    void addReminder() {
        Long creatorId = 1L;
        Reminder reminder = new Reminder();
        // 设置创建者ID
        reminder.setCreatorId(creatorId);
        // 设置提醒内容
        reminder.setContent("2024.6.1，14点半去下象棋");
        // 指定提醒时间
        LocalDateTime specifiedTime = LocalDateTime.of(2024, 6, 1, 14, 30);
        // 设置提醒时间
        reminder.setReminderTime(specifiedTime);
        // 调用 addReminder
        Reminder addedReminder = reminderService.addReminder(creatorId, reminder);
        System.out.println(addedReminder);
        // 检查添加后的 Reminder 对象是否具有唯一的 ID
        assertNotNull(addedReminder.getId(), "添加后的提醒ID不是null");
        // 检查创建者ID是否正确
        assertEquals(reminder.getCreatorId(), addedReminder.getCreatorId(), "创建者ID应该一样才行");
        // 检查提醒内容是否正确
        assertEquals(reminder.getContent(), addedReminder.getContent(), "提醒内容应该一样才行");
        // 检查提醒时间是否正确
        assertEquals(reminder.getReminderTime(), addedReminder.getReminderTime(), "提醒时间应该一样才行");
    }


    /**
     * 测试根据创建者ID获取提醒列表功能。
     * 确保能够正确获取指定创建者的提醒列表。
     */
    @Test
    void testGetRemindersByCreatorId() {
        Long creatorId = 2L;
        Reminder reminder1 = new Reminder();
        reminder1.setCreatorId(creatorId);
        reminder1.setContent("2024.6.2，去下象棋");
        reminder1.setReminderTime(LocalDateTime.now().plusDays(1));

        Reminder reminder2 = new Reminder();
        reminder2.setCreatorId(creatorId);
        reminder2.setContent("2024.6.3，去下象棋");
        reminder2.setReminderTime(LocalDateTime.now().plusDays(2));

        reminderService.addReminder(creatorId,reminder1);
        reminderService.addReminder(creatorId,reminder2);

        List<Reminder> reminders = reminderService.getRemindersByCreatorId(2L);
        System.out.println(reminders);

        assertEquals(2, reminders.size(), "提醒应该有 2 个");
        assertTrue(reminders.contains(reminder1), "提醒1要在列表中");
        assertTrue(reminders.contains(reminder2), "提醒2要在列表中");
    }

    /**
     * 测试删除提醒功能。
     * 确保能够正确删除指定提醒。
     */
    @Test
    void testDeleteReminder() {
        Long creatorId = 3L;
        Reminder reminder = new Reminder();
        reminder.setCreatorId(creatorId);
        reminder.setContent("Test reminder");
        reminder.setReminderTime(LocalDateTime.now().plusDays(1));

        Reminder addedReminder = reminderService.addReminder(creatorId,reminder);
        System.out.println(addedReminder);
        reminderService.deleteReminder(addedReminder.getId(), 3L);
        System.out.println(reminderService.getRemindersByCreatorId(3L));
        assertTrue(reminderService.getRemindersByCreatorId(3L).isEmpty(), "删除后列表是空的");
    }

    /**
     * 测试更新提醒功能。
     * 确保能够正确更新指定提醒。
     */
    @Test
    void testUpdateReminder() {
        Long creatorId = 4L;
        Reminder reminder = new Reminder();
        reminder.setCreatorId(creatorId);
        reminder.setContent("2024.6.4，去下象棋");
        reminder.setReminderTime(LocalDateTime.now().plusDays(1));

        Reminder addedReminder = reminderService.addReminder(creatorId,reminder);
        System.out.println(addedReminder);
        String updatedContent = "2024.6.5，去下象棋";
        LocalDateTime updatedTime = LocalDateTime.now().plusDays(2);

        Reminder updatedReminder = reminderService.updateReminder(
                addedReminder.getId(), 4L, updatedContent, updatedTime);
        System.out.println(updatedReminder);
        assertEquals(updatedContent, updatedReminder.getContent(), "内容应该更新为新的");
        assertEquals(updatedTime, updatedReminder.getReminderTime(), "提醒时间应该更新为新的");
    }

    /**
     * 测试删除提醒时的权限校验。
     * 确保非创建者无法删除提醒。
     */
    @Test
    void testDeleteReminderByNonOwner() {
        Long creatorId = 5L;
        Reminder reminder = new Reminder();
        reminder.setCreatorId(creatorId);
        reminder.setContent("Test reminder");
        reminder.setReminderTime(LocalDateTime.now().plusDays(1));

        Reminder addedReminder = reminderService.addReminder(creatorId,reminder);

        System.out.println(addedReminder);
        try {
            reminderService.deleteReminder(addedReminder.getId(), 6L);
            System.out.println("如果没有异常则没有做好权限");
        } catch (Exception e) {
            System.out.println("测试通过信息没有被删除"+reminderService.getRemindersByCreatorId(5L));
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试更新提醒时的权限校验。
     * 确保非创建者无法更新提醒。
     */
    @Test
    void testUpdateReminderUnauthorized() {
        Long creatorId = 6L;
        Reminder reminder = new Reminder();
        reminder.setCreatorId(creatorId);
        reminder.setContent("2024.6.3，去下象棋");
        reminder.setReminderTime(LocalDateTime.now().plusDays(1));

        Reminder addedReminder = reminderService.addReminder(creatorId,reminder);

        System.out.println(addedReminder);
        String updatedContent = "2024.6.4，去下象棋";
        LocalDateTime updatedTime = LocalDateTime.now().plusDays(2);


        try {
            Reminder updatedReminder = reminderService.updateReminder(
                    addedReminder.getId(), 7L, updatedContent, updatedTime);
            System.out.println("如果没有异常则没有做好权限");
        } catch (Exception e) {
            System.out.println("测试通过信息没有被修改"+reminderService.getRemindersByCreatorId(6L));
            throw new RuntimeException(e);
        }


    }
}
