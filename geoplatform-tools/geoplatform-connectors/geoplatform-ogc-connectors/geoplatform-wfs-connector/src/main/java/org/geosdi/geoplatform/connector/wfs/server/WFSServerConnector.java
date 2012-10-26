/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2012 geoSDI Group (CNR IMAA - Potenza - ITALY).
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
package org.geosdi.geoplatform.connector.wfs.server;

import java.net.URL;
import org.geosdi.geoplatform.connector.server.GPAbstractServerConnector;
import org.geosdi.geoplatform.connector.server.security.GPSecurityConnector;
import org.geosdi.geoplatform.connector.wfs.server.request.WFSGetCapabilitiesRequest;
import org.geosdi.geoplatform.connector.wfs.WFSVersion;
import org.geosdi.geoplatform.connector.wfs.WFSVersionException;
import org.geosdi.geoplatform.connector.wfs.server.request.WFSDescribeFeatureTypeRequest;
import org.geosdi.geoplatform.connector.wfs.server.request.WFSGetFeatureRequest;

/**
 *
 * @author Vincenzo Monteverde <vincenzo.monteverde@geosdi.org>
 */
public class WFSServerConnector extends GPAbstractServerConnector {

    private WFSVersion version;

    /**
     * <p> Create an Instance of {@link WFSServerConnector} with the server URL
     * and the specific version.
     * <p/>
     *
     * @param urlServer the String that represent WFS server URL
     * @param version the value of WFS version. Must be 1.1.0
     */
    public WFSServerConnector(String urlServer, String version) {
        this(urlServer, null, version);
    }

    /**
     * <p> Create an instance of {@link WFSServerConnector} with the server URL,
     * {@link GPSecurityConnector} for security and version.
     * <p/>
     *
     * @param urlServer the String that represent WFS server URL
     * @param securityConnector {@link GPSecurityConnector}
     * @param version the value of WFS version. Must be 1.1.0
     */
    public WFSServerConnector(String urlServer,
            GPSecurityConnector securityConnector,
            String version) {
        this(analyzesServerURL(urlServer), securityConnector, toWFSVersion(
                version));
    }

    /**
     * <p> Create an instance of {@link WFSServerConnector} with the {@link URL}
     * server UR:, {@link GPSecurityConnector} security context and
     * {@link WFSVersion} WFS version.
     * <p/>
     *
     * @param server {@link URL} server URL
     * @param securityConnector {@link GPSecurityConnector}
     * @param theVersion {@link WFSVersion} WFS version. Must be 1.1.0
     */
    public WFSServerConnector(URL server,
            GPSecurityConnector securityConnector,
            WFSVersion theVersion) {
        super(server, securityConnector);
        this.version = theVersion;
    }

    /**
     * <p> Create WFSGetCapabilitiesRequest request.
     * <p/>
     *
     * @return {@link WFSGetCapabilitiesRequest}
     */
    public WFSGetCapabilitiesRequest createGetCapabilitiesRequest() {
        switch (version) {
            case V110:
                return new WFSGetCapabilitiesRequest(this);
            default:
                throw new WFSVersionException(
                        "The version for WFS must be 1.1.0");
        }
    }

    /**
     * <p> Create WFSDescribeFeatureTypeRequest request.
     * <p/>
     *
     * @return {@link WFSDescribeFeatureTypeRequest}
     */
    public WFSDescribeFeatureTypeRequest createDescribeFeatureTypeRequest() {
        switch (version) {
            case V110:
                return new WFSDescribeFeatureTypeRequest(this);
            default:
                throw new WFSVersionException(
                        "The version for WFS must be 1.1.0");
        }
    }

    /**
     * <p> Create WFSGetFeatureRequest request.
     * <p/>
     *
     * @return {@link WFSGetFeatureRequest}
     */
    public WFSGetFeatureRequest createGetFeatureRequest() {
        switch (version) {
            case V110:
                return new WFSGetFeatureRequest(this);
            default:
                throw new WFSVersionException(
                        "The version for WFS must be 1.1.0");
        }
    }

    /**
     * <p> Method that convert String version in {@link WFSVersion} instance
     * </p>
     *
     * @param version
     *
     * @return {@link WFSVersion}
     */
    private static WFSVersion toWFSVersion(String version) {
        if (!version.equalsIgnoreCase("1.1.0")) {
            throw new WFSVersionException("WFS version must be 1.1.0");
        }
        return WFSVersion.V110;
    }
}
