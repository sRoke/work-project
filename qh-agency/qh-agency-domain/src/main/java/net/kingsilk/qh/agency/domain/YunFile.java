package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.ImgWayEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 使用七牛云存储的文件（大部分为图片）。
 * <p>
 * 如果是图片，则是上传的原图，具体使用时，请使用相应的规范化URL。
 *
 * @deprecated 该 domain 类挪到其他子系统中管理。
 */
@Deprecated
@Document
public class YunFile extends Base {

    /**
     * 空间
     */
    private String bucket;
    /**
     * key(或者称之为 路径)。值为文件的md5值 或者ETAG的值
     */
    private String key;
    /**
     * 是否是图片文件
     */
    private Boolean notImg = false;
    /**
     * 上传时提供的文件原名
     */
    private String orginFileName;
    /**
     * MD5还是ETAG
     **/
    private ImgWayEnum way = ImgWayEnum.MD5;
    /**
     * 备注
     */
    private String memo;

    // --------------------------------------- getter && setter

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getNotImg() {
        return notImg;
    }

    public void setNotImg(Boolean notImg) {
        this.notImg = notImg;
    }

    public String getOrginFileName() {
        return orginFileName;
    }

    public void setOrginFileName(String orginFileName) {
        this.orginFileName = orginFileName;
    }

    public ImgWayEnum getWay() {
        return way;
    }

    public void setWay(ImgWayEnum way) {
        this.way = way;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
