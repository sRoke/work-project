//package net.kingsilk.qh.vote.msg.imp.addAdmin;
//
//import net.kingsilk.qh.platform.msg.Q;
//import net.kingsilk.qh.platform.msg.api.admin.AddSAEventEXApi;
//import net.kingsilk.qh.platform.msg.api.admin.AddSAEventEx;
//import net.kingsilk.qh.vote.msg.imp.AbstractJobImpl;
//import net.kingsilk.qh.vote.service.QhVoteProperties;
//import net.kingsilk.qh.vote.service.service.StaffService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component("PlatformAddSAEventEXApi")
//public class AddAdminHandler extends AbstractJobImpl implements AddSAEventEXApi {
//
//
//    @Autowired
//    private QhVoteProperties qhVoteProperties;
//
//
//    @Autowired
//    private StaffService staffService;
//
//
//    public String getLockKey(AddSAEventEx addSAEventEx) {
//        StringBuffer buf = new StringBuffer();
//        buf
//                .append(Q.MQ_EXCHANGE_PREFIX)
//                .append("/").append(AddSAEventEx.class.getName())
//                .append("/").append(addSAEventEx.getOrgId());
//        return buf.toString();
//    }
//
//    @Override
//    public void handle(AddSAEventEx addSAEventEx) {
//
//        String lockKey = getLockKey(addSAEventEx);
//        long waitLockTime = qhVoteProperties.getMq().getDefaultConf().getLockWaitTime();
//
//        lockAndExec(lockKey, waitLockTime, () -> {
//
//            if (addSAEventEx.getAppName().equals("QH_ACTIVITY")) {
//                staffService.setSAStaff(addSAEventEx.getUserId(), addSAEventEx.getBrandAppId());
//            }
//
//        });
//
//    }
//
//
//}
