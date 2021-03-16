package com.study
package domain

import slick.jdbc.H2Profile.api._

import java.time.LocalDate

/**
 * Customer entity.
 *
 * @param id        unique id
 * @param firstName first name
 * @param lastName  last name
 * @param birthday  date of birth
 */
case class Customer(id: Long, firstName: String, lastName: String, birthday: Option[LocalDate])

/**
 * Mapped customers table object.
 */
trait CustomersComponent {
//  object Customers extends Table[Customer]("customers") {
//    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
//    def firstName = column[String]("first_name")
//    def lastName = column[String]("last_name")
//    def birthday = column[Option[LocalDate]]("birthday")
//
//    def * = (id, firstName, lastName, birthday) <> (Customer.tupled, Customer.unapply)
//  }
//  val customers= TableQuery[Customers]
}
