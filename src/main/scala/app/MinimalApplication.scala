package app
import scalatags.Text.all._
import collection.mutable.Buffer

object MinimalApplication extends cask.MainRoutes{

 val Alusta = new Alusta

  @cask.staticFiles("/kuvia")
  def staticFileRoute() = "kuvia"


  @cask.get("/")
  def hello() = {
    html(
      head(if (Alusta.kirjautunutKäyttäjä.isDefined) {
          p("Logged in as user: " + Alusta.kirjautunutKäyttäjä.get.username + ". ",
              a(href := "/kirjauduulos","Log out")
          )
        } else {
          p("You are not logged in.  ",
              a(href := "/login","Log in"),
          p("If you haven't already created an user, ",
              a(href := "/signin", "create it now.")
          )
          )
      },
        meta(charset :="UTF-8"),
          link(
          rel := "stylesheet",
          href := "/kuvia/tyyli.css"
        ),
      body(
       h2("Welcome to Fa$Ebay"),
        img(src := "/kuvia/ebay.png", cls := "ebay"),
        if (Alusta.kirjautunutKäyttäjä.isDefined) {
          p("Create a ",
              a(href := "/newauction", "new auction")
          )
        } else {},
        p("All auctions:")
      )
      )
    )
  }

  @cask.get("/kirjauduulos")
  def kirjauduulos() = {
    Alusta.kirjauduUlos()
    cask.Redirect("/")
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
          div(cls := "container")(
          h1("Signin Page"),
          hr,
          form(action:= "/AlustaLuoKayttaja", method := "post")(
            div(
              input(name := "userName", `type` := "text", placeholder := "Username", width := "50%")
            ),
            div(
              input(name := "password", `type` := "password", placeholder := "Password", width := "50%")
            ),
            input(`type` := "submit", width := "20%")
          )
          )
        )
      )
    }

    @cask.postForm("AlustaLuoKayttaja")
    def luoKayttaja(userName: String, password: String) = {
      Alusta.luoKäyttäjä(userName, password)
      cask.Redirect("/")
    }


  @cask.postForm("/kirjautusisaan")
  def login(username: String, password: String) = {
    if (Alusta.kirjaudu(username,password)) {
      cask.Redirect("/")
    } else {
      cask.Redirect("/loginfailed")
    }
  }

  @cask.get("/loginfailed")
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
           input(name:= "password",`type` := "text", placeholder := "Pasword", width := "20%")
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
             input(name:= "password",`type` := "text", placeholder := "Pasword", width := "20%")
            ),
        input(`type` := "submit", width := "20%")
      )
    )
  )
  }

    @cask.get("/newauction")
  def uusiHuutokauppa() = {
      html(
        head(
          link(
            rel := "stylesheet",
            href := "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          )
        ),
        body(
          div(cls := "container")(
          h1("Create a new auciton"),
          hr,
          form(action:= "/luohuutokauppa", method := "post")(
            div(
              input(name := "nimi", `type` := "text", placeholder := "Name of the selled goods", width := "50%")
            ),
            div(
              input(name := "aloitushinta", `type` := "Int", placeholder := "Starting Price", width := "50%")
            ),
            div(
              input(name := "suoraosto", `type` := "Int", placeholder := "Direct sell. Put as 0 incase you don't want direct sell", width := "50%")
            ),
            input(`type` := "submit", width := "20%")
          )
          )
        )
      )
    }

    @cask.postForm("/luohuutokauppa")
    def luoHuutokauppa(nimi: String, aloitushinta: Int, suoraosto: Int)= {
      Alusta.luoUusiHuutokauppa(nimi, aloitushinta, suoraosto)
      cask.Redirect("/")
    }

 initialize()

}

