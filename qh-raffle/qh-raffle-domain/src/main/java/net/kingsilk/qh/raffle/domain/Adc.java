package net.kingsilk.qh.raffle.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 行政区划代码
 */
@Document
public class Adc {

    /**
     * 编码
     */
    @Id
    private String id;

    @Indexed(unique = true)
    private String no;

    /**
     * 父级行政区
     */
    @DBRef
    private Adc parent;


    /**
     * 名字
     */
    private String name;

    /////////setter、getter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Adc getParent() {
        return parent;
    }

    public void setParent(Adc parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
