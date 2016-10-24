package com.wrox.projavaserver14.chapter19.mbean.dynamic;

import javax.management.DynamicMBean;

import javax.management.AttributeNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.RuntimeOperationsException;

import javax.management.AttributeList;
import javax.management.MBeanInfo;
import javax.management.Attribute;

import java.util.Iterator;

public class Offer implements DynamicMBean
{
    private int id;
    private String name;
    private String description;
    private double askPrice;

    public Offer(int newId, String newName, String newDescription)
    {
        id = newId;
        name = newName;
        description = newDescription;
    }
    
    public MBeanInfo getMBeanInfo()
    {
        return OfferMBeanHelper.getMBeanInfo();
    }
    
    public Object getAttribute(String attribute)
        throws MBeanException, AttributeNotFoundException, ReflectionException
    {
        if (attribute == null)
            throw new RuntimeOperationsException(
                new IllegalArgumentException("Attribute name cannot be null"));
        if (attribute.equals("id"))
            return new Integer(id);
        else if (attribute.equals("name"))
            return name;
        else if (attribute.equals("description"))
            return description;
        else if (attribute.equals("askPrice"))
            return new Double(askPrice);
        throw new AttributeNotFoundException("Cannot find " + attribute);

    }
    
    public void setAttribute(Attribute attribute)
        throws
            MBeanException,
            AttributeNotFoundException,
            InvalidAttributeValueException,
            ReflectionException
    {

        if (attribute == null)
        {
            throw new RuntimeOperationsException(
                new IllegalArgumentException("Attribute cannot be null"));
        }
        String name = attribute.getName();
        Object value = attribute.getValue();

        if (name == null)
        {
            throw new RuntimeOperationsException(
                new IllegalArgumentException("Attribute name cannot be null"));
        }

        try
        {
            if (name.equals("name"))
                name = (String) value;
            else if (name.equals("description"))
                description = (String) value;
            else
                throw new AttributeNotFoundException(
                    "Attribute not found " + name);
        }
        catch (ClassCastException ex)
        {
            throw new InvalidAttributeValueException(
                "Invalid value for " + name);
        }

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
    
    public AttributeList setAttributes(AttributeList attributes)
    {

        if (attributes == null)
            throw new RuntimeOperationsException(
                new IllegalArgumentException("attributeNames[] cannot be null"));

        AttributeList resultList = new AttributeList();

        if (attributes.isEmpty())
            return resultList;

        for (Iterator i = attributes.iterator(); i.hasNext();)
        {

            Attribute attr = (Attribute) i.next();
            try
            {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name);
                resultList.add(new Attribute(name, value));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        return resultList;

    }
    
    public Object invoke(
        String operationName,
        Object params[],
        String signature[])
        throws MBeanException, ReflectionException
    {

        if (operationName == null)
        {
            throw new RuntimeOperationsException(
                new IllegalArgumentException("Operation name cannot be null"));
        }
        if (operationName.equals("bid"))
        {
            System.out.println("Bid price:" + params[0]);
            return null;
        }
        else
            throw new ReflectionException(
                new NoSuchMethodException(operationName));

    }

}
