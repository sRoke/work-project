package net.kingsilk.qh.raffle.service;

import net.kingsilk.qh.oauth.api.user.addr.AddrApi;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.addr.dto.AddrModel;
import net.kingsilk.qh.raffle.domain.Adc;
import net.kingsilk.qh.raffle.repo.AdcRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AddrService {

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private AddrApi addrApi;

    @Autowired
    private RestTemplate restTemplate;

    public static List<Map<String, Object>> getAddr;

    public void addrModelAdc(AddrModel addrModel, Adc adc) {
        Optional.ofNullable(adc).ifPresent(addrAdc ->
                {
                    addrModel.setCountyNo(addrAdc.getNo());
                    addrModel.setArea(addrAdc.getName());
                    Optional.ofNullable(adc.getParent()).ifPresent(adcParent ->
                            {
                                addrModel.setCityNo(adcParent.getNo());
                                addrModel.setCity(adcParent.getName());
                                Optional.ofNullable(adcParent.getParent()).ifPresent(adcParents ->
                                        {
                                            addrModel.setProvince(adcParents.getName());
                                            addrModel.setProvinceNo(adcParents.getNo());
                                        }
                                );
                            }
                    );
                }
        );
    }

    public List<Map<String, Object>> getAddrList() {
        List<Map<String, Object>> arrayList = new ArrayList<>();
        List<Adc> adc = adcRepo.findAll();
        List<Adc> provincials = new ArrayList<>();
        adc.forEach(
                adc1 -> {
                    if (StringUtils.isEmpty(adc1.getParent())) {
                        provincials.add(adc1);
                    }
                }
        );

        provincials.forEach(
                provincial -> {
                    Map<String, Object> provincialMap = new HashMap<>();
                    List<Map<String, Object>> cityMapList = new ArrayList<>();
                    List<Adc> citys = new ArrayList<>();
                    adc.forEach(
                            adc1 -> {
                                if (!StringUtils.isEmpty(adc1.getParent())) {
                                    if (provincial.getId().equals(adc1.getParent().getId())) {
                                        citys.add(adc1);
                                    }
                                }
                            }
                    );
                    if (citys.isEmpty()) {
                        Map<String, Object> cityMap = new HashMap<>();
                        Map<String, String> countyMap = new HashMap<>();
                        List<Map<String, String>> countyMapList = new ArrayList<>();
                        countyMap.put("label", provincial.getName());
                        countyMap.put("value", provincial.getNo());
                        countyMapList.add(countyMap);
                        cityMap.put("label", provincial.getName());
                        cityMap.put("value", provincial.getNo());
                        cityMap.put("children", countyMapList);
                        cityMapList.add(cityMap);
                    } else {
                        citys.forEach(
                                city -> {
                                    Map<String, Object> cityMap = new HashMap<>();
                                    List<Map<String, String>> countyMapList = new ArrayList<>();
                                    List<Adc> countys = new ArrayList<>();
                                    adc.forEach(
                                            adc2 -> {
                                                if (!StringUtils.isEmpty(adc2.getParent())) {
                                                    if (city.getId().equals(adc2.getParent().getId())) {
                                                        countys.add(adc2);
                                                    }
                                                }
                                            }
                                    );
                                    if (countys.isEmpty()) {
                                        Map<String, String> countyMap = new HashMap<>();
                                        countyMap.put("label", city.getName());
                                        countyMap.put("value", city.getNo());
                                        countyMapList.add(countyMap);
                                    } else {
                                        countys.forEach(
                                                adc1 -> {
                                                    Map<String, String> countyMap = new HashMap<>();
                                                    countyMap.put("label", adc1.getName());
                                                    countyMap.put("value", adc1.getNo());
                                                    countyMapList.add(countyMap);
                                                });
                                    }
                                    cityMap.put("children", countyMapList);
                                    cityMap.put("label", city.getName());
                                    cityMap.put("value", city.getNo());
                                    cityMapList.add(cityMap);
                                });
                    }
                    provincialMap.put("label", provincial.getName());
                    provincialMap.put("value", provincial.getNo());
                    provincialMap.put("children", cityMapList);
                    arrayList.add(provincialMap);
                });

        return arrayList;
    }

    public String getAdcInfo(String no) {
        Adc adc = adcRepo.findOneByNo(no);
        if (adc != null) {
            String area = adc.getName();
            Adc parent = adc.getParent();
            String city = parent == null ? null : parent.getName();
            Adc parent2 = adc.getParent();
            Adc parent1 = parent2 == null ? null : parent2.getParent();
            String province = parent1 == null ? null : parent1.getName();
            String resp;
            if (!StringUtils.isEmpty(city)) {
                if (!StringUtils.isEmpty(province)) {
                    resp = province + " " + city + " " + area;
                } else {
                    resp = city + " " + area;
                }
            } else {
                resp = area;
            }
            return resp;
        } else {
            return "";
        }
    }

}
