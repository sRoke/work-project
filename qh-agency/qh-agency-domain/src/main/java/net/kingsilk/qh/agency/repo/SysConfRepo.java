package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.SysConf;

import java.util.List;

public interface SysConfRepo extends BaseRepo<SysConf, String>{
    SysConf findByKeyAndBrandAppId(String key,String brandAppId);
    List<SysConf> findAllByBrandAppId(String brandAppId);
}
