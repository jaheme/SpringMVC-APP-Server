package com.tony.core.http;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.tony.core.constant.EnvConf;

public class JettyServer {
	

    private Logger logger = LoggerFactory.getLogger(getClass());
    private int port = -1;
    private String contextPath = null;
    private ServletHandler servletHandler = null;
    private List<String> webappPaths = Lists.newArrayList();

    public JettyServer(){
        webappPaths.add("lib/webapp");
        webappPaths.add("src/main/webapp");
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * 用在修正idea下的当前目录
     */
    public void addWebappPath(String webappPath) {
        webappPaths.add(webappPath);
    }

    public void setServletHandler(ServletHandler servletHandler) {
        this.servletHandler = servletHandler;
    }

    public void start() {
        Validate.isTrue(port>0);
        
        String address = "0.0.0.0";
        InetSocketAddress inetAddress = new InetSocketAddress(address, port);
        Server server = new Server(inetAddress);
//        QueuedThreadPool threadPool = new QueuedThreadPool(1024, 32);
//        Server server = new Server(threadPool);
//        ServerConnector connector = new ServerConnector(server);
//        server.addConnector(connector);
//
//        connector.setPort(port);
        if(contextPath != null) {
            server.setHandler(new WebAppContext(getWebappPath(), contextPath));
        } else if(servletHandler != null) {
            server.setHandler(servletHandler);
        } else {
            Validate.isTrue(false);
        }
        logger.info("used in production environment : {}", EnvConf.isProd());
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            logger.error("jetty start error", e);
            System.exit(-1);
        }
    }

    private String getWebappPath() {
        for(String webappPath: webappPaths) {
            if(new File(webappPath, "WEB-INF/web.xml").exists()) {
                logger.warn("find {}", webappPath);
                return webappPath;
            }
        }
        throw new IllegalStateException("not find any webappPath");
    }

}
