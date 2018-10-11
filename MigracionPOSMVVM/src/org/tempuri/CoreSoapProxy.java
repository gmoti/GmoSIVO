package org.tempuri;

public class CoreSoapProxy implements org.tempuri.CoreSoap {
  private String _endpoint = null;
  private org.tempuri.CoreSoap coreSoap = null;
  
  public CoreSoapProxy() {
    _initCoreSoapProxy();
  }
  
  public CoreSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initCoreSoapProxy();
  }
  
  private void _initCoreSoapProxy() {
    try {
      coreSoap = (new org.tempuri.CoreLocator()).getCoreSoap();
      if (coreSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)coreSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)coreSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (coreSoap != null)
      ((javax.xml.rpc.Stub)coreSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.CoreSoap getCoreSoap() {
    if (coreSoap == null)
      _initCoreSoapProxy();
    return coreSoap;
  }
  
  public java.lang.String convertDocument(java.lang.String area, java.lang.String password, java.lang.String documentType, java.lang.String documentContent) throws java.rmi.RemoteException{
    if (coreSoap == null)
      _initCoreSoapProxy();
    return coreSoap.convertDocument(area, password, documentType, documentContent);
  }
  
  public java.lang.String convertSignDocument(java.lang.String area, java.lang.String password, java.lang.String documentType, java.lang.String documentContent) throws java.rmi.RemoteException{
    if (coreSoap == null)
      _initCoreSoapProxy();
    return coreSoap.convertSignDocument(area, password, documentType, documentContent);
  }
  
  public java.lang.String addCAF(java.lang.String area, java.lang.String password, java.lang.String CAF, java.lang.String fileName) throws java.rmi.RemoteException{
    if (coreSoap == null)
      _initCoreSoapProxy();
    return coreSoap.addCAF(area, password, CAF, fileName);
  }
  
  public java.lang.String removeCAF(java.lang.String area, java.lang.String password, java.lang.String fileName) throws java.rmi.RemoteException{
    if (coreSoap == null)
      _initCoreSoapProxy();
    return coreSoap.removeCAF(area, password, fileName);
  }
  
  public java.lang.String archiveCAF(java.lang.String area, java.lang.String password, java.lang.String fileName) throws java.rmi.RemoteException{
    if (coreSoap == null)
      _initCoreSoapProxy();
    return coreSoap.archiveCAF(area, password, fileName);
  }
  
  
}