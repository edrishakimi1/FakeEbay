package app
import scalatags.Text.all._
import collection.mutable.Buffer

object MinimalApplication extends cask.MainRoutes{

 val Alusta = new Alusta


  @cask.get("/")
  def hello() = {
    html(
      head("Fa$Ebay"),
      body(
       h1("Welcome to Fa$Ebay"),
        if (Alusta.kirjautunutKäyttäjä.isDefined) {
          p("User: " + Alusta.kirjautunutKäyttäjä.get.username,
              a(href := "/kirjauduulos","Kirjaudu ulos")
          )
        } else {
          p("You are not logged in.  ",
              a(href := "/kirjaudusisään","Log in"),
          p("If you haven't already created an user, ",
              a(href := "/luouusi", "create it now.")
            )
            )
        }
      )
      , form(action:= "/", method := "pos")(
        select(id:= "korut-valitse", name:="korut"),
          option("valitse ", value:="")),
      option("please choose")
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

