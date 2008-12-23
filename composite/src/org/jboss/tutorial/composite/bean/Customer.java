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
package org.jboss.tutorial.composite.bean;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

/**
 *
 */
@Entity
public class Customer implements java.io.Serializable
{
   CustomerPK pk;
   Set<Flight> flights;

   public Customer()
   {
   }

   @EmbeddedId
   public CustomerPK getPk()
   {
      return pk;
   }

   public void setPk(CustomerPK pk)
   {
      this.pk = pk;
   }

   @Transient
   public String getName()
   {
      return pk.getName();
   }

   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy="customers")
   @JoinTable(name="flight_customer_table",
              joinColumns = {@JoinColumn(name = "FLIGHT_ID")},
              inverseJoinColumns = {@JoinColumn(name = "CUSTOMER_ID"), @JoinColumn(name = "CUSTOMER_NAME")})
   public Set<Flight> getFlights()
   {
      return flights;
   }

   public void setFlights(Set<Flight> flights)
   {
      this.flights = flights;
   }

}

