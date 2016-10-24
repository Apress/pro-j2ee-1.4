package com.wrox.projavaserver14.chapter19.mbean.dynamic;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;

public class OfferMBeanHelper
{

    private static MBeanAttributeInfo[] getAttributeInfo()
    {

        return new MBeanAttributeInfo[] {
            new MBeanAttributeInfo(
                "id",
                Integer.TYPE.getName(),
                "Offer Id",
                true,
                false,
                false),
            new MBeanAttributeInfo(
                "name",
                "java.lang.String",
                "Offer Name",
                true,
                true,
                false),
            new MBeanAttributeInfo(
                "description",
                "java.lang.String",
                "Offer Description",
                true,
                true,
                false),
            new MBeanAttributeInfo(
                "askPrice",
                Double.TYPE.getName(),
                "Ask Price",
                true,
                false,
                false)};

    }

    private static MBeanConstructorInfo[] getConstructorInfo()
    {

        return new MBeanConstructorInfo[] {
             new MBeanConstructorInfo(
                "Offer",
                "Creates an offer",
                new MBeanParameterInfo[] {
                    new MBeanParameterInfo(
                        "newId",
                        Integer.TYPE.getName(),
                        "Offer Id"),
                    new MBeanParameterInfo(
                        "newName",
                        "java.lamg.String",
                        "Offer Name"),
                    new MBeanParameterInfo(
                        "newDescription",
                        "java.lamg.String",
                        "Offer Description")})
                    };

    }

    private static MBeanOperationInfo[] getOperationInfo()
    {

        return new MBeanOperationInfo[] {
             new MBeanOperationInfo(
                "bid",
                "Bids for the offer",
                new MBeanParameterInfo[] {
                     new MBeanParameterInfo(
                        "bidPrice",
                        Double.TYPE.getName(),
                        "Bid price")},
                Void.TYPE.getName(),
                MBeanOperationInfo.INFO)
            };

    }

    private static MBeanNotificationInfo[] getNotificationInfo()
    {
        return new MBeanNotificationInfo[0];
    }

    public static MBeanInfo getMBeanInfo()
    {

        return new MBeanInfo(
            "com.wrox.projavaserver14.chapter19.mbean.dynamic.Offer",
            "Dynamic offer MBean",
            getAttributeInfo(),
            getConstructorInfo(),
            getOperationInfo(),
            getNotificationInfo());

    }

}