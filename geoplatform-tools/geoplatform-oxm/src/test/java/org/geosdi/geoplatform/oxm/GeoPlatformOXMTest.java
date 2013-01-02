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
package org.geosdi.geoplatform.oxm;

import java.io.IOException;
import org.geosdi.geoplatform.mock.ClassToXMLMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * @author giuseppe
 * 
 */
@SuppressWarnings("deprecation")
public class GeoPlatformOXMTest extends AbstractDependencyInjectionSpringContextTests {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier(value = "castor")
    private GeoPlatformMarshall castor;
    //
    @Autowired
    @Qualifier(value = "jax")
    private GeoPlatformMarshall jax;
    //
    @Autowired
    @Qualifier(value = "xtream")
    private GeoPlatformMarshall xtream;
    //
    @Autowired
    @Qualifier(value = "jibx")
    private GeoPlatformMarshall jibx;
    //
    private ClassToXMLMap message;

    @Override
    protected void onSetUp() throws Exception {
        message = new ClassToXMLMap();
        message.setData("I am data");
        message.setHistory("in the past");
        super.onSetUp();
    }

    @Test
    public void testCastor() throws Exception {
        String castorFile = "target/castor.xml";
        castor.saveXML(message, castorFile);

        ClassToXMLMap castorMap = (ClassToXMLMap) castor.loadFromXML(castorFile);
        assertNotNull(castorMap);

        logger.info("CASTOR BEAN  ******************** {}", castorMap);

    }

    @Test
    public void testJaxB() throws Exception {
        String jaxbFile = "target/jaxb.xml";
        jax.saveXML(message, jaxbFile);

        ClassToXMLMap jaxbMap = (ClassToXMLMap) jax.loadFromXML(jaxbFile);
        assertNotNull(jaxbMap);

        logger.info("JAX BEAN ***************** {}", jaxbMap);
    }

    @Test
    public void testXStream() throws Exception {
        String xtreamFile = "target/xtream.xml";
        xtream.saveXML(message, xtreamFile);

        ClassToXMLMap xstreamMap = (ClassToXMLMap) xtream.loadFromXML(xtreamFile);
        assertNotNull(xtream.loadFromXML(xtreamFile));

        logger.info("XSTREAM BEAN *************** {}", xstreamMap);
    }
    
    @Test
    public void testJibx() throws IOException {
        String jibxFile = "target/jibx.xml";
        jibx.saveXML(message, jibxFile);

        ClassToXMLMap jibxMap = (ClassToXMLMap) jibx.loadFromXML(jibxFile);
        assertNotNull(jibx.loadFromXML(jibxFile));

        logger.info("JIXB BEAN *************** {}", jibxMap);
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:applicationContext.xml"};
    }
}
