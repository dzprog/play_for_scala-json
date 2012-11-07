package controllers

import play.api.mvc.{Action, Controller}
import models.Product
//import play.api.libs.json.Json
//import play.api.libs.json._

import play.api.libs.json._
import play.api.libs.json.JsNumber

object Products extends Controller {
  def list = Action {
    val productCodes = Product.findAll.map(_.ean)
    Ok(Json.toJson(productCodes))
  }
  
    /**
   * Formats a Product instance as JSON.
   */
  implicit object ProductWrites extends Writes[Product] {
    def writes(p: Product) = Json.toJson(
      Map(
        "ean" -> Json.toJson(p.ean),
        "name" -> Json.toJson(p.name),
        "description" -> Json.toJson(p.description)
      )
    )
  }
  
  def details(ean: Long) = Action {
    Product.findByEan(ean).map { product =>
      Ok(Json.toJson(product))
    }.getOrElse(NotFound)
  }
}