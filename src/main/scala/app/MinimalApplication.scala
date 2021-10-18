package app
import scalatags.Text.all._

object MinimalApplication extends cask.MainRoutes{

  @cask.get("/")
  def hello() = {
    html(
      head(),
      body(
        h1("Hello!"),
        p("World")
      )
    )    
  }

  initialize()
}
