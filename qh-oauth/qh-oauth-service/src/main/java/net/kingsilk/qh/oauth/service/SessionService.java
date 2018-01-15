package net.kingsilk.qh.oauth.service;

import net.kingsilk.qh.oauth.api.s.attr.*;
import org.springframework.stereotype.*;
import org.springframework.web.context.request.*;

import javax.annotation.*;
import java.util.*;

/**
 *
 */
@Service
public class SessionService {

    // FIXME: 分布式情况下该锁仍会有问题

    // ---------------------- PhoneAuthInfo
    @Nonnull
    public List<PhoneAuthInfo> getPhoneAuthInfos() {


        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            List<PhoneAuthInfo> infos = (List<PhoneAuthInfo>) RequestContextHolder.currentRequestAttributes().getAttribute(
                    PhoneAuthInfo.SESSION_KEY,
                    RequestAttributes.SCOPE_SESSION
            );

            if (infos == null) {
                infos = new LinkedList<>();
                setPhoneAuthInfos(infos);
            }
            return infos;
        }
    }


    public void setPhoneAuthInfos(List<PhoneAuthInfo> infos) {

        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            RequestContextHolder.currentRequestAttributes().setAttribute(
                    PhoneAuthInfo.SESSION_KEY,
                    infos,
                    RequestAttributes.SCOPE_SESSION
            );
        }
    }

    public void add(PhoneAuthInfo info) {
        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            List<PhoneAuthInfo> infos = getPhoneAuthInfos();
            infos.add(info);
            setPhoneAuthInfos(infos);
        }
    }



    // ---------------------- WxMpAuthInfo
    @Nonnull
    public List<WxMpAuthInfo> getWxMpAuthInfos() {


        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            List<WxMpAuthInfo> infos = (List<WxMpAuthInfo>) RequestContextHolder.currentRequestAttributes().getAttribute(
                    WxMpAuthInfo.SESSION_KEY,
                    RequestAttributes.SCOPE_SESSION
            );

            if (infos == null) {
                infos = new LinkedList<>();
                setWxMpAuthInfos(infos);
            }
            return infos;
        }
    }

    public void setWxMpAuthInfos(List<WxMpAuthInfo> infos) {

        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            RequestContextHolder.currentRequestAttributes().setAttribute(
                    WxMpAuthInfo.SESSION_KEY,
                    infos,
                    RequestAttributes.SCOPE_SESSION
            );
        }
    }


    public void add(WxMpAuthInfo info) {
        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            List<WxMpAuthInfo> infos = getWxMpAuthInfos();
            infos.add(info);
            setWxMpAuthInfos(infos);
        }
    }



    // ---------------------- WxComMpAuthInfo
    @Nonnull
    public List<WxComMpAuthInfo> getWxComMpAuthInfos() {


        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            List<WxComMpAuthInfo> infos = (List<WxComMpAuthInfo>) RequestContextHolder.currentRequestAttributes().getAttribute(
                    WxComMpAuthInfo.SESSION_KEY,
                    RequestAttributes.SCOPE_SESSION
            );

            if (infos == null) {
                infos = new LinkedList<>();
                setWxComMpAuthInfos(infos);
            }
            return infos;
        }
    }

    public void setWxComMpAuthInfos(List<WxComMpAuthInfo> infos) {

        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            RequestContextHolder.currentRequestAttributes().setAttribute(
                    WxComMpAuthInfo.SESSION_KEY,
                    infos,
                    RequestAttributes.SCOPE_SESSION
            );
        }
    }


    public void add(WxComMpAuthInfo info) {
        synchronized (RequestContextHolder.currentRequestAttributes().getSessionMutex()) {
            List<WxComMpAuthInfo> infos = getWxComMpAuthInfos();
            infos.add(info);
            setWxComMpAuthInfos(infos);
        }
    }

}
