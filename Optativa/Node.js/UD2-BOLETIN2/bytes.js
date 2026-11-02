const texto = process.argv.slice(2).join(" ");

if (!texto) {
  console.error("Uso: node bytes.js <texto>");
  process.exit(1);
}

console.log("Texto      :", texto);
console.log("Caracteres :", texto.length);
console.log("Bytes      :", Buffer.byteLength(texto));
console.log("Buffer     :", Buffer.from(texto));

const abc = Buffer.alloc(26);

for (let i = 0; i < abc.length; i++) {
  abc[i] = 65 + i;
}

console.log("Abecedario :", abc.toString("ascii"));
