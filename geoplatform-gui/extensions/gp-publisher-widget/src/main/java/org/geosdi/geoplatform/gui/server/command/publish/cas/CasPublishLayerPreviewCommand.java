/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2013 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. You should have received a copy of the GNU General 
 * Public License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General Public License cover the whole combination. 
 * 
 * As a special exception, the copyright holders of this library give you permission 
 * to link this library with independent modules to produce an executable, regardless 
 * of the license terms of these independent modules, and to copy and distribute 
 * the resulting executable under terms of your choice, provided that you also meet, 
 * for each linked independent module, the terms and conditions of the license of 
 * that module. An independent module is a module which is not derived from or 
 * based on this library. If you modify this library, you may extend this exception 
 * to your version of the library, but you are not obligated to do so. If you do not 
 * wish to do so, delete this exception statement from your version. 
 *
 */
package org.geosdi.geoplatform.gui.server.command.publish.cas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.gui.client.command.publish.basic.PublishLayerPreviewResponse;
import org.geosdi.geoplatform.gui.client.command.publish.cas.CasPublishLayerPreviewRequest;
import org.geosdi.geoplatform.gui.command.server.GPCommand;
import org.geosdi.geoplatform.gui.global.GeoPlatformException;
import org.geosdi.geoplatform.gui.server.SessionUtility;
import org.geosdi.geoplatform.gui.utility.GPReloadURLException;
import org.geosdi.geoplatform.gui.utility.GPSessionTimeout;
import org.geosdi.geoplatform.services.GPPublisherBasicServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Lazy(true)
@Component(value = "command.publish.cas.CasPublishLayerPreviewCommand")
public class CasPublishLayerPreviewCommand implements
        GPCommand<CasPublishLayerPreviewRequest, PublishLayerPreviewResponse>, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(
            CasPublishLayerPreviewCommand.class);
    private GPPublisherBasicServiceImpl casPublisherService;
    private HttpGet httpget;
    private DefaultHttpClient httpclient;
    private HttpContext localContext;
    private HttpHost targetHost;
    @Autowired
    private SessionUtility sessionUtility;
    //
    private @Value("configurator{cluster_reload_url}")
    String urlClusterReload;
    private @Value("configurator{cluster_reload_host_url}")
    String hostUrlClusterReload;
    private @Value("configurator{cluser_reload_username}")
    String userNameClusterReload;
    private @Value("configurator{cluser_reload_password}")
    String passwordClusterReload;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.httpget = new HttpGet(urlClusterReload);
        this.targetHost = new HttpHost(hostUrlClusterReload);
    }

    @Override
    public PublishLayerPreviewResponse execute(
            CasPublishLayerPreviewRequest request,
            HttpServletRequest httpServletRequest) {
        try {
            sessionUtility.getLoggedAccount(httpServletRequest);
        } catch (GPSessionTimeout timeout) {
            throw new GeoPlatformException(timeout);
        }
        String result = null;
        try {
            List<String> layerList = request.getLayerList();
            casPublisherService.publishAll(
                    httpServletRequest.getSession().getId(), "previews",
                    "dataTest", layerList);
            boolean reloadCluster = request.isReloadCluster();
            if (reloadCluster) {
                HttpResponse response = httpclient.execute(targetHost, httpget,
                        localContext);
//                HttpResponse response = httpclient.execute(get, localContext);
                InputStream is = response.getEntity().getContent();
                Writer writer = new StringWriter();
                char[] buffer = new char[1024];
                try {
                    Reader reader = new BufferedReader(
                            new InputStreamReader(is, "UTF-8"));
                    int n;
                    while ((n = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }
                } finally {
                    is.close();
                }
                result = writer.toString();
            }
        } catch (ResourceNotFoundFault ex) {
            logger.error("Error on publish shape: " + ex);
            throw new GeoPlatformException("Error on publish shape.");
        } catch (FileNotFoundException ex) {
            logger.error("Error on publish shape: " + ex);
            throw new GeoPlatformException("Error on publish shape.");
        } catch (MalformedURLException e) {
            logger.error("Error on cluster url: " + e);
            throw new GeoPlatformException(new GPReloadURLException(
                    "Error on cluster url."));
        } catch (IOException e) {
            logger.error("Error on reloading cluster: " + e);
            throw new GeoPlatformException(new GPReloadURLException(
                    "Error on reloading cluster."));
        }
        return new PublishLayerPreviewResponse(result);

    }

    /**
     * @param geoPlatformServiceClient the geoPlatformServiceClient to set
     */
    @Resource
    public void setCasPublisherService(
            @Qualifier("casPublisherService") GPPublisherBasicServiceImpl casPublisherService) {
//        System.out.println("*************** Setting publisher service: " + );
        this.casPublisherService = casPublisherService;
    }
}
