package app
import com.roundeights.hasher.{Digest, Hasher}

import scala.collection.mutable._
import scala.language.postfixOps

class User(val username: String, spassword: String) {
  val password = Hasher(spassword).sha256
}

class Alusta {
  val kaikkiYksittäiset = Buffer[Yksittäinen]()
  private val käyttäjät = Buffer[User]()
  var kirjautunutKäyttäjä: Option[User] = None

  def luoKäyttäjä(username: String, pass: String) = {
    val uusi = new User(username, pass)
    käyttäjät += uusi
  }
  def käyttäjäSalasanaMap: Map[String, Digest] = {
    var mappi = Map[String, Digest]()
    for (k <- käyttäjät) {
      mappi += k.username -> k.password
    }
    mappi
  }

  def kirjaudu(hUsername: String, password: String): Boolean = {
    if (käyttäjäSalasanaMap.get(hUsername).map(_.hex) == Some(Hasher(password).sha256.hex)) {
      kirjautunutKäyttäjä = käyttäjät.find(_.username==hUsername)
      true
    } else {
      false
    }
  }
  def kirjauduUlos() = {kirjautunutKäyttäjä = None}

  def luoUusiHuutokauppa(nimi: String, aloitushinta: Int, minNosto: Int, suoraOsto: Int) = {
    val uusiHuutokauppa = new Huutokauppa(nimi, aloitushinta, minNosto, suoraOsto)
    kaikkiYksittäiset += uusiHuutokauppa
  }

  def luoUusiSuoraosto(nimi: String, hinta: Int) = {
    val uusiSuoraosto = new suoraMyynti(nimi, hinta)
  }
}
sealed abstract class Yksittäinen(val nimi: String) extends Alusta {
  var currentPrice: Int
  val luoja = kirjautunutKäyttäjä.get.username
  var currentBuyer: Option[User] = None
  var isExpired = false
}

class Huutokauppa(nimi: String, aloitushinta: Int, minNosto: Int, val suoraOsto: Int) extends Yksittäinen(nimi) {
  var currentPrice = aloitushinta
  if (!isExpired) {
    def nosta(korota: Int): Boolean = {
      currentBuyer match {
        case Some(ostaja) => {
          if (korota >= currentPrice + minNosto) {
            currentBuyer = kirjautunutKäyttäjä
            currentPrice = korota
            true
          } else {
            false
          }
        }
        case None => {
          if (korota >= currentPrice) {
            currentBuyer = kirjautunutKäyttäjä
            currentPrice = korota
            true
          } else {
            false
          }
        }
      }
    }

    if (suoraOsto != 0) {
      def osta() = {
        currentBuyer = kirjautunutKäyttäjä
        isExpired = true
        true
      }
    }
  }
}

class suoraMyynti(nimi: String, hinta: Int) extends Yksittäinen(nimi) {
  var currentPrice: Int = hinta
  def osta() = {
    currentBuyer = kirjautunutKäyttäjä
    isExpired = true
  }
}