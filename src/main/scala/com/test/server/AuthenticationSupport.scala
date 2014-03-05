package com.test.server

import org.scalatra.auth.strategy.{BasicAuthStrategy, BasicAuthSupport}
import org.scalatra.auth.{ScentrySupport, ScentryConfig}
import org.scalatra.ScalatraBase
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}


class OurBasicAuthStrategy(protected override val app: ScalatraBase, realm: String)
  extends BasicAuthStrategy[User](app, realm) {

  protected def validate(userName: String, password: String)(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    if (userName == "asdf" && password == "asdf") Some(User("scalatra"))
    else None
  }

  protected def getUserId(user: User)(implicit request: HttpServletRequest, response: HttpServletResponse): String = user.id
}

trait AuthenticationSupport extends ScentrySupport[User] with BasicAuthSupport[User] {
  self: ScalatraBase =>

  val realm = "Test Server Auth"

  protected def fromSession = { case id: String => User(id)  }
  protected def toSession   = { case usr: User => usr.id }

  protected val scentryConfig = new ScentryConfig {}.asInstanceOf[ScentryConfiguration]

  override protected def configureScentry = {
    scentry.unauthenticated {
      scentry.strategies("Basic").unauthenticated()
    }
  }

  override protected def registerAuthStrategies = {
    scentry.register("Basic", app => new OurBasicAuthStrategy(app, realm))
  }

}

case class User(id: String)