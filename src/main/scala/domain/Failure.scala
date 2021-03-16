package com.study
package domain

import spray.http.{StatusCode, StatusCodes}

/**
 * Service failure description.
 *
 * @param message   error message
 * @param errorType error type
 */
case class Failure(message: String, errorType: FailureType.Value) {

  /**
   * Return corresponding HTTP status code for failure specified type.
   *
   * @return HTTP status code value
   */
  def getStatusCode: StatusCode = {
    FailureType.withName(this.errorType.toString) match {
      case FailureType.BadRequest => StatusCodes.BadRequest
      case FailureType.NotFound => StatusCodes.NotFound
      case FailureType.Duplicate => StatusCodes.Forbidden
      case FailureType.DatabaseFailure => StatusCodes.InternalServerError
      case _ => StatusCodes.InternalServerError
    }
  }
}

/**
 * Allowed failure types.
 */
object FailureType extends Enumeration {
  type Failure = Value

  val BadRequest: FailureType.Value = Value("bad_request")
  val NotFound: FailureType.Value = Value("not_found")
  val Duplicate: FailureType.Value = Value("entity_exists")
  val DatabaseFailure: FailureType.Value = Value("database_error")
  val InternalError: FailureType.Value = Value("internal_error")
}