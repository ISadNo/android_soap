package com.example.android_soap;

import android.util.Xml;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by A605698 on 30/06/2015.
 */
public class AgencySOAFetcher {

    //Call Web service
    private static final String SOAP_ACTION = "http://ws.android.com/getAgences";
    private static final String METHOD_NAME = "getAgences";
    private static final String NAMESPACE = "http://ws.android.com";
    private static final String URL = "http://169.254.96.126:8080/AndroidWSTest/services/WSAgencies?wsdl";

    private final SoapSerializationEnvelope envelope;

    public AgencySOAFetcher() {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
    }

    public List<Agency> Fetch() {
        HttpTransportSE httpRequest = new HttpTransportSE(URL);
        AgencySOAHandler agencyParser = new AgencySOAHandler();
        try {
            httpRequest.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Xml.parse(response.toString(), agencyParser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agencyParser.getAgencies();
    }
}
