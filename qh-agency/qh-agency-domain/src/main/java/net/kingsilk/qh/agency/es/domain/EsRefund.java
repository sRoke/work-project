package net.kingsilk.qh.agency.es.domain;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "qh-agency", type = "esRefund", refreshInterval = "-1")
public class EsRefund extends Base {
}
