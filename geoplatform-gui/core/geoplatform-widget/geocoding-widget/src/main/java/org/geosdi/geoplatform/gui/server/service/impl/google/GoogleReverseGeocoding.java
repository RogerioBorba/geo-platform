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
package org.geosdi.geoplatform.gui.server.service.impl.google;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


import org.geosdi.geoplatform.gui.client.model.GeocodingBean;
import org.geosdi.geoplatform.gui.client.model.GeocodingKeyValue;
import org.geosdi.geoplatform.gui.client.model.google.GoogleGeocodeBean;
import org.geosdi.geoplatform.gui.oxm.model.google.GPGoogleGeocodeRoot;
import org.geosdi.geoplatform.gui.oxm.model.google.GPGoogleResult;
import org.geosdi.geoplatform.gui.oxm.model.google.enums.ResponseStatus;
import org.geosdi.geoplatform.gui.server.service.IReverseGeocoding;
import org.geosdi.geoplatform.oxm.GeoPlatformMarshall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author giuseppe
 * 
 */
@Service("googleReverseGeocoding")
public class GoogleReverseGeocoding implements IReverseGeocoding {

    // URL prefix to the reverse geocoder
    private static final String REVERSE_GEOCODER_PREFIX_FOR_XML = "http://maps.googleapis.com/maps/api/geocode/xml";
    //
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    @Autowired
    private GeoPlatformMarshall geocoderGoogleJaxbMarshaller;

    /**
     * (non-Javadoc)
     *
     * @see org.geosdi.geoplatform.gui.server.service.IReverseGeocoding#findLocation(double, double)
     */
    @Override
    public GeocodingBean findLocation(double lat, double lon)
            throws IOException {

        URL url = new URL(REVERSE_GEOCODER_PREFIX_FOR_XML + "?latlng="
                + URLEncoder.encode(lat + "," + lon, "UTF-8") + "&language=it&sensor=true");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        GPGoogleGeocodeRoot oxmBean = (GPGoogleGeocodeRoot) this.geocoderGoogleJaxbMarshaller.loadFromStream(conn.getInputStream());

        if (oxmBean.getStatus().equals(ResponseStatus.EnumResponseStatus.STATUS_OK.getValue())) {
            GPGoogleResult result = oxmBean.getResultList().get(0);
            return new GoogleGeocodeBean(result);
        }

        /**@@@@@@@@@@@@@@ TODO FIXE ME @@@@@@@@@@@@@@@@@@@@ **/
        return new GoogleGeocodeBean(GeocodingKeyValue.ZERO_RESULTS.toString());
    }
}