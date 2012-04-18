//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.18 at 11:58:17 AM CEST 
//


package org.geosdi.geoplatform.xml.gml.v321;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * <p>Java class for OperationMethodType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperationMethodType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml/3.2}IdentifiedObjectType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{http://www.opengis.net/gml/3.2}formulaCitation"/>
 *           &lt;element ref="{http://www.opengis.net/gml/3.2}formula"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}sourceDimensions" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}targetDimensions" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/gml/3.2}parameter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationMethodType", propOrder = {
    "formulaCitation",
    "formula",
    "sourceDimensions",
    "targetDimensions",
    "parameter"
})
public class OperationMethodType
    extends IdentifiedObjectType
    implements ToString
{

    protected FormulaCitationElement formulaCitation;
    @XmlElementRef(name = "formula", namespace = "http://www.opengis.net/gml/3.2", type = JAXBElement.class)
    protected JAXBElement<CodeType> formula;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger sourceDimensions;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger targetDimensions;
    @XmlElementRef(name = "parameter", namespace = "http://www.opengis.net/gml/3.2", type = JAXBElement.class)
    protected List<JAXBElement<AbstractGeneralOperationParameterPropertyType>> parameter;

    /**
     * Gets the value of the formulaCitation property.
     * 
     * @return
     *     possible object is
     *     {@link FormulaCitationElement }
     *     
     */
    public FormulaCitationElement getFormulaCitation() {
        return formulaCitation;
    }

    /**
     * Sets the value of the formulaCitation property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormulaCitationElement }
     *     
     */
    public void setFormulaCitation(FormulaCitationElement value) {
        this.formulaCitation = value;
    }

    public boolean isSetFormulaCitation() {
        return (this.formulaCitation!= null);
    }

    /**
     * Gets the value of the formula property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CodeType }{@code >}
     *     {@link JAXBElement }{@code <}{@link CodeType }{@code >}
     *     
     */
    public JAXBElement<CodeType> getFormula() {
        return formula;
    }

    /**
     * Sets the value of the formula property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CodeType }{@code >}
     *     {@link JAXBElement }{@code <}{@link CodeType }{@code >}
     *     
     */
    public void setFormula(JAXBElement<CodeType> value) {
        this.formula = value;
    }

    public boolean isSetFormula() {
        return (this.formula!= null);
    }

    /**
     * Gets the value of the sourceDimensions property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSourceDimensions() {
        return sourceDimensions;
    }

    /**
     * Sets the value of the sourceDimensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSourceDimensions(BigInteger value) {
        this.sourceDimensions = value;
    }

    public boolean isSetSourceDimensions() {
        return (this.sourceDimensions!= null);
    }

    /**
     * Gets the value of the targetDimensions property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTargetDimensions() {
        return targetDimensions;
    }

    /**
     * Sets the value of the targetDimensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTargetDimensions(BigInteger value) {
        this.targetDimensions = value;
    }

    public boolean isSetTargetDimensions() {
        return (this.targetDimensions!= null);
    }

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link AbstractGeneralOperationParameterPropertyType }{@code >}
     * {@link JAXBElement }{@code <}{@link AbstractGeneralOperationParameterPropertyType }{@code >}
     * {@link JAXBElement }{@code <}{@link AbstractGeneralOperationParameterPropertyType }{@code >}
     * {@link JAXBElement }{@code <}{@link AbstractGeneralOperationParameterPropertyType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<AbstractGeneralOperationParameterPropertyType>> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<JAXBElement<AbstractGeneralOperationParameterPropertyType>>();
        }
        return this.parameter;
    }

    public boolean isSetParameter() {
        return ((this.parameter!= null)&&(!this.parameter.isEmpty()));
    }

    public void unsetParameter() {
        this.parameter = null;
    }

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
            FormulaCitationElement theFormulaCitation;
            theFormulaCitation = this.getFormulaCitation();
            strategy.appendField(locator, this, "formulaCitation", buffer, theFormulaCitation);
        }
        {
            JAXBElement<CodeType> theFormula;
            theFormula = this.getFormula();
            strategy.appendField(locator, this, "formula", buffer, theFormula);
        }
        {
            BigInteger theSourceDimensions;
            theSourceDimensions = this.getSourceDimensions();
            strategy.appendField(locator, this, "sourceDimensions", buffer, theSourceDimensions);
        }
        {
            BigInteger theTargetDimensions;
            theTargetDimensions = this.getTargetDimensions();
            strategy.appendField(locator, this, "targetDimensions", buffer, theTargetDimensions);
        }
        {
            List<JAXBElement<AbstractGeneralOperationParameterPropertyType>> theParameter;
            theParameter = (this.isSetParameter()?this.getParameter():null);
            strategy.appendField(locator, this, "parameter", buffer, theParameter);
        }
        return buffer;
    }

    public void setParameter(List<JAXBElement<AbstractGeneralOperationParameterPropertyType>> value) {
        this.parameter = null;
        List<JAXBElement<AbstractGeneralOperationParameterPropertyType>> draftl = this.getParameter();
        draftl.addAll(value);
    }

}
