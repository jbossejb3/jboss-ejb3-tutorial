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
package org.jboss.tutorial.partial_deployment_descriptor.bean;

import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.logging.Logger;

/**
 * CompleteXMLDDBean
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class CompleteXMLDDBean implements CompleteXMLDD
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(CompleteXMLDDBean.class);

   private PartialXMLDD partialXMLDDBean;

   private TimerService timerService;

   /**
    * @see CompleteXMLDD#greetWithNotSupportedTransaction(String)
    * 
    * Note : The transaction attribute of this method is overriden
    * through xml file
    */
   @TransactionAttribute (TransactionAttributeType.REQUIRES_NEW)
   public String greetWithNotSupportedTransaction(String name)
   {
      String message = "Welcome " + name + ", you are in a method with no transaction supported";
      logger.info(message);
      return message;
   }

   /**
    * @see CompleteXMLDD#greetWithRequiredTransaction(String)
    */
   public String greetWithRequiredTransaction(String name)
   {
      String message = "Welcome " + name + ", you are in a method with a REQUIRED transaction";
      logger.info(message);
      return message;
   }

   /**
    * @see CompleteXMLDD#sayBye(String)
    */
   public String sayBye(String name)
   {
      String message = "Bye, " + name + ". Hope to see you again";
      logger.info(message);
      return message;
   }

   /**
    * @see CompleteXMLDD#sayHello(String)
    */
   public String sayHello(String name)
   {
      String message = "Hello, " + name + ". I am the " + this.getClass().getSimpleName()
            + ". I have the following resources with me:\n" + "Timer Service : " + this.timerService + "\n"
            + "PartialXMLDD Bean : " + this.partialXMLDDBean + "\n";

      logger.info(message);
      return message;
   }

   /**
    * @see CompleteXMLDD#uncallableMethod()
    */
   public void uncallableMethod()
   {
      logger.info("Why is it that no one can call me?");
   }

}
