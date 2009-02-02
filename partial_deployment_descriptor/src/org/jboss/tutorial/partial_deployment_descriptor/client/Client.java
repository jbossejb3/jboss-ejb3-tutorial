/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.tutorial.partial_deployment_descriptor.client;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;
import org.jboss.tutorial.partial_deployment_descriptor.bean.CompleteXMLDD;
import org.jboss.tutorial.partial_deployment_descriptor.bean.PartialXMLDD;

/**
 * Client
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class Client
{
   public static void main(String args[]) throws Exception
   {
      Context ctx = new InitialContext();
      CompleteXMLDD completeXMLDDBean = (CompleteXMLDD) ctx.lookup("CompleteXMLDD/remote");
      SecurityClient securityClient = SecurityClientFactory.getSecurityClient();
      securityClient.setSimple("jai", "jai123");
      securityClient.login();
      
      System.out.println("jai is a normal user");
      // call sayHello
      System.out.println(completeXMLDDBean.sayHello("jai"));

      // call greet methods
      System.out.println(completeXMLDDBean.greetWithNotSupportedTransaction("jai"));
      System.out.println(completeXMLDDBean.greetWithRequiredTransaction("jai"));

      // bye
      System.out.println(completeXMLDDBean.sayBye("jai"));

      // let's try the uncallable method
      System.out.println("We'll try calling an uncallable method");
      try
      {
         completeXMLDDBean.uncallableMethod();
         throw new RuntimeException("Bean method in <excluded-list> was allowed to be invoked");
      }
      catch (EJBAccessException e)
      {
         System.out.println("Caught expected exception : " + e.getMessage());
      }
      securityClient.logout();
      
      // let's login with other user
      securityClient.setSimple("bill", "bill123");
      securityClient.login();
      System.out.println("bill is an admin");
      // Now work on the other bean
      PartialXMLDD partialXMLDDBean = (PartialXMLDD) ctx.lookup("PartialXMLDD/remote");
      System.out.println("Sending Hello World message to bean. We expect the bean to change it");
      System.out.println(partialXMLDDBean.changeMessage("Hello world"));

      System.out.println("Now calling echo message");
      System.out.println(partialXMLDDBean.echoMessage("Hello World"));

      //let's remove the bean
      System.out.println("We are done with the bean, let's remove it");
      partialXMLDDBean.remove();
      System.out.println("Bean removed");

   }
}
