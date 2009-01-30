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
package org.jboss.tutorial.ssl.bean;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.ejb3.annotation.RemoteBindings;
import org.jboss.ejb3.annotation.SecurityDomain;

@Stateless
@RemoteBindings(
{@RemoteBinding(clientBindUrl = "sslsocket://0.0.0.0:3843"), @RemoteBinding(jndiBinding = "CalculatorNormal")})
@SecurityDomain("other")
public class CalculatorBean implements Calculator
{
   @PermitAll
   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   public int add(int x, int y)
   {
      return x + y;
   }

   @RolesAllowed(
   {"student"})
   public int subtract(int x, int y)
   {
      return x - y;
   }

   @RolesAllowed(
   {"teacher"})
   public int divide(int x, int y)
   {
      return x / y;
   }
}
