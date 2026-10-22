const fs = require('fs/promises')

const leerTodo = async () => {
    try {
        const a = await fs.readFile('a.txt', 'utf8')
        const b = await fs.readFile('b.txt', 'utf8')
        const c = await fs.readFile('c.txt', 'utf8')
        const todo = await Promise.all([fs.readFile('a.txt', 'utf8'), fs.readFile('b.txt', 'utf8'), fs.readFile('c.txt', 'utf8')])
        fs.writeFile("todo.txt", todo)

    } catch (err) {
        console.error('Error:', err.message)
    }
}

leerTodo();