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

import org.jboss.ejb3.annotation.Depends;
import org.jboss.ejb3.annotation.Management;
import org.jboss.ejb3.annotation.Service;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 61136 $
 */
@Service(objectName = ServiceTwo.OBJECT_NAME)
@Management(ServiceTwoManagement.class)
@Depends(ServiceOne.OBJECT_NAME)
public class ServiceTwo implements ServiceTwoManagement
{
   /**
    * The ObjectName for {@link ServiceTwo} 
    */
   public static final String OBJECT_NAME = "tutorial:service=ServiceTwo";

   public String sayHello()
   {
      return "Hello from service Two";
   }

   // Lifecycle methods
   public void start() throws Exception
   {
      System.out.println("ServiceTwo - Started");
   }

   public void stop()
   {
      System.out.println("ServiceTwo - Stopped");
   }
}
