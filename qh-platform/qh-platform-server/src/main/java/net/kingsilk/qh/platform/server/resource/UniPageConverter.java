package net.kingsilk.qh.platform.server.resource;

import net.kingsilk.qh.platform.api.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.convert.*;
import org.springframework.core.convert.converter.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Component
public class UniPageConverter implements Converter<Page, UniPage> {

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ObjectProvider<ConversionService> csProvider;

    @Override
    public UniPage convert(Page source) {
        UniPage target = new UniPage();
        target.setTotalPages(source.getTotalPages());
        target.setTotalElements(source.getTotalElements());
        target.setNumber(source.getNumber());
        target.setSize(source.getSize());
        target.setContent(source.getContent());

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
