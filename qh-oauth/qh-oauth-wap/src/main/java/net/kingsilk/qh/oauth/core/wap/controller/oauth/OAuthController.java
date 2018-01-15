package net.kingsilk.qh.oauth.core.wap.controller.oauth;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

/**
 *
 */
@Api(
        tags = "oauth",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "商品分类相关API"
)
@RestController
@RequestMapping("/oauth")
@Deprecated
public class OAuthController {


    @ApiOperation(value = "获取校验 JWT 签名的公钥", nickname = "jwtPubKey", notes = "保存或更新商品分类")
    @RequestMapping(path = "/jwtPubKey", method = RequestMethod.GET)
    public UniResp<OAuthJwtPubKeyResp> jwtPubKey() {
        UniResp resp = new UniResp();
        OAuthJwtPubKeyResp data = new OAuthJwtPubKeyResp();
        PublicKey pubKey = jwtKeyPair.getPublic();

        data.setPubKey("-----BEGIN PUBLIC KEY-----\n" + Base64.getEncoder().encodeToString(pubKey.getEncoded()) + "\n-----END PUBLIC KEY-----");
        resp.setData(data);

        return resp;

    }


    //    @Autowired
    private KeyPair jwtKeyPair;
}
