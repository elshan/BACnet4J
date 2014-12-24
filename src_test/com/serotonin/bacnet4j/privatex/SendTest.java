package com.serotonin.bacnet4j.privatex;

import java.util.HashMap;
import java.util.Map;

import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.service.acknowledgement.ConfirmedPrivateTransferAck;
import com.serotonin.bacnet4j.service.confirmed.ConfirmedPrivateTransferRequest;
import com.serotonin.bacnet4j.service.unconfirmed.UnconfirmedPrivateTransferRequest;
import com.serotonin.bacnet4j.transport.Transport;
import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.SequenceDefinition;
import com.serotonin.bacnet4j.type.SequenceDefinition.ElementSpecification;
import com.serotonin.bacnet4j.type.constructed.Address;
import com.serotonin.bacnet4j.type.constructed.Sequence;
import com.serotonin.bacnet4j.type.primitive.Real;
import com.serotonin.bacnet4j.type.primitive.UnsignedInteger;

public class SendTest {
    static final SequenceDefinition DEF = new SequenceDefinition( //
            new ElementSpecification("value1", UnsignedInteger.class, false, false) //
            , new ElementSpecification("value2", Real.class, false, false) //
    );

    static LocalDevice localDevice;

    public static void main(String[] args) throws Exception {
        localDevice = new LocalDevice(1968, new Transport(new IpNetwork("255.255.255.255", 1234)));
        localDevice.initialize();

        try {
            RemoteDevice d = localDevice.findRemoteDevice(new Address("192.168.0.103", 0xBAC0), null, 1969);
            localDevice.addRemoteDevice(d);

            sendConfirmed(d, 8);
            //sendConfirmed(d, 9);

            sendUnconfirmed(d, 8);
            sendUnconfirmed(d, 9);
        }
        finally {
            localDevice.terminate();
        }
    }

    static void sendConfirmed(RemoteDevice d, int serviceNumber) throws Exception {
        Map<String, Encodable> values = new HashMap<>();
        values.put("value1", new UnsignedInteger(1));
        values.put("value2", new Real(72.4f));
        Sequence parameters = new Sequence(DEF, values);
        ConfirmedPrivateTransferRequest req = new ConfirmedPrivateTransferRequest(25, serviceNumber, parameters);

        ConfirmedPrivateTransferAck ack = (ConfirmedPrivateTransferAck) localDevice.send(d, req);
        System.out.println(ack.getResultBlock());
    }

    static void sendUnconfirmed(RemoteDevice d, int serviceNumber) throws Exception {
        Map<String, Encodable> values = new HashMap<>();
        values.put("value1", new UnsignedInteger(1));
        values.put("value2", new Real(72.4f));
        Sequence parameters = new Sequence(DEF, values);
        UnconfirmedPrivateTransferRequest req = new UnconfirmedPrivateTransferRequest(25, serviceNumber, parameters);

        localDevice.sendUnconfirmed(d.getAddress(), req);
    }
}
