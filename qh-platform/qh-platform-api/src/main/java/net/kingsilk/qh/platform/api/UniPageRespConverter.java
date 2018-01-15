package net.kingsilk.qh.platform.api;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UniPageRespConverter implements Converter<Page, UniPageResp> {

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ObjectProvider<ConversionService> csProvider;

    @Override
    public UniPageResp convert(Page source) {
        UniPageResp target = new UniPageResp();
        target.setTotalPages(source.getTotalPages());
        target.setTotalElements(source.getTotalElements());
        target.setNumber(source.getNumber());
        target.setSize(source.getSize());
//        target.setContent(source.getContent());

        ConversionService cs = csProvider.getObject();

        if (source.getSort() != null) {

            Spliterator<Sort.Order> sp = Spliterators.spliteratorUnknownSize(source.getSort().iterator(), Spliterator.ORDERED);
            List<UniOrder> orders = StreamSupport.stream(sp, false)
                    .map(o -> cs.convert(o, UniOrder.class))
                    .collect(Collectors.toList());
            if (!orders.isEmpty()) {
                target.setOrders(orders);
            }
        }
        return target;
    }

}
