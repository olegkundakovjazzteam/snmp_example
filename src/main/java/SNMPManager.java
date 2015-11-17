import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

/**
 * Created by JazzTeamUser on 13.11.2015.
 */
public class SNMPManager {

    Snmp snmp = null;
    String address = null;

    /**
     * Constructor
     *
     * @param add
     */
    public SNMPManager(String add) {
        address = add;
    }

    public static void main(String[] args) throws IOException {
/**
 * Port 161 is used for Read and Other operations
 * Port 162 is used for the trap generation
 */
        SNMPManager client = new SNMPManager("udp:127.0.0.1/161");
        client.start();
/**
 * OID - .1.3.6.1.2.1.1.1.0 => SysDec
 * OID - .1.3.6.1.2.1.1.5.0 => SysName
 * => MIB explorer will be usefull here, as discussed in previous article
 */
        String sysDescr =
                client.getAsString(
                        new OID(".1.3.6.1.2.1.1.1.0"),
                        new OID(".1.3.6.1.2.1.1.5.0"));
        System.out.println(sysDescr);
    }

    /**
     * Start the Snmp session. If you forget the listen() method you will not
     * get any answers because the communication is asynchronous
     * and the listen() method listens for answers.
     *
     * @throws IOException
     */
    private void start() throws IOException {
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
// Do not forget this line!
        transport.listen();
    }

    /**
     * Method which takes a single OID and returns the response from the agent as a String.
     *
     * @param oid
     * @return
     * @throws IOException
     */
    public String getAsString(OID... oid) throws IOException {
//        ResponseEvent event = get(new OID[]{oid});
//        return event.getResponse().get(0).getVariable().toString();
        ResponseEvent event = get(oid);
        PDU response = event.getResponse();
        int size = oid.length;
//        VariableBinding variableBinding=response.get(0);
//        Variable variable=variableBinding.getVariable();
//        String varStirng=variable.toString();
//        return varStirng;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            VariableBinding variableBinding = response.get(i);
            Variable variable = variableBinding.getVariable();
            String varStirng = variable.toString();
            String oi = variableBinding.getOid().toString();
            builder.append("oid: ")
                    .append(oi)
                    .append(" var: ")
                    .append(varStirng)
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * This method is capable of handling multiple OIDs
     *
     * @param oids
     * @return
     * @throws IOException
     */
    public ResponseEvent get(OID... oids) throws IOException {
        PDU pdu = new PDU();
        for (OID oid : oids) {
            pdu.add(new VariableBinding(oid));
        }
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget(), null);
        if (event != null) {
            return event;
        }
        throw new RuntimeException("GET timed out");
    }

    /**
     * This method returns a Target, which contains information about
     * where the data should be fetched and how.
     *
     * @return
     */
    private Target getTarget() {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

}