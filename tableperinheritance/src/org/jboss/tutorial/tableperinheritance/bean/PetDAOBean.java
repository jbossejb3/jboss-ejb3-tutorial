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
package org.jboss.tutorial.tableperinheritance.bean;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(PetDAO.class)
public class PetDAOBean implements PetDAO
{
   @PersistenceContext
   private EntityManager manager;

   public void createDog(String name, double weight, int bones)
   {
      Dog dog = new Dog();
      dog.setName(name);
      dog.setWeight(weight);
      dog.setNumBones(bones);
      manager.persist(dog);
   }

   public void createCat(String name, double weight, int lives)
   {
      Cat cat = new Cat();
      cat.setName(name);
      cat.setWeight(weight);
      cat.setLives(lives);
      manager.persist(cat);
   }

   public List findByWeight(double weight)
   {
      return manager.createQuery("from Pet p where p.weight < :weight").setParameter("weight", weight).getResultList();
   }
}
