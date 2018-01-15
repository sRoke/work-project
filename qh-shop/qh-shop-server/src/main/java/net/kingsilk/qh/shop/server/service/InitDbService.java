package net.kingsilk.qh.shop.server.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.core.AuthorityEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.OrderRepo;
import net.kingsilk.qh.shop.repo.ShopStaffGroupRepo;
import net.kingsilk.qh.shop.repo.StaffGroupRepo;
import net.kingsilk.qh.shop.repo.StaffRepo;
import net.kingsilk.qh.shop.service.service.AuthorityService;
import net.kingsilk.qh.shop.service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class InitDbService {


    private static final String brandAppId = "59782691f8fdbc1f9b2c4323";

    private static final String staffGroupId = "59cb38fad1e5e709d058093a";

    private static final String shopStaffGroupAdId = "59cb38fad1e5e709d058093b";


    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private StaffGroupRepo staffGroupRepo;

    @Autowired
    private ShopStaffGroupRepo shopStaffGroupRepo;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    @Qualifier("initThreadPool")
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @EventListener
    void onApplicationStated(ContextRefreshedEvent event) {
        initAdminUser();
        initShopGroup();
        checkOrderOverdue();
    }


    private void initAdminUser() {
        Staff user = staffRepo.findOne(
                QStaff.staff.userId.eq("58de6b27785a82000005a140")
        );

        StaffGroup staffGroup = staffGroupRepo.findOne(
                QStaffGroup.staffGroup.brandAppId.eq(staffGroupId)
        );

        if (user == null) {
            user = new Staff();
            user.setMemo("admin");
            user.setEnable(true);
            user.setBrandAppId(brandAppId);
            user.setUserId("58de6b27785a82000005a140");
            user = staffRepo.save(user);
            if (staffGroup == null || !staffGroup.getStaffS().contains(user)) {
                staffGroup = new StaffGroup();
                staffGroup.setBrandAppId(brandAppId);
                staffGroup.setName("超级管理员");
                Set<String> authorities = new HashSet<>();
                Set<Staff> staffs = new HashSet<>();
                staffs.add(user);
                authorities.add(AuthorityEnum.SA.name());
                staffGroup.setAuthorities(authorities);
                staffGroup.setStaffS(staffs);
                staffGroup.setReserved(true);
                staffGroupRepo.save(staffGroup);
            }
        }
    }

    private void initShopGroup() {

        ShopStaffGroup shopStaffGroup = shopStaffGroupRepo.findOne(shopStaffGroupAdId);

        if (shopStaffGroup == null) {
            shopStaffGroup = new ShopStaffGroup();
            //todo 所有权限
            Set<String> stringSet = new HashSet<>();
            stringSet = authorityService.fillAuth(stringSet, AuthorityEnum.SHOP_SA);
            shopStaffGroup.setAuthorities(stringSet);
            shopStaffGroup.setId(shopStaffGroupAdId);
            shopStaffGroup.setBrandAppId(brandAppId);
            shopStaffGroup.setName("shopAdmin");
            shopStaffGroup.setEnable(true);
            shopStaffGroup.setDesc("门店管理员");
            shopStaffGroupRepo.save(shopStaffGroup);
        }

    }

    private void checkOrderOverdue() {
        Iterable<Order> list = orderRepo.findAll(
                Expressions.allOf(
                        QOrder.order.deleted.ne(true),
                        QOrder.order.status.eq(OrderStatusEnum.UNPAYED)
                )
        );
        list.forEach(
                order -> {
                    Runnable run = () -> {
                        taskService.run(order.getId());
                    };

                    Date runDate = new Date(order.getDateCreated().getTime() + 60 * 60 * 1000);
                    if (new Date().after(runDate)) {
                        threadPoolTaskScheduler.schedule(run, new Date(new Date().getTime() + 10 * 1000));

                    } else {
                        threadPoolTaskScheduler.schedule(run, runDate);
                    }
                }
        );

    }

}
