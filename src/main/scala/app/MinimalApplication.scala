package app
import scalatags.Text.all._
import collection.mutable.Buffer

object MinimalApplication extends cask.MainRoutes{

 val Alusta = new Alusta


  @cask.get("/")
  def hello() = {
    html(
      head(if (Alusta.kirjautunutKäyttäjä.isDefined) {
          p("User: " + Alusta.kirjautunutKäyttäjä.get.username,
              a(href := "/kirjauduulos","Kirjaudu ulos")
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


  @cask.postForm("/kirjautusisaan")
  def login(username: String, salansa: String) = {
    if (Alusta.kirjaudu(username,salansa)) {
      cask.Redirect("/")
    } else {
      cask.Redirect("/loginfailed")
    }
  }

  @cask.get("/loginfaileds")
  def LoginFailed() = {
      html(
      head("Login Page"),
      body(
    hr,
        p("This account doesn't exists"),
        form( action := "/kirjautusisaan", method := "post")(
          div(
            input(name:="username", `type` := "text", placeholder := "Username", width := "20%"),

          ),

        div(
     input(name:= "pasword",`type` := "text", placeholder := "Pasword", width := "20%")
        ),
     input(`type` := "submit", width := "20%")
      )
      )
      )

  }


   @cask.get("/login")
  def Login() = {
      html(
      head("Login Page"),
      body(
    hr,
        form( action := "/kirjautusisaan", method := "post")(
          div(
            input(name:="username", `type` := "text", placeholder := "Username", width := "20%"),

          ),

        div(
     input(name:= "pasword",`type` := "text", placeholder := "Pasword", width := "20%")
        ),
     input(`type` := "submit", width := "20%")
      )
      )
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

