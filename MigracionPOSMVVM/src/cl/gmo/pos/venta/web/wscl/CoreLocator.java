/**
 * CoreLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cl.gmo.pos.venta.web.wscl;

public class CoreLocator extends org.apache.axis.client.Service implements cl.gmo.pos.venta.web.wscl.Core {

    public CoreLocator() {
    }


    public CoreLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CoreLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CoreSoap
    private java.lang.String CoreSoap_address = "http://10.216.4.16/Signature.xDocCL.Web.Services/core.asmx";

    public java.lang.String getCoreSoapAddress() {
        return CoreSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CoreSoapWSDDServiceName = "CoreSoap";

    public java.lang.String getCoreSoapWSDDServiceName() {
        return CoreSoapWSDDServiceName;
    }

    public void setCoreSoapWSDDServiceName(java.lang.String name) {
        CoreSoapWSDDServiceName = name;
    }

    public cl.gmo.pos.venta.web.wscl.CoreSoap getCoreSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CoreSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCoreSoap(endpoint);
    }

    public cl.gmo.pos.venta.web.wscl.CoreSoap getCoreSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cl.gmo.pos.venta.web.wscl.CoreSoapStub _stub = new cl.gmo.pos.venta.web.wscl.CoreSoapStub(portAddress, this);
            _stub.setPortName(getCoreSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCoreSoapEndpointAddress(java.lang.String address) {
        CoreSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cl.gmo.pos.venta.web.wscl.CoreSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                cl.gmo.pos.venta.web.wscl.CoreSoapStub _stub = new cl.gmo.pos.venta.web.wscl.CoreSoapStub(new java.net.URL(CoreSoap_address), this);
                _stub.setPortName(getCoreSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CoreSoap".equals(inputPortName)) {
            return getCoreSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "Core");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "CoreSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CoreSoap".equals(portName)) {
            setCoreSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
