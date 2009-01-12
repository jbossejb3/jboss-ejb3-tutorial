/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.tutorial.service.client;

import javax.management.ObjectName;
import javax.naming.InitialContext;

import org.jboss.jmx.adaptor.rmi.RMIAdaptor;
import org.jboss.tutorial.service.bean.ServiceOne;
import org.jboss.tutorial.service.bean.ServiceOneRemote;
import org.jboss.tutorial.service.bean.ServiceThree;
import org.jboss.tutorial.service.bean.XMBeanService;
import org.jboss.tutorial.service.bean.XMBeanServiceRemote;

public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
      //Get hold of the MBean server invoker
      RMIAdaptor server = (RMIAdaptor) ctx.lookup("jmx/invoker/RMIAdaptor");

      invokeService(ctx, server);

      invokeXMBean(ctx, server);
   }

   public static void invokeService(InitialContext ctx, RMIAdaptor server) throws Exception
   {
      System.out.println("invoking remote business interface of ServiceOne...");
      //Set attribute on singleton ServiceOne via remote interface
      ServiceOneRemote serviceOne = (ServiceOneRemote) ctx.lookup("ServiceOne/remote");
      serviceOne.setAttribute(100);
      System.out.println("Set the attribute value through ServiceOneRemote to 100");
      //Create object name for ServiceOne
      ObjectName service1 = new ObjectName(ServiceOne.OBJECT_NAME);
           
      //Get attribute of singleton ServiceOne via JMX
      int attr1 = (Integer) server.getAttribute(service1, "Attribute");
      System.out.println("attribute value for (ServiceOne) singleton obtained via JMX is what we set via remote interface: " + attr1);

      System.out.println("Invoking ServiceThree via JMX...");
      //Create object name for ServiceThree
      ObjectName service3 = new ObjectName(ServiceThree.OBJECT_NAME);
           
      //Call serviceOneHello() and serviceTwoHello() on ServiceThree
      Object[] noArgs = new Object[0];//No arguments
      String[] noSig = new String[0];//No parameters in signature

      String service1Hello = (String) server.invoke(service3, "serviceOneHello", noArgs, noSig);
      System.out.println(service1Hello);
      String service2Hello = (String) server.invoke(service3, "serviceTwoHello", noArgs, noSig);
      System.out.println(service2Hello);
   }

   public static void invokeXMBean(InitialContext ctx, RMIAdaptor server) throws Exception
   {
      System.out.println("invoking XMBean (configured through deployment descriptor)...");
      XMBeanServiceRemote xmbeanRemote = (XMBeanServiceRemote) ctx.lookup("XMBeanService/remote");
      xmbeanRemote.remoteBusinessMethodToSetAttribute(50);
      System.out.println("Set the attribute value to 50");
      
      System.out.println("Invoking XMBean through JMX");
      ObjectName service = new ObjectName(XMBeanService.OBJECT_NAME);
      //Get attribute of singleton XMBeanService via JMX
      int attr = (Integer) server.getAttribute(service, "IntAttribute");
      System.out.println("attribute value for (XMBeanService deployment descriptor configured) singleton obtained via JMX is what we set via remote interface: " + attr);

      //Call sayHello() on XMBeanService
      Object[] noArgs = new Object[0];//No arguments
      String[] noSig = new String[0];//No parameters in signature

      String hello = (String) server.invoke(service, "sayHello", noArgs, noSig);
      System.out.println(hello);
   }
}
