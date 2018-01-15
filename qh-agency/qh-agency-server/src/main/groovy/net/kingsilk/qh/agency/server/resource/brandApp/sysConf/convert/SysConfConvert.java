package net.kingsilk.qh.agency.server.resource.brandApp.sysConf.convert;

import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfInfoResp;
import net.kingsilk.qh.agency.domain.SysConf;
import net.kingsilk.qh.agency.repo.SysConfRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SysConfConvert {

    @Autowired
    private SysConfRepo sysConfRepo;

    public SysConfInfoResp sysConfInfoConvert(SysConf sysConf) {
        SysConfInfoResp sysConfInfoResp = new SysConfInfoResp();
        sysConfInfoResp.setDataType(sysConf.getDataType().getCode());
        sysConfInfoResp.setDefaultTitle(sysConf.getDefaultTitle());
        sysConfInfoResp.setKey(sysConf.getKey());
        sysConfInfoResp.setMemo(sysConf.getMemo());
        sysConfInfoResp.setTitle(sysConf.getTitle());
        sysConfInfoResp.setDefaultValue(sysConf.getDefaultValue());
        switch (sysConf.getDataType()) {
            case INT:
                sysConfInfoResp.setCurrentValue(sysConf.getValueInt().toString());
                break;
            case TEXT:
                sysConfInfoResp.setCurrentValue(sysConf.getValueText());
                break;
            case MAP:
                try {
                    sysConfInfoResp.setCurrentMap(sysConf.getValueMap());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case LIST:
                try {
                    sysConfInfoResp.setCurrentList(sysConf.getValueList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return sysConfInfoResp;
    }

    public String sysConfConvert(String key, String brandAppId, String defaultValue) {
        SysConf cashierDiscountSys = sysConfRepo.findByKeyAndBrandAppId(key, brandAppId);
        return cashierDiscountSys == null ? defaultValue : cashierDiscountSys.getDefaultValue();
    }


}
