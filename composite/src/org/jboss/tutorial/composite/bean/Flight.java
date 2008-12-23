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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity()
public class Flight implements java.io.Serializable
{
   Long id;
   String name;
   long duration;
   long durationInSec;
   Integer version;
   Set<Customer> customers;

   @Id
   public Long getId()
   {
      return id;
   }

   public void setId(Long long1)
   {
      id = long1;
   }

   @Column(updatable = false, name = "flight_name", nullable = false, length = 50)
   public String getName()
   {
      return name;
   }

   public void setName(String string)
   {
      name = string;
   }

   @Basic(fetch = FetchType.LAZY)
   public long getDuration()
   {
      return duration;
   }

   public void setDuration(long l)
   {
      duration = l;
      //durationInSec = duration / 1000;
   }

   @Transient
   public long getDurationInSec()
   {
      return durationInSec;
   }

   public void setDurationInSec(long l)
   {
      durationInSec = l;
   }

   @Version
   @Column(name = "OPTLOCK")
   public Integer getVersion()
   {
      return version;
   }

   public void setVersion(Integer i)
   {
      version = i;
   }

   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
   @JoinTable(name = "flight_customer_table",
                     joinColumns = {@JoinColumn(name = "FLIGHT_ID")},
                     inverseJoinColumns = {@JoinColumn(name = "CUSTOMER_ID"), @JoinColumn(name = "CUSTOMER_NAME")})
   public Set<Customer> getCustomers()
   {
      return customers;
   }

   public void setCustomers(Set<Customer> customers)
   {
      this.customers = customers;
   }
}
