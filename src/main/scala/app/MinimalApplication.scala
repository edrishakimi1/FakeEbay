package app
import scalatags.Text.all._
import collection.mutable.Buffer

object MinimalApplication extends cask.MainRoutes{

 val Alusta = new Alusta


  @cask.get("/")
  def hello() = {
    html(
      head(if (Alusta.kirjautunutKäyttäjä.isDefined) {
          p("User: " + Alusta.kirjautunutKäyttäjä.get.username + "   ",
              a(href := "/kirjauduulos","Log out")
          )
        } else {
          p("You are not logged in.  ",
              a(href := "/login","Log in"),
          p("If you haven't already created an user, ",
              a(href := "/signin", "create it now.")
          )
          )
      }
        ),
      body(
       h1("Welcome to Fa$Ebay"),


      )
    )
  }

  @cask.get("/kirjauduulos")
  def kirjauduulos() = {
    Alusta.kirjauduUlos()
    cask.Redirect("/")
  }

  @cask.get("/Prices")
  def korujentiedot() = {
    val tiedot = Buffer("gold jewelry 800 Euro","silver jewelry 200 Euro","diamond jewelry 10000 Euro" )
    val listaa = for(korut <- tiedot) yield li(korut)
    html(
      head(),
      body(
       //div(cls := "container")(
          ul(
            listaa.toSeq

    )
      )
    )
  }

 initialize()

}

