package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.domain.QYunFile
import net.kingsilk.qh.agency.domain.YunFile
import net.kingsilk.qh.agency.repo.YunFileRepo
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

import static org.springframework.data.mongodb.core.query.Query.query

@Deprecated
class UploadService {

    @Autowired
    private QiNiuSdk qiNiuSdk

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    YunFileRepo yunFileRepo

    private String accessKey;
    private String secretKey;
    private String defaultBucket;



    public UploadService() {

    }

    public UploadService(String accessKey, String secretKey, String defaultBucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.defaultBucket = defaultBucket;
    }

    /**
     * 上传到默认空间
     * @param bytes
     * @return YunFile
     */
    YunFile qiNiuUploadCustom(byte[] bytes) {
        // 使用文件的md5sum作为七牛云上的文件名，防止重复上传
        def md5FileName = DigestUtils.md5Hex(bytes)
        // 先检查七牛云上是否已经存在该文件
        YunFile yunFile=yunFileRepo.findOne(
                Expressions.allOf(
                        QYunFile.yunFile.key.eq(md5FileName),
                        QYunFile.yunFile.deleted.in([null, false])
                )
        )

        // 文件已经存在
        if (yunFile) {
            return yunFile
        }
        // 新上传
        def resp = qiNiuSdk.put(bytes, md5FileName)
//        [status:SUCCESS, hash:FoDwk6V22oid8Hr9xD7ytuQSZgQV, key:FoDwk6V22oid8Hr9xD7ytuQSZgQV]
        if (resp.status) {
            YunFile yf = new YunFile();
            yf.key = md5FileName;
            yf.bucket = defaultBucket;
            yunFileRepo.save(yf);
            return yf
        } else {
            return null
        }
    }
    /**
     * 使用七牛云，第三方抓取数据
     * @return
     */
    Map fetch(String url, String name) {
        def map = qiNiuSdk.fetch(url, name);
        return map;
    }
}
