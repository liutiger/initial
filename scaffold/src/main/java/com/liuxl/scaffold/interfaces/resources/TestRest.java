package com.liuxl.scaffold.interfaces.resources;

import com.sun.jersey.spi.resource.Singleton;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created with IntelliJ IDEA.
 * Description:demo
 *
 * @author liuxl
 * @date 2018/8/29
 */
@Controller
@Singleton
@Path("/demo")
public class TestRest {

    @GET
    @Path("/demo")
    @Produces("application/json;charset=utf-8")
    public String demo() {
        return "demo";
    }
}
