package com.study
package config

import com.typesafe.config.{Config, ConfigFactory}

import util.Try

/**
 * Holds service configuration settings.
 */
trait Configuration {

  /**
   * Application config object.
   */
  val config: Config = ConfigFactory.load()

  /** Host name/address to start service on. */
  lazy val serviceHost: String = Try(config.getString("service.host")).getOrElse("localhost")

  /** Port to start service on. */
  lazy val servicePort: Int = Try(config.getInt("service.port")).getOrElse(8080)

  /** Database host name/address. */
  lazy val dbHost: String = Try(config.getString("db.host")).getOrElse("localhost")

  /** Database host port number. */
  lazy val dbPort: Int = Try(config.getInt("db.port")).getOrElse(3306)

  /** Service database name. */
  lazy val dbName: String = Try(config.getString("db.name")).getOrElse("rest")

  /** User name used to access database. */
  lazy val dbUser: String = Try(config.getString("db.user")).toOption.orNull

  /** Password for specified user and database. */
  lazy val dbPassword: String = Try(config.getString("db.password")).toOption.orNull
}