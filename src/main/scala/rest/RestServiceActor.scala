package com.study
package rest

import dao.CustomerDAO
import domain.{Customer, CustomerSearchParameters, Failure}

import akka.actor.{Actor, ActorContext}
import akka.event.slf4j.SLF4JLogging
import net.liftweb.json.Serialization._
import net.liftweb.json.{DateFormat, DefaultFormats, Formats}
import spray.http._
import spray.httpx.unmarshalling._
import spray.routing._

import java.text.{ParseException, SimpleDateFormat}
import java.time.LocalDate
import java.util.Date
import scala.concurrent.ExecutionContextExecutor

/**
 * REST Service actor.
 */
class RestServiceActor extends Actor with RestService {

  implicit def actorRefFactory: ActorContext = context

  def receive: Receive = runRoute(rest)
}

/**
 * REST Service
 */
trait RestService extends HttpService with SLF4JLogging {

  val customerService = new CustomerDAO
  val service= "study"
  val version= "v1"

  implicit val executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher

  implicit val liftJsonFormats: Formats = new Formats {
    val dateFormat: DateFormat = new DateFormat {
      val sdf = new SimpleDateFormat("yyyy-MM-dd")

      def parse(s: String): Option[Date] = try {
        Some(sdf.parse(s))
      } catch {
        case e: Exception => None
      }

      def format(d: Date): String = sdf.format(d)
    }
  }

  implicit val string2Date: Deserializer[String, Date] = new FromStringDeserializer[Date] {
    def apply(value: String): Either[MalformedContent, Date] = {
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      try Right(sdf.parse(value))
      catch {
        case e: ParseException =>
          Left(MalformedContent("'%s' is not a valid Date value" format (value), e))
      }
    }
  }

  implicit val customRejectionHandler: RejectionHandler = RejectionHandler {
    case rejections => mapHttpResponse {
      response =>
        response.withEntity(HttpEntity(ContentType(MediaTypes.`application/json`),
          write(Map("error" -> response.entity.asString))))
    } {
      RejectionHandler.Default(rejections)
    }
  }

  val rest: Route = respondWithMediaType(MediaTypes.`application/json`) {
    path(service / version / "testing") {
      get {
        complete {
          implicit val formats: DefaultFormats.type = DefaultFormats
          <h1>API working</h1>
        }
      }
    }
//    path(service / version / "customer") {
//      post {
//        entity(Unmarshaller(MediaTypes.`application/json`) {
//          case httpEntity: HttpEntity =>
//            read[Customer](httpEntity.asString(HttpCharsets.`UTF-8`))
//        }) {
//          customer: Customer =>
//            ctx: RequestContext =>
//              handleRequest(ctx, StatusCodes.Created) {
//                log.debug("Creating customer: %s".format(customer))
//                customerService.create(customer)
//              }
//        }
//      } ~
//      get {
//        parameters('firstName.as[String] ?, 'lastName.as[String] ?, 'birthday.as[LocalDate] ?)
//          .as(Customer) {
//          searchParameters: Custo => {
//            ctx: RequestContext =>
//              handleRequest(ctx) {
//                log.debug("Searching for customers with parameters: %s".format(searchParameters))
//                customerService.search(searchParameters)
//              }
//          }
//        }
//      } ~
//    }
//      path(service / version / "customer" / LongNumber) {
//        customerId =>
//          put {
//            entity(Unmarshaller(MediaTypes.`application/json`) {
//              case httpEntity: HttpEntity =>
//                read[Customer](httpEntity.asString(HttpCharsets.`UTF-8`))
//            }) {
//              customer: Customer =>
//                ctx: RequestContext =>
//                  handleRequest(ctx) {
//                    log.debug("Updating customer with id %d: %s".format(customerId, customer))
//                    customerService.update(customerId, customer)
//                  }
//            }
//          } ~
//          delete {
//            ctx: RequestContext =>
//              handleRequest(ctx) {
//                log.debug("Deleting customer with id %d".format(customerId))
//                customerService.delete(customerId)
//              }
//          } ~
//          get {
//            ctx: RequestContext =>
//              handleRequest(ctx) {
//                log.debug("Retrieving customer with id %d".format(customerId))
//                customerService.get(customerId)
//              }
//          } ~
//      }
  }

  /**
   * Handles an incoming request and create valid response for it.
   *
   * @param ctx         request context
   * @param successCode HTTP Status code for success
   * @param action      action to perform
   */
  protected def handleRequest(ctx: RequestContext, successCode: StatusCode = StatusCodes.OK)(action: => Either[Failure, _]) {
    action match {
      case Right(result: Object) =>
        ctx.complete(successCode, write(result))
      case Left(error: Failure) =>
        ctx.complete(error.getStatusCode, net.liftweb.json.Serialization.write(Map("error" -> error.message)))
      case _ =>
        ctx.complete(StatusCodes.InternalServerError)
    }
  }
}