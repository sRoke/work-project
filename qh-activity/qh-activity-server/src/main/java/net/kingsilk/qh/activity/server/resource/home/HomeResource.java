package net.kingsilk.qh.activity.server.resource.home;

import io.swagger.annotations.Api;
import net.kingsilk.qh.activity.api.home.HomeApi;

import javax.inject.Singleton;
import javax.ws.rs.Path;

@Path("/home")
@Api
@Singleton
public class HomeResource implements HomeApi {


}
