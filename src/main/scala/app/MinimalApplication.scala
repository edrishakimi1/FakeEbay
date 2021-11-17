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

  @cask.get("/signin")
  def signIn() = {
    html(
      head(
        link(
          rel := "stylesheet",
          href := "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        )
      ),
      body(
        h1("Signin Page"),
        hr,
        form(action:= "/AlustaLuoKayttaja", method := "post")(
          div(
            input(name := "userName", `type` := "text", placeholder := "Username", width := "20%")
          ),
          div(
            input(name := "password", `type` := "password", placeholder := "Password", width := "20%")
          ),
          input(`type` := "submit", width := "10%")
        )
      )
    )
  }

  @cask.postForm("AlustaLuoKayttaja")
  def luoKayttaja(userName: String, password: String) = {
    Alusta.luoKäyttäjä(userName, password)
    cask.Redirect("/")
  }

 initialize()

}

