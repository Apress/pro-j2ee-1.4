package com.wrox.projavaserver14.chapter19.mbean.open;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfo;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfo;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

public class OfferReport implements DynamicMBean
{

    private static CompositeType customerType;
    private static CompositeType offerType;
    private static TabularType offerReportType;
    private static MBeanInfo offerReportInfo;
    private static TabularData reportData;

    static {
        try
        {
            buildTypes();
            buildMetadata();
            buildData();
        }
        catch (OpenDataException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Object getAttribute(String attribute)
        throws AttributeNotFoundException, MBeanException, ReflectionException
    {

        if (attribute == null)
            throw new RuntimeOperationsException(
                new IllegalArgumentException("Attribute name cannot be null"));

        if (attribute.equals("report"))
            return reportData;

        throw new AttributeNotFoundException("Cannot find " + attribute);

    }

    public AttributeList getAttributes(String[] attributeNames)
    {

        if (attributeNames == null)
            throw new RuntimeOperationsException(
                new IllegalArgumentException("attributeNames[] cannot be null"));

        AttributeList resultList = new AttributeList();

        if (attributeNames.length == 0)
            return resultList;

        for (int i = 0; i < attributeNames.length; i++)
        {
            try
            {
                Object value = getAttribute((String) attributeNames[i]);
                resultList.add(new Attribute(attributeNames[i], value));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return resultList;

    }

    public MBeanInfo getMBeanInfo()
    {
        return offerReportInfo;
    }

    public Object invoke(String operationName, Object[] obj, String[] str2)
        throws MBeanException, ReflectionException
    {
        throw new ReflectionException(new NoSuchMethodException(operationName));
    }

    public void setAttribute(Attribute attribute)
        throws
            MBeanException,
            AttributeNotFoundException,
            InvalidAttributeValueException,
            ReflectionException
    {
        throw new AttributeNotFoundException("No settable attributes");
    }

    public AttributeList setAttributes(AttributeList attributeList)
    {
        return new AttributeList();
    }

    private static void buildTypes() throws OpenDataException
    {

        customerType =
            new CompositeType(
                "customerType",
                "Customer",
                new String[] { "name", "email" },
                new String[] { "Name", "E-Mail" },
                new OpenType[] { SimpleType.STRING, SimpleType.STRING });

        ArrayType customersType = new ArrayType(1, customerType);

        offerType =
            new CompositeType(
                "offerType",
                "Offer",
                new String[] { "name", "description", "askPrice", "customers" },
                new String[] {
                    "Name",
                    "Description",
                    "Ask Price",
                    "Customers" },
                new OpenType[] {
                    SimpleType.STRING,
                    SimpleType.STRING,
                    SimpleType.DOUBLE,
                    customersType });

        offerReportType =
            new TabularType(
                "offerReportType ",
                "Offer Report",
                offerType,
                new String[] { "name" });

    }

    private static void buildMetadata()
    {

        offerReportInfo =
            new OpenMBeanInfoSupport(
                "com.wrox.projavaserver14.chapter19.mbean.open.OfferReport",
                "Offer report metadata",
                new OpenMBeanAttributeInfo[] {
                     new OpenMBeanAttributeInfoSupport(
                        "report",
                        "Offer report",
                        offerReportType,
                        true,
                        false,
                        false)},
                new OpenMBeanConstructorInfo[] {
        }, new OpenMBeanOperationInfo[] {
        }, new MBeanNotificationInfo[] {
        });

    }

    private static void buildData() throws OpenDataException
    {

        CompositeData customer1 =
            new CompositeDataSupport(
                customerType,
                new String[] { "name", "email" },
                new Object[] { "Fred Flintstone", "fred@waterbuffalos.com"});

        CompositeData customer2 =
            new CompositeDataSupport(
				customerType,
                new String[] { "name", "email" },
                new Object[] { "Barney Rubble", "barney@waterbuffalos.com"});

        CompositeData offer1 =
            new CompositeDataSupport(
                offerType,
                new String[] { "name", "description", "askPrice", "customers" },
                new Object[] {
                    "MP3 Player",
					"256 Mb MP3 Player",
                    new Double(100),
                    new CompositeData[] { customer1, customer2 }
        });

        reportData = new TabularDataSupport(offerReportType);
        reportData.put(offer1);

    }

}