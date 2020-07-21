package com.csw.services.impl;

import com.csw.services.interfaces.ITransformSensorMLToEbrimService;
import com.csw.transform.util.TransformSensorMLToebRIMAction;
import javax.jws.WebService;

public class TransformSensorMlToEbrimService implements ITransformSensorMLToEbrimService {
    public String transformSensorMLToEbrimResponse(String sensorML) {
        TransformSensorMLToebRIMAction trans = new TransformSensorMLToebRIMAction();
        String str = trans.parseSensorMLToEbRIM(sensorML);
        return str;
    }
}
