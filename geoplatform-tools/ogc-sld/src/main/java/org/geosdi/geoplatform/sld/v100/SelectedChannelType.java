//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.17 at 12:51:09 PM CET 
//


package org.geosdi.geoplatform.sld.v100;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SelectedChannelType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SelectedChannelType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/sld}SourceChannelName"/>
 *         &lt;element ref="{http://www.opengis.net/sld}ContrastEnhancement" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectedChannelType", propOrder = {
    "sourceChannelName",
    "contrastEnhancement"
})
public class SelectedChannelType {

    @XmlElement(name = "SourceChannelName", required = true)
    protected String sourceChannelName;
    @XmlElement(name = "ContrastEnhancement")
    protected ContrastEnhancement contrastEnhancement;

    /**
     * Gets the value of the sourceChannelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceChannelName() {
        return sourceChannelName;
    }

    /**
     * Sets the value of the sourceChannelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceChannelName(String value) {
        this.sourceChannelName = value;
    }

    /**
     * Gets the value of the contrastEnhancement property.
     * 
     * @return
     *     possible object is
     *     {@link ContrastEnhancement }
     *     
     */
    public ContrastEnhancement getContrastEnhancement() {
        return contrastEnhancement;
    }

    /**
     * Sets the value of the contrastEnhancement property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContrastEnhancement }
     *     
     */
    public void setContrastEnhancement(ContrastEnhancement value) {
        this.contrastEnhancement = value;
    }

}