const fs = require('fs')
console.log('A')
setTimeout(() => console.log('B'), 0)
fs.readFile("no-existe.txt", 'utf8',)
.then(console.log('C'))
.catch(err => console.log(err))

const texto = fs.readFileSync(__filename, 'utf8')
console.log('D', texto.length > 0)