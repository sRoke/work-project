package net.kingsilk.qh.vote.server.resource.voteApp.vote.test;


import net.kingsilk.qh.vote.api.UniPage;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteActivity.dto.VoteActivityPageResp;
import net.kingsilk.qh.vote.api.voteApp.vote.voteActivity.dto.VoteActivityReq;
import net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto.VoteWorksResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/voteApp/{voteAppId}/test")
@Component
public class TestResource {

    @Context
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RestOperations restTemplate;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<UniPage<VoteActivityPageResp>> getPage() {
        String at = httpServletRequest.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();


        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("Authorization", at);
        //todo 修改
        headers.set("voteApp-Id", "5a4338081c087d330d311c1d");
        RequestEntity requestEntity = new RequestEntity(null, headers, null, null);
        String url = "https://kingsilk.net/vote/rs/local/16000/api/voteApp/{voteAppId}/vote/admin";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("voteAppId", "5a4338081c087d330d311c1d");
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        mup.set("page", "0");
        mup.set("size", "10");


        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();
        ResponseEntity<UniResp<UniPage<VoteActivityPageResp>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<UniPage<VoteActivityPageResp>>>() {
                });
        return responseEntity.getBody();
    }


    @GET
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<String> add() {
        String at = httpServletRequest.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();


        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("Authorization", at);

        //todo 修改
        headers.set("voteApp-Id", "5a4338081c087d330d311c1d");
        VoteActivityReq voteActivityReq = new VoteActivityReq();
        voteActivityReq.setVoteName("测试");
        voteActivityReq.setVoteAppId("5a4338081c087d330d311c1d");
        voteActivityReq.setDesp("test新增");
        voteActivityReq.setForceFollow(true);
        voteActivityReq.setForcePhone(true);
        voteActivityReq.setMaxTicketPerDay(1);
        voteActivityReq.setMaxVotePerDay(1);
        voteActivityReq.setMustCheck(true);
        voteActivityReq.setRule("testAdd");
        voteActivityReq.setPrimaryImgUrl("123");
        voteActivityReq.setShareContent("分享");
        voteActivityReq.setShareTitle("测试");
        voteActivityReq.setWordsOfThanks("感谢语～～～");
//        voteActivityReq.setVoteStatusEnum(VoteStatusEnum.NORMAL);
//        voteActivityReq.setVoteStartTime("2017-1-1 12:00:00");
//        voteActivityReq.setVoteEndTime("2018-1-1 12:00:00");
//        voteActivityReq.setVotePeoplePerDay(1);
//        voteActivityReq.setSignUpStartTime("2017-1-1 12:00:00");
//        voteActivityReq.setSignUpEndTime("2018-1-1 12:00:00");
        voteActivityReq.setTotalVoteCount(5);
        RequestEntity requestEntity = new RequestEntity(voteActivityReq, headers, null, null);

        String url = "https://kingsilk.net/vote/rs/local/16000/api/voteApp/{voteAppId}/vote/admin";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("voteAppId", "5a4338081c087d330d311c1d");


        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();
        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });
        return responseEntity.getBody();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<VoteActivityPageResp> info() {
        String at = httpServletRequest.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();


        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("Authorization", at);
        //todo 修改
        headers.set("voteApp-Id", "5a4338081c087d330d311c1d");
        RequestEntity requestEntity = new RequestEntity(null, headers, null, null);
        String url = "https://kingsilk.net/vote/rs/local/16000/api/voteApp/{voteAppId}/vote/admin/{id}";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("voteAppId", "5a4338081c087d330d311c1d");
        map.put("id", "5a434b781c087d417881a1ec");


        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();
        ResponseEntity<UniResp<VoteActivityPageResp>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<VoteActivityPageResp>>() {
                });
        return responseEntity.getBody();
    }


    @GET
    @Path("/changeStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<String> changeStatus() {
        String at = httpServletRequest.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();


        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("Authorization", at);
        //todo 修改
        headers.set("voteApp-Id", "5a4338081c087d330d311c1d");
        RequestEntity requestEntity = new RequestEntity(null, headers, null, null);
        String url = "https://kingsilk.net/vote/rs/local/16000/api/voteApp/{voteAppId}/vote/admin/{id}/changeStatus";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("voteAppId", "5a4338081c087d330d311c1d");
        map.put("id", "5a434b781c087d417881a1ec");


        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("enable", false)
                .buildAndExpand(map)
                .toUri();
        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });
        return responseEntity.getBody();
    }

    @GET
    @Path("/workPage")
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<UniPage<VoteWorksResp>> workPage() {
        String at = httpServletRequest.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();


        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("Authorization", at);
        //todo 修改
        headers.set("voteApp-Id", "5a4338081c087d330d311c1d");
        RequestEntity requestEntity = new RequestEntity(null, headers, null, null);
        String url = "https://kingsilk.net/vote/rs/local/16000/api/voteApp/{voteAppId}/vote/{voteActivityId}/vote/wap/voteWorks";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("voteAppId", "5a4338081c087d330d311c1d");
        map.put("voteActivityId", "5a434b781c087d417881a1ec");
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        mup.set("page", "0");
        mup.set("size", "10");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();
        ResponseEntity<UniResp<UniPage<VoteWorksResp>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<UniPage<VoteWorksResp>>>() {
                });
        return responseEntity.getBody();
    }

    @GET
    @Path("/voteRecordPage")
    @Produces(MediaType.APPLICATION_JSON)
    public UniResp<UniPage<VoteWorksResp>> voteRecordPage() {
        String at = httpServletRequest.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();


        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("Authorization", at);
        //todo 修改
        headers.set("voteApp-Id", "5a4338081c087d330d311c1d");
        RequestEntity requestEntity = new RequestEntity(null, headers, null, null);
        String url = "https://kingsilk.net/vote/rs/local/16000/api/voteApp/{voteAppId}/vote/{voteActivityId}/voteWork/{voteWorkId}/voteRecord";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("voteAppId", "5a4338081c087d330d311c1d");
        map.put("voteActivityId", "5a434b781c087d417881a1ec");
        map.put("voteWorkId","xxxxx");
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        mup.set("page", "0");
        mup.set("size", "10");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();
        ResponseEntity<UniResp<UniPage<VoteWorksResp>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<UniPage<VoteWorksResp>>>() {
                });
        return responseEntity.getBody();
    }

}
