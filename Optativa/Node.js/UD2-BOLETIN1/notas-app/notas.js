function media(lista) {
  let suma = 0;
  lista.forEach((alumno) => {
    suma += alumno.nota;
  });
  return suma / lista.length;
}

function aprobados(lista) {
  return lista.filter((alumno) => alumno.nota >= 5);
}

function mejor(lista) {
  let mejorAlumno = lista[0];
  lista.forEach((alumno) => {
    if (alumno.nota > mejorAlumno.nota) mejorAlumno = alumno;
  });
  return mejorAlumno;
}

module.exports = { media, aprobados, mejor };
