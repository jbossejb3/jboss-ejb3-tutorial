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
package org.jboss.tutorial.ejb21_client_adaptors.client;

import org.jboss.tutorial.ejb21_client_adaptors.bean.Session1RemoteHome;
import org.jboss.tutorial.ejb21_client_adaptors.bean.Session1Remote;

import javax.naming.InitialContext;

public class Client
{
   public static void main(String[] args) throws Exception
   {
      accessHomes();
      
      accessDeploymentDescriptorHomes();
   }
   
   public static void accessHomes() throws Exception
   {
      InitialContext jndiContext = new InitialContext();
      Session1RemoteHome home = (Session1RemoteHome)jndiContext.lookup("Session1/home");
      Session1Remote session1 = home.create();
      String initValue1 = session1.getInitValue();
      System.out.println("Session1 init value is " + initValue1);
      
      String initValue2 = session1.getLocalSession2InitValue();
      System.out.println("Session2 init value is " + initValue2);
   }
   
   public static void accessDeploymentDescriptorHomes() throws Exception
   {
      InitialContext jndiContext = new InitialContext();
      Session1RemoteHome home = (Session1RemoteHome)jndiContext.lookup("DeploymentDescriptorSession1/home");
      Session1Remote session1 = home.create();
      String initValue1 = session1.getInitValue();
      System.out.println("DeploymentDescriptor Session1 init value is " + initValue1);
      
      String initValue2 = session1.getLocalSession2InitValue();
      System.out.println("DeploymentDescriptor Session2 init value is " + initValue2);
   }
}
