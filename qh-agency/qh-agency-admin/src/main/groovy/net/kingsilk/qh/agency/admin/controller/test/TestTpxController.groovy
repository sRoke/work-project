package net.kingsilk.qh.agency.admin.controller.test

import io.swagger.annotations.*
import net.kingsilk.qh.agency.domain.YunFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

import static org.springframework.data.mongodb.core.query.Criteria.where
import static org.springframework.data.mongodb.core.query.Query.query

/**
 * Created by tpx on 17-3-14.
 */
@RestController()
@RequestMapping("/testTpx")
@Api( // 用在类上，用于设置默认值
        tags = "testTpx",
        consumes = MediaType.APPLICATION_ATOM_XML_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http",
        description = "汀舟的测试demo"
)
class TestTpxController {
    @Autowired
    MongoTemplate mongoTemplate

    @RequestMapping(path = "/addYunfile",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "插入yunfile",
            nickname = "测试插入yunfile",
            notes = "测试插入yunfile"
    )
    @ResponseBody
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    Object addYunfile(@ApiParam(value = "备注", required = false) @ModelAttribute("memo") String memo,
                      @ApiParam(value = "空间", required = true) @ModelAttribute("bucket") String bucket,
                      @ApiParam(value = "加密的值", required = true) @ModelAttribute("key") String key) {
        YunFile file = new YunFile(memo: memo, bucket: bucket, key: key)
        mongoTemplate.save(file);
        return [
                msg: "SUCCESS"
        ]
    }


    //@ApiIgnore 不显示该api
    @RequestMapping(path = "/getYunfile",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "getYunfile",
            nickname = "getYunfile",
            notes = "getYunfile"
    )
    @ResponseBody
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    List<YunFile> getYunfile(@ApiParam(value = "加密的值", required = false) @ModelAttribute("key") String key) {

        Criteria criteria = new Criteria().andOperator(
                    where('key').is(key)
        )

        Query query = query(criteria)
                .skip(1)  // 分页
                .limit(2)
                .with(new Sort(Sort.Direction.DESC, "key")) // 排序

        // 应该只有 age = 15, 16 的记录
        List<YunFile> yunFileList = mongoTemplate.find(query, YunFile)
        return yunFileList
    }

    @Autowired
    private Environment env;

    @RequestMapping(path = "/test",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    def test(){
        return  env.getProperty("spring.data.mongodb.port")
    }

}
