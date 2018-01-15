package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Adc;

import java.util.List;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public interface AdcRepo extends BaseRepo<Adc, String> {
    public abstract List<Adc> findAllByParent(Adc adc);

    public abstract Adc findOneByNo(String no);
}
