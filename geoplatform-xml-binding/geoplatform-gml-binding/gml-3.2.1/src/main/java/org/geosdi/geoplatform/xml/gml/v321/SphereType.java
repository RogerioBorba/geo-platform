//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.18 at 11:58:17 AM CEST 
//


package org.geosdi.geoplatform.xml.gml.v321;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * <p>Java class for SphereType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SphereType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}AbstractGriddedSurfaceType">
 *       &lt;attribute name="horizontalCurveType" type="{http://www.opengis.net/gml/3.2}CurveInterpolationType" fixed="circularArc3Points" />
 *       &lt;attribute name="verticalCurveType" type="{http://www.opengis.net/gml/3.2}CurveInterpolationType" fixed="circularArc3Points" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SphereType")
public class SphereType
    extends AbstractGriddedSurfaceType
    implements ToString
{

    /**
     * 
     * 
     */
    @XmlAttribute(name = "horizontalCurveType")
    public final static CurveInterpolationType HORIZONTAL_CURVE_TYPE = CurveInterpolationType.CIRCULAR_ARC_3_POINTS;
    /**
     * 
     * 
     */
    @XmlAttribute(name = "verticalCurveType")
    public final static CurveInterpolationType VERTICAL_CURVE_TYPE = CurveInterpolationType.CIRCULAR_ARC_3_POINTS;

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        super.appendFields(locator, buffer, strategy);
        {
            CurveInterpolationType theHORIZONTAL_CURVE_TYPE;
            theHORIZONTAL_CURVE_TYPE = SphereType.HORIZONTAL_CURVE_TYPE;
            strategy.appendField(locator, this, "horizontalCURVETYPE", buffer, theHORIZONTAL_CURVE_TYPE);
        }
        {
            CurveInterpolationType theVERTICAL_CURVE_TYPE;
            theVERTICAL_CURVE_TYPE = SphereType.VERTICAL_CURVE_TYPE;
            strategy.appendField(locator, this, "verticalCURVETYPE", buffer, theVERTICAL_CURVE_TYPE);
        }
        return buffer;
    }

}
