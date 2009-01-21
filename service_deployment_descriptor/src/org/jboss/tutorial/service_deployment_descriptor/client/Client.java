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
package org.jboss.tutorial.service_deployment_descriptor.client;

import org.jboss.tutorial.service_deployment_descriptor.bean.ServiceOneRemote;
import org.jboss.tutorial.service_deployment_descriptor.bean.ServiceOne;
import org.jboss.jmx.adaptor.rmi.RMIAdaptor;

import javax.naming.InitialContext;
import javax.management.ObjectName;


public class Client
{
   public static void main(String[] args) throws Exception
   {
      try {
      InitialContext ctx = new InitialContext();
      //Get hold of the MBean server invoker
      RMIAdaptor server = (RMIAdaptor)ctx.lookup("jmx/invoker/RMIAdaptor");

      //Set attribute on singleton ServiceOne via remote interface
      ServiceOneRemote serviceOne = (ServiceOneRemote) ctx.lookup("serviceOne/remote");
      serviceOne.setAttribute(100);

      //Create object name for ServiceOne
      ObjectName service1 = new ObjectName("tutorial:service=serviceOne");
      //Get attribute of singleton ServiceOne via JMX
      int attr1 = (Integer)server.getAttribute(service1, "Attribute");
      System.out.println("attribute value for singleton obtained via JMX is what we set via remote interface: " + attr1);

      //Create object name for ServiceThree
      ObjectName service3 = new ObjectName("tutorial:service=serviceThree");
      //Call serviceOneHello() and serviceTwoHello() on ServiceThree
      Object[] noArgs = new Object[0];//No arguments
      String[] noSig = new String[0];//No parameters in signature

      String service1Hello = (String)server.invoke(service3, "serviceOneHello", noArgs, noSig);
      System.out.println(service1Hello);
      String service2Hello = (String)server.invoke(service3, "serviceTwoHello", noArgs, noSig);
      System.out.println(service2Hello);
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
