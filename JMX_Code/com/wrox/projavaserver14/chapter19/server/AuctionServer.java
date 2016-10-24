package com.wrox.projavaserver14.chapter19.server;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import com.wrox.projavaserver14.chapter19.mbean.dynamic.Offer;

public class AuctionServer
{

    public static void main(String[] args) throws Exception
    {

        MBeanServer server = MBeanServerFactory.createMBeanServer();

        final HtmlAdaptorServer adaptorMBean = new HtmlAdaptorServer();

        ObjectName adaptorMBeanName =
            new ObjectName("Adaptor:name=html,port=8000");

        server.registerMBean(adaptorMBean, adaptorMBeanName);

        server.setAttribute(
            adaptorMBeanName,
            new Attribute("Port", new Integer(8000)));

		Offer offerMBean = new Offer(1, "MP3 Player", "256 Mb MP3 Player");
		ObjectName mp3PlayerMBeanName =
			new ObjectName(
		"AuctionApplication:type=Offer,name=MP3 Player,mBeanType=dynamic");

		server.registerMBean(offerMBean, mp3PlayerMBeanName);


        adaptorMBean.start();

        System.out.println("Auction server started");

    }

}
