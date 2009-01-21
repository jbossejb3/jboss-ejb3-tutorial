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
package org.jboss.tutorial.service.bean;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.jboss.ejb3.annotation.Depends;
import org.jboss.ejb3.annotation.Management;
import org.jboss.ejb3.annotation.Service;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 61136 $
 */
@Service(objectName = ServiceThree.OBJECT_NAME)
@Management(ServiceThreeManagement.class)
public class ServiceThree implements ServiceThreeManagement
{
   /**
    * The ObjectName for {@link ServiceThree} 
    */
   public static final String OBJECT_NAME = "tutorial:service=ServiceThree";

   @Depends(ServiceOne.OBJECT_NAME)
   public ObjectName serviceOneName;

   
   private ServiceTwoManagement service2;

   @Depends(ServiceTwo.OBJECT_NAME)
   public void setServiceTwo(ServiceTwoManagement service2)
   {
      this.service2 = service2;
   }

   public String serviceOneHello() throws Exception
   {
      Object[] args = new Object[0];
      String[] signature = new String[0];
      System.out.println("ServiceThree - Calling ServiceOne.sayHello() via JMX server");
      MBeanServer server = (MBeanServer) MBeanServerFactory.findMBeanServer(null).get(0);
      return (String) server.invoke(serviceOneName, "sayHello", args, signature);
   }

   public String serviceTwoHello()
   {
      System.out.println("ServiceThree - Calling ServiceTwo.sayHello() via MBean proxy");
      return service2.sayHello();
      
   }

   // Interceptors
   @AroundInvoke
   public Object intercept(InvocationContext ctx) throws Exception
   {
      System.out.println("ServiceThree - Interceptor");
      return ctx.proceed();
   }

   // Lifecycle methods
   public void start() throws Exception
   {
      System.out.println("ServiceThree - Started");
   }

   public void stop()
   {
      System.out.println("ServiceThree - Stopped");
   }

}
