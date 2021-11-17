package app
import scalatags.Text.all._
import collection.mutable.Buffer

object MinimalApplication extends cask.MainRoutes{


  @cask.get("/")
  def hello() = {
    html(
      head("NICE TO SEE YOU"),
      body(
       h1("Welcome to our JEWELRY SHOP"),
      p("We sell  jewelry at a good price and our prices can be found when you inject / Prices")

    )
      , form(action:= "/", method := "pos")(
        select(id:= "korut-valitse", name:="korut"),
          option("valitse ", value:="")),
      option("please choose")
      )


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

