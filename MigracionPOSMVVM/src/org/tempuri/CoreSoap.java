/**
 * CoreSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface CoreSoap extends java.rmi.Remote {
    public java.lang.String convertDocument(java.lang.String area, java.lang.String password, java.lang.String documentType, java.lang.String documentContent) throws java.rmi.RemoteException;
    public java.lang.String convertSignDocument(java.lang.String area, java.lang.String password, java.lang.String documentType, java.lang.String documentContent) throws java.rmi.RemoteException;
    public java.lang.String addCAF(java.lang.String area, java.lang.String password, java.lang.String CAF, java.lang.String fileName) throws java.rmi.RemoteException;
    public java.lang.String removeCAF(java.lang.String area, java.lang.String password, java.lang.String fileName) throws java.rmi.RemoteException;
    public java.lang.String archiveCAF(java.lang.String area, java.lang.String password, java.lang.String fileName) throws java.rmi.RemoteException;
}
