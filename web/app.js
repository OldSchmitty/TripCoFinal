const Server = require('./server.js')
const port = (process.env.PORT || 9350)
const app = Server.app()

app.listen(port)
console.log(`Listening at http://localhost:${port}`)
