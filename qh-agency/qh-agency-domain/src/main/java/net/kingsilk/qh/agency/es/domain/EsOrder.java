package net.kingsilk.qh.agency.es.domain;


import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by lit on 17/8/29.
 */
@Document(indexName = "qh-agency", type = "esOrder", refreshInterval = "-1")
public class EsOrder extends Base {

}
