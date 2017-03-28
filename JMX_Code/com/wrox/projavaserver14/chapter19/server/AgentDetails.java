package com.wrox.projavaserver14.chapter19.server;

import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class AgentDetails
{

    public static void main(String[] args) throws Exception
    {
        MBeanServer server = MBeanServerFactory.createMBeanServer();

        ObjectName delegateMBeanName =
            new ObjectName("JMImplementation:type=MBeanServerDelegate");

        AttributeList attribs =
            server.getAttributes(
                delegateMBeanName,
                new String[] {
                    "ImplementationName",
                    "ImplementationVendor",
                    "ImplementationVersion",
                    "MBeanServerId",
                    "SpecificationName",
                    "SpecificationVendor",
                    "SpecificationVersion" });

        Iterator it = attribs.iterator();
        while (it.hasNext())
        {
            Attribute att = (Attribute) it.next();
            System.out.println(att.getName() + ":" + att.getValue());
        }

    }

}
