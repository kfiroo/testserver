package com.test.server

class TestServer extends TestserverStack {

  before("/") {
    if (!isAuthenticated) {
      basicAuth
    }
  }

  get("/") {
    <html>
      <body>
        <h1>Hello, {user.id}</h1>
        <a href="/app/logout">Logout</a>
      </body>
    </html>
  }

  get("/user/:username") {
    <html>
      <body>
        <h1>Hello, {params("username")}</h1>
        <a href="/app/logout">Logout</a>
      </body>
    </html>
  }

  get("/logout") {
    scentry.logout
    scentry.unauthenticated {
      scentry.strategies("Basic").unauthenticated()
    }
    <html>
      <body>
        <h1>Bye.</h1>
        <a href="/app/">Home</a>
      </body>
    </html>
  }

}
